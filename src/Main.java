
public class Main {

	public static void main(String[] args) {
		Server server = new Server(23);
		Thread chatServerThread = new Thread(server);
		chatServerThread.start();
	}
}
