package request;

import java.io.IOException;
import javax.jdo.PersistenceManager;
import java.util.List;
import javax.jdo.Query;
import javax.servlet.http.*;
import datastore.*;
import javax.jdo.JDOObjectNotFoundException;
import com.google.gson.Gson;
import java.util.logging.Logger;

@SuppressWarnings("serial")
public class UserListServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(UserListServlet.class.getName());
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		DatastoreUtils dsutils = new DatastoreUtils();
		PersistenceManager pm = PMF.get().getPersistenceManager();
		// Build the object that will be converted to JSON with gson.
		Gson gson = new Gson();
		Query query = pm.newQuery(User.class);
	    try {
	        List<User> results = (List<User>) query.execute();
	        if (!results.isEmpty()) {
	        	log.info("Found "+results.size()+" users.");
	        	String jsonString = gson.toJson(results);
	        	dsutils.returnJSON(resp, jsonString);
	        } else {
	        	log.info("No users found.");
	        	dsutils.returnJSON(resp, "{\"MSG\": \"NOINVITE\"}");
	        }
	    } finally {
	        query.closeAll();
	    }
	}
}
