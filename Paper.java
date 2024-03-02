package Material;

public class Paper extends Material
{
	//data field
	private int page = 0;
	
	//constructor
	public Paper(int number)
	{
		setNumber(number);
	}
	public Paper(int number, String title, String author, int year, int page)
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
}