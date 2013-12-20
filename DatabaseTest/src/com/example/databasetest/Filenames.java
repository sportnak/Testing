package com.example.databasetest;

public class Filenames {
	private long id;
	private String filename;
	
	public long getId(){
		return id;
	}
	
	public void setId(long id){
		this.id = id;
	}
	
	public String getFilename(){
		return filename;
	}
	
	public void setFilename(String filename){
		this.filename = filename;
	}
	
	public String toString(){
		return filename;
	}

}
