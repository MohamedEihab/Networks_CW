/*
 * Created on 01-Mar-2016
 */
package rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

import common.*;

public class RMIServer extends UnicastRemoteObject implements RMIServerI {

	private int totalMessages = -1;
	private int[] receivedMessages;

	public RMIServer() throws RemoteException {
	}

	public void receiveMessage(MessageInfo msg) throws RemoteException {

		// TO-DO: On receipt of first message, initialise the receive buffer
    if (totalMessages < 0){
      totalMessages = msg.totalMessages;
    }

		// TO-DO: Log receipt of the message
    receivedMessages[msg.messageNum] = 1;

    System.out.println("Message " + msg.messageNum + " arrived"); // Cout message arrival

		// TO-DO: If this is the last expected message, then identify
		//        any missing messages


	}


	public static void main(String[] args) {

		RMIServer rmis = null;

		// TO-DO: Initialise Security Manager
    System.setSecurityManager(new RMISecurityManager());

    // TO-DO: Instantiate the server class
    try{
      rmis = new RMIServer();
    }catch(RemoteException e){
      e.printStackTrace();
    }

		// TO-DO: Bind to RMI registry
    String url_name = new String("RMIServer");
    rebindServer(url_name, rmis);
	}

	protected static void rebindServer(String serverURL, RMIServer server) {

		// TO-DO:
		// Start / find the registry (hint use LocateRegistry.createRegistry(...)
		// If we *know* the registry is running we could skip this (eg run rmiregistry in the start script)
    try{
      Registry loc_reg;
      try{
          loc_reg = LocateRegistry.createRegistry(1997);
      }catch(RemoteException e){
          loc_reg = LocateRegistry.getRegistry();
          e.printStackTrace();
      }
      loc_reg.rebind(serverURL, server);
  }catch(RemoteException e){
      e.printStackTrace();
  }
		// TO-DO:
		// Now rebind the server to the registry (rebind replaces any existing servers bound to the serverURL)
		// Note - Registry.rebind (as returned by createRegistry / getRegistry) does something similar but
		// expects different things from the URL field.
	}
}
