package Library;

import java.io.*;
import java.util.*;
import Material.*;
import Member.Member;

public class ReferenceRoom extends Manage{
	Scanner sc = new Scanner(System.in);
	private int numberOfBooks;
	private Book[] bookList = new Book[10000];
	private int numberOfPapers;
	private Paper[] paperList = new Paper[10000];
	
	public ReferenceRoom() {}
	
	// Book method
	public void sorting() {
		for(int i = 0; i < numberOfBooks - 1; i++) { 
			for(int j = 1; j < numberOfBooks - i; j++)
				if(bookList[j].getNumber() < bookList[j-1].getNumber()) {
					Book temp = bookList[j];
					bookList[j] = bookList[j-1];
					bookList[j-1] = temp;
				}
		}
		System.out.println("Total : " + numberOfBooks);
		for(int i = 0; i < numberOfBooks; i++) {
			System.out.format("[%d] %s | %s | %d | %dp\n", bookList[i].getNumber(), bookList[i].getTitle(),
					bookList[i].getAuthor(), bookList[i].getYear(), bookList[i].getPage());
		}
		
	}
	
	public void makeBookList(File file) {
		if(!file.exists()) {
			System.out.println(file.getName() + " does not exist");
			return;
		}
		try (Scanner sc = new Scanner(file); ){
			while(sc.hasNext()) {
				if(numberOfBooks >= bookList.length) {
					System.out.println("소장 가능한 도서의 수를 초과했습니다.");
					return;
				}
				int number = sc.nextInt();
				String title = sc.next();
				String author = sc.next();
				int year = sc.nextInt();
				int page = sc.nextInt();
				newMaterial(new Book(number, title, author, year, page));
			}
		} catch (Exception e) {
			System.out.println("Error!");
		}
	}
	//newBook -> newMaterial, 새로운 도서 추가, true이면 추가 성공 false 이면 추가 실패
	public boolean newMaterial(Book book) {
		if(numberOfBooks >= bookList.length) {
			System.out.println("소장 가능한 도서의 수를 초과했습니다.");
			return false;
		}
		for(int i = 0; i < numberOfBooks; i++) {
			if(book.getNumber() == bookList[i].getNumber()) {
				return false;
			}
		}
		bookList[numberOfBooks] = book;
		numberOfBooks++;
		bookList[0].setNumOfMat(bookList[0].getNumOfMat() + 1);
		return true;
	}

	// 도서 리스트를 파일에 출력하는 메소드 추가
	public void storeBookList(File file) {
		if(file.exists()) {
			System.out.println(file.getName() + " already exists");
			return;
		}
		int i =0;
		try(PrintWriter output = new PrintWriter(file);){
			while(i < numberOfBooks) {
				output.print(bookList[i].getNumber() + "\t");
				output.print(bookList[i].getTitle() + "\t");
				output.print(bookList[i].getAuthor() + "\t");
				output.print(bookList[i].getYear() + "\t");
				output.println(bookList[i].getPage());
				i++;
			}
		} catch (Exception e) {}
	}
	
	public Book[] getBookList() {
		return bookList;
	}
	
	public int getNumberOfBooks() {
		return numberOfBooks;
	}
	
	// Paper Method
	public void copyPaper(int number) {
		int i;
		for(i = 0; i < numberOfPapers; i++) {
			if(paperList[i].getNumber() == number) {
				System.out.println("복사가 완료되었습니다."+ "");
				System.out.format("--[%d] %s | %s | %d | %dp--\n", paperList[i].getNumber(), paperList[i].getTitle(),
						paperList[i].getAuthor(), paperList[i].getYear(), paperList[i].getPage());
			}
		}
		if(i == paperList.length) System.out.println("자료를 찾을 수 없습니다.");
		
	}
	
	public void makePaperList(File file) {
		if(!file.exists()) {
			System.out.println(file.getName() + " does not exist");
			return;
		}
		try (Scanner sc = new Scanner(file);){
			while(sc.hasNext()) {
				if(numberOfPapers >= paperList.length) {
					System.out.println("소장 가능한 논문의 수를 초과했습니다.");
					return;
				}
				int number = sc.nextInt();
				String title = sc.next();
				String author = sc.next();
				int year = sc.nextInt();
				int page = sc.nextInt();
				newMaterial(new Paper(number, title, author, year, page));
			}
		} catch (Exception e) {
			System.out.println("Error!");
		}
	}
	// 새로운 논문을 등록하는 메소드, 메소드 오버로딩
	public boolean newMaterial(Paper paper) {
		if(numberOfPapers >= paperList.length) {
			System.out.println("소장 가능한 논문의 수를 초과했습니다.");
			return false;
		}
		for(int i = 0; i < numberOfPapers; i++) {
			if(paper.getNumber() == paperList[i].getNumber()) {
				return false;
			}
		}
		paperList[numberOfPapers] = paper;
		numberOfPapers++;
		paperList[0].setNumOfMat(paperList[0].getNumOfMat() + 1);
		return true;
	}
	
	// 논문 리스트를 파일에 출력하는 메소드 추가
	public void storePaperList(File file) {
		if(file.exists()) {
			System.out.println(file.getName() + " already exists");
			return;
		}
		int i =0;
		try(PrintWriter output = new PrintWriter(file);){
			while(i < numberOfPapers) {
				output.print(paperList[i].getNumber() + "\t");
				output.print(paperList[i].getTitle() + "\t");
				output.print(paperList[i].getAuthor() + "\t");
				output.print(paperList[i].getYear() + "\t");
				output.println(paperList[i].getPage());
				i++;
			}
		} catch (Exception e) {}
	}

	public Paper[] getPaperList() {
		return paperList;
	}
	
	public int getNumberOfPapers() {
		return numberOfPapers;
	}
	
	@Override
	void borrow(Member m, int number) {
		if(m.getNumberOfBorrows() >= m.showBorrows().length) {
			System.out.println("대출 가능한 자료의 수를 초과했습니다.");
			return;
		}
		// 도서 찾기
		int i = 0;
		while(i < numberOfBooks) {
			if(bookList[i].isExist(number))
				break;
			i++;
		}
		// 대출하기
		if(i < numberOfBooks) {
			if(bookList[i].getReservation().equals("")) { // 예약자가 없으면 대출 진행
				if(bookList[i].canBorrow(number)) {
					System.out.format("[%d] %s | %s | %d | %dp\n", bookList[i].getNumber(), bookList[i].getTitle(),
							bookList[i].getAuthor(), bookList[i].getYear(), bookList[i].getPage());
					System.out.println("대출이 완료되었습니다.");
					System.out.println(currentTime());
					bookList[i].setAvailable(false);
					m.addBorrows(bookList[i]);
				}
				else {
					System.out.println("대출 중인 도서입니다.");
					System.out.print("예약하시겠습니다?(Y/N) ");
					char ans = sc.next().charAt(0);
					if(ans=='Y')
						System.out.println(borrowTime());
				}
			}
			else if(bookList[i].getReservation().equals(m.getName())) { // 예약자가 대출하는 경우
				if(bookList[i].canBorrow(number)) {
					System.out.format("[%d] %s | %s | %d | %dp\n", bookList[i].getNumber(), bookList[i].getTitle(),
							bookList[i].getAuthor(), bookList[i].getYear(), bookList[i].getPage());
					System.out.println("대출이 완료되었습니다.");
					System.out.println(currentTime());
					bookList[i].setAvailable(false);
					bookList[i].setReservation("");
					m.addBorrows(bookList[i]);
				}
				else {
					System.out.println("대출 중인 도서입니다.");
				}
			}
			else { // 예약자가 있으나 다른 사람이 대출하러 온 경우
				if(bookList[i].canBorrow(number))
					System.out.println("예약 중인 도서이므로 대출할 수 없습니다.");
				else
					System.out.println("대출 중인 도서입니다.");
			}
		}
		else {
			System.out.println("존재하지 않는 도서입니다.");
		}
		
	}
	
	@Override
	void turn(Member m, int number) {
		if(m.getNumberOfBorrows() == 0) {
			System.out.println("반납 불가능한 도서입니다.");
			return;
		}
		// 도서 찾기
		int i = 0;
		while(i < numberOfBooks) {
			if(bookList[i].isExist(number))
				break;
			i++;
		}
		// 반납하기
		if(i < numberOfBooks) { // 도서 있음
			if(bookList[i].canBorrow(number) == false){ // 대출 불가 -> 반납 가능
				int j = 0;
				try {
					while(j < m.showBorrows().length) { 
						if((m.showBorrows()[j] instanceof Book) && (m.showBorrows()[j].getNumber() == bookList[i].getNumber())) {
							System.out.format("[%d] %s | %s | %d | %dp\n", bookList[i].getNumber(), bookList[i].getTitle(),
									bookList[i].getAuthor(), bookList[i].getYear(), bookList[i].getPage());
							System.out.println("반납이 완료되었습니다.");
							bookList[i].setAvailable(true);
							m.addBorrows(bookList[i]);
							break;
						}
						j++;
					}
				} catch(NullPointerException e) { // 해당 회원이 빌린 도서가 아님
					System.out.println("반납 불가능한 도서입니다.");
				} catch(ArrayIndexOutOfBoundsException e) { // 해당 회원이 빌린 도서가 아님
					System.out.println("반납 불가능한 도서입니다.");
				}
			}
		}
		else { // 도서 없음
			System.out.println("존재하지 않는 도서입니다.");
		}
		
	}

	@Override
	public String currentTime() { // 현재 시간(대출일)과 현재로부터 2주 지난 반납 예정일 출력
		Calendar cal = Calendar.getInstance();
		String date = "대출일: " + cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONTH)+1) + "/" + cal.get(Calendar.DATE);
		cal.add(Calendar.DAY_OF_MONTH, 14); // 도서 대출 기간 14일
		return date + "\t반납예정일: " + cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONTH)+1) + "/" + cal.get(Calendar.DATE);
	}

	@Override
	public String borrowTime() { // 도서 대출 예약 기능 제공
		System.out.print("Book Number: ");
		int number = sc.nextInt();
		// 도서 찾기
		int i = 0;
		while(i < numberOfBooks) {
			if(bookList[i].isExist(number))
				break;
			i++;
		}
		if(i == numberOfBooks) {
			return "존재하지 않는 도서입니다";
		}
		else {
			if(bookList[i].canBorrow(number))
				return "예약 불가능한 도서입니다";
			else {
				System.out.print("이름: ");
				String name = sc.next();
				bookList[i].setReservation(name);
				return "예약되었습니다"; // 대출 중(대출 불가)인 도서만 예약 가능
			}
		}
		
	}

}
