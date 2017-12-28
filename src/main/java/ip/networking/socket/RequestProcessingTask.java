package ip.networking.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RequestProcessingTask implements Runnable {

	private Socket socket;
	private static final String[] albaphet = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"}; 
	private int responseCounter;
	
	public RequestProcessingTask(Socket socket, int responseCounter) {
		this.socket = socket;
		this.responseCounter = responseCounter;
	}
	
	@Override
	public void run() {
		try(
				BufferedReader clientRequestBr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter responseOutputWriter = new PrintWriter(socket.getOutputStream());
				){
			String line = null;
			while((!socket.isClosed()) && (line = clientRequestBr.readLine()) != null){
				Thread.sleep(10000);
				Integer reqNum = Integer.parseInt(line.trim());
				responseOutputWriter.write(getAlphaEqNumNonRecursive(reqNum));
				System.out.println(responseCounter + ". Responding:" + getAlphaEqNumNonRecursive(reqNum));
				responseOutputWriter.flush();
				responseOutputWriter.close();
			}
		} catch (IOException | InterruptedException e1) {
			e1.printStackTrace();
		} 
	}
	
	private String getAlphaEqNumRecursive(int num){
		num = num -1;
		StringBuilder alphaEqNum = new StringBuilder();
		if(num < 26){
			alphaEqNum.append(albaphet[num]);
		}else{
			alphaEqNum.append(albaphet[num % 26]);
			alphaEqNum.insert(0, getAlphaEqNumRecursive(num / 26));
		}
		return alphaEqNum.toString();
	}
	
	private String getAlphaEqNumNonRecursive(int num){
		StringBuilder alphaEqNum = new StringBuilder();
		while(num > 0){
			num = num -1;
			if(num < 26){
				alphaEqNum.insert(0, albaphet[num]);
				num = 0;
			}else{
				alphaEqNum.insert(0, albaphet[num % 26]);
				num = num / 26;
			}
		}
		return alphaEqNum.toString();
	}
}

