package ip.networking.socket;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class SocketClient {

	public static void main(String[] args) throws UnknownHostException, IOException {
		//BlockingQueue<Socket> socketQueue = new SynchronousQueue()<>();
		BlockingQueue<Socket> socketQueue = new ArrayBlockingQueue<>(15);
		ClientRequestSenderTask requestTask = new ClientRequestSenderTask(socketQueue, "127.0.0.1", 9876);
		ClientResponseReceiverTask resonseTask = new ClientResponseReceiverTask(socketQueue);
		Thread requesterThread = new Thread(requestTask,"Requester");
		Thread responseReaderThread = new Thread(resonseTask,"ResponseReader");
		requesterThread.start();
		responseReaderThread.start();
		System.out.println("Requester and Response Reader threads started");
	}
}
