package Material;

public class Book extends Material
{
	//data field
	private int page = 0;
	//수정
	private String reservation = "";
	
	
	//constructor
	public Book(int number)
	{
		setNumber(number);
	}
	public Book(int number, String title, String author, int year, int page)
	{
		setNumber(number);
		setTitle(title);
		setAuthor(author);
		setYear(year);
		this.page = page;
	}
	
	//methods
	public int getPage()
	{
		return page;
	}
	public void setPage(int page)
	{
		this.page = page;
	}
	//수정
	public void setReservation(String reser)
	{
		reservation = reser;
	}
	public String getReservation( )
	{
		return reservation;
	}
}
