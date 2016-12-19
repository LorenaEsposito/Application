package com.hdm.Application.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import com.hdm.Application.server.db.*;
import com.hdm.Application.shared.*;
import com.hdm.Application.shared.bo.*;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import com.hdm.Application.shared.NoteAdministration;
import com.hdm.Application.shared.NoteAdministrationAsync;


@SuppressWarnings("serial")
public class NoteAdministrationImpl extends RemoteServiceServlet
    implements NoteAdministration {

	/**
	 * Eindeutige SerialVersion ID. Wird zum Serialisieren der Klasse benoetigt.
	 */
	private static final long serialVersionUID = 1L;
    
	private AppUser currentUser = null;

  /**
   * Referenz auf das zugehörige Note-Objekt.
   */
  private Note note = null;
  
  private Date date = new Date();
  
  /**
	 * Referenz auf den DatenbankMapper, der Userobjekte mit der Datenbank
	 * abgleicht.
	 */
private UserMapper uMapper = null;

/**
 * Referenz auf den DatenbankMapper, der Noteobjekte mit der Datenbank
 * abgleicht.
 */
private NoteMapper nMapper;

/**
 * Referenz auf den DatenbankMapper, der Permissionobjekte mit der Datenbank
 * abgleicht.
 */
private PermissionMapper pMapper;

/**
 * Referenz auf den DatenbankMapper, der Notebookobjekte mit der Datenbank
 * abgleicht.
 */
private NotebookMapper nbMapper;

/**
 * Referenz auf den DatenbankMapper, der Duedateobjekte mit der Datenbank
 * abgleicht.
 */
private DueDateMapper ddMapper;

   
  public NoteAdministrationImpl() throws IllegalArgumentException {

  }

public void init() throws IllegalArgumentException {

    this.uMapper = UserMapper.userMapper();
    this.nMapper = NoteMapper.noteMapper();
    this.pMapper = PermissionMapper.permissionMapper();
    this.ddMapper = DueDateMapper.dueDateMapper();
    this.nbMapper = NotebookMapper.notebookMapper();
    
  }

/*
 * Auslesen des aktuellen Benutzernamen aus der Google Accounts API, um
 * das Profil des aktuellen Benutzers aus der Datenbank zu lesen.
 */
public AppUser getCurrentUser() throws IllegalArgumentException {
	AppUser currentUser = new AppUser();
	UserService userService = UserServiceFactory.getUserService();
	User user = userService.getCurrentUser();
	int atIndex = user.getEmail().indexOf("@");
	String userName = user.getEmail().substring(0, atIndex);
	currentUser = this.getUserByGoogleID(userName);
	return currentUser;
}

public AppUser getUserByGoogleID(String name){
	ArrayList<AppUser> users = new ArrayList<AppUser>();
	
	if (this.uMapper.findByGoogleID(name) != null){
		AppUser user = new AppUser();
		user = this.uMapper.findByGoogleID(name);
		users.add(user);
	}
	else{
		AppUser cUser = new AppUser();
		cUser.setGoogleID(name);
		this.createUser(cUser);
		
		Notebook notebook = new Notebook();
		notebook.setNbTitle("Dein erstes eigenes Notizbuch");
		notebook.setNbCreDate(date);
		notebook.setNbModDate(date);
		this.createNotebook(notebook);
		
		Permission permission = new Permission();
		permission.setNbID(notebook.getNbID());
		permission.setUserID(cUser.getUserID());
		permission.setNID(0);
		permission.setIsOwner(true);
		permission.setPermissionType(true);
		this.createPermission(permission);
		
		AppUser user2 = new AppUser();
		user2 = this.uMapper.findByGoogleID(name);
		users.add(user2);
	}
	return users.get(0);
}

/**
 * Erstellt einen neuen User in der Datenbank. Dazu ruft sie mit dem
 * uebergebenen User den UserMapper auf, der dieses dann ueber eine
 * INSERT-Abfrage in die Datenbank einfuegt.
 * 
 * @author Lorena Esposito
 * @param u
 *            Der User, der in die Datenbank eingefuegt werden soll
 */
@Override
public void createUser(AppUser u) throws IllegalArgumentException{
	this.uMapper.insert(u);
}

/**
 * Aktualisiert die Attribute eines Users in der
 * Datenbank. Dazu ruft sie mit dem uebergebenen User den UserMapper auf,
 * der dieses dann ueber eine UPDATE-Abfrage in der Datenbank aktualisiert.
 * 
 * @author Lorena Esposito
 * @param u
 *            Der User, der aktualisiert werden soll
 */
@Override
public void editUser(AppUser u) throws IllegalArgumentException{
	this.uMapper.edit(u);
}

/**
 * Loescht den uebergebenen User endgueltig aus der Datenbank. Die Methode
 * ruft dazu, alle notwendigen Mapper (Notebook, Note, Permission) 
 * mit dem uebergebenen User auf, um ein
 * vollstaendiges Loeschen aller Userdaten sicherzustellen.
 * 
 * @author Lorena Esposito
 * @param u
 *            Der User, der aus der Datenbank entfernt werden soll
 */

@Override
public void deleteUser(AppUser u) throws IllegalArgumentException {
    this.uMapper.delete(u);
    
    Vector<Permission> vector = new Vector<Permission>();
    vector = this.pMapper.findOwnerByUserId(u.getUserID());
    ArrayList<Permission> permissions = new ArrayList<Permission>(vector);
    for(int i = 0; i < permissions.size(); i++){
    	this.pMapper.delete(permissions.get(i));
    	this.nMapper.deleteNote(this.nMapper.findByID(permissions.get(i).getNID()));
    	this.nbMapper.deleteNotebook(this.nbMapper.findById(permissions.get(i).getNbID()));
    }
  }

/**
 * Erstellt ein neues Notebook in der Datenbank. Dazu ruft sie mit dem
 * uebergebenen Notebook den NotebookMapper auf, der dieses dann ueber eine
 * INSERT-Abfrage in die Datenbank einfuegt.
 * 
 * @author Lorena Esposito
 * @param nb
 *            Das Notebook, das in die Datenbank eingefuegt werden soll
 */
@Override
public Notebook createNotebook(Notebook nb) throws IllegalArgumentException {
	Notebook notebook = new Notebook();
	notebook = this.nbMapper.createNotebook(nb);
	return notebook;
}

/**
 * Aktualisiert die Attribute eines Notebooks in der
 * Datenbank. Dazu ruft sie mit dem uebergebenen Notebook den NotebookMapper auf,
 * der dieses dann ueber eine UPDATE-Abfrage in der Datenbank aktualisiert.
 * 
 * @author Lorena Esposito
 * @param nb
 *            Das Notebook, das aktualisiert werden soll
 */
@Override
public void editNotebook(Notebook nb) throws IllegalArgumentException {
this.nbMapper.updateNotebook(nb);
}

/**
 * Loescht das uebergebene Notebook endgueltig aus der Datenbank. Die Methode
 * ruft dazu, alle notwendigen Mapper (Note, Permission) 
 * mit dem uebergebenen Notebook auf, um ein
 * vollstaendiges Loeschen aller Notebookdaten sicherzustellen.
 * 
 * @author Lorena Esposito
 * @param nb
 *            Das Notebook, das aus der Datenbank entfernt werden soll
 */
@Override
public void deleteNotebook(Notebook nb) throws IllegalArgumentException {
this.nbMapper.deleteNotebook(nb);

Vector<Note> vector = new Vector<Note>();
vector = this.nMapper.findByNotebook(nb);
ArrayList<Note> notes = new ArrayList<Note>(vector);

for(int i = 0; i < notes.size(); i++){
	this.nMapper.deleteNote(notes.get(i));
}

Vector<Permission> vector2 = new Vector<Permission>();
vector2 = this.pMapper.findByNotebook(nb);
ArrayList<Permission> permissions = new ArrayList<Permission>(vector2);

for(int i = 0; i < permissions.size(); i++){
	this.pMapper.delete(permissions.get(i));
}
//this.nMapper.delete(nb);
//this.pMapper.delete(nb);
	}

/**
 * Erstellt eine neue Note in der Datenbank. Dazu ruft sie mit der
 * uebergebenen Note den NoteMapper auf, der diese dann ueber eine
 * INSERT-Abfrage in die Datenbank einfuegt.
 * 
 * @author Lorena Esposito
 * @param n
 *            Die Note, die in die Datenbank eingefuegt werden soll
 */
@Override
public Note createNote(Note n) throws IllegalArgumentException {
	Note note = new Note();
	note = this.nMapper.createNote(n);
	return note;
}

/**
 * Aktualisiert die Attribute einer Note in der
 * Datenbank. Dazu ruft sie mit der uebergebenen Note den NoteMapper auf,
 * der diese dann ueber eine UPDATE-Abfrage in der Datenbank aktualisiert.
 * 
 * @author Lorena Esposito
 * @param n
 *            Die Note, die aktualisiert werden soll
 */
@Override
public void editNote(Note n) throws IllegalArgumentException {
this.nMapper.updateNote(n);
}

/**
 * Loescht die uebergebene Note endgueltig aus der Datenbank. Die Methode
 * ruft dazu, alle notwendigen Mapper (Permission) 
 * mit der uebergebenen Note auf, um ein
 * vollstaendiges Loeschen aller Notedaten sicherzustellen.
 * 
 * @author Lorena Esposito
 * @param n
 *            Die Note, die aus der Datenbank entfernt werden soll
 */
@Override
public void deleteNote(Note n) throws IllegalArgumentException {
    this.nMapper.deleteNote(n);
//	this.pMapper.delete(n);
  }

/**
 * Erstellt eine neue Permission in der Datenbank. Dazu ruft sie mit der
 * uebergebenen Permission den PermissionMapper auf, der diese dann ueber eine
 * INSERT-Abfrage in die Datenbank einfuegt.
 * 
 * @author Lorena Esposito
 * @param p
 *            Die Permission, die in die Datenbank eingefuegt werden soll
 */
@Override
public void createPermission(Permission p) throws IllegalArgumentException{
	this.pMapper.insert(p);
}

/**
 * Aktualisiert die Attribute einer Permission in der
 * Datenbank. Dazu ruft sie mit der uebergebenen Permission den PermissionMapper auf,
 * der dieses dann ueber eine UPDATE-Abfrage in der Datenbank aktualisiert.
 * 
 * @author Lorena Esposito
 * @param p
 *            Die Permission, die aktualisiert werden soll
 */
@Override
public void editPermission(Permission p) throws IllegalArgumentException{
//	this.pMapper.edit(p);
}
/**
 * Loescht die uebergebene Permission endgueltig aus der Datenbank.
 * 
 * @author Andra Weirich
 * @param p
 *            Die Permission, die aus der Datenbank entfernt werden soll
 */
@Override
public void deletePermission(Permission p) throws IllegalArgumentException {
    this.pMapper.delete(p);
  }

/**
 * Erstellt ein neues Duedate in der Datenbank. Dazu ruft sie mit dem
 * uebergebenen Duedate den DuedateMapper auf, der diese dann ueber eine
 * INSERT-Abfrage in die Datenbank einfuegt.
 * 
 * @author Lorena Esposito
 * @param dd
 *            Das Duedate, das in die Datenbank eingefuegt werden soll
 */
@Override
public void createDuedate(DueDate dd) throws IllegalArgumentException{
	this.ddMapper.createDueDate(dd);
}

/**
 * Aktualisiert die Attribute eines Duedates in der
 * Datenbank. Dazu ruft sie mit dem uebergebenen Duedate den DuedateMapper auf,
 * der dieses dann ueber eine UPDATE-Abfrage in der Datenbank aktualisiert.
 * 
 * @author Lorena Esposito
 * @param dd
 *            Das Duedate, das aktualisiert werden soll
 */
@Override
public void editDuedate(DueDate dd) throws IllegalArgumentException{
	this.ddMapper.updateDueDate(dd);
}
/**
 * Loescht das uebergebene Duedate endgueltig aus der Datenbank.
 * 
 * @author Andra Weirich
 * @param dd
 *            Das Duedate, das aus der Datenbank entfernt werden soll
 */
@Override
public void deleteDuedate(DueDate dd) throws IllegalArgumentException {
    this.ddMapper.deleteDueDate(dd);
  }




/**
 * Es kann nach einem bestimmten User anhand seines Namens gesucht werden.
 * Dazu wird der UserMapper aufgerufen, der eine Methode beinhaltet mit der in
 * der Datenbank nach dem gesuchten Namen gesucht wird. In der Methode wird
 * eine ArrayList erstellt, die mit den Suchergebnissen befuellt wird.
 * 
 * @author Lorena Esposito
 * @param userName
 * @return users
 * @throws IllegalArgumentException
 */
public ArrayList<AppUser> searchForUser(String userName) throws IllegalArgumentException{
	Vector<AppUser> vector = new Vector<AppUser>();
	vector = this.uMapper.findByName(userName);
	ArrayList<AppUser> users = new ArrayList<AppUser>(vector);
	return users;
}

/**
 * Es kann nach einem bestimmten Notebook anhand seines Titels gesucht werden.
 * Dazu wird der NotebookMapper aufgerufen, der eine Methode beinhaltet mit der
 * in der Datenbank nach einem bestimmten Titel gesucht wird. In der Methode wird
 * eine ArrayList erstellt, die mit den Suchergebnissen befuellt wird.
 * 
 * @author Lorena Esposito
 * @param title
 * @return notebooks
 * @throws IllegalArgumentException
 */
public ArrayList<Notebook> searchForNotebook(String title) throws IllegalArgumentException{
	Vector<Notebook> vector = new Vector<Notebook>();
	vector = this.nbMapper.findByTitle(title);
	
	ArrayList<Notebook> notebooks = new ArrayList<Notebook>(vector);
	
	return notebooks;

}

/**
 *Es kann nach einer bestimmten Note anhand ihres Titels gesucht werden.
 * Dazu wird der NoteMapper aufgerufen, der eine Methode beinhaltet mit der
 * in der Datenbank nach einem bestimmten Titel gesucht wird. In der Methode wird
 * eine ArrayList erstellt, die mit den Suchergebnissen befuellt wird. 
 * 
 * @author Lorena Esposito
 * @param title
 * @return notes
 * @throws IllegalArgumentException
 */
  public ArrayList<Note> searchForNote(String title) throws IllegalArgumentException{
	Vector<Note> vector = new Vector<Note>();
	vector = this.nMapper.findByTitle(title);
	
	ArrayList<Note> notes = new ArrayList<Note>(vector);

	return notes;
  }

  /**
   *Es kann nach einer bestimmten Note anhand ihres Faelligkeitsdatums gesucht werden.
   * Dazu wird der NoteMapper aufgerufen, der eine Methode beinhaltet mit der
   * in der Datenbank nach einem bestimmten Faelligkeitsdatum gesucht wird. In der Methode wird
   * eine ArrayList erstellt, die mit den Suchergebnissen befuellt wird. 
   * 
   * @author Lorena Esposito
   * @param duedate
   * @return notes
   * @throws IllegalArgumentException
   */
    public ArrayList<Note> searchForNoteByDD(Date duedate) throws IllegalArgumentException{
  	Vector<Note> vector = new Vector<Note>();
//  	vector = this.nMapper.findByDuedate(duedate);
  	ArrayList<Note> notes = new ArrayList<Note>(vector);

  	return notes;
    }
    
    /**
     * In dieser Methode kann anhand eines Users nach dessen Notizbuechern gesucht werden.
     * Diese werden dann in einer ArrayList zurueck gegeben. Ist noch kein Notizbuch angelegt
     * wird eins angelegt, zusammen mit einer Permission.
     */
    public ArrayList<Notebook> getNotebooksOfUser(AppUser u) throws IllegalArgumentException{
    	
    	Vector<Notebook> vector = this.nbMapper.findByUser(u);
   	
    	if(vector == null){
    		Notebook notebook = new Notebook();
    		notebook.setNbTitle("Dein erstes eigenes Notizbuch");
    		notebook.setNbCreDate(date);
    		notebook.setNbModDate(date);
    		this.createNotebook(notebook);
    		Permission permission = new Permission();
    		permission.setNbID(notebook.getNbID());
    		permission.setUserID(u.getUserID());
    		permission.setNID(0);
    		permission.setIsOwner(true);
    		permission.setPermissionType(true);
    		this.createPermission(permission);
    		vector = this.nbMapper.findByUser(u);
    	}
    	
    	if(vector != null){
    	vector = this.nbMapper.findByUser(u);
    	}

    	ArrayList<Notebook> notebooks = new ArrayList<Notebook>(vector);
    	return notebooks;
    }
    
    /**
     * Diese Methode gibt alle Notizen eines Notizbuchs in einer ArrayList zurueck.
     * 
     * @param nbTitle
     * @return ArrayList<Note>
     * @throws IllegalArgumentException
     */
    public ArrayList<Note> getNotesOfNotebook(String nbTitle, AppUser u) throws IllegalArgumentException{
    	Vector<Note> vector = new Vector<Note>();

    	Notebook nb = new Notebook();
    	
    	Vector<Notebook> nbVector = new Vector<Notebook>();
    	nbVector = this.nbMapper.findByTitle(nbTitle);

    	Vector<Notebook> nbVector2 = new Vector<Notebook>();
    	
  
    	nbVector2 = this.nbMapper.findByUser(u);

    	for(int i = 0; i < nbVector.size(); i++){
    		for(int x = 0; x < nbVector2.size(); x++){	
    			if(nbVector.get(i).getNbID() == nbVector2.get(x).getNbID()){
    				nb = nbVector.get(i);
    			}
    		}
    	}	
    	
    	if(this.nMapper.findByNotebook(nb) == null){
    		Note note = new Note();
    		note.setnTitle("Erste Notiz");
    		note.setNbID(nb.getNbID());
    		this.createNote(note);
    		vector = this.nMapper.findByNotebook(nb);
    	}    	
    	if(this.nbMapper.findById(nb.getNbID()) != null){
    		vector = this.nMapper.findByNotebook(nb);
    	}
    	
    	ArrayList<Note> notes = new ArrayList<Note>(vector);

    	return notes;
    }
    
    /**
     * Diese Methode ruft alle Notizbuecher eines Users, die von ihm erstellt wurden.
     * @author Lorena Esposito
     * @param user
     * @return ArrayList<Permission> permissions
     */
    public ArrayList<Permission> getOwnedNotebookPermissions(AppUser user){
    	Vector<Permission> vector = new Vector<Permission>();
    	vector = this.pMapper.findOwnedNotebooks(user.getUserID());
    	ArrayList<Permission> permissions = new ArrayList<Permission>(vector);
    	return permissions;
    }
    
    public ArrayList<Notebook> getOwnedNotebooks(AppUser user){
    	Vector<Permission> vector = new Vector<Permission>();
    	vector = this.pMapper.findOwnedNotebooks(user.getUserID());
    	ArrayList<Permission> permissions = new ArrayList<Permission>(vector);
    	ArrayList<Notebook> notebooks = new ArrayList<Notebook>();
    	for(int i = 0; i < permissions.size(); i++){
    		Notebook notebook = this.nbMapper.findById(permissions.get(i).getNbID());
    		notebooks.add(notebook);
    	}
    	return notebooks;
    }
    
    /**
     * Diese Methode sucht einen bestimmten Nutzer anhand seiner GoogleID
     * 
     * @param googleID
     * @return AppUser user
     */
    public AppUser searchUserByGoogleID(String googleID){
    	AppUser user = new AppUser();
    	
    	if(this.uMapper.findByGoogleID(googleID) == null){
    		user = null;
    	}
    	
    	if(this.uMapper.findByGoogleID(googleID) != null){
    		user = this.uMapper.findByGoogleID(googleID);
    	}
    	return user;
    }
}
