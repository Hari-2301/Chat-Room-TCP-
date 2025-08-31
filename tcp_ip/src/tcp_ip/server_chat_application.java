package tcp_ip;
import java.net.*;
import java.io.*;

public class server_chat_application {
	
	private ServerSocket serversocket;
	
	public server_chat_application(ServerSocket serversocket) {
		this.serversocket = serversocket;
	}
	
	public void startServer() {
		
		try {
			while(!serversocket.isClosed()) {
				
				Socket socket = serversocket.accept();
				System.out.println("A new Client has connected");
				ClientHandler clienthandler = new ClientHandler(socket);
				
				Thread thread = new Thread(clienthandler);
				thread.start();
			}
		}
		catch(Exception e){
			
		}
	}
	public void closeServerSocket() {
		try {
			if(serversocket != null) {
				serversocket.close();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException {
		
		ServerSocket serversocket = new ServerSocket(2301);
		server_chat_application server_chat_app = new server_chat_application(serversocket);
		server_chat_app.startServer();
	}
}
