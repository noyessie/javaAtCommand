/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.ensp.command;

/**
 * Permet de spécifier une exception liée à une commande
 * @author Hubert
 */
public class CommandException extends Exception{
    
    public CommandException(String message){
        super(message);
    }
}
