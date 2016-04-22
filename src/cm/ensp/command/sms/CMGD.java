/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.ensp.command.sms;

import cm.ensp.command.ATCommand;
import cm.ensp.kernel.PortCom;
import cm.ensp.kernel.PortComException;

/**
 * Cette classe répresenter la commande permettant d'effacer un message
 * @author Hubert
 */
public class CMGD extends ATCommand{
    
    public CMGD(String s){
        this.state = s;
        this.statement = "AT+CMGD"+this.state;
    }
    
    public CMGD(int index){        
        this.statement = "AT+CMGD="+index;
    }
    
    public void setCommand(String state){
        this.state = state;
        this.statement = "AT+CMGD"+this.state;
    }
    
    public void setCommand(int index){
        this.statement = "AT+CMGD="+index;
    }
    
    /*@Override
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
        try{
            String s = this.state;
            if(s.equals(CMGD.TEST_COMMAND)){
                int d = this.statementResult.indexOf("(");
                int f = this.statementResult.indexOf(")");
                if(d >=0 && f>=0 && d>f){
                    String sub = this.statementResult.substring( d+1 , f);
                    System.out.println(sub);
                }
                
                
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
    }
    
}
