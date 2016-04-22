/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.ensp.command;

/**
 *
 * @author Hubert
 */
public class CommandExecutionException extends Exception {

    /**
     * Creates a new instance of
     * <code>CommandExecutionException</code> without detail message.
     */
    public CommandExecutionException() {
    }

    /**
     * Constructs an instance of
     * <code>CommandExecutionException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public CommandExecutionException(String msg) {
        super(msg);
    }
}
