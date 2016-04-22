/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.ensp.tools;

/**
 *
 * @author Hubert
 */
public class Error {
    private String label = "Error : ";
    private String lastError = "";
    private String AllError = "";

    
    public Error(String l){
        this.label = l;
    }
    
    public void newError(String s){
        this.lastError = s;
        this.AllError += "\n"+s;
    }
    
    public String getLastError(){
        return this.lastError;
    }
}
