package com.hdm.Application.server;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import com.hdm.Application.server.db.*;
import com.hdm.Application.shared.bo.*;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import com.hdm.Application.shared.NoteAdministration;

/**
 * Implementierungsklasse des Interface {@link AdministationService}. Diese
 * Klasse ist <em>die</em> Klasse, die saemtliche Applikationslogik (oder engl.
 * Business Logic) aggregiert. Die Applikationslogik findet sich in den Methoden
 * dieser Klasse. Diese Klasse steht mit einer Reihe weiterer Datentypen in
 * Verbindung. Dies sind:
 * <ol>
 * <li>{@link AdministrationService}: Dies ist das <em>lokale</em> - also
 * Server-seitige - Interface, das die im System zur Verfuegung gestellten
 * Funktionen deklariert.</li>
 * <li>{@link AdministrationServiceAsync}:
 * <code>AdministartionServiceImpl</code> und <code>AdministrationService</code>
 * bilden nur die Server-seitige Sicht der Applikationslogik ab. Diese basiert
 * vollstaendig auf synchronen Funktionsaufrufen. Wir muessen jedoch in der Lage
 * sein, Client-seitige asynchrone Aufrufe zu bedienen. Dies bedingt ein
 * weiteres Interface, das in der Regel genauso benannt wird, wie das synchrone
 * Interface, jedoch mit dem zusaetzlichen Suffix "Async". Es steht nur
 * mittelbar mit dieser Klasse in Verbindung. Die Erstellung und Pflege der
 * Async Interfaces wird durch das Google Plugin semiautomatisch unterstuetzt.
 * Weitere Informationen unter {@link AdministrationServiceAsync}.</li>
 * <li>{@link RemoteServiceServlet}: Jede Server-seitig instantiierbare und
 * Client-seitig ueber GWT RPC nutzbare Klasse muss die Klasse
 * <code>RemoteServiceServlet</code> implementieren. Sie legt die funktionale
 * Basis fuer die Anbindung von <code>AdministrationServiceImpl</code> an die
 * Runtime des GWT RPC-Mechanismus.</li>
 * </ol>
 * <b>Wichtiger Hinweis:</b> Diese Klasse bedient sich sogenannter
 * Mapper-Klassen. Sie gehoeren der Datenbank-Schicht an und bilden die
 * objektorientierte Sicht der Applikationslogik auf die relationale
 * organisierte Datenbank ab. Beachten Sie, dass saemtliche Methoden, die
 * mittels GWT RPC aufgerufen werden koennen ein
 * <code>throws IllegalArgumentException</code> in der Methodendeklaration
 * aufweisen. Diese Methoden duerfen also Instanzen von
 * {@link IllegalArgumentException} auswerfen. Mit diesen Exceptions koennen
 * z.B. Probleme auf der Server-Seite in einfacher Weise auf die Client-Seite
 * transportiert und dort systematisch in einem Catch-Block abgearbeitet werden.
 */

@SuppressWarnings("serial")
public class NoteAdministrationImpl extends RemoteServiceServlet
    implements NoteAdministration {

	/**
	 * Eindeutige SerialVersion ID. Wird zum Serialisieren der Klasse benoetigt.
	 */
	private static final long serialVersionUID = 1L;
    
	//private AppUser currentUser = null;

  /**
   * Referenz auf das zugehörige Note-Objekt.
   */

  //private Note note = null;
  
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
public NoteMapper nMapper;

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

/**
 * Auslesen des aktuellen Benutzernamen aus der Google Accounts API, um
 * das Profil des aktuellen Benutzers aus der Datenbank zu lesen.
 */
public AppUser getCurrentUser() throws IllegalArgumentException {
	AppUser currentUser = new AppUser();
	UserService userService = UserServiceFactory.getUserService();
	User user = userService.getCurrentUser();
	String userName = user.getEmail();
	currentUser = this.getUserByMail(userName);
	return currentUser;
}

/**
 * Auslesen des Users anhand seiner Mailadresse. Ist noch kein User unter dieser Mailadresse angelegt wird dies
 * in der Methode gemacht. Inklusive Notizbuch und Notiz sowie der Berechtigung.
 */
public AppUser getUserByMail(String mail){
	AppUser user = new AppUser();
	if (this.uMapper.findByMail(mail) != null){
		user = this.uMapper.findByMail(mail);
	}
	else{
		AppUser newUser = new AppUser();
		newUser.setMail(mail);
		user = this.createUser(newUser);
		
		Notebook notebook = new Notebook();
		notebook.setNbTitle("Dein erstes eigenes Notizbuch");
		notebook.setNbCreDate(date);
		notebook.setNbModDate(date);
		this.createNotebook(notebook);
		
		ArrayList<Permission> permissions = new ArrayList<Permission>();
		Permission permission = new Permission();
		permission.setNbID(notebook.getNbID());
		permission.setUserID(user.getUserID());
		permission.setNID(0);
		permission.setIsOwner(true);
		permission.setPermissionType(3);
		permissions.add(permission);
		this.createPermissions(permissions);
		
	}
	return user;
}

/**
 * Auslesen eines Users anhand seiner UserID.
 */
public AppUser getUserByID(int userID){
	AppUser user = new AppUser();
	user = this.uMapper.findByKey(userID);
	return user;
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
public AppUser createUser(AppUser u) throws IllegalArgumentException{
	AppUser user = new AppUser();
	user = this.uMapper.insert(u);
	return user;
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
public AppUser editUser(AppUser u) throws IllegalArgumentException{
	AppUser user = new AppUser();
	user = this.uMapper.edit(u);
	return user;
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
    Vector<Permission> vector1 = new Vector<Permission>();
    vector1 = this.pMapper.findByUserID(u.getUserID());
    for(int y = 0; y< vector1.size(); y++){
    	if(vector1.get(y).getIsOwner() == true && vector1.get(y).getNID() == 0){
    		this.deleteNotebook(this.nbMapper.findById(vector1.get(y).getNbID()));
    	}
    	if(vector1.get(y).getIsOwner() == true && vector1.get(y).getNID() != 0){
    		this.deleteNote(this.nMapper.findByID(vector1.get(y).getNID()));
    	}
    	this.pMapper.delete(vector1.get(y));
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
	
	Note note = new Note();
	note.setNbID(notebook.getNbID());
	note.setnTitle("Erste Notiz");
	note.setnSubtitle("");
	note.setnContent("");
	note.setnCreDate(date);
	note.setnModDate(date);
	this.createNote(note);
	
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
public Notebook editNotebook(Notebook nb) throws IllegalArgumentException {
	Notebook notebook = new Notebook();
	this.nbMapper.updateNotebook(nb);
	return notebook;
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
vector2 = this.pMapper.findByNotebookID(nb.getNbID());
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
public Note editNote(Note n) throws IllegalArgumentException {
Note note = this.nMapper.updateNote(n);
return note;
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
    Vector<Permission> vector1 = new Vector<Permission>();
    vector1 = this.pMapper.findByNoteID(n.getnID());
    if(vector1.size() != 0){
    	for(int i = 0; i < vector1.size(); i++){
	this.pMapper.delete(vector1.get(i));
    	}
    }
    DueDate duedate = new DueDate();
    duedate = this.ddMapper.findByNoteID(n.getnID());
    if(duedate != null){
	this.ddMapper.delete(duedate);
    }
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
public void createPermissions(ArrayList<Permission> p) throws IllegalArgumentException{
	for(int i = 0; i < p.size(); i++){
		this.pMapper.insert(p.get(i));
	}
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
public ArrayList<Permission> editPermissions(ArrayList<Permission> p) throws IllegalArgumentException{
	ArrayList<Permission> permissions = new ArrayList<Permission>();
	for(int i = 0; i < p.size(); i++){
	permissions.add(this.pMapper.edit(p.get(i)));
	}
	return permissions;
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
    this.ddMapper.delete(dd);
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
    @SuppressWarnings("deprecation")
	public ArrayList<Note> searchForNoteByDD(Date duedate) throws IllegalArgumentException{
  	Vector<Note> vector = new Vector<Note>();
  	
  	int tag = duedate.getDate();
	 int monat = duedate.getMonth()+1;
	 int jahr = duedate.getYear()+1900;
	 System.out.println("Datumsformat gefaked: "+jahr+"-"+monat+"-"+tag);

	 String datum = jahr+"-"+monat+"-"+tag;
	Vector<Note> notes = this.nMapper.findByDueDate(java.sql.Date.valueOf(datum));	
	 System.out.println("notes size : "+notes.size());
	 ArrayList<Note> result = new ArrayList<Note>();
	 result.addAll(notes);
  	return result;
    }
    
    public Notebook getNotebookByID(int nbID) throws IllegalArgumentException{
    	Notebook notebook = new Notebook();
    	notebook = this.nbMapper.findById(nbID);
    	return notebook;
    }
    
    /**
     * In dieser Methode kann anhand eines Users nach dessen Notizbuechern gesucht werden.
     * Diese werden dann in einer ArrayList zurueck gegeben. Ist noch kein Notizbuch angelegt
     * wird eins angelegt, zusammen mit einer Permission.
     */
    public ArrayList<Notebook> getNotebooksOfUser(AppUser u) throws IllegalArgumentException{
    	Vector<Notebook> vector = this.nbMapper.findByUser(u);
    	ArrayList<Notebook> notebooks = new ArrayList<Notebook>();

    	if(vector.size() == 0){
    		Notebook notebook = new Notebook();
    		notebook.setNbTitle("Dein erstes eigenes Notizbuch");
    		notebook.setNbCreDate(date);
    		notebook.setNbModDate(date);
    		notebook = this.createNotebook(notebook);
    		ArrayList<Permission> permissions = new ArrayList<Permission>();
    		Permission permission = new Permission();
    		permission.setNbID(notebook.getNbID());
    		permission.setUserID(u.getUserID());
    		permission.setNID(0);
    		permission.setIsOwner(true);
    		permission.setPermissionType(3);
    		permissions.add(permission);
    		this.createPermissions(permissions);
    		vector = this.nbMapper.findByUser(u);
    	}else{
    		Boolean isExisting = new Boolean(false);
    		notebooks.add(vector.get(0));
    		for(int i = 0; i < vector.size(); i++){
    			for(int y = 0; y < notebooks.size(); y++){
    				if(vector.get(i).getNbID() == notebooks.get(y).getNbID()){
    					isExisting = true;
    				}
    			}
    			if(isExisting == false){
    				notebooks.add(vector.get(i));
    			}else{
    				isExisting = false;
    			}
    		}
    	}
    	return notebooks;
    }
    
    /**
     * Diese Methode gibt alle Notizen eines Notizbuchs in einer ArrayList zurueck.
     * 
     * @param nbTitle
     * @return ArrayList<Note>
     * @throws IllegalArgumentException
     */
    public ArrayList<Note> getNotesOfNotebookTitle(String nbTitle, AppUser u) throws IllegalArgumentException{
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
     * Diese Methode gibt alle Notizen eines Notizbuchs zurueck
     */
    public ArrayList<Note> getNotesOfNotebook(Notebook nb){
    	Vector<Note> vector = new Vector<Note>();
    	vector = this.nMapper.findByNotebook(nb);
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
    
    /**
     * Diese Methode gibt alle Notizbuecher eines Users zurueck, bei denen er Eigentuemer ist.
     */
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
     * Diese Methode gibt alle Berechtigungen einer Notiz oder eines Notizbuches aus.
     * Um Notizbuch-Berechtigungen zu erhalten muss der Parameter int nID = 0 sein.
     */
    public ArrayList<Permission> getPermissions(int nID, int nbID){
    	Vector<Permission> vector1 = new Vector<Permission>();
    	vector1 = this.pMapper.findByNotebookID(nbID);
    	ArrayList<Permission> permissions = new ArrayList<Permission>();
//    	Vector<Permission> vector2 = new Vector<Permission>();
//    	vector2 = this.pMapper.findByNoteID(nID);
    	for(int i = 0; i < vector1.size(); i++){
    		if(vector1.get(i).getNID() == 0){
    			permissions.add(vector1.get(i));
    		}
    		if(vector1.get(i).getNID() != 0){
//    			for(int y = 0; y < vector2.size(); y++){
//    				if(vector1.get(i).getNID() == vector2.get(y).getNID()){
//    					permissions.add(vector2.get(y));
//    				}
//    			}
    			if(vector1.get(i).getNID() == nID){
    				permissions.add(vector1.get(i));
    			}
    		}
    	}
//    	for(int i = 0; i < vector2.size(); i++) {
//    		permissions.add(vector2.get(i));
//    	}
    	return permissions;
    }
    
    /**
     * Diese Methode gibt eine Berechtigung fuer eine Notiz oder ein Notizbuch fuer einen bestimmten
     * Nutzer zurueck.
     */
    public Permission getPermission(int uID, int nbID, int nID){
    	Vector<Permission> vector1 = new Vector<Permission>();
    	ArrayList<Permission> permissions = new ArrayList<Permission>();
    	Permission permission = new Permission();
    	vector1 = this.pMapper.findByNotebookID(nbID);
    	
    	for(int z = 0; z < vector1.size(); z++){
    		if(vector1.get(z).getUserID() == uID){
    			permissions.add(vector1.get(z));
    		}
    	}

    	if(permissions.size() == 1){
    		permission = permissions.get(0);
    	}
    	else{
    		for(int i = 0; i < permissions.size(); i++){
    			if(permissions.get(i).getNID() == nID){
    				permission = permissions.get(i);
    				break;
    			}
    		}
    	}
    	return permission;
    }
    
    public DueDate getDuedate(int nID){
    	DueDate duedate = new DueDate();
    	duedate = this.ddMapper.findByNoteID(nID);
    	return duedate;
    }
    
    /**
     * Diese Methode sucht einen bestimmten Nutzer anhand seiner Mail
     * 
     * @param mail
     * @return AppUser user
     */
    public AppUser searchUserByMail(String mail){
    	AppUser user = new AppUser();
    	if(this.uMapper.findByMail(mail) == null){
    		user.setMail("error");
    	}else{
    	user = this.uMapper.findByMail(mail);
    	}
    	return user;
    }

	/**
	 * Diese Methode sucht einen bestimmten User anhand seiner Mail.
	 */
	public AppUser getUserByEmail(String email) throws IllegalArgumentException {
		AppUser user = new AppUser();
		user = this.uMapper.findByMail (email);
		return user;
	}
	
	/**
	 * Diese Methode gibt alle Notizen zurueck, die in der Datenbank vorhanden sind.
	 */
	public ArrayList<Note> findAll() throws IllegalArgumentException{
		Vector<Note> vector = new Vector<Note>();
		vector = this.nMapper.findAll();
		ArrayList<Note> notes = new ArrayList<Note>(vector);
		return notes;
	}

}
