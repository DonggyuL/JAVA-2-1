package Member;
import Material.*;

public class Member
{
	//data field
	private String name;
	private String id;
	private String password;
	private Material[] borrows = new Material[10000]; 
	private int sit;
	private int numberOfBorrows = 0; 
	private static int numOfMem = 0;
	private String[] room=new String[2];//추가한 field
	
	//constructor
	public Member() {};
	public Member(String name, String id, String password)
	{
		this.name = name;
		this.id = id;
		this.password = password;
	}
	
	//methods
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	public Material[] showBorrows()
	{
		return borrows;
	}
	
	public void addBorrows(Material m){
        int i;
        for(i = 0; i< numberOfBorrows; i++){
            if(m instanceof Book){
                if(borrows[i] instanceof Book && borrows[i].getNumber() == m.getNumber()) break;
            }
            else{
                if(borrows[i] instanceof Video && borrows[i].getNumber() == m.getNumber()) break;
            }
        }
        if(i == numberOfBorrows){ 
            borrows[numberOfBorrows] = m;
            numberOfBorrows++;
        }
        else{ 
            int j = 0;
            for(j = i; j < numberOfBorrows - 1; j++){
                borrows[j] = borrows[j+1];
            } 
            borrows[j] = new Material();
            numberOfBorrows--;
        }
    }
	
	public int sitWhere()
	{
		return sit;
	}
	public void setSit(int seat)
	{
		sit = seat;
	}
	
	public static int getNumOfMem( )
	{
		return numOfMem;
	}
	public static void setNumOfMem(int numOfMem)
	{
		Member.numOfMem = numOfMem;
	}

	public void setBorrows(Material[] borrows)
	{
		
		this.borrows = borrows;
	}

	public void setNumberOfBorrows(int numberOfBorrows) {
		this.numberOfBorrows = numberOfBorrows;
	}
	public int getNumberOfBorrows() {
		return numberOfBorrows;
	}
	//추가한 Method
	public String[] getRoom()
	{
		return room;
	}
	public void setRoom(String[]room)
	{
		this.room=room;
	}
}