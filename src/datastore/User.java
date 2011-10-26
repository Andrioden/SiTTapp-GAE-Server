package datastore;


import javax.jdo.PersistenceManager;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
//import com.google.appengine.api.datastore.Key;
import java.util.ArrayList;
import java.util.Set;

@PersistenceCapable
public class User {
	
	// VARIABLES
    @PrimaryKey
    private String name;
	
	@Persistent
	private Set<Long> gangs;

	@Persistent
	private Set<Long> gangInvites;
	
	// TEMPORARY VARIABLES
	@NotPersistent
	public ArrayList<Gang> gangobjs;
	
	@NotPersistent
	public ArrayList<Gang> ganginvobjs;
	
	// CONSTRUCTOR
	public User(String name) {
		this.name = name;
	}
	
	// GETTERS AND SETTERS
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Long> getGangs() {
		return gangs;
	}

	public Set<Long> getGangInvites() {
		return gangInvites;
	}
	
	// OTHER METHODS
	
	/**
	 * This method loads and sets the necessary variables for generation
	 * of JSON data.
	 */
	public void initJSONvars() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		this.gangobjs = new ArrayList<Gang>();
		for (Long gid : this.getGangs()) {
			Gang gang = pm.getObjectById(Gang.class, gid);
			this.gangobjs.add(gang);
		}
		this.ganginvobjs = new ArrayList<Gang>();
		for (Long gid : this.getGangInvites()) {
			Gang gang = pm.getObjectById(Gang.class, gid);
			this.ganginvobjs.add(gang);
		}
	}
	
	public void addGang(Long Id) {
		gangs.add(Id);
	}
	
	public void addGangInvite(Long Id) {
		gangInvites.add(Id);
	}
	
	public void removeGangInvite(Long id) {
		gangInvites.remove(id);
	}
	
	public String toString() {
		String str = name+" - Invites:";
		for (Long id : gangInvites) {
			str += " "+id;
		}
		return str;
	}
}