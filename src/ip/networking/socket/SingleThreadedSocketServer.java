package ip.networking.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SingleThreadedSocketServer extends SocketServer{
	
	public SingleThreadedSocketServer(ServerSocket serverSocket) {
		super(serverSocket);
	}
	
	public void start() throws IOException, InterruptedException{
		System.out.println("Starting SingleThreadedSocketServer...");
		int responseCounter = 0;
		while(true){
			Socket connection = serverSocket.accept();
			responseCounter++;
			Thread.sleep(10000);
			RequestProcessingTask task = new RequestProcessingTask(connection, responseCounter); 
			task.run();
		}
	}

}
