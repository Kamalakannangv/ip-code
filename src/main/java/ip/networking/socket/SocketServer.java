package ip.networking.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

public abstract class SocketServer {
	
	protected ServerSocket serverSocket;
	
	public SocketServer(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}
	
	public abstract void start() throws IOException, InterruptedException;
	
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		ServerSocket serverSocket = new ServerSocket (9876, 10, InetAddress.getByName("127.0.0.1"));
		//SocketServer socketServer = new SingleThreadedSocketServer(serverSocket);
		SocketServer socketServer = new MultiThreadedSockerServer(serverSocket);
		socketServer.start();
	}
	
	
	

}
