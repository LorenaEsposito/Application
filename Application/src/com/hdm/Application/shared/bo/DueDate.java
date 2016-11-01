package com.hdm.Application.shared.bo;
import java.util.Date;


public class DueDate extends BusinessObject {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date dueDate; 
	private int nID;
	/**
	 * @return the dueDate
	 */
	public Date getDueDate() {
		return dueDate;
	}
	/**
	 * @param dueDate the dueDate to set
	 */
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
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

	
}