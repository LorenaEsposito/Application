package com.hdm.Application.shared.bo;
import java.util.Date;

public class Notebook extends NoteObject {
	
	private static final long serialVersionUID = 1L;
	
	private int nbID ; 
	private String nbTitle; 
	private Date modDate; 
	private int unbID ; 
	private Date creDate;
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
	 * @return the nbTitle
	 */
	public String getNbTitle() {
		return nbTitle;
	}
	/**
	 * @param nbTitle the nbTitle to set
	 */
	public void setNbTitle(String nbTitle) {
		this.nbTitle = nbTitle;
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
	 * @return the unbID
	 */
	public int getUnbID() {
		return unbID;
	}
	/**
	 * @param unbID the unbID to set
	 */
	public void setUnbID(int unbID) {
		this.unbID = unbID;
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
}
	
	
	