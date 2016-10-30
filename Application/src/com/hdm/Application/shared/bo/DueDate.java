package com.hdm.Application.shared.bo;
import java.util.Date;


public class DueDate extends BusinessObject {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date dueDate; 
	private int nID;
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public int getnID() {
		return nID;
	}
	public void setnID(int nID) {
		this.nID = nID;
	} 

}