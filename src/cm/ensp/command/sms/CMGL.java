package cm.ensp.command.sms;

import cm.ensp.command.ATCommand;

/**
 * Liste les SMS stockés en mémoire
 * @author Hubert
 */
public class CMGL extends ATCommand{
    /**
     * Message reçu non lu
     */
    public static final int REC_UNREAD = 0 ; //Message reçcu non lu
    /**
     * Message reçu lu
     */
    public static final int REC_READ = 1;//Message reçu lu
    /**
     * Message stocké non envoyé
     */
    public static final int STO_UNSENT = 2; //Message stocké non envoyé
    /**
     * Message stocké déja envoyé
     */
    public static final int STO_SENT = 3; //Message stocké déjà envoyé
    /**
     * Tous les messages
     */
    public static final int ALL = 4; //Tous les messages
    /**
     * listes des index d'un sms
     */
    public static final String INDEX = "INDEX";
    /**
     * listes des trames pdu correspondant
     */
    public static final String PDU = "PDU";
    
    
    /**
     * Cette commande retourner tous les messages du TE
     */
    public CMGL(){
        super();
        this.name = "CMGL";
        this.statement = "AT+CMGL=" + this.ALL;
    }
    /**
     * Constructeur permettant d'interroger le TE sur la comande
     * @param state 
     */
    public CMGL(String state){
        this.name = "CMGL";
        this.state = state;
        this.statement = "AT+CMGL"+this.state;
        if(this.state.equals(this.WRITE_COMMAND))
            this.statement = "AT+CMGL=" + this.ALL;
    }
    
    /**
     * Constructeur permettant de spécifier le type de message 
     * @param typeMessage 
     */
    public CMGL(int typeMessage){
        this.name = "CMGL";
        this.statement = "AT+CMGL="+typeMessage;
    }
    

    @Override
    public void format() {
        String s = this.state;
        System.out.println("format");
        if(s.equals(this.WRITE_COMMAND)){
            
            String tab[] = this.getStatementResult().replaceFirst("\n", "").split("\n");
            
            String index = "";
            String pdu = "";
            System.out.println("boucle : " + tab.length);
            for(int i=0 ; i<tab.length ; i+=2){
                  //  maintenat on a les chaines suivantes:
                 // +CMGL: 1,1,,159 pour les indices paires ici c'est le premier chiffre qui indique l'index du sms dans la puce
                 // trame pdu pour les indices impaires
                
                System.out.println("traitement de la ligne i = " + i);
                index += tab[i].split(":")[1].split(",")[0];
                pdu += tab[i+1];
                
                if(i < tab.length -1){
                    index += ";";
                    pdu += ";";
                }
            }
            this.data.put(INDEX, index);
            this.data.put(PDU, pdu);
            
        }
    }
    
}
