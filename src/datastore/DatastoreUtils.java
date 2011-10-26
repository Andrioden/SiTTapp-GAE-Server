package datastore;

import java.io.IOException;
import java.io.PrintWriter;
import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServletResponse;

public class DatastoreUtils {
	/** Add an element to the datastore
	 * 
	 * @param Any element that shall be added to the datastore.
	 * @return
	 */
	public boolean create(Object element) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		boolean suc;
        try {
        	pm.makePersistent(element);
            suc = true;
        } catch (Exception e) {
        	System.out.println(e);
        	suc = false;
        } finally {
            pm.close();
        }
        return suc;
	}
	
	public void returnJSON(HttpServletResponse resp, String jsonString) {
		resp.setContentType("application/json");
		try {
			PrintWriter out = resp.getWriter();
			System.out.println(jsonString);
			out.print(jsonString);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
