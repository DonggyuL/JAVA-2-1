package Library;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import Material.Video;
import Member.Member;

public class ULounge extends Manage {
	Scanner sc = new Scanner(System.in);
	private int numberOfVideos;
	private Video[] videoList = new Video[10000];
	private String[][] room = new String[10][6]; // smart learning room 의 개수가 10개이고, 최근 5일만 예약 가능함. 오늘이 0일 기준. 배열 속에 예약한 사람의 이름을 저장
	
	public ULounge() {
		resetRoom();
	}
	
	public void sorting() {
		for(int i = 0; i < numberOfVideos - 1; i++) { 
			for(int j = 1; j < numberOfVideos - i; j++)
				if(videoList[j].getNumber() < videoList[j-1].getNumber()) {
					Video temp = videoList[j];
					videoList[j] = videoList[j-1];
					videoList[j-1] = temp;
				}
		}
		System.out.println("Total : " + numberOfVideos);
		for(int i = 0; i < numberOfVideos; i++) {
			System.out.format("[%d] %s | %s | %d | %dm\n", videoList[i].getNumber(), videoList[i].getTitle(),
					videoList[i].getAuthor(), videoList[i].getYear(), videoList[i].getRuntime());
		}
		
	}
	
	public void makeVideoList(File file){
		if(!file.exists()) {
			System.out.println(file.getName() + " does not exist");
			return;
		}
		try (Scanner sc = new Scanner(file);){
			while(sc.hasNext()) {
				if(numberOfVideos >= videoList.length) {
					System.out.println("소장 가능한 비디오의 수를 초과했습니다.");
					return;
				}
				int number = sc.nextInt();
				String title = sc.next();
				String author = sc.next();
				int year = sc.nextInt();
				int runtime = sc.nextInt();
				newVideo(new Video(number, title, author, year, runtime));
			}
			sc.close();
		} catch (Exception e) {
			System.out.println("Error!");
		}
	}
	// 반환값을 void에서 boolean으로 수정
	public boolean newVideo(Video video) {
		if(numberOfVideos >= videoList.length) {
			System.out.println("소장 가능한 비디오의 수를 초과했습니다.");
			return false;
		}
		for(int i = 0; i < numberOfVideos; i++) {
			if(video.getNumber() == videoList[i].getNumber()) {
				return false;
			}
		}
		videoList[numberOfVideos] = video;
		numberOfVideos++;
		videoList[0].setNumOfMat(videoList[0].getNumOfMat() + 1);
		return true;
	}
	
	// 비디오 리스트를 파일에 출력하는 메소드 추가
	public void storeVideoList(File file) {
		if(file.exists()) {
			System.out.println(file.getName() + " already exists");
			return;
		}
		int i =0;
		try(PrintWriter output = new PrintWriter(file);){
			while(i < numberOfVideos) {
				output.print(videoList[i].getNumber() + "\t");
				output.print(videoList[i].getTitle() + "\t");
				output.print(videoList[i].getAuthor() + "\t");
				output.print(videoList[i].getYear() + "\t");
				output.println(videoList[i].getRuntime());
				i++;
			}
		} catch (Exception e) {}
	}
	
	public Video[] getVideoList() {
		return videoList;
	}

	public int getNumberOfVideos() {
		return numberOfVideos;
	}
	
	@Override
	void borrow(Member m, int number) {
		if(m.getNumberOfBorrows() >= m.showBorrows().length) {
			System.out.println("대출 가능한 자료의 수를 초과했습니다.");
			return;
		}
		// 도서 찾기
		int i = 0;
		while(i < numberOfVideos) {
			if(videoList[i].isExist(number))
				break;
			i++;
		}
		// 대출하기
		if(i < numberOfVideos) {
			if(videoList[i].canBorrow(number)) {
				System.out.println("대출이 완료되었습니다.");
				System.out.println(currentTime());
				videoList[i].setAvailable(false);
				m.addBorrows(videoList[i]);
			}
			else {
				System.out.println("대출 중인 비디오입니다.");
			}
		}
		else {
			System.out.println("존재하지 않는 비디오입니다.");
		}	
	}
	
	@Override
	void turn(Member m, int number) {
		if(m.getNumberOfBorrows() == 0) {
			System.out.println("반납 불가능한 도서입니다.");
			return;
		}
		// 비디오 찾기
		int i = 0;
		while(i < numberOfVideos) {
			if(videoList[i].isExist(number))
				break;
			i++;
		}
		// 반납하기
		if(i < numberOfVideos) { // 비디오 있음
			if(videoList[i].canBorrow(number) == false){ // 대출 불가 -> 반납 가능
				int j = 0;
				try {
					while(j < m.showBorrows().length) { 
						if((m.showBorrows()[j] instanceof Video) && (m.showBorrows()[j].getNumber() == videoList[i].getNumber())) {
							System.out.format("[%d] %s | %s | %d | %dm\n", videoList[i].getNumber(), videoList[i].getTitle(),
									videoList[i].getAuthor(), videoList[i].getYear(), videoList[i].getRuntime());
							System.out.println("반납이 완료되었습니다.");
							videoList[i].setAvailable(true);
							m.addBorrows(videoList[i]);
							break;
						}
						j++;
					}
				} catch(NullPointerException e) { // 해당 회원이 빌린 비디오가 아님
					System.out.println("반납 불가능한 비디오입니다.");
				} catch(ArrayIndexOutOfBoundsException e) { // 해당 회원이 빌린 비디오가 아님
					System.out.println("반납 불가능한 비디오입니다.");
				}
			}
		}
		else { // 비디오 없음
			System.out.println("존재하지 않는 비디오입니다.");
		}
		
	}
	
	@Override
	public String currentTime() {
		// 현재 날짜
		Calendar cal = Calendar.getInstance();
		String date = "대출일: " + cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONTH)+1) + "/" + cal.get(Calendar.DATE);
		cal.add(Calendar.DAY_OF_MONTH, 14);
		return date + "\t반납예정일: " + cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONTH)+1) + "/" + cal.get(Calendar.DATE);
	}

	@Override
	public String borrowTime() { // room 빌릴 시간 예약하기
		// 빌릴 방 번호와 날짜 입력받기
		System.out.print("Room Number(1-10): ");
		int roomNumber = sc.nextInt();
		if(roomNumber < 0 || roomNumber > 10) return "no room";
		System.out.print("Date: (ex. 2021 12 31)");
		int year = sc.nextInt(), month = sc.nextInt(), date = sc.nextInt();
		// 날짜 확인하기
		Calendar today = Calendar.getInstance();
		Calendar borrow = new GregorianCalendar(year, month-1, date);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		int i;
		for(i = 0; i < 6; i++) {
			if(sdf.format(today.getTime()).equals(sdf.format(borrow.getTime())))
				break;
			today.add(Calendar.DAY_OF_MONTH, 1);
		}
		if(i==6) return "no available date";
		// 방 확인하기
		if(room[roomNumber-1][i].equals("")) {
			System.out.print("Name: ");
			String name = sc.next();
			room[roomNumber-1][i] = name;
			return "available: Room" + roomNumber
					+ " - " + borrow.get(Calendar.YEAR) + " / " + (borrow.get(Calendar.MONTH)+1) + " / " + borrow.get(Calendar.DATE);
		}
		else return "no available room";
	}

	// room 초기화하는 메소드 추가
	public void resetRoom() {
		for(int i = 0; i < room.length; i++)
			for(int j = 0; j < room[i].length; j++)
				room[i][j] = "";
	}
	// room을 return하는 메소드 추가
	public String[][] getRoom(){
		return room;
	}
}