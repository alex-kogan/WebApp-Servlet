package WebAppPkg;
 
import java.io.BufferedReader;
import java.io.IOException;
// SQL
import WebAppPkg.WebAppDB;
import java.sql.ResultSet;
import java.sql.SQLException;
// Servlet
import javax.servlet.*;
import javax.servlet.http.*;
// JSON
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

// Data types
import WebAppPkg.User;
 
public class UserServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private String dbURL = "jdbc:derby://localhost:1527/c:/Apache/Databases/MyDbTest";
    private String tableName = "USERS";
    @Override
    public void doPost (HttpServletRequest request, HttpServletResponse response)
 		   throws IOException, ServletException
    {
    	WebAppDB db = new WebAppDB();
		ResultSet users;
		db.createConnection(dbURL); 
	
        StringBuilder sb = new StringBuilder();
        BufferedReader br = request.getReader();
        String str = null;
        while ((str = br.readLine()) != null)
        {
            sb.append(str);
        }
		User userData = new Gson().fromJson(sb.toString(), User.class);
        users = db.executeQuery("select * from " + tableName + " WHERE NAME='"+userData.getName()+"'");
        try
        {
            String msg = "[]";
            String json = null;
            boolean loggedIn = false;
            if (!users.next())
    		{
            	// no such user exists
        		msg = "[\"No such user exists\"]";
    		}
            else if (!userData.getPassword().equals(users.getString(3))) 
    		{
            	// password wrong
            	msg = "[\"Wrong password\"]";
    		}
            else
            {
            	userData = new User(users.getInt(1),users.getString(2),users.getString(3),
            						users.getString(4), users.getString(5), users.getString(6));
            	loggedIn = true;
            }
            if (loggedIn)
            {
            	json = new Gson().toJson(userData);
            }
            else
            {
            	JsonParser parser = new JsonParser();
            	JsonObject o = parser.parse("{\"msg\":"+ msg +"}").getAsJsonObject();
            	json = new Gson().toJson(o);
            }
        	response.setContentType("application/json");
        	response.setCharacterEncoding("UTF-8");
        	response.getWriter().write(json);
        	response.getWriter().close();
		}
        catch (SQLException e)
        {
			e.printStackTrace();
		}
    }
    @Override
    public void doGet (HttpServletRequest request, HttpServletResponse response)
  		   throws IOException, ServletException
  	{
    	RequestDispatcher rd = request.getRequestDispatcher("./html/Index.html");
    	rd.include(request, response);
  	}
}
