package urjc.ist.echoservice;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.net.ServerSocket;

public class ServerEcho {
	
//	private static final String CHARSET_NAME = "UTF-8";
	private static final int NCLIENTES = 5;
	private ServerSocket servidor;
	
	
	public ServerEcho(int port){
		try{
			this.servidor = new ServerSocket(port);
		}catch(IOException e){
			System.out.println("Couldn't connect");
			System.exit(-1); //abnormal exit
		}
	}
	
	public void launchServer(){
		ArrayList<ServerThread> threads = new ArrayList<ServerThread>(); 
		boolean serverOn = true;
		int clients = 0;
		
		System.out.println("||-- SERVER ECHO 1.0 --||");
		while(serverOn){
			if (clients < ServerEcho.NCLIENTES){
				try{
					Socket conectado = servidor.accept();
					System.out.println("Connect!");
						
					clients++; //new thread, increment
					ServerThread t = new ServerThread(conectado, clients);
					threads.add(t); //add to ArrList
					
					t.start();
					
				}catch(IOException e){
					System.out.println("Exception in 'accept'");
					//e.printStackTrace();
				}
			}else{
				serverOn = false;	
			}
		}
			
		
		//NO MORE CLIENTS, wait all threads ends and exit 
		for (ServerThread x : threads){
			if(x.isAlive()){
				try {
					x.join();
				} catch (InterruptedException e) {
					System.out.println("You clicked STOP button while" +
									"server was waiting to his threads ended");
					//e.printStackTrace(); 
				}
			}
		}
		
		
		//SERVER OFF
		try { 
	         servidor.close(); 
	         System.out.println("Server stopped"); 
	      } catch(Exception e) { 
	         System.out.println("Error stopping server"); 
	         System.exit(-1); 
	    } 
		
	}
	
	
    public static void main(String[] args) {
    	ServerEcho server = new ServerEcho(15000);
    	server.launchServer();	
    }
}