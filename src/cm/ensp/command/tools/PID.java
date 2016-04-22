/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.ensp.command.tools;

/**
 * Le champs PID de la trame contient une réference sur le type de trame
 * @author Hubert
 */
public interface PID {
    
    /**
     * La trame est traitée comme un message court
     */
    public static byte MESSAGE_COURS = 0x00;
    /**
     * la trame est traité comme un telex
     */
    public static byte TELEX = 0x11;
    /**
     * La trame est traité comme un telefax de groupe 3
     */
    public static byte TELEFAX3 = 0x02;
    
    /**
     * La  trame est traité comme un telefax de groupe 4
     */
    public static byte TELEFAX4 = 0x03;
    /**
     * La trame est traitée comme un e-mail
     */
    public static byte MAIL = 0x12;
}
