package com.hdm.Application.client.gui;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.hdm.Application.shared.bo.Note;
import com.hdm.Application.shared.bo.Notebook;

public class EditNotebookView extends Update {
	
	private Notebook currentNotebook = new Notebook();
	
	NotebookNotesTreeViewModel nntvm = null;
	
	protected String getHeadlineText() {
	    return "";
	}
	
	/**
	   * Erstellung aller Panels
	   */

	HorizontalPanel headlinePanel = new HorizontalPanel();
	HorizontalPanel mainPanel = new HorizontalPanel();
	
	/**
	   * Erstellung aller Widgets
	   */
	 
	Label mainheadline = new Label("Notiz bearbeiten");
	TextBox notebookTitleTB = new TextBox();
	
	protected void run() {
//	    this.append("");
	    
	    mainPanel.setStyleName("detailsPanel");
	    
	    /**
	     * Zuteilung der Widgets zum jeweiligen Panel
	     */
	    
	    headlinePanel.add(mainheadline);
	    mainPanel.add(headlinePanel);
	    mainPanel.add(notebookTitleTB);
	    
	    RootPanel.get("Details").add(mainPanel);
	    
	}
	
	public void setNntvm(NotebookNotesTreeViewModel nntvm) {
		this.nntvm = nntvm;
	}

	public void setSelected(Notebook nb) {
		if (nb != null) {
			currentNotebook = nb;
			notebookTitleTB.setText(currentNotebook.getNbTitle());
		} else {
			notebookTitleTB.setText("");
		}
	}
	
}
