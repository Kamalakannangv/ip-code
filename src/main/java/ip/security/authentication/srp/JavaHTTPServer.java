package ip.security.authentication.srp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.json.JSONException;
import org.json.JSONObject;

public class JavaHTTPServer implements Runnable{ 

	private static final int PORT = 9876;

	private Socket socket;
	private SRPServer srpServer;

	public JavaHTTPServer(Socket c) {
		socket = c;
		srpServer = new SRPServer();
	}

	public static void main(String[] args) {
		try(
				ServerSocket serverConnect = new ServerSocket(PORT);	
				) {
			System.out.println("Server started and listening to port : " + PORT + "\n");

			// Infinite loop till the server(this program) is stopped
			while (true) {
				JavaHTTPServer myServer = new JavaHTTPServer(serverConnect.accept());
				// one thread for one request from client
				Thread thread = new Thread(myServer);
				thread.start();
			}

		} catch (IOException e) {
			System.err.println("Error in server connection : " + e.getMessage());
		}
	}

	@Override
	public void run() {
		try(
				InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				PrintWriter printWriter = new PrintWriter(socket.getOutputStream()); 
				){

			HttpRequest requestInfo = getRequestMsg(bufferedReader);
			if(null != requestInfo){
				if(requestInfo.getEndpoint().equals(SRPConfig.REGISTER_ENDPOINT)){
					HttpResponse response = srpServer.registerUser(requestInfo);
					writeResponse(printWriter, response);
				}else if(requestInfo.getEndpoint().equals(SRPConfig.AUTHENTICATE_ENDPOINT)){
					HttpResponse response = srpServer.authenticate(requestInfo);
					writeResponse(printWriter, response);
				}
			}
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
	}

	private void writeResponse(PrintWriter printWriter, HttpResponse response) throws JSONException{
		printWriter.println("HTTP/1.1 " + response.getHttpStatus()+ " " + response.getHttpStatusDesc());
		Iterator<String> headerNameIter = response.getHeaders().keySet().iterator();
		while(headerNameIter.hasNext()){
			String headerName = headerNameIter.next();
			String headerValue = response.getHeaders().get(headerName);
			printWriter.println(headerName + ": " + headerValue);
		}
		printWriter.println();
		if(response.getJsonBody() != null){
			printWriter.println(response.getJsonBody().toString(4));
		}
		printWriter.flush();
	}

	private HttpRequest getRequestMsg(BufferedReader reader){
		HttpRequest requestInfo = null;
		String requestMsgLine = null;
		boolean isRequestFirstLine = true;
		boolean isHeader = false;
		try {
			while((requestMsgLine = reader.readLine()) != null){
				if(isRequestFirstLine){
					StringTokenizer parse = new StringTokenizer(requestMsgLine);
					requestInfo = new HttpRequest(parse.nextToken(), parse.nextToken());
					isRequestFirstLine = false;
					isHeader = true;
				}else if(isHeader){
					if(requestMsgLine.trim().length() == 0){
						break;
					}else{
						String[] headers = requestMsgLine.split(": ");
						requestInfo.getHeaders().put(headers[0], headers[1]);
					}
				}
			}
			while(reader.ready()){
				requestInfo.getRequestBody().append((char)reader.read());
			}
		}catch (IOException  e) {
			e.printStackTrace();
		}
		if(null != requestInfo){
			setRequestBodyObject(requestInfo);
		}
		return requestInfo;
	}

	private void setRequestBodyObject(HttpObject request){
		try{
			JSONObject object = new JSONObject(request.getRequestBody().toString());
			request.setJsonBody(object);
		}catch(JSONException e){
			e.printStackTrace();
		}
	}

}
