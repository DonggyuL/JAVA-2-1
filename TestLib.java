package Library;
import java.io.File;
import java.util.Scanner;
import Material.*;
import Member.Member;

public class TestLib
{
	static Scanner sc = new Scanner(System.in);
	//수정 전체적인 출력 결과 바뀜
	public static void main(String[] args)
	{
		if(args.length != 3)
		{
			System.out.println("!!Usage: java TestLib BookList PaperList VideoList");
			System.exit(0);
		}
		System.out.print("number of library, number of ReferenceRoom, number of ULounge, number of SLounge, number of Cretec Zone: ");
		final int n = sc.nextInt();
		final int nR = sc.nextInt();
		final int nU = sc.nextInt();
		final int nS = sc.nextInt();
		final int nC = sc.nextInt();
		ReferenceRoom[ ] ReferenceRoom = new ReferenceRoom[nR];
		ULounge[ ] ULounge = new ULounge[nU];
		SLounge[ ] SLounge = new SLounge[nS];
		CretecZone[ ] CretecZone = new CretecZone[nC];
		Member[ ] members = new Member[1000];
		for(int i = 0; i < 1000; i++) members[i] = new Member();
		for(int i = 0; i < nR; i++) ReferenceRoom[i] = new ReferenceRoom();
		for(int i = 0; i < nU; i++) ULounge[i] = new ULounge();
		for(int i = 0; i < nS; i++) SLounge[i] = new SLounge();
		for(int i = 0; i < nC; i++) CretecZone[i] = new CretecZone();
		//수정사항: Manage는 abstract라서 new로 선언 불가=>object class 이용
		Object[][][] Library = new Object[n][4][100];
		for(int i = 0; i < n; i++)
		{
			Library[i][0] = ReferenceRoom;
			Library[i][1] = ULounge;
			Library[i][2] = SLounge;
			Library[i][3] = CretecZone;
		}
		for(int i = 0; i < n; i++)
		{
			for(int j = 0; j < nR; j++)
			{
				((ReferenceRoom) Library[i][0][j]).makeBookList(new File(args[0]) );
				((ReferenceRoom) Library[i][0][j]).makePaperList(new File(args[1]) );
			}
			for(int j = 0; j < nU; j++)
			{
				((ULounge) Library[i][1][j]).makeVideoList(new File(args[2]) );
			}
		}
		
		int select = 0;
		while(true)
		{
			boolean flag = false;
			Member m = website(members);
			flag = false;
			//수정 : 출력에 library n개중에 설정 필요
			
			do
			{
				System.out.println("\nLibrary number in [0, "+(n-1)+"]: ");
				int a = 0;
				while(true)
				{
					a = sc.nextInt();
					if (a <= n-1 && a>=0) break;
					System.out.print("range in [0, "+(n-1)+"]");
				}
				System.out.println("which section? :");
				System.out.print("1: ReferenceRoom, 2: ULounge 3: SLounge, 4: CretecZone, 5: log out :");
				select = sc.nextInt( );
				int b = 0;
				if(select == 1 || select == 2 || select == 3 || select == 4)
				{
					System.out.print("section number: ");
					if(select == 1) System.out.println("ReferenceRoom: [0, "+(nR-1)+"]");
					else if(select == 2) System.out.println("ULounge: [0, "+(nU-1)+"]");
					else if(select == 3) System.out.println("SLounge: [0, "+(nS-1)+"]");
					else System.out.println("CretecZone: [0, "+(nC-1)+"]");
					b = sc.nextInt();
				}
				switch(select)
				{
					case 1:
					{
						flag = referenceRoom( Library[a][0][b], m );
						continue;
					}
					case 2:
					{
						flag = uLounge( Library[a][1][b], m );
						continue;
					}
					case 3:
					{
						flag = sLounge( Library[a][2][b], m );
						continue;
					}
					case 4:
					{
						flag = cretecZone( Library[a][3][b], m );
						continue;
					}
					case 5:
					{
						System.out.println("log out successfully!");
						flag = true;
					}
				}
			}while(!flag);
		}
		
	}
	
	static Member website( Member[ ] members )
	{
		boolean loginSuccess = false;
		System.out.println("Welcome to the Library!");
		int k = 0;
		Member re = members[0];
		do
		{
			int i = Member.getNumOfMem( );
			System.out.print("1: log in, 2: sign up, 3: drop out, 4: exit ");
			k = sc.nextInt();
			switch(k)
			{
				case 1: 
				{	
					if(i==0) { System.out.println("sign up first"); break;}
					int j;
					while(true)
					{
						System.out.print("id: password: ");
						String id = sc.next( );
						String password = sc.next( );
						for(j = 0; j < i; j++) {
							if( members[j].getId().equals(id) && members[j].getPassword().equals(password) )
							{
								loginSuccess = true;
								System.out.println("Log in successfully!");
								re = members[j];
								break;
							}
						}
						if(j == i)
						{
							System.out.print("Wrong information.\nPress 1 if you want to quit.(else press anything)");
							if(sc.next().equals("1")) break;
						}
						else break;
					}
					break;
				}
				case 2:
				{
					if(i >= members.length) { 
						System.out.println("Number of members is limited to " + members.length);
						break;
					}
					while(true) {
						int j;
						System.out.print("name: id: password:");
						String name = sc.next();
						String id = sc.next();
						String password = sc.next();
						for(j = 0; j < i; j++) {
							if(members[j].getId().equals(id)) {
								System.out.println("ID already exists");
								break;
							}
						}
						if(j == i) {
							members[i] = new Member(name, id, password);
							Member.setNumOfMem(i+1);
							System.out.println("Sing up successfully");
							break;
						}
						else {
							System.out.println("Press 1 if you want to quit.(else press anything)");
							if(sc.next().equals("1")) break;
						}
					}
					break;
				}
				case 3:
				{
					if(i == 0) { System.out.println("sign up first!"); break; }
					while(true) {
						System.out.println("id: password: ");
						String id = sc.next(), passwrod = sc.next();
						int j = 0;
						while(j < i)
						{
							if(members[j].getId().equals(id)&& members[j].getPassword().equals(passwrod))
							{
								if(members[j].getNumberOfBorrows() > 0) {
									System.out.println(" You should retrun materials.");
								}
								else {
									int l = 0;
									for(l = j; l < i-1; l++) {
										members[l] = members[l+1];
									}
									members[l] = new Member("","","");
									Member.setNumOfMem(i-1);
								}
								break;
							}
							j++;
						}
						if(j == i)
						{
							System.out.print("Wrong information.\nPress 1 if you want to quit.(else press anything)");
							if(sc.next().equals("1")) break;
						}
						else
							break;
					}
					break;
				}
				case 4:
				{
					System.out.println("Exit the Library");
					System.exit(0);
				}
			}
		} while(!loginSuccess);
		return re;
	}
	
	static boolean referenceRoom( Object library, Member m)
	{	
		System.out.print("Choose the function\n1: interlibrary loan(new book)\n2: borrow 3: turn 4: sort\n5: copyPaper 6: store List 7: return website");
		switch(sc.nextInt( ))
		{
			case 1:
			{
				System.out.print("1: Book, 2: Paper");
				switch(sc.nextInt() )
				{
					case 1:
					{
						System.out.print("number: title: author: year: page: ");
						if(( (ReferenceRoom) library).newMaterial( new Book(sc.nextInt(), sc.next(), sc.next(), sc.nextInt(), sc.nextInt())))
							System.out.println("The book is added!");
						else
							System.out.println("The book is not added!");
					}
					break;
					case 2:
					{
						System.out.print("number: title: author: year: page: ");
						if(( (ReferenceRoom) library).newMaterial( new Paper(sc.nextInt(), sc.next(), sc.next(), sc.nextInt(), sc.nextInt()) ))
							System.out.println("The paper is added!");
						else
							System.out.println("The paper is not added!");
					}
				}
			}
			break;
			case 2:
			{
				System.out.print("number: ");
				((ReferenceRoom) library).borrow(m, sc.nextInt( ));
			}
			break;
			case 3:
			{
				System.out.print("number: ");
				((ReferenceRoom) library).turn(m, sc.nextInt() );
			}
			break;
			case 4:
			{
				((ReferenceRoom) library).sorting();
				System.out.println("sort successfully!");
			}
			break;
			case 5:
			{
				System.out.print("num of paper which will be copied: ");
				((ReferenceRoom) library).copyPaper(sc.nextInt() );
			}
			break;
			case 6:
			{
				System.out.print("1: Book, 2: Paper ");
				switch(sc.nextInt() )
				{
					case 1:
					{
						System.out.print("File Name: ");
						String file = sc.next();
						((ReferenceRoom) library).storeBookList(new File(file) );
					}
					break;
					case 2:
					{
						System.out.print("File Name: ");
						String file = sc.next();
						((ReferenceRoom) library).storePaperList(new File(file) );
					}
				}
			}
			break;
			case 7:
			{
				System.out.println("return to the website!");
			}
		}
		return false;
	}
	
	static boolean uLounge( Object library, Member m )
	{
		System.out.print("Choose the function\n1: interlibrary loan(new video)\n2: borrow 3: turn 4: sort\n 5: store List 6: appoint space 7: return website");
		switch(sc.nextInt() )
		{
			case 1:
			{
				System.out.print("number: title: author: year: runtime: ");
				if(( (ULounge) library).newVideo( new Video(sc.nextInt(), sc.next(), sc.next(), sc.nextInt(), sc.nextInt()) ))
					System.out.println("The video is added!");
				else
					System.out.println("The video is not added!");
			}
			break;
			case 2:
			{
				System.out.print("number: ");
				((ULounge) library).borrow(m, sc.nextInt());
			}
			break;
			case 3:
			{
				System.out.print("number: ");
				((ULounge) library).turn(m, sc.nextInt() );
			}
			break;
			case 4:
			{
				((ULounge) library).sorting();
				System.out.println("sort successfully!");
			}
			break;
			case 5:
			{
				System.out.println("File Name: ");
				String file = sc.next();
				((ULounge) library).makeVideoList(new File(file));
				
			}
			break;
			case 6:
			{
				System.out.println(((ULounge) library).borrowTime());
			}
			case 7:
			{
				System.out.println("return to the website!");
			}
		}
		return false;
	}
	
	static boolean sLounge( Object library, Member m )
	{
		System.out.println("Choose the function\n1:appoint space 2: turn space 3: cafe 4: return website");
		switch(sc.nextInt() )
		{
			case 1:
			{
				System.out.print("select a room number: ");
				( (SLounge) library).borrow(m, sc.nextInt());
			}
			break;
			case 2:
			{
				System.out.println("select a room number: ");
				((SLounge) library).turn(m, sc.nextInt() );				
			}
			break;
			case 3:
			{
				System.out.println("어떤 메뉴를 보시겠습니까? 1. Coffee 2. Tea & Milk Tea, 3. Latte 4. Ade & Fruit Juice 5. Smoothie & Frappe");
				int choice = sc.nextInt();
				((SLounge) library).showMenu(choice);
				System.out.print("menu number: ");
				((SLounge) library).order(choice, sc.nextInt() );
			}
			case 4:
			{
				System.out.println("return to the website!");
			}
		}
		return false;
	}
	
	static boolean cretecZone( Object library, Member m )
	{
		System.out.println("Choose the function\n1:appoint space 2: turn space 3: borrow laptop 4: turn laptop 5: return website");
		switch(sc.nextInt() )
		{
			case 1:
			{
				System.out.print("select a room number: ");
				((CretecZone) library).borrow(m, sc.nextInt() );
			}
			break;
			case 2:
			{
				System.out.print("select a room number: ");
				((CretecZone) library).turn(m, sc.nextInt() );
			}
			break;
			case 3:
			{
				((CretecZone) library).borrowLaptop(m);
			}
			break;
			case 4:
			{
				((CretecZone) library).turnLaptop(m);
			}
			case 5:
			{
				System.out.println("return to the website!");
			}
		}
		return false;
	}
}