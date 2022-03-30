package client;

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

import data.Book;


@WebServlet(
	    name = "BookServlet",
	    urlPatterns = {"/bookservlet"}
	)
public class BookServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;


	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws IOException {
		doGet(request, response);
	}

	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws IOException {
		
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		//Käytetään alla olevia metodeja, kun doGet-metodi toteutetaan
		PrintWriter out=response.getWriter();
		printForm(out);
		List<Book> list=useService(request);
		printBook(list, out);
	}

//Tulostetaan kirjat listana
	private void printBook(List<Book> list, PrintWriter out) {
		out.println("<ol>");
		int num = 0;
		for (Book item : list) {
			
			String polku = "/bookclient" + num;
			out.println("<li>" + item.getName() + ", " + item.getPages()+ " " + "<a href='/bookclient?number="+num+"'>Remove</a>");
			num++;
		}
		out.println("</ol>");
	}
	
//Kirjan nimi ja sivumäärä tulevat formilta parametreina
	//Tiedot välitetään BookServicen addOneBook-metodille
	
	private List<Book> useService(HttpServletRequest request)
	{
		String kohde = "http://127.0.0.1:8080/rest/bookservice/addonebook";
		
		String name=request.getParameter("name");
		String pages=request.getParameter("pages");
		Book book=new Book(name, pages);
		
		GenericType<List<Book>> genericList = new GenericType<List<Book>>() {}; //Lista pitää ottaa genericinä vastaan
		Client c= ClientBuilder.newClient();
		WebTarget wt=c.target(kohde); // haetaan metodi, joka lisää kirjan ja tulostaa kaikki kirjat
		Builder b=wt.request();
		Entity<Book> e=Entity.entity(book,MediaType.APPLICATION_JSON); //Muutetaan Entityllä kirja oikeaan muotoon, 

		List<Book> returned=b.post(e, genericList); //Kirjat listana. Tässä tehdään haku
		
		return returned;
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
