package ip.networking.socket;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class ClientRequestSenderTask implements Runnable {
	
	private BlockingQueue<Socket> socketQueue;
	private String ipAddress;
	private int port;
	private Random rand = new Random();
	
	public ClientRequestSenderTask(BlockingQueue<Socket> socketQueue, String ipAddress, int port) {
		this.socketQueue = socketQueue;
		this.ipAddress = ipAddress;
		this.port = port;
	}

	@Override
	public void run() {
		Socket socket;
		int requestCounter = 0;
		boolean isServerBusyStatusPrinted = false;
		while(true){
			try {
				socket = new Socket(ipAddress, port);
				try {
					PrintWriter pw = new PrintWriter(socket.getOutputStream());
					int reqNum = rand.nextInt(10000);
					pw.println(reqNum);
					requestCounter++;
					isServerBusyStatusPrinted = false;
					System.out.println(requestCounter + ". Requesting: " + reqNum);
					pw.flush();
					socketQueue.put(socket);
				} catch (InterruptedException | IOException e) {
					e.printStackTrace();
				}
			} catch (IOException e1) {
				if(e1 instanceof ConnectException){
					if(!isServerBusyStatusPrinted){
						System.out.println("Server busy... client connection refused");
						isServerBusyStatusPrinted = true;
					}
				}else{
					e1.printStackTrace();
				}
			}
			
		}
	}
}
