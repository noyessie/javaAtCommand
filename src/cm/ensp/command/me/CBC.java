/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.ensp.command.me;

import cm.ensp.command.ATCommand;
import cm.ensp.kernel.PortCom;

/**
 * Etat de charge de batterie
 * @author Hubert
 */
public class CBC extends ATCommand{

    @Override
    public void executeQuery(PortCom port) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void format() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
