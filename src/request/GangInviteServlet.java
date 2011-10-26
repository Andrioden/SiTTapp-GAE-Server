package request;

import java.io.IOException;
import java.util.logging.Logger;
import javax.jdo.PersistenceManager;
import javax.servlet.http.*;
import datastore.*;

@SuppressWarnings("serial")
public class GangInviteServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(GangInviteServlet.class.getName());
	
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
		// Add gang invitation
		String jsonMessage = "";
		//TODO: Check that person inviting is member of the gang.
		if (!user.getGangInvites().contains(gang.getId())) {
			user.addGangInvite(gang.getId());
			log.info("Invited "+user.getName()+" to gang "+gang.getName());
			jsonMessage = "{\"MSG\": \"OK\"}";
		}
		else {
			log.info(user.getName()+" allready member of gang "+gang.getName());
			jsonMessage = "{\"MSG\": \"MEMBER\"}";
		}
		pm.close();
		dsutils.returnJSON(resp, jsonMessage);
	}
}