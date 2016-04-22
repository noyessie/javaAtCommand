
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.ensp.command;

import cm.ensp.kernel.PortCom;

/**
 * Cette classe permet l'appel des commandes
 * @author Hubert
 * @version 1.0
 */
public class Command {
    PortCom port;
    ATCommand c = new AT();
    
    
    public Command(PortCom com){
        this.port = com;
    }
    
    public Command(PortCom com , ATCommand at){
        this.port = com;
        this.c = at;
    }
    
    /**
     * Permet de changer la commande à exécuter dans la méthode execute
     * @param c la commande qui séra exécute lors de l'appel de la commande do
     */
    public void setCommand(ATCommand at){
        this.c = at;
    }
    
    public void execute() throws CommandExecutionException{
        this.c.execute(port);
    }
    
    public void execute(ATCommand at) throws CommandExecutionException{
        this.setCommand(at);
        this.c.execute(port);
    }
    
    public ATCommand getCommand(){
        return this.c;
    }
    
    public String getStatement(){
        return this.c.getStatement();
    }
    
    public String getStatementResult(){
        return this.c.getStatementResult();
    }
    public String getCommandName(){
        return this.c.getCommandName();
    }
    public String[] getDataKeys(){
        return this.c.getdataKeys();
    }
    
    public String getStatementStatus(){
        return this.c.getStatementStatus();
    }
    
    public String getOutput(){
        return this.c.getOutput();
    }
    
    public String toString(){
        String ch = "";
        ch += this.c.toString();
        
        
        return ch;
    }
}
