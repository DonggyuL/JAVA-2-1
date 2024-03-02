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
	private String[][] room = new String[10][6]; // smart learning room �� ������ 10���̰�, �ֱ� 5�ϸ� ���� ������. ������ 0�� ����. �迭 �ӿ� ������ ����� �̸��� ����
	
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
					System.out.println("���� ������ ������ ���� �ʰ��߽��ϴ�.");
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
	// ��ȯ���� void���� boolean���� ����
	public boolean newVideo(Video video) {
		if(numberOfVideos >= videoList.length) {
			System.out.println("���� ������ ������ ���� �ʰ��߽��ϴ�.");
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
	
	// ���� ����Ʈ�� ���Ͽ� ����ϴ� �޼ҵ� �߰�
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
			System.out.println("���� ������ �ڷ��� ���� �ʰ��߽��ϴ�.");
			return;
		}
		// ���� ã��
		int i = 0;
		while(i < numberOfVideos) {
			if(videoList[i].isExist(number))
				break;
			i++;
		}
		// �����ϱ�
		if(i < numberOfVideos) {
			if(videoList[i].canBorrow(number)) {
				System.out.println("������ �Ϸ�Ǿ����ϴ�.");
				System.out.println(currentTime());
				videoList[i].setAvailable(false);
				m.addBorrows(videoList[i]);
			}
			else {
				System.out.println("���� ���� �����Դϴ�.");
			}
		}
		else {
			System.out.println("�������� �ʴ� �����Դϴ�.");
		}	
	}
	
	@Override
	void turn(Member m, int number) {
		if(m.getNumberOfBorrows() == 0) {
			System.out.println("�ݳ� �Ұ����� �����Դϴ�.");
			return;
		}
		// ���� ã��
		int i = 0;
		while(i < numberOfVideos) {
			if(videoList[i].isExist(number))
				break;
			i++;
		}
		// �ݳ��ϱ�
		if(i < numberOfVideos) { // ���� ����
			if(videoList[i].canBorrow(number) == false){ // ���� �Ұ� -> �ݳ� ����
				int j = 0;
				try {
					while(j < m.showBorrows().length) { 
						if((m.showBorrows()[j] instanceof Video) && (m.showBorrows()[j].getNumber() == videoList[i].getNumber())) {
							System.out.format("[%d] %s | %s | %d | %dm\n", videoList[i].getNumber(), videoList[i].getTitle(),
									videoList[i].getAuthor(), videoList[i].getYear(), videoList[i].getRuntime());
							System.out.println("�ݳ��� �Ϸ�Ǿ����ϴ�.");
							videoList[i].setAvailable(true);
							m.addBorrows(videoList[i]);
							break;
						}
						j++;
					}
				} catch(NullPointerException e) { // �ش� ȸ���� ���� ������ �ƴ�
					System.out.println("�ݳ� �Ұ����� �����Դϴ�.");
				} catch(ArrayIndexOutOfBoundsException e) { // �ش� ȸ���� ���� ������ �ƴ�
					System.out.println("�ݳ� �Ұ����� �����Դϴ�.");
				}
			}
		}
		else { // ���� ����
			System.out.println("�������� �ʴ� �����Դϴ�.");
		}
		
	}
	
	@Override
	public String currentTime() {
		// ���� ��¥
		Calendar cal = Calendar.getInstance();
		String date = "������: " + cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONTH)+1) + "/" + cal.get(Calendar.DATE);
		cal.add(Calendar.DAY_OF_MONTH, 14);
		return date + "\t�ݳ�������: " + cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONTH)+1) + "/" + cal.get(Calendar.DATE);
	}

	@Override
	public String borrowTime() { // room ���� �ð� �����ϱ�
		// ���� �� ��ȣ�� ��¥ �Է¹ޱ�
		System.out.print("Room Number(1-10): ");
		int roomNumber = sc.nextInt();
		if(roomNumber < 0 || roomNumber > 10) return "no room";
		System.out.print("Date: (ex. 2021 12 31)");
		int year = sc.nextInt(), month = sc.nextInt(), date = sc.nextInt();
		// ��¥ Ȯ���ϱ�
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
		// �� Ȯ���ϱ�
		if(room[roomNumber-1][i].equals("")) {
			System.out.print("Name: ");
			String name = sc.next();
			room[roomNumber-1][i] = name;
			return "available: Room" + roomNumber
					+ " - " + borrow.get(Calendar.YEAR) + " / " + (borrow.get(Calendar.MONTH)+1) + " / " + borrow.get(Calendar.DATE);
		}
		else return "no available room";
	}

	// room �ʱ�ȭ�ϴ� �޼ҵ� �߰�
	public void resetRoom() {
		for(int i = 0; i < room.length; i++)
			for(int j = 0; j < room[i].length; j++)
				room[i][j] = "";
	}
	// room�� return�ϴ� �޼ҵ� �߰�
	public String[][] getRoom(){
		return room;
	}
}