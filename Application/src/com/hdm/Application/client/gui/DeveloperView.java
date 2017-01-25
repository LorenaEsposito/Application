package com.hdm.Application.client.gui;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

public class DeveloperView extends Update {
	
	protected String getHeadlineText(){
		return "";
	}
	
	/**
	 * Erstellung aller Panels
	 */
	HorizontalPanel headlinePanel = new HorizontalPanel();
	VerticalPanel developerPanel = new VerticalPanel();
	
	
	/**
	 * Erstellung aller Widgets
	 */
	Label mainheadline = new Label("Developer-Seite");
	Label developerLabel = new Label();
	Label label1 = new Label();
	Label label2 = new Label();
	Label label3 = new Label();
	Label label4 = new Label();
	Label label5 = new Label();
	Label label6 = new Label();
	Label label7 = new Label();
	Label label8 = new Label();
	Label label9 = new Label();
	Label label10 = new Label();
	Label label11 = new Label();
	Label label12 = new Label();
	Label label13 = new Label();
	Label label14 = new Label();
	Label label15 = new Label();
	Label label16 = new Label();
	Label label17 = new Label();
	Label label18 = new Label();
	Label label19 = new Label();
	
	
	protected void run() {
		this.append("");

		developerPanel.setStyleName("detailsPanel");
		headlinePanel.setStyleName("headlinePanel");
		
		headlinePanel.add(mainheadline);
		developerPanel.add(developerLabel);
		developerPanel.add(label3);
		developerPanel.add(label4);
		developerPanel.add(label5);
		developerPanel.add(label6);
		developerPanel.add(label7);
		developerPanel.add(label8);
		developerPanel.add(label9);
		developerPanel.add(label10);
		developerPanel.add(label11);
		developerPanel.add(label12);
		developerPanel.add(label13);
		developerPanel.add(label14);
		developerPanel.add(label15);
		developerPanel.add(label16);
		developerPanel.add(label17);
		RootPanel.get("Details").add(headlinePanel);
		RootPanel.get("Details").add(developerPanel);
		
		developerLabel.setText("HTML fuer einen Button:");
		

		label4.setText("<p>Klicke den Button um zu Notework weitergeleitet zu werden</p>");
		label5.setText("<button onclick=\"myFunction()\">Versuche es!</button>");
		label6.setText("<script>");
		label7.setText("function myFunction() {");
		label8.setText("    var currentPageUrlIs = \"\";");
		label9.setText("    if (typeof this.href != \"undefined\") {");
		label10.setText("       currentPageUrlIs = this.href.toString().toLowerCase();");
		label11.setText("   }else{");
		label12.setText("       currentPageUrlIs = document.location.toString().toLowerCase();");
		label13.setText("   }");
		label14.setText("   var url = 'http://1-dot-notework-152915.appspot.com/Application.html?url='+currentPageUrlIs;");
		label15.setText("   window.location.href = url;");
		label16.setText("}");
		label17.setText("</script>");

		
		
		
//		text.setText("<!DOCTYPE html>"  + \n + "<html>" \n "<body>" \n "<p>Klicke den Button um zu Notework weitergeleitet zu werden</p>"
//				\n "<button onclick="myFunction()">Versuche es!</button>" \n "<script>" \n "function myFunction() {"
//				\n "var currentPageUrlIs = "";" \n "if (typeof this.href != "undefined") {" \n "currentPageUrlIs = this.href.toString().toLowerCase();" 
//				\n "}else{" \n "currentPageUrlIs = document.location.toString().toLowerCase();" \n "}"
//				\n "var url = 'http://127.0.0.1:8888/Application.html?url='+currentPageUrlIs;" \n "window.location.href = url;"
//				\n "}" \n "</script>" \n "</body>" \n "</html>");

	}
}
