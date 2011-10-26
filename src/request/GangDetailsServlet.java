package request;

import java.io.IOException;
import java.util.logging.Logger;
import javax.jdo.PersistenceManager;
import javax.servlet.http.*;

import com.google.gson.Gson;

import datastore.*;

@SuppressWarnings("serial")
public class GangDetailsServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(GangDetailsServlet.class.getName());
	
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
		gang.initJSONvars();
		Gson gson = new Gson();
		dsutils.returnJSON(resp, gson.toJson(gang));
		pm.close();
	}
}
