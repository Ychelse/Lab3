package test;

import java.util.*;
import java.net.*;
import java.io.*;
import java.util.concurrent.*;

public class EchoServer {
	private int port = 8888;
	private ServerSocket serverSocket;
	private ExecutorService executorService;
	private final int POOL_SIZE = 6;
	OutputStream os = null;
	InputStream is = null;

	public EchoServer() throws IOException {
		serverSocket = new ServerSocket(port);

		executorService = Executors.newFixedThreadPool(Runtime.getRuntime()
				.availableProcessors() * POOL_SIZE);
		System.out.println("number of thread:"
				+ Runtime.getRuntime().availableProcessors());
		System.out.println("start server");
	}

	public void service() {
		while (true) {
			Socket socket = null;
			try {
				socket = serverSocket.accept();
				executorService.execute(new Handler(socket));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	class Handler implements Runnable {
		private Socket socket;
		public Chatroomlist list;
		public Chatroom room;
		public User user;

		public Handler(Socket socket) {
			this.socket = socket;
		}

		private PrintWriter getWriter(Socket socket) throws IOException {
			os = socket.getOutputStream();
			return new PrintWriter(os, true);
		}

		private BufferedReader getReader(Socket socket) throws IOException {
			is =socket.getInputStream();
			return new BufferedReader(new InputStreamReader(is));
		}

		public void run() {
			
				System.out.println("New connect port:" + socket.getPort());
				BufferedReader br = null;
				try {
					br = getReader(socket);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				PrintWriter pw = null;
				try {
					pw = getWriter(socket);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				String msg = null;
				try {
					while((msg=br.readLine())!=null){
						System.out.println("socket input:"+msg);
						if(msg.startsWith("JOIN_CHATROOM")){
							String roomname = msg.split(":")[1];
							System.out.println("socket roomname:"+roomname);
							msg = br.readLine();
							System.out.println("socket ip:"+msg);
							msg = br.readLine();
							System.out.println("socket port:"+msg);
							msg = br.readLine();
							String username = msg.split(":")[1];
							System.out.println("socket username:"+username);
							
							String ip = socket.getLocalAddress().toString();
							int port = socket.getLocalPort();
							
							user = new User(username,ip,port,pw);
							room = Chatroomlist.getroombyname(roomname);
							
							String echo = JoinRoom(room,user);
							pw.println(echo);
						}else if(msg.startsWith("LEAVE_CHATROOM")){
							
							
							
							String roomid = msg.split(":")[1];
							System.out.println("leave roomid:"+roomid);
							msg = br.readLine();
							String joinid = msg.split(":")[1];
							System.out.println("leave joinid:"+joinid);
							msg = br.readLine();
							String username = msg.split(":")[1];
							System.out.println("socket username:"+username);
							
							
							int roomidint = Integer.parseInt(roomid);
							room = Chatroomlist.getroombyid(roomidint);
							String echo = LeaveRoom(room,joinid);
							pw.println(echo);
						}else if(msg.startsWith("CHAT")){
							
							String roomid = msg.split(":")[1];
							System.out.println("leave roomid:"+roomid);
							msg = br.readLine();
							String joinid = msg.split(":")[1];
							System.out.println("leave joinid:"+joinid);
							msg = br.readLine();
							String username = msg.split(":")[1];
							System.out.println("socket username:"+username);
							msg = br.readLine();
							String message = msg.split(":")[1];
							System.out.println("socket message:"+message);
							
							int roomidint = Integer.parseInt(roomid);
							room = Chatroomlist.getroombyid(roomidint);
							
							String result = Message(room,joinid,message);
						//	pw.println(echo);
						}else if(msg.startsWith("DISCONNECT")){
							try {
								br.close();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							pw.close();
							try {
								socket.close();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							System.exit(0);
						}
					}
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    }

		private String LeaveRoom(Chatroom room, String joinid) {
			System.out.println("LEAVEROOM");
			String echo = null;
			if(room == null){
				echo = "ERROR_CODE: 1\nERROR_DESCRIPTION: This room is not existed.";
			}else{
				String leaveid = room.delete(joinid);
				if(leaveid.equals("success")){
					echo = 	"LEFT_CHATROOM:"+ room.getRoomid() +
							"\nJOIN_ID:"+joinid;
				}else if(leaveid.equals("none")){
					echo = "ERROR_CODE: 3\nERROR_DESCRIPTION:You are not in this room!";
				}
			}
		
			return echo;
		}
		private String JoinRoom(Chatroom room, User user) {
			System.out.println("JOINROOM");
			String echo = "";
			if(room == null){
				echo = "ERROR_CODE: 1\nERROR_DESCRIPTION: This room is not existed.";
			}else{
				String joinid = room.add(user);
				if(joinid.equals("no")){
					echo = "ERROR_CODE: 2\nERROR_DESCRIPTION: You have joined.";
				}else {
						 echo = 	"JOINED_CHATROOM:"+ room.getRoomname() +
								"\nSERVER_IP:"+ socket.getLocalAddress().toString()+
								"\nPORT:"+socket.getLocalPort()+
								"\nROOM_REF:"+ room.getRoomid() +
								"\nJOIN_ID:"+joinid;
				}
			
			}
			return echo;
			
		}
		
		private String Message(Chatroom room, String joinid, String message) {
			// TODO Auto-generated method stub
			System.out.println("MESSAGE");
			String feedback = room.message(joinid, message);
			return null;
		}
}