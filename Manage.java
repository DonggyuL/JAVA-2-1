package Library;
import Member.Member;

public abstract class Manage implements ManageLibrary
{
	//data field
	int seat = 0;
	boolean isAvailable;
	Member[] members = new Member[1000000];
	
	//Constructor
	Manage( ) { };
	
	//methods
	abstract void borrow(Member m, int number);
	abstract void turn(Member m, int number);

	// members[0] -> Member 
	// (members[0]으로 하니까 NullPointerException이 나서 getNumOfMem과 setNumOfMem을 static으로 바꿨습니다.)
	void signup(String name, String id, String password)
	{	
		for(int i = 0; i < Member.getNumOfMem(); i++) {
			if(members[i].getId().equals(id)) {
				System.out.println("동일한 아이디가 이미 존재합니다.");
				return;
			}
		}
		int num = Member.getNumOfMem( );
		members[num] = new Member(name, id, password);
		Member.setNumOfMem(num+1);
	}

	void dropout(String id, String password) {
		int i = 0, num = Member.getNumOfMem();
		while(i < num)
		{
			if(id.equals( members[i].getId()) && password.equals(members[i].getPassword()))
					break;
			i++;
		}
		if(i < Member.getNumOfMem()) {
			if(members[i].getNumberOfBorrows() > 0) {
				System.out.println("대출한 자료를 반납해야 합니다.");
				return;
			}
			int j;
			for(j = i; j < num-1; j++)
				members[j] = members[j+1];
			members[j] = new Member("","","");
			Member.setNumOfMem(num - 1);
		}
		else {
			System.out.println("아이디나 비밀번호를 잘못 입력하였습니다.");
		}
		
	}
	
}
