package com.hdm.Application.shared.bo;

import java.util.Date;

public class Note extends NoteObject {
	
	private static final long serialVersionUID = 1L;
	
	
	private int nID; 
	/**
	 * @return the nID
	 */
	public int getnID() {
		return nID;
	}
	/**
	 * @param nID the nID to set
	 */
	public void setnID(int nID) {
		this.nID = nID;
	}
	/**
	 * @return the nTitle
	 */
	public String getnTitle() {
		return nTitle;
	}
	/**
	 * @param nTitle the nTitle to set
	 */
	public void setnTitle(String nTitle) {
		this.nTitle = nTitle;
	}
	/**
	 * @return the nSubtitle
	 */
	public String getnSubtitle() {
		return nSubtitle;
	}
	/**
	 * @param nSubtitle the nSubtitle to set
	 */
	public void setnSubtitle(String nSubtitle) {
		this.nSubtitle = nSubtitle;
	}
	/**
	 * @return the nbID
	 */
	public int getNbID() {
		return nbID;
	}
	/**
	 * @param nbID the nbID to set
	 */
	public void setNbID(int nbID) {
		this.nbID = nbID;
	}
	/**
	 * @return the nContent
	 */
	public String getnContent() {
		return nContent;
	}
	/**
	 * @param nContent the nContent to set
	 */
	public void setnContent(String nContent) {
		this.nContent = nContent;
	}
	/**
	 * @return the modDate
	 */
	public Date getModDate() {
		return modDate;
	}
	/**
	 * @param modDate the modDate to set
	 */
	public void setModDate(Date modDate) {
		this.modDate = modDate;
	}
	/**
	 * @return the creDate
	 */
	public Date getCreDate() {
		return creDate;
	}
	/**
	 * @param creDate the creDate to set
	 */
	public void setCreDate(Date creDate) {
		this.creDate = creDate;
	}
	private String nTitle; 
	private String nSubtitle; 
	private int nbID; 
	private String nContent; 
	private Date modDate; 
	private Date creDate;

	}
	
	

 

