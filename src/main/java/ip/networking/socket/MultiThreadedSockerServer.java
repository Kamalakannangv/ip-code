package ip.networking.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadedSockerServer extends SocketServer {
	
	private ExecutorService executorService;
	
	public MultiThreadedSockerServer(ServerSocket serverSocket) {
		super(serverSocket);
		executorService = Executors.newFixedThreadPool(10);
	}
	
	public void start() throws IOException, InterruptedException{
		System.out.println("Starting SingleThreadedSocketServer...");
		int responseCounter = 0;
		while(true){
			Socket connection = serverSocket.accept();
			responseCounter++;
			RequestProcessingTask task = new RequestProcessingTask(connection, responseCounter); 
			executorService.execute(task);
		}
	}
}
