package Material;

public class Material
{
	//data field
	protected int number = 0;
	protected String title = "";
	protected String author = "";
	protected int year = 0;
	protected static int numberOfMaterials = 0;
	protected boolean available = true;
	
	//constructor
	public Material() {};
	public Material(int number, String title, String author, int year)
	{
		this.number = number;
		this.title = title;
		this.author = author;
		this.year = year;
	}
	
	//methods
	public int getNumber()
	{
		return number;
	}
	public void setNumber(int number)
	{
		this.number = number;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getAuthor()
	{
		return author;
	}
	public void setAuthor(String author)
	{
		this.author = author;
	}
	public int getYear()
	{
		return year;
	}
	public void setYear(int year)
	{
		this.year = year;
	}
	public boolean isExist(int number)
	{
		return number == this.number;
	}
	public boolean canBorrow(int number)
	{
		return (number == this.number && available);
	}
	public int getNumOfMat()
	{
		return numberOfMaterials;
	}
	public void setNumOfMat(int numberOfMaterials)
	{
		Material.numberOfMaterials = numberOfMaterials;
	}
	
	//수정
	public void setAvailable(boolean k)
	{
		available = k;
	}
}