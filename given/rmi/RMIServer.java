/*
 * Created on 01-Mar-2016
 */
package rmi;
 
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
 
import common.*;
 
public class RMIServer extends UnicastRemoteObject implements RMIServerI {
 
    private int totalMessages = -1;
    private int[] receivedMessages;
 
    public RMIServer() throws RemoteException {

        super();
 
    }


    public String helloTo(String name) throws RemoteException{

                System.err.println(name + " is trying to contact!");
                return "Server says hello to " + name;

        }        
 
    public void receiveMessage(MessageInfo msg) throws RemoteException {
 
        // TO-DO: On receipt of first message, initialise the receive buffer

	if ((totalMessages == -1 ) || (totalMessages != msg.totalMessages)){
		totalMessages = msg.totalMessages;
		receivedMessages = new int[totalMessages];
	}
 
        // TO-DO: Log receipt of the message

	receivedMessages[msg.messageNum] = 1;

	System.out.println(msg.toString());
 
        // TO-DO: If this is the last expected message, then identify
        //        any missing messages

	
	if(msg.messageNum == totalMessages-1){
	System.out.println(" ");
	System.out.println(" ");
	for (int i = 0; i < totalMessages; i++){
		if(receivedMessages[i] == 0){
		System.out.println("Message number " + i+ " was not received");
	}
}
 
    }
}
 
 
    public static void main(String[] args) {
 
        RMIServer rmis = null;
 
        // TO-DO: Initialise Security Manager

        //if(System.getSecurityManager() == null){
        //    System.setSecurityManager(new RMISecurityManager());
        //}

        System.setProperty("java.rmi.server.hostname","127.0.0.1");            
            
        try{

            Naming.rebind("//localhost/MyServer", new RMIServer());
            System.err.println("Server Ready");
        }catch(Exception e){
            System.err.println("Server Exception: " + e.toString());
            e.printStackTrace();
 
        // TO-DO: Instantiate the server class
 
        // TO-DO: Bind to RMI registry
 
    }
/*
    protected static void rebindServer(String serverURL, RMIServer server) {
 
        // TO-DO:
        // Start / find the registry (hint use LocateRegistry.createRegistry(...)
        // If we *know* the registry is running we could skip this (eg run rmiregistry in the start script)
 
        // TO-DO:
        // Now rebind the server to the registry (rebind replaces any existing servers bound to the serverURL)
        // Note - Registry.rebind (as returned by createRegistry / getRegistry) does something similar but
        // expects different things from the URL field.
    }
*/
}
}






