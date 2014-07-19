package com.project.devicemeneger;


public class Device {

	private long id;
	public String name;
	private long owner;
	private long ip;
	
	
	
	public Device(long id,  String name, long owner, long ip){
		this.id = id;
		this.name = name;
		this.owner = owner;
		this.ip = ip;
	}
	public long getId(){
		return id;
	}
	
	public void setId(long id){
		this.id = id;
	}
	
	public long getIp(){
		return ip;
	}
	
	public void setIp(long ip){
		this.ip = ip;
	}
	
	
	public long getOwner(){
		return owner;
	}
	
	public void setOwner(long owner){
		this.owner = owner;
	}
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	@Override
	public String toString(){
		return name;
	}
}
