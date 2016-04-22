/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.ensp.command.tools;

/**
 * Adresse spécifer dans la trame pdu ( destinataire ou recepteur )
 * @author Hubert
 */
public class Adresse extends NumeroGSM{
    
    public Adresse(String ad){
        super(ad);
    }

    @Override
    public int length() {
        if(this.getHexNumber() != null){
            return this.getHexNumber().length*2;
        }
        return 0;
    }
}
