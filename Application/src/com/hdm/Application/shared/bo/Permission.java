package com.hdm.Application.shared.bo;

import java.io.Serializable;

public class Permission implements Serializable{

	/*
	 * Attribute
	 */
	/**
	 * Deklaration der serialVersionUID zur Serialisierung der Objekte
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Die id der Permission - eindeutiger Primaerschluessel fuer die Datenbank
	 */
	private int permissionID;
	
	/**
	 * Der permissiontype der Permission - Lese- oder Bearbeitungsberechtigung
	 */
	private int permissiontype;
	
	/**
	 * Die id des Users der Permission
	 */
	private int userID;
	
	/**
	 * Die id der Notiz der Permission
	 */
	private int nID;
	
	/**
	 * Die id des Notizbuchs der Permission
	 */
	private int nbID;
	
	/**
	 * Legt fest, ob der Nutzer der Eigentuemer der Notiz oder des Notizbuchs ist.
	 */
	private boolean isOwner;
	
	/*
	 * Get-/Set-Operations
	 */
	
	/**
	 * Rueckgabe der permissionID
	 * @return permissionID
	 */
	public int getPermissionID(){
		return permissionID;
	}
	
	/**
	 * Setzen der permissionID
	 * @param permissionID
	 */
	public void setPermissionID(int permissionID){
		this.permissionID = permissionID;
	}
	
	/**
	 * Rueckgabe des permissiontypes
	 * @return permissiontype
	 */

	public int getPermissionType(){
		return permissiontype;
	}
	
	/**
	 * Setzen des permissiontypes
	 * @param permissiontype
	 */
	public void setPermissionType(int permissiontype){
		this.permissiontype = permissiontype;
	}
	
	/**
	 * Rueckgabe der userID
	 * @return userID
	 */
	public int getUserID(){
		return userID;
	}
	
	/**
	 * Setzen der userID
	 * @param userID
	 */
	public void setUserID(int userID){
		this.userID = userID;
	}
	
	/**
	 * Rueckgabe der NotizID
	 * @return nID
	 */
	public int getNID(){
		return nID;
	}
	
	/**
	 * Setzen der NotizID
	 * @param nID
	 */
	public void setNID(int nID){
		this.nID = nID;
	}
	
	/**
	 * Rueckgabe der NotizbuchID
	 * @return nbID
	 */
	public int getNbID(){
		return nbID;
	}
	
	/**
	 * Setzen der NotizbuchID
	 * @param nbID
	 */
	public void setNbID(int nbID){
		this.nbID = nbID;
	}
	
	/**
	 * Rueckgabe des Eigentuemerwertes
	 * @return isOwner
	 */
	public boolean getIsOwner(){
		return isOwner;
	}

	/**
	 * Setzen des Eigentuemerwertes
	 * @param b
	 */
	public void setIsOwner(boolean b) {
		this.isOwner = b;
		
	}
}
