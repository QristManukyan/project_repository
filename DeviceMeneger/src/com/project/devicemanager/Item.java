package com.project.devicemanager;
import android.annotation.SuppressLint;

@SuppressLint("DefaultLocale")
public class Item implements Comparable<Item>{
	
	private String name;
	private String date;
	private String path;
	private String data;
	private String image;
	private boolean chack;
	
	public Item(String name, String date, String data, String path, String image, boolean chack ){
		this.name = name;
		this.date = date;
		this.path = path;
		this.data = data;
		this.image = image;
		this.chack = chack;
	}

	public boolean getChack (){
		return chack;
	}
	public String getName() {
		return name;
	}
	
	public String getDate() {
		return date;
	}
	public String getData() {
		return data;
	}
	
	public String getPath() {
		return path;
	}
	
	public String getImage() {
		return image;
	}
	
	@Override
	public int compareTo(Item another) {
		if (this.name != null)
			return this.name.toLowerCase().compareTo(another.getName().toLowerCase());
		else 
			throw new IllegalArgumentException();
	}

}
