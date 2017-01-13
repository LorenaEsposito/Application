package com.hdm.Application.client.gui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.hdm.Application.shared.bo.UserPermission;



/**
 * Die Klasse dient zur Darstellung von "UserPermission" Objekten und dient zur Erzeugung von HTML-Code.
 *  In diesem Fall wird die <code>id</code>
 * eines Kontoobjekts mit einem vorangestellten "Kontonnr. " in einem <code>div-</code>Element
 * erzeugt.
 * 
 * @author rathke
 *
 */
public class UserPermissionCell extends AbstractCell<UserPermission>{

	@Override
	public void render(Context context, UserPermission value, SafeHtmlBuilder sb) {
		
		if (value == null){
			return;
		}
		
		sb.appendHtmlConstant("<div> Mail");
		sb.appendEscaped(value.getMail());
		sb.appendHtmlConstant("</div>");
		sb.appendHtmlConstant("<div Berechtigung>");
		sb.append(value.getPermissionType());
		sb.appendHtmlConstant("</div>");
		
	}
	
	

}
