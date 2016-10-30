package com.hdm.Application.shared;

import java.util.ArrayList;
import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import com.hdm.Application.shared.bo.*;

@RemoteServiceRelativePath("noteadministration")
public interface NoteAdministration extends RemoteService {

public void init() throws IllegalArgumentException;


}
