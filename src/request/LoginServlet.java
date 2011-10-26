package request;

import java.io.IOException;
import javax.jdo.PersistenceManager;
import javax.servlet.http.*;
import datastore.*;
import javax.jdo.JDOObjectNotFoundException;
import com.google.gson.Gson;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		DatastoreUtils dsutils = new DatastoreUtils();
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Gson gson = new Gson();
		// Fetch parameters from request
		String loginName = req.getParameter("name");
		if ((loginName == "")||(loginName == null)) {
			System.out.println("User/Login name not given, aborted.");
			return;
		}
		// Build the object that will be converted to JSON with gson.
		try {
			User user = pm.getObjectById(User.class, loginName);
			user.initJSONvars();
			String jsonString = gson.toJson(user);
			dsutils.returnJSON(resp, jsonString); // Send data
		} catch (JDOObjectNotFoundException e) {
			boolean suc = dsutils.create(new User(loginName));
			if (suc) {
				User user = pm.getObjectById(User.class, loginName);
				user.initJSONvars();
				String jsonString = gson.toJson(user);
				dsutils.returnJSON(resp, jsonString);
				System.out.println("User created: "+loginName);
			}
			else {
				System.out.println("Failed to find or create user with name: "+loginName);
			}
		} finally {
			pm.close();
		}
	}
}
