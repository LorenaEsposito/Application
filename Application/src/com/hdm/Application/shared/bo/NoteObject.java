package com.hdm.Application.shared.bo;

import java.util.Date;

public class NoteObject extends BusinessObject {
	
	private static final long serialVersionUID = 1L;
	
	
	private String title;
	private Date creationDate;
	private Date modificationDate; 
	private int ownerID; 
	private int noID;
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}
	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	/**
	 * @return the modificationDate
	 */
	public Date getModificationDate() {
		return modificationDate;
	}
	/**
	 * @param modificationDate the modificationDate to set
	 */
	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}
	/**
	 * @return the ownerID
	 */
	public int getOwnerID() {
		return ownerID;
	}
	/**
	 * @param ownerID the ownerID to set
	 */
	public void setOwnerID(int ownerID) {
		this.ownerID = ownerID;
	}
	/**
	 * @return the noID
	 */
	public int getNoID() {
		return noID;
	}
	/**
	 * @param noID the noID to set
	 */
	public void setNoID(int noID) {
		this.noID = noID;
	} 
	
}