
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

        String urlServer = new String("rmi://" + args[0] + "/RMIServer");
        int numMessages = Integer.parseInt(args[1]);
	    MessageInfo message;

    // Locate and lookup registry for port 8090
	   Registry reg = LocateRegistry.getRegistry(args[0], 8090);
       iRMIServer = (RMIServerI) reg.lookup("Mostafa");


       // Send messages
	for(int i = 0; i < numMessages; i++){
		message = new MessageInfo(numMessages, i);
		iRMIServer.receiveMessage(message);
	}

    }
}
