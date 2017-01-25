package de.jetwick.snacktory.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import de.jetwick.snacktory.babe.BabeNewsUtil;



public class HttpApp {
	


    private static class filter extends HttpServlet {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            
            PrintWriter out = resp.getWriter();
            try {
            	String url = req.getParameter("url");
            	if(url != null && url.length() > 0) {
            		String res = BabeNewsUtil.scrapData(url).getTopNode().html();
            		res = "<html>\n" + res + "\n</html>";
            		out.print(res);
            	} else {
            		out.print("");
            	}
            	
                out.flush();
                out.close();
            } catch (Exception e) {
                throw new ServletException(e);
            }
        }
    }
	
    public static void main(String[] args) throws Exception {
    	int port = 8888;
    	if(args.length > 0) {
    		try {
    			port = Integer.valueOf(args[0]);
    		} catch (Exception e) {}
    	}
        Server server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        context.addServlet(new ServletHolder(new filter()), "/filter");
        server.start();
        server.join();
    }

}
