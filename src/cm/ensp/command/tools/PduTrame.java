package cm.ensp.command.tools;

/**
 * Permet de constituer la trame pdu lors de l'envoie
 * d'un message
 * @author Hubert
 */
public class PduTrame {
    
    /**
     * Message réference
     * Laisser la valeur à zero pour laisser le mobile s'en occupe
     * {@value cm.ensp.command.tools.PduTrame#MR}
     */
    protected static byte MR = 0x00;
    
    /**
     * DCS Data Conding Schene
     * permet de spécifier les classes pour le champs UD
     * Pour le moment, il est fixé à  {@value cm.ensp.command.tools.PduTrame#DCS}
     */
    protected static byte DCS = 0x00;
    
}
