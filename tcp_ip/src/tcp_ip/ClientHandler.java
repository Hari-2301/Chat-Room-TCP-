package tcp_ip;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientHandler implements Runnable{
	
	public static ArrayList<ClientHandler> clienthandler = new ArrayList<>();
	private Socket socket;
	private BufferedReader bufferedreader;
	private BufferedWriter bufferedwriter;
	private String clientname;
	
	public ClientHandler(Socket socket) {
		try {
			this.socket = socket;
			this.bufferedwriter =  new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));
			this.bufferedreader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.clientname = bufferedreader.readLine();
			clienthandler.add(this);
			broadcastMessage("Server: "+clientname+" has entered chat");
		}
		catch(Exception e){
			closeEverything(socket,bufferedreader,bufferedwriter);
		}
	}
	
	@Override
	public void run() {
		String msgfromclient ;
		while(socket.isConnected()) {
			try {
				msgfromclient = bufferedreader.readLine();
				broadcastMessage(msgfromclient);
			}
			catch(Exception e) {
				closeEverything(socket,bufferedreader,bufferedwriter);
				break;
			}
		}
	}
	
	public void broadcastMessage(String msgtosend) {
		for(ClientHandler clientHandler : clienthandler) {
			try {
				if(!clientHandler.clientname.equals(clientname)) {
					clientHandler.bufferedwriter.write(msgtosend);
					clientHandler.bufferedwriter.newLine();
					clientHandler.bufferedwriter.flush();
				}
			}
			catch(Exception e) {
				closeEverything(socket,bufferedreader,bufferedwriter);
			}
		}
	}
	
	public void removeClientHandler() {
		clienthandler.remove(this);
		broadcastMessage("Server: "+clientname+" has left the chat");
		
	}
	
	public void closeEverything(Socket socket,BufferedReader bufferedreader , BufferedWriter bufferedwriter) {
		removeClientHandler();
		try {
			if(bufferedreader != null) {
				bufferedreader.close();
			}
			if(bufferedwriter != null) {
				bufferedwriter.close();
			}
			if(socket!=null) {
				socket.close();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}

