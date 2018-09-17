package urjc.ist.echoservice;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class ClientEcho {

	private Socket socketClient;	
	
	public ClientEcho(){
		this.socketClient = new Socket();
	}
	
	
	public void launchClient(){
		int port = 15000;
		boolean clientOn = true;
		BufferedReader stdIn = null;
		PrintWriter out = null;
		BufferedReader in = null;
		
		SocketAddress servidor = new InetSocketAddress("localhost", port);
		try{
			socketClient.connect(servidor);
			stdIn = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintWriter(socketClient.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
			
			System.out.println("WELCOME to Client Echo 1.0. Write 'quit' to leave...");
			
			while(clientOn){
				String palabras = stdIn.readLine();
				out.println(palabras);
				out.flush();
				
				//Esperamos un poco para que el stream 'in' no este aun a null
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//--------------
				
				
				if (palabras.equalsIgnoreCase("quit")){
					clientOn = false; //exit condition
					
				}else if (!in.ready()) { //Stream null. Chat out
					System.out.println("Server full. You can't enter. " +
									"Please, write 'quit' to leave...");
					
				}else{ //Normal messages
					String recibido = in.readLine(); //Server message in
					System.out.println("SERVER: " + recibido);
				}
			}
			
		}catch(IOException e){
			System.out.println("Error in 'connect' or in the stream...");
			//e.printStackTrace(); //where is my error?
			
		}finally{
			try{
				stdIn.close();
				out.close();
				socketClient.close();
				System.out.println("Client Echo closed.");
				
				
			}catch(IOException e) {
				System.out.println("Error closing client");
				//e.printStackTrace();
			}
		}
	}
	
	
    public static void main(String[] args){
        ClientEcho client = new ClientEcho();
        client.launchClient();
    }
}