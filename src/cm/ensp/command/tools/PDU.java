/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.ensp.command.tools;

/**
 * Cette classe contient la partie de la trame qui concerne la trame pdu
 * @author Hubert
 */
public class PDU {
    
    /**
     * parametres de la trame pdu
     * <ul>
     *  <li>RP</li>
     *  <li>UDHI</li>
     *  <li>SRI</li>
     *  <li>MMS</li>
     *  <li>MTI</li>
     * </ul>
     */
    
    /**
     * le champs RP determiner s'il exister un chemin de réplit
     * <ul>
     *  <li>RP = 0 : il n'existe pas de chemin de repli</li>
     *  <li>RP = 1 : Il existe un chemin de repli</li>
     * </ul>
     * {@value cm.ensp.command.tools.PDU#RP}
     */
    protected static byte RP = 0;
    
    /**
     * decrit le message contenue dans le champs ud
     * <ul>
     *  <li>UDHI = 0 : le champ UD contient uniquement un message</li>
     *  <li>UDHI = 1 : Le champs UD contient un en-tête en plus du message</li>
     * </ul>
     * {@value cm.ensp.command.tools.PDU#UDHI}
     */
    protected static byte UDHI = 0;
    
    /**
     * indique si un rapport d'état doit être délivrè
     * <ul>
     *  <li>SRR = 0 : Aucun rapport d'état ne sera retourné au mobile</li>
     *  <li>SRR = 1 : Un rapport d'état sera retourné au mobile</li>
     * </ul>
     * * {@value cm.ensp.command.tools.PDU#SRR}
     */
    protected static byte SRR = 0;
    
    /**
     * Permet de spécifier si le champs VP de la trame contient
     * une date ou nom
     * <ul>
     *  <li>VPF = 0 : le champ VP n'existe pas</li>
     *  <li>VPF = 8 : le champ VP existe et est codé en entier relatif</li>
     *  <li>VPF = 12 : le champ VP exister et est code en semi-octect (absolue)</li>
     *  <li></li>
     *  <li></li>
     * 
     * </ul>
     */
    protected static byte VPF = 0;
    
    /**
     * <ul>
     *  <li> RD = 0 : des messages supplémentaires pour le 
     *        MS sont en attente dans le SMSC</li>
     * <li>RD = 1 : Pas e message supplémentaire en attente pour le MS dana le SMSC</li>
     * </ul>
     */
    protected static byte RD = 0;
    
    
    /**
     * indique le type de message
     * <ul>
     *  <li>MTI = 0 : SMS-DELIVER</li>
     *  <li></li>
     * </ul>
     */
    protected static byte MTI=0;
    
    
    private byte trame;
    
    public PDU(){
        init();
    }
    
    /**
     * permet de crée la trame pdu concidère
     */
    private void init(){
        trame = (byte) (RP | UDHI | SRR | VPF | RD | MTI);
    }
    
    /**
     * Permet de spécifier s'il y a un chemin de repli pour le message
     * @param y spécifier s'il y à un chemin de repli ou non
     */
    public void setCheminReplie(boolean y){
        if(y){
            PDU.RP = 64;
        }else{
            PDU.RP = 0;
        }
        init();
    }
    
    /**
     * Permet de spécifier si c'est un message uniquement ou bien un message avec entete
     * @param y spécifier si c'est un message uniquement ou bien un message avec entete
     */
    public void SimpleMessage(boolean y){
        if(y){
            PDU.UDHI = 32;
        }else{
            PDU.UDHI=1;
        }
        init();
    }
    
    /**
     * Permet de spécifier si un rapport d'état doit être retournée
     * @param y spécifier si un rapport d'état doit être retourner
     */
    public void deleveryReport(boolean y){
        if(y){
            PDU.SRR = 16;
        }else{
            PDU.SRR = 0;
        }
        init();
    }
    
    /**
     * Permet de spécifier si des messages supplémentaires pour le MS sont
     * en attentes dans le SMSC
     * @param y 
     */
    public void ContinueMessage(boolean y){
        if(y){
            PDU.RD = 4;
        }else{
            PDU.RD = 0;
        }
        init();
    }
    
    /**
     * Permet de spécifier si la trame pdu final contiendra un champs ou pas
     * 
     * @param VPF
     * le paramètre VPF est utiliser coe suit
     * <table>
     * <tr>
     *  <td> 0...143 </td>
     *  <td> (VP+1)x5minutes</td>
     * </tr>
     * <tr>
     *  <td> 144...167 </td>
     *  <td>12 heures + ((VPF-143)x30minutes)</td>
     * </tr>
     * <tr>
     *  <td> 168...196 </td>
     *  <td> (VPF-166)x1jour </td>
     * </tr>
     * <tr>
     *  <td> 197 ... 255 </td>
     *  <td> (VP-192) x 1 semaines </td>
     * </tr>
     * </table>
     */
    public void setVPF(int VPF){
        PDU.VPF = (byte) VPF;
        init();
    }
    
}
