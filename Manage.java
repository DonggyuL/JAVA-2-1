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
	// (members[0]���� �ϴϱ� NullPointerException�� ���� getNumOfMem�� setNumOfMem�� static���� �ٲ���ϴ�.)
	void signup(String name, String id, String password)
	{	
		for(int i = 0; i < Member.getNumOfMem(); i++) {
			if(members[i].getId().equals(id)) {
				System.out.println("������ ���̵� �̹� �����մϴ�.");
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
				System.out.println("������ �ڷḦ �ݳ��ؾ� �մϴ�.");
				return;
			}
			int j;
			for(j = i; j < num-1; j++)
				members[j] = members[j+1];
			members[j] = new Member("","","");
			Member.setNumOfMem(num - 1);
		}
		else {
			System.out.println("���̵� ��й�ȣ�� �߸� �Է��Ͽ����ϴ�.");
		}
		
	}
	
}
