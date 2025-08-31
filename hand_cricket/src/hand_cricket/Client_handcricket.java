package hand_cricket;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class Client_handcricket {
	public static void main(String [] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		
		Socket socket = new Socket("localhost",7777);
		
		DataInputStream datainputstream = new DataInputStream(socket.getInputStream());
		String msg = datainputstream.readUTF();//1st read utf
		System.out.println("Player-1 asking:"+msg);
		
		DataOutputStream dataoutputstream = new DataOutputStream(socket.getOutputStream());
		System.out.println("Your Choice :");
		String reply = sc.nextLine();
		dataoutputstream.writeUTF(reply);//1st write utf
		
		msg = datainputstream.readUTF();//2nd read utf
		System.out.println(msg);
		System.out.print("Your choice :");
		int toss = sc.nextInt();
		while(toss<1 || toss>6) {
			System.out.print("Choose The Number BetWeen 1 to 6");
			toss = sc.nextInt();
		}
		dataoutputstream.writeInt(toss);
		
		String toss_result = datainputstream.readUTF();//3rd read utf
		System.out.println(toss_result);
		
		int flag = datainputstream.readInt();
		String decision = ""; 
		
		if(flag==1) {
			dataoutputstream.writeUTF("Choose to bat or bowl");//2nd write utf
			String res = datainputstream.readUTF();
			System.out.print(res);
		}
		else {
			System.out.println("Choose to bat or bowl");
			decision = sc.nextLine();
			System.out.println("Player-2 decided to "+decision+"First");
			dataoutputstream.writeUTF("Player-2 decided to "+decision+"First");
			dataoutputstream.writeUTF(decision);
		}
		
		decision = decision.toLowerCase();
		
		int flag1;
		if(decision.equals("bowl")) {

			while(true) {
				String curr_action = datainputstream.readUTF();
				System.out.println(curr_action);
				
				int curr_play2 = sc.nextInt();
				System.out.println("Player 2 is Bowling "+curr_play2);
				dataoutputstream.writeInt(curr_play2);
				
				int curr_play1 = datainputstream.readInt();
				System.out.println("Player 1 is Batting "+curr_play1);
				
				int run_play1 = datainputstream.readInt();
				System.out.println("Run Scored by Player 1 is "+run_play1);
				
				flag1 = datainputstream.readInt();
				if(flag1 == 0) {
					System.out.println("Player 1 Got Out!!!");
					break;
				}
				else {
					continue;
				}
				
			}
			
			while(true) {
				String curr_action = datainputstream.readUTF();
				System.out.println(curr_action);
				
				int curr_play2 = sc.nextInt();
				System.out.println("Player 2 is Batting "+curr_play2);
				dataoutputstream.writeInt(curr_play2);
				
				int curr_play1 = datainputstream.readInt();
				System.out.print("Player 1 is Bowling "+curr_play1);
				
				int run_play2 = datainputstream.readInt();
				System.out.println("Run Scored by Player 2 is "+run_play2);
				flag1 = datainputstream.readInt();
				if(flag1 == 0) {
					System.out.println("Player 2 Got Out!!!");
					break;
				}
				else {
					continue;
				}
			}
			
		}//player 2 bowling
		
		else {
			
			while(true) {
				String curr_action = datainputstream.readUTF();
				System.out.println(curr_action);
				
				int curr_play2 = sc.nextInt();
				System.out.println("Player 2 is Btting "+curr_play2);
				dataoutputstream.writeInt(curr_play2);
				
				int curr_play1 = datainputstream.readInt();
				System.out.println("Player 1 is Batting "+curr_play1);
				
				int run_play2 = datainputstream.readInt();
				System.out.println("Run Scored by Player 2 is "+run_play2);
				
				flag1 = datainputstream.readInt();
				if(flag1 == 0) {
					System.out.println("Player 2 Got Out!!!");
					break;
				}
				else {
					continue;
				}
				
			}
			
			while(true) {
				String curr_action = datainputstream.readUTF();
				System.out.println(curr_action);
				
				int curr_play2 = sc.nextInt();
				System.out.println("Player 1 is Bowling "+curr_play2);
				dataoutputstream.writeInt(curr_play2);
				
				int curr_play1 = datainputstream.readInt();
				System.out.print("Player 1 is Batting "+curr_play1);
				
				int run_play1 = datainputstream.readInt();
				System.out.println("Run Scored by Player 1 is "+run_play1);
				
				flag1 = datainputstream.readInt();
				if(flag1 == 0) {
					System.out.println("Player 1 Got Out!!!");
					break;
				}
				else {
					continue;
				}
			}
			
		}//player 1 bowling
		datainputstream.close();
		dataoutputstream.flush();
		dataoutputstream.close();
		socket.close();
		sc.close();
	}
}
