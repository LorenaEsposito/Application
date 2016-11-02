package com.hdm.Application.server;

import java.util.ArrayList;
import java.util.Vector;

import com.hdm.Application.server.db.*;
import com.hdm.Application.shared.*;
import com.hdm.Application.shared.bo.*;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.hdm.Application.shared.NoteAdministration;
import com.hdm.Application.shared.NoteAdministrationAsync;


@SuppressWarnings("serial")
public class NoteAdministrationImpl extends RemoteServiceServlet
    implements NoteAdministration {

     

  /**
   * Referenz auf das zugehörige Note-Objekt.
   */
  private Note Note = null;
private UserMapper uMapper;
//private NoteMapper nMapper;
//private PermissionMapper pMapper;
//private NotebookMapper nbMapper;
//private DueDateMapper ddMapper;

   
  public NoteAdministrationImpl() throws IllegalArgumentException {

  }

public void init() throws IllegalArgumentException {

    this.uMapper = UserMapper.userMapper();
    //this.nMapper = NoteMapper.noteMapper();
    //this.pMapper = PermissionMapper.permissionMapper();
    //this.ddMapper = DueDateMapper.duedateMapper();
    //this.nbMapper = NotebookMapper.notebookMapper();
    
  }

public void delete(User u) throws IllegalArgumentException {
    this.uMapper.delete(u);
  }
//public void delete(Note n) throws IllegalArgumentException {
//    this.nMapper.delete(n);
//  }
//public void delete(Permission p) throws IllegalArgumentException {
//    this.pMapper.delete(p);
//  }
//public void delete(DueDate d) throws IllegalArgumentException {
//    this.ddMapper.delete(d);
//  }
//public void delete(Notebook no) throws IllegalArgumentException {
//    this.nbMapper.delete(no);
//  }

}



























