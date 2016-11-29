package com.hdm.Application.shared.bo;

import java.io.Serializable;

import com.google.gwt.view.client.ProvidesKey;


public class AppUser implements Serializable {

	/*
	 * Attribute
	 */

	/**
	 * Deklaration der serialVersionUID zur Serialisierung der Objekte
	 */
	private static final long serialVersionUID = 1L;

	public static final ProvidesKey<AppUser> KEY_PROVIDER = null;
	
	/**
	 * Die id des Users - eindeutiger Primaerschluessel fuer die Datenbank
	 */
	private int userID;
	
	/**
	 * Die GoogleID des Users - ebenfalls eindeutiger Primaerschluessel
	 */
	private String googleID;
	
	/**
	 * Der Name des Users
	 */
	private String userName;
	
	/**
	 * Der Nachname des Users
	 */
	private String userLastName;
	
	/**
	 * Die Information, ob der User in der aktuellen Session durch einen Login
	 * mit einer bisher nicht bekannten Googlemail erstellt wurde.
	 */

	/*
	 * Get-/Set-Operations
	 */
	
	/**
	 * Rueckgabe der UserID
	 * @return userID des Users
	 */
	public int getUserID(){
		return this.userID;
	}
	
	/**
	 * Setzen der UserID
	 * @param userID
	 */
	public void setUserID(int userID){
		this.userID = userID;
	}
	
	/**
	 * Rueckgabe der GoogleID
	 * @return googleID
	 */
	public String getGoogleID(){
		return this.googleID;
	}
	
	/**
	 * Setzen der GoogleID
	 * @param googleID
	 */
	public void setGoogleID(String googleID){
		this.googleID = googleID;
	}
	
	/**
	 * Rueckgabe des Usernamens
	 * @return userName
	 */
	public String getUserName(){
		return this.userName;
	}
	
	/**
	 * Setzen des Usernamens
	 * @param userName
	 */
	public void setUserName(String userName){
		this.userName = userName;
	}
	
	/**
	 * Rueckgabe des Nachnamens des Users
	 * @return userLastName
	 */
	public String getUserLastName(){
		return this.userLastName;
	}
	
	/**
	 * Setzen des Nachnamens des Users
	 * @param userLastName
	 */
	public void setUserLastName(String userLastName){
		this.userLastName = userLastName;
	}
}