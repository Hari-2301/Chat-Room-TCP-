package tcp_ip;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class client_chat_application {
	
	private Socket socket;
	private BufferedReader bufferedreader;
	private BufferedWriter bufferedwriter;
	private String usrname;
	
	public client_chat_application(Socket socket , String usrname) {
		try {
			this.socket = socket;
			this.bufferedwriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			this.bufferedreader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.usrname = usrname;
		}
		catch(Exception e) {
			closeEverything(socket,bufferedreader,bufferedwriter);
		}
	}
	
	public void sendmsg() {
		try {
			bufferedwriter.write(usrname);
			bufferedwriter.newLine();
			bufferedwriter.flush();
			
			Scanner scanner = new Scanner(System.in);
			while(socket.isConnected()) {
				String msg = scanner.nextLine();
				bufferedwriter.write(usrname+":"+msg);
				bufferedwriter.newLine();
				bufferedwriter.flush();
			}
			
		}
		catch(Exception e) {
			closeEverything(socket,bufferedreader,bufferedwriter);
		}
	}
	
	public void listenForMsg() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String msg;
				
				while(socket.isConnected()) {
					try {
						msg = bufferedreader.readLine();
						System.out.println(msg);
					}
					catch(Exception e){
						closeEverything(socket,bufferedreader,bufferedwriter);
					}
				}
			}
		}).start();
	}
	
	public void closeEverything(Socket socket,BufferedReader bufferedreader , BufferedWriter bufferedwriter) {
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
	
	public static void main(String[] args) throws IOException{
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter your username for the group chat: ");
		String usrname = sc.nextLine();
		Socket socket = new Socket("localhost",2301);
		client_chat_application client_chat_app = new client_chat_application(socket,usrname);
		client_chat_app.listenForMsg();
		client_chat_app.sendmsg();
	}

}
