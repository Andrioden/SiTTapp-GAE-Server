package request;

import java.io.IOException;
import java.util.logging.Logger;
import javax.jdo.PersistenceManager;
import javax.servlet.http.*;
import datastore.*;

@SuppressWarnings("serial")
public class GangDeclineServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(GangDeclineServlet.class.getName());
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		DatastoreUtils dsutils = new DatastoreUtils();
		// Fetch data from request
		String gangId = req.getParameter("gangid");
		if ((gangId == "")||(gangId == null)) {
			log.info("Gang id not given, aborted.");
			return;
		}
		Gang gang = pm.getObjectById(Gang.class, Long.parseLong(gangId.trim()));
		String userName = req.getParameter("username");
		if ((userName == "")||(userName == null)) {
			log.info("User name not given, aborted.");
			return;
		}
		User user = pm.getObjectById(User.class, userName);
		// Decline invitation
		String jsonMessage = "";
		if (user.getGangInvites().contains(gang.getId())) {
			user.removeGangInvite(gang.getId());
			log.info(user.getName()+" declined invitation to gang "+gang.getName());
			jsonMessage = "{\"MSG\": \"OK\"}";
		}
		else {
			log.info(user.getName()+" got no invitation from gang "+gang.getName());
			jsonMessage = "{\"MSG\": \"NOINVITE\"}";
		}
		pm.close();
		dsutils.returnJSON(resp, jsonMessage);
	}
}
