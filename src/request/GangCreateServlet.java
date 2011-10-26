package request;

import java.io.IOException;
import javax.jdo.PersistenceManager;
import javax.servlet.http.*;
import com.google.gson.Gson;
import datastore.*;
import java.util.logging.Logger;

@SuppressWarnings("serial")
public class GangCreateServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(GangCreateServlet.class.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		DatastoreUtils dsutils = new DatastoreUtils();
		String userName = req.getParameter("username");
		if ((userName == "")||(userName == null)) {
			System.out.println("Username not given, aborted.");
			return;
		}
		String gangName = req.getParameter("gangname");
		if ((gangName == "")||(gangName == null)) {
			System.out.println("Gang name not given, aborted.");
			return;
		}
		Gang newGang = new Gang(gangName);
		newGang.addUser(userName);
		boolean suc = dsutils.create(newGang);
		String jsonMessage = "";
		if (suc) {
			User user = pm.getObjectById(User.class, userName);
			user.addGang(newGang.getId());
			Gson gson = new Gson();
			log.info("Gang created: "+gangName);
			jsonMessage = gson.toJson(newGang);
		}
		else {
			log.info("Failed to create gang with name: "+gangName);
			jsonMessage = "{\"MSG\": \"ERROR\"}";
		}
		dsutils.returnJSON(resp, jsonMessage);
		pm.close();
	}
}
