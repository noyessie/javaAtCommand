/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.ensp.kernel;

import java.util.ArrayList;

/**
 *
 * @author Hubert
 */
public class PortEvent {
    ArrayList<String> history;
    String output = "";
    
    public PortEvent(String o , ArrayList<String> h){
        this.history = h;
        this.output = o;
    }
    
    public ArrayList<String> getHistory(){
        return this.history;
    }
    public String getLastOutput(){
        return this.output;
    }
    
    @Override
    public String toString(){
        String ch;
        
        ch = "History of Command";
        int i=0;
        for(String s : history){
            ch += "\n****************** Commande " + i + "***************************\n";
            
            ch += s;
            
            ch += "\n****************** End of " + i + "***************************\n";
        }
        
        return ch;
    }
}
