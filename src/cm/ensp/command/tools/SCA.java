/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.ensp.command.tools;

/**
 *
 * @author Hubert
 */
public class SCA extends NumeroGSM{
    
    public SCA(){
        super();
    }
    
    public SCA(String number){
        super(number);
    }
    
    public SCA(String number , byte type){
        super(number , type);
    }
    
    @Override
    public int length() {
        if(this.getHexNumber() != null){
            return this.getHexNumber().length;
        }
        return 0;
    }
    
}
