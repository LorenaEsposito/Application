package com.hdm.Application.client.gui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.hdm.Application.shared.bo.Notebook;

public class NotebookCell extends AbstractCell<Notebook> {
	
	@Override
    public void render(Context context, Notebook value, SafeHtmlBuilder sb) {
      // Value can be null, so do a null check..
      if (value == null) {
        return;
      }

      sb.appendHtmlConstant("<div>");
      sb.appendEscaped(value.getNbTitle());
      sb.appendHtmlConstant("</div>");
    }

}
