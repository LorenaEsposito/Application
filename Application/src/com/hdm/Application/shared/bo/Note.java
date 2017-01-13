package com.hdm.Application.shared.bo;

import java.util.Date;

public class Note extends NoteObject {

	private static final long serialVersionUID = 1L;

	private int nID;
	private String nTitle;
	private String nSubtitle;
	private int nbID;
	private String nContent;
	private Date nModDate;
	private Date nCreDate;
	private int userID;
	private String source;
	private DueDate dDate;
	private int pType; 
	

	/**
	 * @return the nID
	 */
	public int getnID() {
		return nID;
	}

	/**
	 * @param nID
	 *            the nID to set
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
	 * @param nTitle
	 *            the nTitle to set
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
	 * @param nSubtitle
	 *            the nSubtitle to set
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
	 * @param nbID
	 *            the nbID to set
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
	 * @param nContent
	 *            the nContent to set
	 */
	public void setnContent(String nContent) {
		this.nContent = nContent;
	}

	/**
	 * @return the nModDate
	 */

	public Date getnModDate() {
		return nModDate;
	}

	/**
	 * @param nModDate
	 *            the nModDate to set
	 */

	public void setnModDate(Date nModDate) {
		this.nModDate = nModDate;
	}

	/**
	 * @return the nCreDate
	 */
	public void setnCreDate(Date nCreDate) {
		
		if (nCreDate != null){
		this.nCreDate = nCreDate;
		}
		else {
			this.nCreDate = new Date();
		}
	}

	/**
	 * @param creDate
	 *            the creDate to set
	 */

	public Date getnCreDate() {
		return nCreDate;
	}

	/**
	 * @return the userID
	 */
	public int getUserID() {
		return userID;
	}

	/**
	 * @param userID
	 *            the userID to set
	 */

	public void setUserID(int userID) {
		this.userID = userID;
	}

	/**
	 * @return the source
	 */

	public String getSource() {
		return source;
	}

	/**
	 * @param source
	 *            the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	public int getpType() {
		return pType;
	}

	public void setpType(int pType) {
		this.pType = pType;
	}

}
