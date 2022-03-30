package data;

import java.io.Serializable;

public class Book implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//Variables
	private String name;
	private int pages;
	
	//Constructors
	
	public Book()
	{
		
	}
	
	public Book( String name, int pages)
	{
		this.name = name;
		this.pages = pages;
	}
	
	public Book( String name, String pages)
	{
		this.name = name;
		setPages(pages);
	}
	
	//Getters and setters
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPages() {
		return pages;
	}
	public void setPages(int pages) {
		this.pages = pages;
	}
	
	public void setPages(String pages) {
		try {
			this.pages = Integer.parseInt(pages);
		}
		catch (NumberFormatException | NullPointerException e) {
			
		}
	}
}
