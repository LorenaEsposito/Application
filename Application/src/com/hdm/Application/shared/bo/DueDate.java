package com.hdm.Application.shared.bo;

import java.sql.Date;

public class DueDate extends BusinessObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date dDate;
	private int nID;
	private int ddID;

	/**
	 * @return the dDate
	 */
	public Date getdDate() {
		return dDate;
	}

	/**
	 * @param dDate
	 *            the dDate to set
	 */

	public void setdDate(Date dDate) {
		this.dDate = dDate;
	}

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
	 * @return the ddID
	 */
	public int getDdID() {
		return ddID;
	}

	/**
	 * @param ddID
	 *            the ddID to set
	 */
	public void setDdID(int ddID) {
		this.ddID = ddID;
	}

}