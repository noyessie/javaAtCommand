/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.ensp.kernel;

import com.sun.comm.Win32Driver;
import java.io.*;
import java.util.ArrayList;
import javax.comm.*;
import javax.swing.event.EventListenerList;

/**
 *
 * @author Hubert
 */
public class PortCom implements SerialPortEventListener{

    /**
     * Representer le port serie qui séra utilisé
     */
    private SerialPort comPort;
    /**
     * Indentifiant du port
     */
    private CommPortIdentifier portId;
    
    //flux ouvert sur le port serie
    /**
     * flux de sortie
     */
    private OutputStream os;
    
    /**
     * flux de sortie bufferisé
     */
    private BufferedWriter bos;
    /**
     * flux d'entrée
     */
    private InputStream is;
    
    //Paramètrage du port série
    /**
     * paramètres dataBits du port série
     */
    private int dataBits = SerialPort.DATABITS_8;
    /**
     * paramètres stopBits du port série
     */
    private int stopBits = SerialPort.STOPBITS_1;
    /**
     * paramètres baudRate du port série
     */
    private int baudRate = 9600;
    /**
     * paramètres parity du port série
     */
    private int parity = SerialPort.PARITY_NONE;
    /**
     * paramètres flowControl du port série
     */
    private int flowControl = SerialPort.FLOWCONTROL_NONE;
    /**
     * Nom qui séra données au port série
     */
    private String portName = "";
    
    
    //gestion des flux
    /**
     * contient les resultats de la dernière commande saisie
     */
    private String output = "";
    /**
     * contient les données lue directement dans le buffer
     */
    private String buf = "";
    /**
     * Contient un historique de toutes les commandes saisie
     */
    private ArrayList<String> history = new ArrayList();
    /**
     * carractère utiliser par le prots (CARRIER DETECT)
     */
    public static final char CR = 13;
    /**
     * carractère utiliser par le prots LINE FEED
     */
    public static final char LF = 10;
    /**
     * chaine de debut d'une commande
     */
    public static final String OK_END = LF+"OK";
    /**
     * chaine de fin d'une commande
     */
    public static final String ERROR_END = "ERROR";
    
    /**
     * indique le debut d'une commane
     */
    public static final String BEGIN = "AT";

    
    /**
     * listes des écouteur du port
     */
    private final EventListenerList listeners = new EventListenerList();

    /**
     * disponibilité du port
     */
    private boolean portReady = true;
    
    
    
    /**
     * Constructeur de la classe
     * @param name le nom qui séra donnée au port série
     */
    public PortCom(String name) {
            Win32Driver w32Driver = new Win32Driver();
            w32Driver.initialize();
            this.portName = name;
    }
    /**
     * Ouverture d'un port 
     * @param Portname nom du port à ouvrir
     * @throws PortComException
     */
    public void openPort(String PortName) throws PortComException{
        try {
                portId = CommPortIdentifier.getPortIdentifier(PortName);
                ComControl();
        } catch (Exception e) {
            throw new PortComException("Erreur lors de l'ouverture du port : " + portName);
        }
    }
    
    
    /**
     * Permet d'initialiser le port.
     * @throws PortComException
     */
    private synchronized void ComControl() throws PortComException{
        try{
            // ouverture du portt
            comPort = (SerialPort) portId.open( portName, 100000);
            comPort.addEventListener(null);
            comPort.setSerialPortParams(baudRate, dataBits, stopBits, parity);
            comPort.setFlowControlMode(flowControl);

            // on recup�re le flux d'entr�e et le flux de sortie li�es au port
            os = comPort.getOutputStream();
            bos = new BufferedWriter(new OutputStreamWriter(os));
            is = comPort.getInputStream();


            // permet de dire au prot de spécifié quand des données sont valides
            comPort.notifyOnDataAvailable(true);
            comPort.notifyOnCarrierDetect(true);
            
            //ajout de l'eventListener
            comPort.addEventListener(this);

        } catch (Exception ex) {
            throw new PortComException("Erreur d'ouverture du port \n\t Message d'érreur : " + ex.getMessage());
        }

    }
    /**
     * permet la fermuture du port com
     * @throws PortComException
     */
    public void closePort() throws PortComException{
        try{
            comPort.close();
        }catch(Exception e){
            throw new PortComException("Fermeture du port com impossible ");
        }
    }
    @Override
    public void serialEvent(SerialPortEvent ev){
        /**
         * si le contenue de la chaine s commencer par AT et se terminer par 
         * <LF>OK<LF> ou <LF>ERROR[code possible d'erreur]<LF>
         * alors il s'agit bien du debut et de la fin d'une commande.
         * on peut placer alors le contenu dans output et dans history
         */

        try{
            
            buf += this.read();
            //System.out.println("action" + buf);
            //System.out.println( buf.substring(0, 2).toUpperCase().equals(BEGIN) + " " + buf.toUpperCase().contains(OK_END));
            if(buf.substring(0, 2).toUpperCase().equals(BEGIN)
                    && (buf.toUpperCase().contains(ERROR_END)
                    || buf.toUpperCase().contains(OK_END))){
                this.portReady = true;//on libère le port
                output = buf;
                history.add(output); 
                buf = "";
                fireCommandEnd(output , history);
            } 
        } catch (PortComException ex) {
            //pb
        }catch(Exception ex ){
            //unknow Exception
        }  
    }
    
    /**
     * récuperer la dernière commande saisie et son resultat
     */
    public String get(){
        return this.output;
    }
    
    /**
     * récuperer l'historique des commandes saisies
     */
    public ArrayList<String> getHistory(){
        return this.history;
    }
    
    /**
     * Permet la lecture du flux de sortie du prot com
     * @return le contenue disponnible dans le port com
     * @throws PortComException 
     */
    public synchronized String read() throws PortComException{
        try {
            byte[] receive = new byte[comPort.getInputBufferSize()];
            int n = is.read(receive);
            String s = new String(receive);
            s = s.substring(0 , n);
            return s;
        } catch (IOException ex) {
            throw new PortComException("Erreur de lecture dans le port : \n\tMessage d'erreur : " + ex.getMessage());
        }
    }
    
    /**
     * Permet d'écrire la chaine de carratère dans le port. le port est automatiquement
     * bloqué après l'envoie de la commande et se debloque une fois celle ci terminée
     * @param str la chaine à écrire
     * @throws PortComException 
     */
    public void write(String str) throws PortComException{
        if(this.portReady){
            try {
                this.portReady = false;
                bos.write(str);
                bos.flush();
            } catch (Exception ex) {
                throw new PortComException("Impossible d'écrire les données sur le prot : " + str);
            }
        }else{
            throw new PortComException("Le port est occupée");
        }
    }
    
    /**
     * Permet d'écrire la chaine de caractère dans le port. le blockage du port
     * est optionnel
     * @param str la chaine à écrire
     * @param lock <ul><li>true : le port ne sera pas blocké </li><li>false : Le port sera blockée</li></ul>
     * @throws PortComException 
     */
    public void write(String str , boolean lock) throws PortComException{
        if(this.portReady){
            try {
                this.portReady = lock;
                os.write((byte[]) str.getBytes());
            } catch (Exception ex) {
                throw new PortComException("Impossible d'écrire les données sur le prot : " + str);
            }
        }else{
            throw new PortComException("Le port est occupée");
        }
    }
    
    
    
    /**
     * Permet de savoir si le port est disponible pour l'envoie de commandes
     * @return l'état du port <ul><li>true : le port est disponible </li><li>false: le port n'est pas disponnible</li></ul>
     */
    public boolean isReady(){
        return this.portReady;
    }
    
    /**
     * permet de changer le paramètre dataBits du port
     * @param data
     * @throws PortComException 
     */
    public void setDataBits(int data) throws PortComException {
        switch (data) {
        case 5:
                dataBits = SerialPort.DATABITS_5;
                break;
        case 6:
                dataBits = SerialPort.DATABITS_6;
                break;
        case 7:
                dataBits = SerialPort.DATABITS_7;
                break;
        case 8:
                dataBits = SerialPort.DATABITS_8;
                break;
        default:
                System.err.println("erreur de dataBits");

        }
        setPortParameter(baudRate, dataBits, stopBits, parity);
    }
    
    /**
     * permet de chager le paramètre stopBits du port
     * @param stopbits
     * @throws PortComException 
     */
    public void setStopBits(int stopbits) throws PortComException {
        switch (stopBits) {
        case 1:
                stopBits = SerialPort.STOPBITS_1;
                break;
        case 2:
                stopBits = SerialPort.STOPBITS_2;
                break;
        default:
                stopBits = SerialPort.STOPBITS_1_5;
                break;
        }
        setPortParameter(baudRate, dataBits, stopBits, parity);
    }
    
    /**
     * permet de chager le paramètre setParity du port
     * @param parite
     * @throws PortComException 
     */
    public void setParity(String parite) throws PortComException {
        if (parite.equalsIgnoreCase("even")) {
                parity = SerialPort.PARITY_EVEN;
        } else if (parite.equalsIgnoreCase("None")) {
                parity = SerialPort.PARITY_NONE;
        } else {
                parity = SerialPort.PARITY_ODD;
        }
        setPortParameter(baudRate, dataBits, stopBits, parity);
    }
    
    /**
     * permet de chager le paramètre flowControl du port
     * @param flowC 
     */
    public void setflowControl(String flowC) {

    }
    
    /**
     * permet de chager le paramètre BaudRate du port
     * @param baud
     * @throws PortComException 
     */
    public void setBaudRate(int baud) throws PortComException {
        baudRate = baud;
        setPortParameter(baudRate, dataBits, stopBits, parity);
    }
    
    /**
     * permet de chager les paramètre du ports
     * @param br
     * @param db
     * @param sb
     * @param p
     * @throws PortComException 
     */
    private void setPortParameter(int br , int db , int sb , int p) throws PortComException{
        try {
            comPort.setSerialPortParams(br, db, sb, p);
        } catch (UnsupportedCommOperationException ex) {
            throw new PortComException("Impossible de changer les paramètres du port :\n\t Message Error : " + ex.getMessage());
        }
    }
    
    /**
     * ajout d'un écouteur
     * @param listener 
     */
    public void addPortListener(PortListener listener){
        this.listeners.add(PortListener.class, listener);
    }
    /**
     * suppresion d'un écouteur
     * @param listener 
     */
    public void removeListener(PortListener listener){
        this.listeners.add(PortListener.class, listener);
    }
    
    /**
     * Retourner la listes des élèments qui écouter le port
     * @return 
     */
    public PortListener[] getPortListener(){
        return listeners.getListeners(PortListener.class);
    }
    
    public int getNumberPortListener(){
        return listeners.getListenerCount(PortListener.class);
    }
    
    /**
     * notify aux écouteurs qu'une commande vient de se termeiner
     * @param output
     * @param history 
     */
    public void fireCommandEnd(String output , ArrayList<String> history){
        PortEvent event = null;
        
        for(PortListener listener : getPortListener()){
            if(event == null){
                event = new PortEvent(output ,  history);
            }
            listener.commandEnd(event);
        }
        
    }
    
}
