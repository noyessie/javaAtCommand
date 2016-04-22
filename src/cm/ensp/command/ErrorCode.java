/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.ensp.command;

/**
 *
 * @author Hubert
 */
public class ErrorCode {
    public static String getMessage(int code){
        /*
         * @todo completer la listes des erreurs ci présenter
         */
        switch(code){
            case 0 : return "échec du téléphone"; 
            case 1 : return "pas de connexion au téléphone";
            case 2 : return "phone-adaptator link reserved";
            case 3 : return "opération interdite";
            case 4 : return "Cette opération n'est pas supporté";
            case 321: return "bonjour";
            default: return "unknow Exception";
        }
    }
}
