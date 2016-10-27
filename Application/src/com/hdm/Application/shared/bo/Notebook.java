package com.hdm.Application.shared.bo;
import java.util.Date;

public class Notebook extends NoteObject {
	
	private static final long serialVersionUID = 1L;
	
	private int nbID ; 
	private String nbTitle; 
	private Date modDate; 
	private int unbID ; 
	private Date creDate;
	
	
	public int getNbID() {
		return nbID;
	}
	public void setNbID(int nbID) {
		this.nbID = nbID;
	}
	public String getNbTitle() {
		return nbTitle;
	}
	public void setNbTitle(String nbTitle) {
		this.nbTitle = nbTitle;
	}
	public Date getModDate() {
		return modDate;
	}
	public void setModDate(Date modDate) {
		this.modDate = modDate;
	}
	public int getUnbID() {
		return unbID;
	}
	public void setUnbID(int unbID) {
		this.unbID = unbID;
	}
	public Date getCreDate() {
		return creDate;
	}
	public void setCreDate(Date creDate) {
		this.creDate = creDate;
	} 
	
	
	

}
