import java.io.*;
import java.net.*;
	
public class Server implements Runnable{

	String clientSentence;
	String capitalizedSentence;
	ServerSocket welcomeSocket;
	BufferedReader inFromClient;
	DataOutputStream outToClient;
	Boolean closing = false;
	
	public Server(int port) {
		try {
			welcomeSocket = new ServerSocket(port);
			System.out.println("server is listening");
		} 
		catch (IOException e) {
			e.printStackTrace();
		}		 
	}
	
	public void run() {
		Socket connectionSocket;
		while (!welcomeSocket.isClosed()) {
			System.out.println("server is running");

			try {
				if (!welcomeSocket.isClosed()) {
					connectionSocket = welcomeSocket.accept();
					DataInputStream inFromClient2 = new DataInputStream(connectionSocket.getInputStream());
					DataOutputStream outToClient2 = new DataOutputStream(connectionSocket.getOutputStream());
					
					clientSentence = inFromClient2.readUTF();
					System.out.println("Received: " + clientSentence);
					String returnMessage = "Bonjour!";
					outToClient2.writeUTF(returnMessage);
					System.out.println("Message sent");
					clientSentence = inFromClient2.readUTF();
					System.out.println("Received: " + clientSentence);
					
					while (closing == false) {
						if (clientSentence.contains("10")) {
							System.out.println("Counting to Client");
							for (int i = 0; i < 11; i++) {
								String counter = Integer.toString(i);
								outToClient2.flush();
								outToClient2.writeUTF(counter);
							}
							outToClient2.writeUTF("To easy");
							clientSentence = inFromClient2.readUTF();
						}
						else if (clientSentence.contains("bye")) {
							System.out.println("Recived: " + clientSentence);
							System.out.println("Client Left");
							closing = true;
						}
					}
					outToClient2.flush();
					inFromClient2.close();
					connectionSocket.close();
					welcomeSocket.close();
				}
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void sendDataToClient(String data) {
		try {
			System.out.println("Writing data");
			BufferedWriter pw = new BufferedWriter(new OutputStreamWriter(outToClient));
			pw.write(data);
			outToClient.close();
			pw.close();
			System.out.println("Writen data");
		} 
		catch (IOException e) {
			System.out.println("Breaking here");
			e.printStackTrace();
		}
	}
}
