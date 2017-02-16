package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Arrays;

import common.MessageInfo;

public class UDPServer {

	private DatagramSocket recvSoc;
	private int totalMessages = -1;
	private int[] receivedMessages;
	private boolean close;

	private void run() throws SocketException, IOException{
		int				pacSize = 2048; // Try different sizes?
		byte[]			pacData;
		DatagramPacket 	pac;
		// TO-DO: Receive the messages and process them by calling processMessage(...).
		//        Use a timeout (e.g. 30 secs) to ensure the program doesn't block forever
		close = false;
		while (!close){

			pacData = new byte[pacSize];

			pac = new DatagramPacket(pacData, pacSize);

			try {
				recvSoc.setSoTimeout(45000); //45 seconds
				recvSoc.receive(pac);
				String pac_message = new String(pac.getData());
				try{
					processMessage(pac_message);
				}catch(Exception e){
					e.printStackTrace();
				}
			}catch(SocketTimeoutException e){
				int lost_messages = 0;
				// If there are no messages to begin with, exit
				if (receivedMessages == null){
					return;
				}

				for (int i = 0; i < receivedMessages.length; i++){
					if (receivedMessages[i] != 1){ //missing if the message is not equal to one?
						System.out.println("Message: " + i + " is missing.");
						lost_messages++;
					}
				}

				System.out.println("Number of successful message arrivals: " + (totalMessages - lost_messages));
				if (lost_messages > 0){
					System.out.println(lost_messages + " messages have been lost.");
				}else{
					System.out.println("100% of messages arrived.");
				}

				receivedMessages = null;
				totalMessages = -1;
				System.out.println("Socket Timeout");
				close = true;
			}
		}


	}

	public void processMessage(String data) throws Exception{

		MessageInfo msg = new MessageInfo(data.trim());

		// TO-DO: Use the data to construct a new MessageInfo object

		// TO-DO: On receipt of first message, initialise the receive buffer
		 if ((receivedMessages == null)||(msg.totalMessages != receivedMessages.length)){
			 receivedMessages = new int[msg.totalMessages];
			 totalMessages = 0;
		 }
		// TO-DO: Log receipt of the message

		totalMessages++;

		if (msg.messageNum % 100 == 0 ){
			System.out.println(msg.messageNum);
		}
		receivedMessages[msg.messageNum] = 1;
		// TO-DO: If this is the last expected message, then identify
		//        any missing messages

		if (totalMessages == msg.totalMessages){
			int lost_messages = 0;
			// If there are no messages to begin with, exit
			if (receivedMessages == null){
				return;
			}

			for (int i = 0; i < receivedMessages.length; i++){
				if (receivedMessages[i] != 1){ //missing if the message is not equal to one?
					System.out.println("Message: " + i + " is missing.");
					lost_messages++;
				}
			}

			System.out.println("Number of successful message arrivals: " + (totalMessages - lost_messages));
			if (lost_messages > 0){
				System.out.println(lost_messages + " messages have been lost.");
			}else{
				System.out.println("100% of messages arrived.");
			}

			receivedMessages = null;
			totalMessages = -1;
		}


	}

	public UDPServer(int rp) {
		// TO-DO: Initialise UDP socket for receiving data
		try {
			recvSoc = new DatagramSocket(rp);
			System.out.println("Server Running...");
		}catch (SocketException e){
			e.printStackTrace();
		}
		// Done Initialisation
	}

	public static void main(String args[]) {

		// Get the parameters from command line
		if (args.length < 1) {
			System.err.println("Arguments required: recv port");
			System.exit(-1);
		}
		int recvPort = Integer.parseInt(args[0]);

		// TO-DO: Construct Server object and start it by calling run().
		UDPServer udp_server = new UDPServer(recvPort);
		try{
			udp_server.run();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
