
package rmi;

import java.rmi.registry.Registry;
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

    // Used for debugging
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

	System.out.println("Message " + (msg.messageNum + 1) + " received.");

        // TO-DO: If this is the last expected message, then identify
        //        any missing messages

    int lostMessages = 0;

	if(msg.messageNum == totalMessages-1){
	System.out.println(" ");
	System.out.println(" ");
	for (int i = 0; i < totalMessages; i++){
		if(receivedMessages[i] == 0){
        lostMessages++;
		System.out.println("Message number " + i+ " was not received");
	}
}
    System.out.println("Number of messages successfully received: " + (totalMessages - lostMessages));
    System.out.println("Number of messages lost: " + lostMessages);

    }
}


    public static void main(String[] args) throws Exception {

        RMIServer rmis = new RMIServer();

		Registry registry;
        // Bind to registry 8090
		try{
		          registry = LocateRegistry.createRegistry(8090);
		}catch(RemoteException e){
		          registry = LocateRegistry.getRegistry();
		}
		registry.bind("Mostafa",rmis);
        System.out.println("Server is ready");
        }

}
