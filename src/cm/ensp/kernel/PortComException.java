/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.ensp.kernel;

/**
 *
 * @author Hubert
 */
public class PortComException extends Exception {
    
    /**
     * Creates a new instance of
     * <code>PortComException</code> without detail message.
     */
    public PortComException() {
        super("unknow");
    }

    /**
     * Constructs an instance of
     * <code>PortComException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public PortComException(String msg) {
        super(msg);
    }
}
