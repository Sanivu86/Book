package app;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import data.Book;

@WebServlet(
	    name = "BookClient",
	    urlPatterns = {"/bookclient"}
	)
public class BookClient extends HttpServlet{


	private static final long serialVersionUID = 1L;
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}

	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws IOException {
		
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out=response.getWriter();
		List<Book> list=useService(request, response);
		printBook(list, out);
		
		
		
	}
	private List<Book> useService(HttpServletRequest request, HttpServletResponse response)
	{
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");

		String snum = request.getParameter("number");
		int num = Integer.parseInt(snum);

		String kohde = "http://127.0.0.1:8080/rest/bookservice/deletebook/" + num;

		GenericType<List<Book>> genericList = new GenericType<List<Book>>() {}; //Lista pitää ottaa genericinä vastaan
		Client c= ClientBuilder.newClient();
		WebTarget wt=c.target(kohde); // haetaan metodi, joka lisää kirjan ja tulostaa kaikki kirjat
		Builder b=wt.request();
		//Response r= b.get();
		List<Book> returnedList=b.get(genericList); 
	
		return returnedList;

	}
	
			
	//Tulostetaan kirjat listana
		private void printBook(List<Book> list, PrintWriter out) {
			out.println("<ol>");
			int num = 0;
			for (Book item : list) {
				
				out.println("<li>" + item.getName() + ", " + item.getPages()+ " " + "<a href='/bookclient?number="+num+"'>Remove</a>");
				num++;
			}
			out.println("</ol>");
		}

//Tulostetaan formi, jotta voidaan lisätä lisää kirjoja
	
	private void printForm(PrintWriter out) {
		out.println("<h2>Add book!</h2>");
		out.println("<form action='./bookservlet' method='post'>");
		out.println("Name:<input type='text' name='name' value=''><br>");
		out.println("Pages:<input type='text' name='pages' value=''><br>");
		out.println("<input type='submit' name='ok' value='OK'><br>");
		out.println("</form>");
	}

}
