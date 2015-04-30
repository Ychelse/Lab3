package test;

import java.util.Vector;


public class Chatroomlist {

	private static Vector<Chatroom> list  = new Vector<Chatroom> ();

	public Chatroomlist(){
		Chatroomlist.list = new Vector<Chatroom>();
	}
	
	public static Vector<Chatroom> getList() {
		return list;
	}

	public void setList(Vector<Chatroom> list) {
		this.list = list;
	}
	
	public static Chatroom getroombyname(String roomname){
		System.out.println("List getroom: "+roomname);
		System.out.println("List size: "+Chatroomlist.list.size());
		int index = -1;
		for(int i=0;i<list.size();i++){
			System.out.println("room list name "+list.elementAt(i).getRoomname());
			if(list.elementAt(i).getRoomname().equals(roomname)){
				index = i;
			}
		}
		System.out.println("index: "+index);
		if(index == -1){
			return null;
		}else{
			Chatroom roomreturn = list.elementAt(index);
			return roomreturn;
		}
	}

	public static Chatroom getroombyid(int roomid) {
		// TODO Auto-generated method stub
		int index = -1;
		for(int i=0;i<list.size();i++){
			System.out.println("room list name "+list.elementAt(i).getRoomname());
			if(list.elementAt(i).getRoomid() == roomid){
				index = i;
			}
		}
		System.out.println("index: "+index);
		if(index == -1){
			return null;
		}else{
			Chatroom roomreturn = list.elementAt(index);
			return roomreturn;
		}
	}
	
	public  void add(Chatroom room1) {
		// TODO Auto-generated method stub
		list.add(room1);
	}
}



