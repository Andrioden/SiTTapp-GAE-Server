package datastore;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import com.google.appengine.api.datastore.Key;
import java.util.Set;

@PersistenceCapable
public class User {
	
	// VARIABLES
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

	@Persistent
	private String name;
	
	@Persistent
	private Set<Key> gangs;
	
	// CONSTRUCTOR
	public User(String name) {
		this.name = name;
	}
	
	// GETTERS AND SETTERS
	public Key getKey() {
		return key;
	}
	
	public void setKey(Key key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Key> getGangs() {
		return gangs;
	}
	
	// OTHER METHODS
	
	public void addGang(Key key) {
		gangs.add(key);
	}
}