package client;
import rest.*;

import java.util.List;
import java.util.Scanner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;


import data.*;

public class BookClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String kohde = "http://127.0.0.1:8080/rest/bookservice/addonebook";

		//Kysytään käyttäjältä kirjan tiedot
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Anna kirjalle nimi: ");
		String nimi = sc.nextLine();
		System.out.println("Paljonko kirjassa on sivuja?");
		int sivut = sc.nextInt();
		
		
		GenericType<List<Book>> genericList = new GenericType<List<Book>>() {}; //Lista pitää ottaa genericinä vastaan
		Book book=new Book(nimi, sivut);
		Client c= ClientBuilder.newClient();
		WebTarget wt=c.target(kohde); // haetaan metodi, joka lisää kirjan ja tulostaa kaikki kirjat
		Builder b=wt.request();
		//Here we create an Entity of a DogBreed object as JSON string format
		Entity<Book> e=Entity.entity(book,MediaType.APPLICATION_JSON); //Muutetaan Entityllä kirja oikeaan muotoon, 
																		//jotta voidaan antaa se parametrina addOneBook-metodille
		
		List<Book> returned=b.post(e, genericList);//Kirjat listana
		String s=b.post(e, String.class); // Kirjat JSON Stringinä
		
		//Tulostetaan for-loopilla kirjan nimet ja sivut olioista
		for(Book item : returned)
		{
			System.out.println("Kirjan nimi on " + item.getName()); //Kirjan nimi
			System.out.println("Kirjassa on sivuja " + item.getPages()); //Kirjan sivut
			System.out.println("Kirjan oliomuoto on " + item); // vertaa, jos tulostettaisiin pelkkä olio
		}
		System.out.println("JSON: "+s); //Vertaa, jos tulostetaan jsonina
				

	}

}
