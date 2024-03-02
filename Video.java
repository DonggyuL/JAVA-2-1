package Material;

public class Video extends Material
{
	//data field
	private int runtime = 0;
	
	//constructor
	public Video(int number)
	{
		setNumber(number);
	}
	public Video(int number, String title, String author, int year, int runtime)
	{
		setNumber(number);
		setTitle(title);
		setAuthor(author);
		setYear(year);
		this.runtime = runtime;
	}
	
	//methods
	public int getRuntime()
	{
		return runtime;
	}
	public void setRuntime(int runtime)
	{
		this.runtime = runtime;
	}
}