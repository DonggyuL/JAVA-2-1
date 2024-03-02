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
					System.out.println("���� ������ ������ ���� �ʰ��߽��ϴ�.");
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
	//newBook -> newMaterial, ���ο� ���� �߰�, true�̸� �߰� ���� false �̸� �߰� ����
	public boolean newMaterial(Book book) {
		if(numberOfBooks >= bookList.length) {
			System.out.println("���� ������ ������ ���� �ʰ��߽��ϴ�.");
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

	// ���� ����Ʈ�� ���Ͽ� ����ϴ� �޼ҵ� �߰�
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
				System.out.println("���簡 �Ϸ�Ǿ����ϴ�."+ "");
				System.out.format("--[%d] %s | %s | %d | %dp--\n", paperList[i].getNumber(), paperList[i].getTitle(),
						paperList[i].getAuthor(), paperList[i].getYear(), paperList[i].getPage());
			}
		}
		if(i == paperList.length) System.out.println("�ڷḦ ã�� �� �����ϴ�.");
		
	}
	
	public void makePaperList(File file) {
		if(!file.exists()) {
			System.out.println(file.getName() + " does not exist");
			return;
		}
		try (Scanner sc = new Scanner(file);){
			while(sc.hasNext()) {
				if(numberOfPapers >= paperList.length) {
					System.out.println("���� ������ ���� ���� �ʰ��߽��ϴ�.");
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
	// ���ο� ���� ����ϴ� �޼ҵ�, �޼ҵ� �����ε�
	public boolean newMaterial(Paper paper) {
		if(numberOfPapers >= paperList.length) {
			System.out.println("���� ������ ���� ���� �ʰ��߽��ϴ�.");
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
	
	// �� ����Ʈ�� ���Ͽ� ����ϴ� �޼ҵ� �߰�
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
			System.out.println("���� ������ �ڷ��� ���� �ʰ��߽��ϴ�.");
			return;
		}
		// ���� ã��
		int i = 0;
		while(i < numberOfBooks) {
			if(bookList[i].isExist(number))
				break;
			i++;
		}
		// �����ϱ�
		if(i < numberOfBooks) {
			if(bookList[i].getReservation().equals("")) { // �����ڰ� ������ ���� ����
				if(bookList[i].canBorrow(number)) {
					System.out.format("[%d] %s | %s | %d | %dp\n", bookList[i].getNumber(), bookList[i].getTitle(),
							bookList[i].getAuthor(), bookList[i].getYear(), bookList[i].getPage());
					System.out.println("������ �Ϸ�Ǿ����ϴ�.");
					System.out.println(currentTime());
					bookList[i].setAvailable(false);
					m.addBorrows(bookList[i]);
				}
				else {
					System.out.println("���� ���� �����Դϴ�.");
					System.out.print("�����Ͻðڽ��ϴ�?(Y/N) ");
					char ans = sc.next().charAt(0);
					if(ans=='Y')
						System.out.println(borrowTime());
				}
			}
			else if(bookList[i].getReservation().equals(m.getName())) { // �����ڰ� �����ϴ� ���
				if(bookList[i].canBorrow(number)) {
					System.out.format("[%d] %s | %s | %d | %dp\n", bookList[i].getNumber(), bookList[i].getTitle(),
							bookList[i].getAuthor(), bookList[i].getYear(), bookList[i].getPage());
					System.out.println("������ �Ϸ�Ǿ����ϴ�.");
					System.out.println(currentTime());
					bookList[i].setAvailable(false);
					bookList[i].setReservation("");
					m.addBorrows(bookList[i]);
				}
				else {
					System.out.println("���� ���� �����Դϴ�.");
				}
			}
			else { // �����ڰ� ������ �ٸ� ����� �����Ϸ� �� ���
				if(bookList[i].canBorrow(number))
					System.out.println("���� ���� �����̹Ƿ� ������ �� �����ϴ�.");
				else
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
		while(i < numberOfBooks) {
			if(bookList[i].isExist(number))
				break;
			i++;
		}
		// �ݳ��ϱ�
		if(i < numberOfBooks) { // ���� ����
			if(bookList[i].canBorrow(number) == false){ // ���� �Ұ� -> �ݳ� ����
				int j = 0;
				try {
					while(j < m.showBorrows().length) { 
						if((m.showBorrows()[j] instanceof Book) && (m.showBorrows()[j].getNumber() == bookList[i].getNumber())) {
							System.out.format("[%d] %s | %s | %d | %dp\n", bookList[i].getNumber(), bookList[i].getTitle(),
									bookList[i].getAuthor(), bookList[i].getYear(), bookList[i].getPage());
							System.out.println("�ݳ��� �Ϸ�Ǿ����ϴ�.");
							bookList[i].setAvailable(true);
							m.addBorrows(bookList[i]);
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
	public String currentTime() { // ���� �ð�(������)�� ����κ��� 2�� ���� �ݳ� ������ ���
		Calendar cal = Calendar.getInstance();
		String date = "������: " + cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONTH)+1) + "/" + cal.get(Calendar.DATE);
		cal.add(Calendar.DAY_OF_MONTH, 14); // ���� ���� �Ⱓ 14��
		return date + "\t�ݳ�������: " + cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONTH)+1) + "/" + cal.get(Calendar.DATE);
	}

	@Override
	public String borrowTime() { // ���� ���� ���� ��� ����
		System.out.print("Book Number: ");
		int number = sc.nextInt();
		// ���� ã��
		int i = 0;
		while(i < numberOfBooks) {
			if(bookList[i].isExist(number))
				break;
			i++;
		}
		if(i == numberOfBooks) {
			return "�������� �ʴ� �����Դϴ�";
		}
		else {
			if(bookList[i].canBorrow(number))
				return "���� �Ұ����� �����Դϴ�";
			else {
				System.out.print("�̸�: ");
				String name = sc.next();
				bookList[i].setReservation(name);
				return "����Ǿ����ϴ�"; // ���� ��(���� �Ұ�)�� ������ ���� ����
			}
		}
		
	}

}
