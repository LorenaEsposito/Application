package com.hdm.Application.client.gui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.hdm.Application.shared.bo.UserPermission;



/**
 * Die Klasse dient zur Darstellung von "UserPermission" Objekten und dient zur Erzeugung von HTML-Code.
 *  In diesem Fall wird die Googelmail eines Users mit einem vorangestellten "Mail " in einem <code>div-</code>Element
 * erzeugt.
 * Ebenfalls wird der Berechtigungstyp eines Users mit einem vorangestellten "Berechtigungstyp" als String umgewandelt ausgegeben.
 * 
 *
 */
public class UserPermissionCell extends AbstractCell<UserPermission>{

	@Override
	public void render(Context context, UserPermission value, SafeHtmlBuilder sb) {
		
		if (value == null){
			return;
		}
		
		sb.appendHtmlConstant("<div Mail>");
		sb.appendEscaped(value.getMail());
		sb.appendHtmlConstant("</div>");
		sb.appendHtmlConstant("<div Berechtigung>");
		sb.appendEscaped(value.getPermissionTypeAsString());
		sb.appendHtmlConstant("</div>");
		
	}
	
	

}
