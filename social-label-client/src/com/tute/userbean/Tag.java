package com.tute.userbean;

public class Tag {
	private int id;
	private String tagname;
	private String theme;
	public Tag(){
		
	}
	public Tag(String tagname, String theme) {
		this.tagname = tagname;
		this.theme = theme;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTagname() {
		return tagname;
	}
	public void setTagname(String tagname) {
		this.tagname = tagname;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}

}
