/*
 * Created on 01-Mar-2016
 */
package rmi;
 
import java.rmi.Naming;
import java.rmi.registry.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.net.MalformedURLException;
 
import common.MessageInfo;
 
public class RMIClient {
 
    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
 
        RMIServerI iRMIServer = null;
 
        // Check arguments for Server host and number of messages
        //if (args.length < 2){
          //  System.out.println("Needs 2 arguments: ServerHostName/IPAddress, TotalMessageCount");
            //System.exit(-1);
        //}
 
        String urlServer = new String("rmi://" + args[0] + "/RMIServer");
        int numMessages = Integer.parseInt(args[1]);
	MessageInfo message;
        //iRMIServer = (RMIServerI) Naming.lookup(urlServer);
        //String name = "mostafa";

       // String response = iRMIServer.helloTo(name);

       // System.out.println(response);


	Registry reg = LocateRegistry.getRegistry(args[0], 8090);
        iRMIServer = (RMIServerI) reg.lookup("Mostafa");
 
        // TO-DO: Initialise Security Manager
 
        // TO-DO: Bind to RMIServer
 
        // TO-DO: Attempt to send messages the specified number of times

	for(int i = 0; i < numMessages; i++){
		message = new MessageInfo(numMessages, i);
		iRMIServer.receiveMessage(message);
	}
 
    }
}


