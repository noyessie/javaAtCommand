/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.ensp.test;

import cm.ensp.command.*;
import cm.ensp.command.sms.CMGD;
import cm.ensp.command.sms.CMGL;
import cm.ensp.command.tools.*;
import cm.ensp.command.tools.SCA;
import cm.ensp.kernel.PortCom;
import cm.ensp.kernel.PortComException;
import cm.ensp.kernel.PortEvent;
import cm.ensp.kernel.PortListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hubert
 */
public class Main {
    public boolean ready = true;
    private static int i=0;
    private static PortCom port = new PortCom("bonjour");
    static final private char cntrlZ = (char)26;
    
    public static void main(String[] args) throws IOException, InterruptedException{
        
        
        /*try {
            port.openPort("COM5");
        } catch (PortComException ex) {
            System.out.println(" "+ex.getMessage());
        }
        
        Command cmd = new Command(port , new CMGD(CMGD.TEST_COMMAND));
        
        
        for(int i=0 ; i<1 ; i++){
            try{
                //System.out.println("bonsoir");
                cmd.execute(new CMGL(CMGL.ALL)); 
                System.out.println("***********************************************************************\n");
                try {
                    System.out.println(cmd.getCommand().get(CMGL.INDEX));
                    System.out.println(cmd.getCommand().get(CMGL.PDU));
                } catch (KeyException ex) {
                    System.out.println("clée inexistante");
                }
                System.out.println("======================================================================\n");
            }catch(CommandExecutionException e){
                System.err.println("**********************************************************************\n");
                System.err.println("erreur lors de l'envoie de la commande : " + e.getMessage());
                System.err.println(cmd.getCommand().toString());
                System.err.println("======================================================================\n");
            }
        }
        
        
        try {
            port.closePort();
        } catch (PortComException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        
        NumeroGSM num = new SCA("2379900929");
        byte tr[] = num.getTrame();
        
        for(int i=0 ; i<tr.length ; i++){
            System.out.print(tr[i]);
        }
        
        System.out.println("\n" + SMSTools.convertCharArray2String(SMSTools.toHexString(tr)));
    }
    
}
