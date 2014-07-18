package com.project.devicemeneger;


public class Device {

	private long id;
	public String name;
	private String owner;
	private String ip;
	
	
	
	public Device(long id,  String name, String owner, String ip){
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
	
	public String getIp(){
		return ip;
	}
	
	public void setIp(String ip){
		this.ip = ip;
	}
	
	
	public String getOwner(){
		return owner;
	}
	
	public void setOwner(String owner){
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
