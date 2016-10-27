package com.hdm.Application.shared.bo;

import java.util.Date;

public class Note extends NoteObject {
	
	private static final long serialVersionUID = 1L;
	
	
	private int nID; 
	private String nTitle; 
	private String nSubtitle; 
	private int nbID; 
	private String nContent; 
	private Date modDate; 
	private Date creDate;
	public int getnID() {
		return nID;
	}
	public void setnID(int nID) {
		this.nID = nID;
	}
	public String getnTitle() {
		return nTitle;
	}
	public void setnTitle(String nTitle) {
		this.nTitle = nTitle;
	}
	public String getnSubtitle() {
		return nSubtitle;
	}
	public void setnSubtitle(String nSubtitle) {
		this.nSubtitle = nSubtitle;
	}
	public int getNbID() {
		return nbID;
	}
	public void setNbID(int nbID) {
		this.nbID = nbID;
	}
	public String getnContent() {
		return nContent;
	}
	public void setnContent(String nContent) {
		this.nContent = nContent;
	}
	public Date getModDate() {
		return modDate;
	}
	public void setModDate(Date modDate) {
		this.modDate = modDate;
	}
	public Date getCreDate() {
		return creDate;
	}
	public void setCreDate(Date creDate) {
		this.creDate = creDate;
	}
	
	

 
}
