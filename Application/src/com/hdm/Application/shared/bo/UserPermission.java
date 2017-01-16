package com.hdm.Application.shared.bo;


import java.io.Serializable;

public class UserPermission implements Serializable{
	
	/*
	 * Attribute
	 */
	/**
	 * Deklaration der serialVersionUID zur Serialisierung der Objekte
	 */
	
	private static final long serialVersionUID = 1L;
	
	
	private String mail;
	private int permissionType;
	private int permissionID;

	
	
	public String getMail() {
		return mail;
	}
	
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	
	public int getPermissionType() {
		return permissionType;
	}
	
	/** Umwandeln eines integer Wertes des permissionType in einen Ausdruck als String fuer die Ausgabe des User
	 * 
	 * @return String
	 */
	
	public String getPermissionTypeAsString(){
		if(permissionType == 1){
			return "Leseberechtigung";
		}
		else if(permissionType == 2){
			return "Schreibberechtigung";
		}
		else if (permissionType == 3){
			return "Loeschberechtigung";
		}
		
		else return "Unbekannte Berechtigung";
	}
	
	
	public void setPermissionType(int permissionType) {
		this.permissionType = permissionType;
	}
	
	public int getPermissionID() {
		return permissionID;
	}
	
	public void setPermissionID(int permissionID) {
		this.permissionID = permissionID;
	}
	




	
	

}
