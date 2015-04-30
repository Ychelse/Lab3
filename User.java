package test;

import java.io.PrintWriter;

public class User {
	private String username;
	
	private String ip;
	
	private int port;
	
	private PrintWriter pw;
	
	public User(String username, String ip, int port, PrintWriter pw){
		this.username = username;
		this.ip = ip;
		this.port = port;
		this.setPw(pw);
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	public String getUsername(){
		return this.username;
	}
	public PrintWriter getPw() {
		return pw;
	}
	public void setPw(PrintWriter pw) {
		this.pw = pw;
	}
	
}
