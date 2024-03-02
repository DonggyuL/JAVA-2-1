package Library;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

import Member.Member;

public class SLounge {
	Scanner sc=new Scanner(System.in);
	Member m=new Member();
	Date date=new Date();
	//data field
	//2차원 배열로 바꿨습니다.
	String[][]menu= {{"아메리카노", "스페셜 아메리카노", "헤이즐넛" ,"카페라떼","카푸치노","카라넛 카페라떼", "바닐라/카라멜/헤이즐 카페라떼"},
					 {"허브티","홍차","레몬/자몽/유자차","녹차","대추차.진저티","히비스커스","레몬/유자 히스커스","피치 아이스티"},
					 {"다크 초콜릿 라떼","제주산 말차 라떼", "고구마 라데", "12곡 라떼", "민트초코 라떼","카라넛 라떼","죠리퐁 라떼"},
					 {"레몬/자몽/청포도 에이드","블루레몬 프리미엄 에이드", "한라봉 프리미엄 에이드","딸기/바나나/키위 쥬스","딸기바나나/키위바나나/초코바나나 쥬스"},
					 {"플레인/딸기/블루베리 요거트 스무디","모카/카라멜 프라페","제주산 말차 프라페","타로 버블티"}
					};
	private boolean[] availableLaptopRoom=new boolean[28];
	private boolean[] availableStudyRoom=new boolean[7];
	//constructor
	public SLounge() 
	{
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
	
	//1. Laptoproom 2. Studyroom 중에서 입력받은 숫자를 number로 받을게요
	public void borrow(Member a, int number)
	{
		String[] s=a.getRoom();	int room;
		for(int i=0;i<s.length;i++)
		{
			if("PcZone".equals(s[0]) || "LaptopRoom".equals(s[0]) || "StudyRoom".equals(s[0]))
			{
				System.out.println("이미 예약한 자리가 있습니다. \n");
				return;
			}
		}
		if(number==1)
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
			}while(room<1 || room>28 || availableLaptopRoom[(room-1)]!=true);
			a.setRoom(s);
			availableLaptopRoom[(room-1)]=false;
			System.out.println("\n예약이 완료되었습니다.");;
		}
		else if(number==2)
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
			}while(room<1 || room>7 || availableStudyRoom[(room-1)]!=true);
			s[0]="StudyRoom";
			a.setRoom(s);
			availableStudyRoom[(room-1)]=false;
			System.out.println("\n예약이 완료되었습니다.");
		}
		System.out.println("");
	}
	
	//예약한 방 번호를 number로 입력받을게요. 방의 종류는 입력 안받아도 됨.
	public void turn(Member a, int number)
	{
		String[] s=a.getRoom();	
		for(int i=0;i<s.length;i++)
		{
			if( ("LaptopRoom".equals(s[i]) && availableLaptopRoom[number-1]==true) || ("StudyRoom".equals(s[i]) && availableStudyRoom[number-1]==true))
			{
				System.out.println("해당 좌석을 예약하지 않았습니다.");
				break;
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
		}
			System.out.println("");
	}
	/*	Main에서 이런식으로 짜면 될거 같습니다.
	System.out.println("어떤 메뉴를 보시겠습니까? 1. Coffee 2. Tea & Milk Tea, 3. Latte 4. Ade & Fruit Juice 5. Smoothie & Frappe");
	int choice=sc.nextInt();*/
	public void showMenu(int choice)
	{
		for(int i=0;i<menu[choice-1].length;i++)
		{
			System.out.println((i+1)+". "+menu[choice-1][i]);
		}
	}
	/*System.out.println("메뉴 번호를 입력해 주세요");
	  int menuNum=sc.nextInt();
	  choice는 showMenu할때 받은 변수를 그대로 이용하면 될 것 같습니다.
	*/
	public void order(int choice, int menuNum)
	{
		System.out.println(menu[choice-1][menuNum-1]+"의 주문이 완료되었습니다. 주문번호는 "+(int)(1+25*Math.random())+" 입니다.");
		
	}
	
}