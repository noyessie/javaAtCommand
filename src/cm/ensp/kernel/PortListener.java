/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.ensp.kernel;

import cm.ensp.command.CommandExecutionException;
import java.util.EventListener;

/**
 *
 * @author Hubert
 */
public interface PortListener extends EventListener{
    
    /**
     * Notyfie la fin d'une commande
     * @param e
     */
    void commandEnd(PortEvent e);
}
