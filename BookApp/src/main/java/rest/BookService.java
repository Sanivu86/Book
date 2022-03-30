package rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import data.*;

@Path("/bookservice")
public class BookService {
	

	
	@SuppressWarnings("unchecked")
	@POST //Huom POST
	@Path("/addbook")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes("application/x-www-form-urlencoded")
	public ArrayList<Book> addBook(@FormParam("name") String name, @FormParam("pages") int pages)
	{
		Book kirja = new Book(name, pages);
		
		File file=new File("books.dat"); 
		ArrayList<Book> list = new ArrayList<>();
		
		
			if (file.exists()) { 
			  try { 
		      FileInputStream fis=new FileInputStream(file); 
		      ObjectInputStream ois=new ObjectInputStream(fis);
		      
		      list = (ArrayList<Book>)ois.readObject(); 

		      ois.close(); 
		      fis.close(); 
		      
			      } 
			      catch (FileNotFoundException e) { 
 
			         e.printStackTrace(); 
			      } 
			      catch (IOException e) { 

			        e.printStackTrace(); 
			      } 
			      catch (ClassNotFoundException e) { 

			        e.printStackTrace(); 
			      } 
			    } 

				try { 
				      list.add(kirja); 
				      FileOutputStream fos=new FileOutputStream(file); 
				      ObjectOutputStream oos=new ObjectOutputStream(fos); 
				      oos.writeObject(list); 
				      oos.close(); 
				      fos.close();
				    } 
				    catch(FileNotFoundException e) {
				    } 
				    catch (IOException e) { 

				      e.printStackTrace(); 
				    } 

			    return list;
	
	}
	
	
	@SuppressWarnings("unchecked")
	@GET //Huom GET
	@Path("/getall")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes("application/x-www-form-urlencoded")
	public ArrayList<Book> getAll()
	{
		ArrayList<Book> kaikkiKirjat = new ArrayList<>();
		File file=new File("books.dat"); 
		
		if (file.exists()) { 
			  try { 
		      FileInputStream fis=new FileInputStream(file); 
		      ObjectInputStream ois=new ObjectInputStream(fis);
		      
		      kaikkiKirjat = (ArrayList<Book>)ois.readObject(); 

		      ois.close(); 
		      fis.close(); 
		      
			      } 
			      catch (FileNotFoundException e) { 

			         e.printStackTrace(); 
			      } 
			      catch (IOException e) { 

			        e.printStackTrace(); 
			      } 
			      catch (ClassNotFoundException e) { 

			        e.printStackTrace(); 
			      } 
			    } 
	      
		return kaikkiKirjat;
		
	}
	
	
	@GET  //Huom GET
	@Path("/getbook/{number}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes("application/x-www-form-urlencoded")
	public Book getBook(@PathParam("number") int number)
	{
		ArrayList<Book> list = getAll();
		try {
			return list.get(number);
		}
		catch(IndexOutOfBoundsException e) {
			return null;
		}

	}
	
	//Tehtävä 5
	
	@SuppressWarnings("unchecked")
	@POST //Huom POST
	@Path("/addonebook")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ArrayList<Book> addOneBook(Book kirja)
	{
		
		File file=new File("books.dat"); 
		ArrayList<Book> list = new ArrayList<>();
		
		
			if (file.exists()) { 
			  try { 
		      FileInputStream fis=new FileInputStream(file); 
		      ObjectInputStream ois=new ObjectInputStream(fis);
		      
		      list = (ArrayList<Book>)ois.readObject(); 

		      ois.close(); 
		      fis.close(); 
		      
			      } 
			      catch (FileNotFoundException e) { 
 
			         e.printStackTrace(); 
			      } 
			      catch (IOException e) { 

			        e.printStackTrace(); 
			      } 
			      catch (ClassNotFoundException e) { 

			        e.printStackTrace(); 
			      } 
			    } 

				try { 
				      list.add(kirja); 
				      FileOutputStream fos=new FileOutputStream(file); 
				      ObjectOutputStream oos=new ObjectOutputStream(fos); 
				      oos.writeObject(list); 
				      oos.close(); 
				      fos.close();
				    } 
				    catch(FileNotFoundException e) {
				    } 
				    catch (IOException e) { 

				      e.printStackTrace(); 
				    } 

			    return list;
	
	}
	
	//Tehtävä 7, poista valittu kirja listasta

	@SuppressWarnings("unchecked")
	@GET
	@Path("/deletebook/{number}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes("application/x-www-form-urlencoded")
	public ArrayList<Book> deleteBook(@PathParam("number") int number)
	{
		File file=new File("books.dat"); 
		ArrayList<Book> list = new ArrayList<>();
		
		
			if (file.exists()) { 
			  try { 
		      FileInputStream fis=new FileInputStream(file); 
		      ObjectInputStream ois=new ObjectInputStream(fis);
		      
		      list = (ArrayList<Book>)ois.readObject(); 

		      ois.close(); 
		      fis.close(); 
		      
			      } 
			      catch (FileNotFoundException e) { 
 
			         e.printStackTrace(); 
			      } 
			      catch (IOException e) { 

			        e.printStackTrace(); 
			      } 
			      catch (ClassNotFoundException e) { 

			        e.printStackTrace(); 
			      } 
			    } 

				try { 
				      list.remove(number); 
				      FileOutputStream fos=new FileOutputStream(file); 
				      ObjectOutputStream oos=new ObjectOutputStream(fos); 
				      oos.writeObject(list); 
				      oos.close(); 
				      fos.close();
				      System.out.println("Poistettu nro. " + number);
				    } 
				    catch(FileNotFoundException e) {
				    	System.out.println("Poisto ei onnistunut.");
				    } 
				    catch (IOException e) { 

				    	System.out.println("Poisto ei onnistunut.");
				      e.printStackTrace(); 
				    } 

			    return list;
	}
}
