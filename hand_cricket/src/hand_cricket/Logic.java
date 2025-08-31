package hand_cricket;

import java.util.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Logic {
	public static void main(String [] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		
		ServerSocket serversocket = new ServerSocket(7777);
		Socket socket = serversocket.accept();
		
		DataOutputStream dataoutputstream = new DataOutputStream(socket.getOutputStream());
		dataoutputstream.writeUTF("Choose Odd or Even");//1st write utf

		
		DataInputStream datainputstream = new DataInputStream(socket.getInputStream());
		String msg = datainputstream.readUTF();//1st read utf
		msg = msg.trim().toLowerCase();
		System.out.println("Player-2 Choice is "+msg);
		
		System.out.println("Player-1 Choose Number From 1 to 6 :");
		int toss = sc.nextInt();
		while(toss<1 || toss>6) {
			System.out.print("Choose Number Between 1 to 6:");
			toss = sc.nextInt();
		}
		sc.nextLine();
		
		dataoutputstream.writeUTF("Player-2 Choose Number From 1 to 6 :");//2nd write utf
		
		toss+=datainputstream.readInt();
		String toss_result = "";
		int flag=0;
		
		if(msg.equals("odd")) {
			if(toss%2==1) {
				toss_result = "Player-2 Won The Toss ";
				flag=2;
			}
			else {
				toss_result = "Player-1 Won The Toss";
				flag=1;
			}
		}
		else {
			if(toss%2==0) {
				toss_result = "Player-2 Won The Toss";
				flag=2;
			}
			else {
				toss_result = "Player-1 Won The Toss";
				flag=1;
			}
		}
		System.out.println(toss_result);

		dataoutputstream.writeUTF(toss_result);//3rd write utf
		dataoutputstream.writeInt(flag);
		
		String decision_player1 = "";
		String decision_player2 = "";
	
		if(flag==1) {
			String str1 = datainputstream.readUTF();//2nd read utf
			System.out.println(str1);
			String decision = sc.nextLine();
			decision = decision.toLowerCase();
			System.out.print("Player-1 decided to "+decision+" First");
			dataoutputstream.writeUTF("Player-1 decided to "+decision+" First");
			if(decision.equals("bat")) {
				decision_player1 = "bat";
				decision_player2 = "bowl";
			}
			else{
				decision_player1 = "bowl";
				decision_player2 = "bat";
			}
		}
		else {
			String res = datainputstream.readUTF();
			System.out.println(res);
			String temp = datainputstream.readUTF();
			temp = temp.toLowerCase();
			if(temp.equals("bat")) {
				decision_player2 = "bat";
				decision_player1 = "bowl";
			}
			else{
				decision_player2 = "bowl";
				decision_player1 = "bat";
			}
		}
		
		int score_player1 = 0;
		int score_player2 = 0;
		if(decision_player1.equals("bat")) {
			
			while(true) {
				System.out.print("Player 2 going to bowl....");
				dataoutputstream.writeUTF("Player 2 going to bowl....");
				
				int curr_player1 = sc.nextInt();
				System.out.println("Player 1 is batting :"+curr_player1);
				dataoutputstream.writeInt(curr_player1);
				
				int curr_player2 = datainputstream.readInt();
				System.out.println("Player 2 is bowling :"+curr_player2);
				
				if(curr_player1 == curr_player2) {
					System.out.println("Player-1 got Out!!!");
					System.out.println("Runs scored by Player-1 is :"+score_player1);
					break;
				}
				else {
					score_player1+=curr_player1;
					System.out.println("Score of Playe-1 is :"+score_player1);				}
			}
			
			while(true) {
				System.out.println("Player 1 is going to bowl....");
				dataoutputstream.writeUTF("Player 1 is going to bowl....");
				
				int curr_player2 = datainputstream.readInt();
				System.out.println("Player 2 is batting :"+curr_player2);
				
				int curr_player1 = sc.nextInt();
				System.out.println("Player 1 is bowling :"+curr_player1);
				dataoutputstream.writeInt(curr_player1);
				
				dataoutputstream.writeInt(score_player2);
				
				if(curr_player1 == curr_player2) {
					System.out.print("Player-2 got Out!!!");
					if(score_player1 > score_player2) {
						System.out.println("Player 1 won the match");
					}
					else if(score_player1 == score_player2) {
						System.out.println("It's a tie");
					}
					break;
				}
				else if(curr_player1 != curr_player2) {
					score_player2+=curr_player2;
					System.out.println("Runs Scored by Player-2 is:"+score_player2);
				}
				if(score_player2>score_player1) {
					System.out.print("Player 2 won the game");
					break;
				}
			}
			
		}//player 1 batting code
		
		else {
			
			while(true) {
				System.out.println("Player 1 is going to bowl....");
				dataoutputstream.writeUTF("Player 1 is going to bowl....");
				
				int curr_player2 = datainputstream.readInt();
				System.out.println("Player 2 is batting :"+curr_player2);
				
				int curr_player1 = sc.nextInt();
				System.out.println("Player 1 is bowling :"+curr_player1);
				dataoutputstream.writeInt(curr_player1);
				
				dataoutputstream.writeInt(score_player2);
				
				if(curr_player1 == curr_player2) {
					System.out.print("Player 2 got Out!!!");
					System.out.println("Runs Scored by Player 2 is :"+score_player2);
					dataoutputstream.writeInt(0);
					break;
				}
				
				else {
					score_player2 += curr_player2;
					System.out.println("Runs scored by player 2 is :"+score_player2);
					dataoutputstream.writeInt(1);
				}
				
			}
			
			while(true) {
				
				System.out.println("Player 2 is going to Bowl...");
				dataoutputstream.writeUTF("Player 2 is going to Bowl...");
				
				int curr_player2 = datainputstream.readInt();
				System.out.println("Player 2 is bowling :"+curr_player2);
				
				int curr_player1 = sc.nextInt();
				System.out.println("Player 1 is batting :"+curr_player1);
				dataoutputstream.writeInt(curr_player1);
				
				dataoutputstream.writeInt(score_player1);

				if(curr_player1 == curr_player2) {
					System.out.print("Player 1 got Out!!!");
					if(score_player1 > score_player2) {
						System.out.println("Player 2 won the match");
					}
					else if(score_player1 == score_player2) {
						System.out.println("It's a tie");
					}
					dataoutputstream.writeInt(0);
					break;
				}
				else if(curr_player1 != curr_player2) {
					score_player1 += curr_player1;
					System.out.println("Runs Scored by player 1 is : " + score_player1);
				}
				if(score_player1 > score_player2) {
					System.out.print("Player 1 won the match");
					dataoutputstream.writeInt(0);
					break;
				}
			}
		}//player 2 batting code
		
		datainputstream.close();
		dataoutputstream.flush();
		dataoutputstream.close();
		socket.close();
		serversocket.close();
		sc.close();
	}
}
