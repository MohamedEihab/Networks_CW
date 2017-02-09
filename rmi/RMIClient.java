/*
 * Created on 01-Mar-2016
 */
package rmi;

import java.rmi.*;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import common.MessageInfo;

public class RMIClient {

	public static void main(String[] args) {

		RMIServerI iRMIServer = null;
		int test_port = 2050;
		String server_host = args[0];

		// Check arguments for Server host and number of messages
		if (args.length < 2){
			System.out.println("Needs 2 arguments: ServerHostName/IPAddress, TotalMessageCount");
			System.exit(-1);
		}

		String urlServer = new String("rmi://" + args[0] + "/RMIServer");
		int numMessages = Integer.parseInt(args[1]);

				// TO-DO: Initialise Security Manager


		// TO-DO: Bind to RMIServer
		try {
			//System.setSecurityManager(new SecurityManager());

			try{
				String server_name = "RMIServer";
				Registry loc_reg = LocateRegistry.getRegistry(args[0], 1997);
				iRMIServer = (RMIServerI) loc_reg.lookup(server_name);
			}catch(NotBoundException e){
				System.out.println("Not Bound Exception: " + e);
			}

			for (int i = 0; i < numMessages; i++){
					iRMIServer.receiveMessage(new MessageInfo(numMessages, i));
				}
			}catch(RemoteException e){
				System.out.println("Remote Exception" + e);
			}

		}
		// TO-DO: Attempt to send messages the specified number of times

	}
