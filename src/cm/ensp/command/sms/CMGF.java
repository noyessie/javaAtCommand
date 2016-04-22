/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.ensp.command.sms;

import cm.ensp.command.ATCommand;
import cm.ensp.kernel.PortCom;

/**
 * Sélection du format du SMS (PDU ou TEXT)
 * @author Hubert
 */
public class CMGF extends ATCommand{

    @Override
    public void executeQuery(PortCom port) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void format() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
