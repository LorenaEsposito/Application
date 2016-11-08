package com.hdm.Application.shared.bo;
import java.util.Date;

public class Notebook extends NoteObject {
	
	private static final long serialVersionUID = 1L;
	
	private int nbID ; 
	private String nbTitle; 
	private Date nbModDate;  
	private Date nbCreDate;
	private int userID;
	
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
	 * @return the NbModDate
	 */
	
	public Date getNbModDate() {
		return nbModDate;
	}
	
	
	/**
	 * @param nbModDate the nbModDate to set
	 */

	public void setNbModDate(Date nbModDate) {
		this.nbModDate = nbModDate;
	}
	
	
	/**
	 * @return the NbCreDate
	 */
	
	
	public Date getNbCreDate() {
		return nbCreDate;
	}
	
	
	/**
	 * @param NbCreDate the NbCreDate to set
	 */
	
	public void setNbCreDate(Date nbCreDate) {
		this.nbCreDate = nbCreDate;
	}
	
	
	/**
	 * @return the UserID
	 */
	public int getUserID() {
		return userID;
	}
	
	/**
	 * @param userID the userID to set
	 */
	public void setUserID(int userID) {
		this.userID = userID;
	}



}


	