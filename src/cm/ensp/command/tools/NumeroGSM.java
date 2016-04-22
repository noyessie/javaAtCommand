
package cm.ensp.command.tools;

/**
 * Permet de coder decoder les numéro au format gsm
 * @author Hubert
 */
public abstract class NumeroGSM {
    
    //paramètres en mode texte
    /**
     * representer la taille 
     */
    private int length = 0;

    /**
     * répresenter le numero
     */
    private String number = "";
    
    
    //paramètre en mode hexadécimale
    /**
     * type de nombre
     */
    private byte typeNumber;
    /**
     * répresenter le numéro sous forme hexadecimale
     */
    private byte[] hexNumber;
    /**
     * reprensenter la trame du numéro 
     */
    private byte[] trame;
    
    
    
    //différents types de nombres
    /**
     * Pour un numéro de format non spécifié
     */
    public static byte SPECIFIE = 0;
    /**
     * Format Numéro international
     */
    public static byte INTERNATIONAL = 16;
    /**
     * Numéro National
     */
    public static byte NATIONAL = 32;
    
    /**
     * Numéro Spécifique au réseaux
     */
    public static byte RESEAU = 48;
    /**
     * Numéro d'abonné
     */
    public static byte ABONNE = 64;
    /**
     * codification en accord avec la norme GSM TS 03.38 alphabet par défaut sur 7 bits
     * 
     */
    public static byte CODIFICATION = 80;
    /**
     * Numéro abrégé
     */
    public static byte ABREGER = 96;
    
    // numbering plan identification
    
    /**
     * ISDN/téléphone numbering plan
     */
    public static byte TELEPHONE = 1;
    /**
     * Data numbering plan (X.121)
     */
    public static byte DATA = 3;
    /**
     * Telex numbering plan
     */
    public static byte TELEX = 4;
    /**
     * private numbering plan
     */
    public static byte PRIVATE = 9;
    /**
     * ERMES numbering plan
     */
    public static byte ERMES = 10;
    
    /**
     * Dans ce cas c'est le numéro du ta qui est utlisé
     */
    public NumeroGSM(){
        this.trame = new byte[1];
        this.trame[0] = (byte)0x00;
    }
    /**
     * constructeur
     * @param numero numéro à codifier en string
     */
    public NumeroGSM(String numero){
        this.number = numero;
        this.typeNumber = (byte) 0x91; //numéro international
        init();
    }
    public NumeroGSM(String numero , byte typeNumber){
        this.number = numero;
        this.typeNumber = typeNumber;
        init();
        
    }
    
    private void init(){
        //conversion du numéro
        this.hexNumber = SMSTools.convertDialNumber(this.number);
        //construction de la trame pdu
        int taille = this.length() + 2;
        this.trame = new byte[taille];
        trame[0] = (byte)taille;
        trame[1] = this.typeNumber;
        
        for(int i=2 ; i< taille ; i++){
            trame[i] = this.hexNumber[i - 2];
        }
    }
    
    public abstract int length();
    
    public static String decode(String trame){
        return SMSTools.decodeAddressField(trame);
    }
    
    
    public byte[] getTrame(){
        return this.trame;
    }
    
    
    
    public byte[] getHexNumber(){
        return this.hexNumber;
    }
    
    public String getNumber(){
        return this.number;
    }
    
    public void setNumber(String numero){
        this.number = numero;
        init();
    }
    
    public void setNumberType(int typeNumber){
        this.typeNumber = (byte)typeNumber;
        init();
    }
    
    
}
