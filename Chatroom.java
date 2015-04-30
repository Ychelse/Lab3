package test;

import java.io.PrintWriter;
import java.util.*;


public class Chatroom {
	
	private String roomname;
	
	private int roomid;
	
	private HashMap<String,User> userlist = new HashMap<String,User>();
	
	public Chatroom(int roomid, String roomname, HashMap<String, User> list){
		this.roomid = roomid;
		this.roomname = roomname;
		this.userlist = list;
	}
	
	public int getRoomid() {
		return roomid;
	}

	public void setRoomid(int roomid) {
		this.roomid = roomid;
	}

	public HashMap<String,User> getUserlist() {
		return userlist;
	}

	public void setUserlist(HashMap<String,User> userlist) {
		this.userlist = userlist;
	}

	public String getRoomname() {
		return roomname;
	}

	public void setRoomname(String roomname) {
		this.roomname = roomname;
	}

	public String add(User user) {
		//Éú³Éjoinid
		
		int id = 0;
		String joinid = "";
		boolean userin = false;
		User usertemp;
		Iterator iter = this.userlist.entrySet().iterator();
		Vector<String> joinidlist = new Vector<String>();
		while(iter.hasNext()){
			@SuppressWarnings("unchecked")
			Map.Entry<String, User> entry = (Map.Entry<String, User>) iter.next();
			usertemp = entry.getValue();
			if(usertemp==user){
				userin = true;
				break;
			}
			joinid = entry.getKey();
			joinidlist.add(joinid);
		}
		for(int i=0; i<joinidlist.size();i++){
			String join = joinidlist.get(i);
			join = join.substring(3,join.length());
			int joinint = Integer.parseInt(join);
			if(joinint>id){
				id = joinint;
			}
		}	
		if(!userin){
			id = id+1;
			joinid = ""+this.roomid+id;
			String joinmessage = user.getUsername()+" Join In!";
			groupmessage(joinmessage);
			this.userlist.put(joinid,user);
		}else{
			joinid = "no";
		}
		
		return joinid;
	}

	public String delete(String joinid) {
		User user = this.userlist.get(joinid);
		if(user == null){
		//	return "none";
			return "success";
		}else{
			this.userlist.remove(joinid);
			String leavemessage = user.getUsername()+" Left!";
			groupmessage(leavemessage);
			return "success";
		}
	}

	public String message(String joinid, String message) {
		User sender = this.userlist.get(joinid);
		String echo = sender.getUsername()+":"+message;
		groupmessage(echo);
		return null;
	}
	
	public String groupmessage(String message){
		Iterator iter = this.userlist.entrySet().iterator();
		Vector<PrintWriter> pwlist = new Vector<PrintWriter>();
		while(iter.hasNext()){
			@SuppressWarnings("unchecked")
			Map.Entry<String, User> entry = (Map.Entry<String, User>) iter.next();
			User user = entry.getValue();
			PrintWriter pw = user.getPw();
			pwlist.add(pw);
		}
		for(int i=0;i<pwlist.size();i++){
			pwlist.elementAt(i).println(message);
		}
		return null;
	}

	
}
