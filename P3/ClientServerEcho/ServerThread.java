package urjc.ist.echoservice;

import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {
	Socket clientSocket;
	int clientId;
	boolean out;
	
	
	public ServerThread(){
		super();
	}
	
	public ServerThread(Socket clientSocket, int clientId){
		this.clientSocket = clientSocket;
		this.clientId = clientId;
	}
	
	public void run(){
		BufferedReader stdIn = null;
		PrintWriter out = null;
		boolean threadOn= true;
		
		
		System.out.println("Client accepted. Name: Client" + clientId);
		
		try {
			stdIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
		
			while(threadOn){
				String palabras = stdIn.readLine();
				System.out.println("Client" + clientId +": " + palabras);
			
				if (palabras.equalsIgnoreCase("quit")){
					System.out.println("Client Thread...STOP!");
					out.println("you leaves the chat...");
					out.flush();
					threadOn = false;
					
				}else{
					out.println(palabras);
					out.flush();
				}
			}
			
		}catch(IOException e) {
			System.out.println("Something happened in the stream...");
			System.out.println("Probably, you clicked STOP button");
			//e.printStackTrace(); //where is my error?
			
		}finally{
			try{
				out.close();
				stdIn.close();
				clientSocket.close();
				System.out.println("...Stream closed");
			}catch(IOException e){
				System.out.println("Error closing stream");
				//e.printStackTrace();
			}
		}
	}
}
