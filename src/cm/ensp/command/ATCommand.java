/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.ensp.command;

import cm.ensp.kernel.PortCom;
import cm.ensp.kernel.PortComException;
import cm.ensp.kernel.PortEvent;
import cm.ensp.kernel.PortListener;
import java.util.HashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hubert
 */
public abstract class ATCommand implements PortListener{
    
    /**
     * Contient le nom de la commande à éxecuter
     */
    protected String name = "";
    
    /**
     * Permet de spécifier si l'on effectue une commande de test
     */
    public static String TEST_COMMAND = "=?";
    
    /**
     * Permet de tester l'état actuel de la commande
     */
    public static String GET_STATE = "?";
    
    /**
     * Permet l'écriture proprement dite de la commande
     */
    public static String WRITE_COMMAND = " ";
    
    /**
     * protected String state
     */
    protected String state = this.WRITE_COMMAND;
    
    /**
     * Contient la sortie de la commande
     */
    protected String out = "";
    
    /**
     * Conteint la commande executer
     */
    protected String statement = "";
    
    /**
     * Contient le résultat de la commande
     */
    protected String statementResult = "";
    
    /**
     * Contient le status final de la commande à savoir :
     * *<ul><li><ERROR</li>
     * *<li>OK</li></ul>
     */
    protected String statementStatus = "";
    
    /**
     * Permet de spécifier si l'éxecution de la commande s'est terminé sans problème
     */
    protected boolean error = false;
    
    /**
     * Code de l'erreur
     */
    private int errorCode = 0;
    
    /**
     * Contient les différents résultats de la commandes.
     * Les clés de ce HashMap sont fonctions de la commande éxecuté.
     * C'est donc un formatage de la sortie statementResult
     */
    protected HashMap<String , String> data = new HashMap<String , String>();
    
    /**
     * Object de vérou
     */
    protected Lock portLock = new ReentrantLock();
    /**
     * object de condition
     */
    protected Condition dataReady = portLock.newCondition();
    /**
     * indique la fin de la commande
     */
    private boolean end = false;
    /**
     * Permet d'executer une commande et formater le résultat.
     * @param  port le port vers lequels la commandes sera exécuté
     * @return retourner la sortie total de la commande
     */
    public void execute(PortCom port) throws CommandExecutionException{
        port.addPortListener(this);
        portLock.lock();
       try{
            this.executeQuery(port);
            while(!end){
                try {
                    dataReady.await();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            if(this.isError()){
                throw new CommandExecutionException(ErrorCode.getMessage(this.errorCode));
            }
            dataReady.signalAll();
       }finally{
           portLock.unlock();
       }
    }
    
    public void executeQuery(PortCom port){
        boolean flag = true;
        
        this.portLock.lock();
        try{
            while(flag){
                try {
                    port.write(this.statement + '\r');
                } catch (PortComException ex) {
                    flag = false;
                }
            }
        }finally{
            this.portLock.unlock();
        }
    }
    
    /**
     * Permet de formater le résultat de la commande généralement sous forme de String
     */
    abstract public void format();
    
    /**
     * retourner le paramètre spécifié par la clée. A noter que la lister de ces paramètres est donnée
     * par la méthode data
     * @param key
     * @return
     * @throws KeyException 
     */
    public String get(Object key) throws KeyException{
        if(this.data != null && this.data.containsKey(key)){
            return this.data.get(key);
        }
        throw new KeyException("clee inexistante");
    }
    
    /**
     * Permet d'obtenir uniquement le résultat de la commande
     * @return le resultat de la commande envoyer
     */
    public String getStatementResult(){
        return this.statementResult;
    }
    
    /**
     * Permet d'obténir la commande saisie
     * @return le resultat de la commande saisie
     */
    public String getStatement(){
        return this.statement;
    }
    
    /**
     * Permet d'obtenir le status avec lequel la commande s'est terminé
     * @return OK or ERROR
     */
    public String getStatementStatus(){
        return this.statementStatus;
    }
    
    /**
     * Permet d'otenir le nom de la commande saisie
     * @return le nom de la commande saisie
     */
    public String getCommandName(){
        return this.name;
    }
    
    /**
     * Permet d'obtenir la sortie standart liée à la commande
     * @return la sortie standart
     */
    public String getOutput(){
        return this.out;
    }
    
    /**
     * Permet de savoir s'il y à eu une erreur lors de l'éxecution d'une commande
     * Si oui ragarder le message d'erreur si disponible
     * @return retourner l'état d'éxecution de la commande
     */
    public boolean isError(){
        return this.error;
    }
    
    /**
     * Permet d'obtenir les clées disponibles d'une commades
     * @return 
     */
    public String[] getdataKeys(){
        int size = data.size();
        if(size > 0){
            String[] s = new String[size];
            data.keySet().toArray(s);
            return s;
        }
        return null;
    }
    
    /**
     * Evénement qui se déclenche à la fin de l'éxécution d'une commande
     * @param e Contient les données sur l'évènement qui s'est réaliser sur le prot
     */
    @Override
    public void commandEnd(PortEvent e) {
        this.out = e.getLastOutput();

        //l'attribut statement est utiliser dans les sous classes pour conserver la commander à executer
        //si on ne l'initialiser pas, nous aurrons des additions de commandes
        this.statement = "";
        String[] chaine = this.out.split(PortCom.CR + "");
        for(String s : chaine){
            if(s.contains(PortCom.BEGIN)){
                this.statement += s;
            }else if(s.contains(PortCom.ERROR_END)){
                this.statementStatus += s;
                this.error = true;
                //errorCode = Integer.valueOf(this.statementResult.split(":")[1]).intValue();
            }else if(s.contains(PortCom.OK_END)){
                this.statementStatus += s;
                this.error = false;
            }else{
                this.statementResult += s;
            }
        }
        
        this.format();
        portLock.lock();
        try{
            this.end = true;
            this.dataReady.signalAll();
        }finally{
            portLock.unlock();
        }
        
        
    }
    
    public String toString(){
        String ch = "";
        
        ch += "commande saisie         : " + this.statement+"\n";
        ch += "resultat de la commande : " + this.statementResult + "\n";
        ch += "status de l'éxécution   : " + this.statementStatus + "\n";
        
        return ch;
    }
    
}
