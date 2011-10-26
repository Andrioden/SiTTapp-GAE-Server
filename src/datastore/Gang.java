package datastore;


import javax.jdo.PersistenceManager;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
//import com.google.appengine.api.datastore.Key;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@PersistenceCapable
public class Gang {
	// VARIABLES
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
    
    @Persistent
    private String name;
    
	@Persistent
	private Set<String> users = new HashSet<String>();
	
	// TEMPORARY VARIABLES
	@NotPersistent
	public ArrayList<User> userobjs;
	
	// CONSTRUCTOR
    public Gang(String name) {
    	this.name = name;
    }
    
	// GETTERS AND SETTERS
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<String> getUsers() {
		return users;
	}
    
    // OTHER METHODS
	
	/**
	 * This method loads and sets the necessary variables for generation
	 * of JSON data.
	 */
	public void initJSONvars() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		this.userobjs = new ArrayList<User>();
		for (String name : this.getUsers()) {
			User user = pm.getObjectById(User.class, name);
			this.userobjs.add(user);
		}
	}
	
	public void addUser(String name) {
		users.add(name);
	}
	
	public String toString() {
		String str = name +"("+ id +") - users:";
		for (String n : users) {
			str += " "+n;
		}
		return str;
	}
}