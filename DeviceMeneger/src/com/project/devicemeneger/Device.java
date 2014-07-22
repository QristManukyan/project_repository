package com.project.devicemeneger;


public class Device {

	private int id;
	private String name;
	private int owner;
	private int ip;
	
	public Device(int id,  String name, int owner, int ip){
		this.id = id;
		this.name = name;
		this.owner = owner;
		this.ip = ip;
	}
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public int getIp(){
		return ip;
	}
	
	public void setIp(int ip){
		this.ip = ip;
	}
	
	
	public int getOwner(){
		return owner;
	}
	
	public void setOwner(int owner){
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
