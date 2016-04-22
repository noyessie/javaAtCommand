/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.ensp.command;

import cm.ensp.kernel.PortCom;
import cm.ensp.kernel.PortComException;
import cm.ensp.kernel.PortEvent;
import cm.ensp.kernel.PortListener;


/**
 *
 * @author Hubert
 */
public class AT extends ATCommand{
    public AT(){
        super();
        this.name = "AT";
        this.statement = "AT";
    }
    

   /* @Override

    public void executeQuery(PortCom port) {
        boolean flag = true;
        
        this.portLock.lock();
        try{
            while(flag){
                try {
                    port.write(this.statement + '\r');
                } catch (PortComException ex) {
                    flag = false;
                }
            }
        }finally{
            this.portLock.unlock();
        }
    }*/

    @Override
    public void format() {
    }
}
