package com.hdm.Application.shared.bo;

import java.util.Date;

public class NoteObject extends BusinessObject {
	
	private static final long serialVersionUID = 1L;
	
	
	private String title;
	private Date creationDate;
	private Date modificationDate; 
	private int ownerID; 
	private int noID; 
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getModificationDate() {
		return modificationDate;
	}
	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}
	public int getOwnerID() {
		return ownerID;
	}
	public void setOwnerID(int ownerID) {
		this.ownerID = ownerID;
	}
	public int getNoID() {
		return noID;
	}
	public void setNoID(int noID) {
		this.noID = noID;
	}
	
	
	
	
	
	
}
