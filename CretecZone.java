package Library;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import Member.Member;

public class CretecZone extends Manage{
	
	Scanner sc=new Scanner(System.in);
	Member m=new Member();
	Date date=new Date();
	//data field
	private int numberOfLaptop=7;
	private boolean[] availablePcZone=new boolean[75]; 
	private boolean[] availableLaptopRoom=new boolean[22];
	private boolean[] availableStudyRoom=new boolean[2];
	//constructor
	public CretecZone() 
	{
		setarr(availablePcZone);
		setarr(availableLaptopRoom);
		setarr(availableStudyRoom);
	}
	//methods

	public String currentTime()
	{
		long milli= date.getTime();
		String s=String.valueOf(milli);
		return s;
	}
	
	public String borrowTime()
	{
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd h:mm");
		System.out.print("예약할 시간을 입력하세요 (ex: 2021-05-29 14:00); ");
		Date date= null;
		String t=sc.nextLine();
		try {
			date=format.parse(t);
		}catch(ParseException e){};
		long milli=date.getTime();
		String s=String.valueOf(milli);
		return s;
	}
	//초기 상태 세팅하는 메소드입니다.
	private void setarr(boolean[] room)
	{
		Arrays.fill(room, true);
	}
	
	//굳이 3개의 메소드로 안 나누고 한번에 처리했습니다.
	public void showAvailableRoom(boolean[] room)
	{
		int cnt=0;
		System.out.println("예약 가능한 자리입니다.");
		for(int i=0;i<room.length;i++)
		{
			if(room[i])
			{
				System.out.printf("%3d",(i+1));
				cnt++;
			}
			if(cnt==7)
			{
				System.out.println("");
				cnt=0;
			}
		}
		System.out.println("");
	}
	
	//1. PcZone 2. Laptoproom 3. Studyroom 중에서 입력받은 숫자를 number로 받을게요
	public void borrow(Member a, int number)
	{
		String[] s=a.getRoom();	int room;

		if("PcZone".equals(s[0]) || "LaptopRoom".equals(s[0]) || "StudyRoom".equals(s[0]))
		{
			System.out.println("이미 예약한 자리가 있습니다. \n");
			return;
		}
		
		if(number==1)
		{
			String t="";
			do {
					t=borrowTime();
					System.out.println("");
			}while(Long.parseLong(t)<Long.parseLong(currentTime()));
			showAvailableRoom(availablePcZone);
			do 
			{
				System.out.print("원하는 자리를 선택하세요 : ");
				room=sc.nextInt();
			}while(room<1 || room>75 || availablePcZone[(room-1)]!=true);
			availablePcZone[(room-1)]=false;
			s[0]="PcZone";
			a.setRoom(s);
			System.out.println("\n예약이 완료되었습니다.");
			System.out.println("");
		}
		else if(number==2)
		{
			String t="";
			do {
					t=borrowTime();
					System.out.println("");
			}while(Long.parseLong(t)<Long.parseLong(currentTime()));
			showAvailableRoom(availableLaptopRoom);
			do 
			{
				System.out.print("원하는 자리를 선택하세요 : ");
				room=sc.nextInt();
			}while(room<1 || room>75 || availableLaptopRoom[(room-1)]!=true);
			s[0]="LaptopRoom";
			a.setRoom(s);
			availableLaptopRoom[(room-1)]=false;
			System.out.println("\n예약이 완료되었습니다.");
		}
		else if(number==3)
		{
			String t="";
			do {
					t=borrowTime();
					System.out.println("");
			}while(Long.parseLong(t)<Long.parseLong(currentTime()));
			showAvailableRoom(availableStudyRoom);
			do 
			{
				System.out.print("원하는 자리를 선택하세요 : ");
				room=sc.nextInt();
			}while(room<1 || room>2 || availableStudyRoom[(room-1)]!=true);
			s[0]="StudyRoom";
			a.setRoom(s);
			availableStudyRoom[(room-1)]=false;
			System.out.println("\n예약이 완료되었습니다.");
		}
		System.out.println("");
	}
	
	//예약한 방 번호를 number로 입력받을게요.
	public void turn(Member a, int number)
	{
		String[] s=a.getRoom();	
		if( ("PcZone".equals(s[0]) && availablePcZone[number-1]==true) || ("LaptopRoom".equals(s[0]) && availableLaptopRoom[number-1]==true) || ("StudyRoom".equals(s[0]) && availableStudyRoom[number-1]==true))
		{
			System.out.println("해당 좌석을 예약하지 않았습니다.");
		}
		else if("PcZone".equals(s[0]) && availablePcZone[number-1]==false)
		{
			availablePcZone[number-1]=true;
			s[0]=null;
			a.setRoom(s);
			System.out.println("반납이 완료되었습니다.");				
		}
		else if("LaptopRoom".equals(s[0]) && availableLaptopRoom[number-1]==false)
		{
			availableLaptopRoom[number-1]=true;
			s[0]=null;
			a.setRoom(s);
			System.out.println("반납이 완료되었습니다.");		
		}
		else if("StudyRoom".equals(s[0]) && availableStudyRoom[number-1]==false)
		{
			availableStudyRoom[number-1]=true;
			s[0]=null;
			a.setRoom(s);
			System.out.println("반납이 완료되었습니다.");		
		}
			System.out.println("");
	}
	
	
	//노트북을 빌리는 method 입니다.
	public void borrowLaptop(Member a)
	{
			String[] s=a.getRoom(); 
			if("Laptop".equals(s[1]))
			{
				System.out.println("이미 노트북을 대여하였습니다.\n");
				return;
			}
			if(numberOfLaptop<=0)
			{
				System.out.println("대여 가능한 노트북이 남아있지 않습니다");
			}
			else
			{
				s[1]="Laptop";
				a.setRoom(s);
				System.out.println("노트북 대여가 완료되었습니다.\n");
				numberOfLaptop--;
			}
	}
	
	//노트북을 반납하는 method 입니다.
	public void turnLaptop(Member a)
	{
		String[] s=a.getRoom();
		if("Laptop".equals(s[1]))
		{
			s[1]=null;
			System.out.println("반납이 완료되었습니다.\n");
			a.setRoom(s);
			return;
		}
		else
		{
		System.out.println("노트북을 대여하지 않았습니다.\n");
		}
	}
}

