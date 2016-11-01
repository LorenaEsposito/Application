package com.hdm.Application.shared;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import com.hdm.Application.shared.bo.*;

import com.hdm.Application.shared.NoteAdministration;

/**
 * Das asynchrone Gegenstueck des Interface {@link NoteAdministration} fuer
 * RPCs, um die Geschaeftsobjekte zu verwalten. Es wird semiautomatisch durch das
 * Google Plugin erstellt und gepflegt. Daher erfolgt hier keine weitere
 * Dokumentation. Fuer weitere Informationen: siehe das synchrone Interface
 * {@link NoteAdministration}
 * 
 * @author Lorena Esposito
 */

public interface NoteAdministrationAsync {

	public void init(AsyncCallback<Void> callback) throws IllegalArgumentException;
	
}
