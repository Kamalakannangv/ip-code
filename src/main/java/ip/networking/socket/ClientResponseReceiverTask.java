package ip.networking.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class ClientResponseReceiverTask implements Runnable {
	
	private BlockingQueue<Socket> socketQueue;
	
	public ClientResponseReceiverTask(BlockingQueue<Socket> socketQueue) {
		this.socketQueue = socketQueue;
	}

	@Override
	public void run() {
		try {
			Socket socket = null;
			while((socket = socketQueue.take()) != null){
				try(BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))){
					String line;
					while((line = br.readLine()) != null){
						System.out.println("Recieving: " + line.trim());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
