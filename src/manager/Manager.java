package manager;

import java.util.*;

import interfaceClass.ManagerInterface;
import utils.Office;
import utils.OfficeTime;

import java.io.*;
import java.text.SimpleDateFormat;

public class Manager implements ManagerInterface,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Office office;
	ArrayList<OfficeTime> officeTime;
	
	//transient : ��� �� ������ ������ ���.
	transient ObjectOutputStream oos = null;
	transient ObjectInputStream ois = null;
	transient ObjectOutputStream oos2 = null;
	transient ObjectInputStream ois2 = null;
	
	File f = new File(".//src//saveClass//office.txt");
	File f2 = new File(".//src//saveClass//officeTime.txt");
	
	private int[][] dayPay; // ��,�Ͽ� �ϱ� ����
	private int[] monthPay; // �� ���� ���� ����
	
	private StringBuffer sb1 = new StringBuffer();
	private StringBuffer sb2 = new StringBuffer();
	
	Date today = new Date();
	SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd / a hh:mm");
	
	public Manager(){ // ������(dayPay�迭�� monthPay�迭, officeTime ����Ʈ ��ü���� ����)
		int[] dayInMonth = {31,28,31,30,31,30,31,31,30,31,30,31};
		dayPay = new int[12][];
		for(int i=0; i<dayPay.length; i++) {
			dayPay[i] = new int[dayInMonth[i]];
		}
		monthPay = new int[12];
		this.officeTime = new ArrayList<OfficeTime>();
	}
	@Override
	public void regist(String name, int timePay) {
		office = new Office(name, timePay);
		System.out.println("�ٹ��� ["+office.getName()+"]��(��) ��ϵǾ����ϴ�.");
	}	// �ٹ��� �� �ñ� ����
	
	public void regist(String timeName, int time, int breakTime) {
		OfficeTime oTime = new OfficeTime(timeName, time, breakTime);
		for(OfficeTime ofTime : officeTime) {
			if(ofTime.getTimeName().equals(timeName)) {
				System.out.println("[Error : �ߺ��� �̸��� �ֽ��ϴ�.]");
				return;
			}
		}
		officeTime.add(oTime);
		System.out.println("["+oTime.getTimeName()+"] �ð��밡 ��ϵǾ����ϴ�.");
	}	// �ٹ� �ð��� ���
	
	//�ٹ� ���� ��� ����
	@Override
	public void delete(String name) {
		if(office.getName().equals(name)) {
			System.out.println("["+office.getName()+"]������ ��� �����Ǿ����ϴ�.");
			office.setName(null);
			office.setTimePay(0);
			officeTime.clear();
		}
		else {
			System.out.println("[Error : �ش� �ٹ����� �����ϴ�.]");
		}
	}
	//�ٹ� �ð��� ����
	public void timeDelete(String timeName) {
		for(int i=0; i<officeTime.size(); i++) {
			if(officeTime.get(i).getTimeName().equals(timeName)) {
				System.out.println("["+officeTime.get(i).getTimeName()+"] �ð��� ������ �����Ǿ����ϴ�.");
				officeTime.remove(i);
				break;
			}
		}
	}
	//�ٹ� �ð��� ���� ����
	@Override
	public void modify(String timeName, String changeName, int time, int breakTime) {
		for(int i=0; i<officeTime.size(); i++) {
			if(officeTime.get(i).getTimeName().equals(timeName)) {
				officeTime.get(i).setTimeName(changeName);
				officeTime.get(i).setTime(time);
				officeTime.get(i).setBreakTime(breakTime);
				System.out.println("********�� ��  �� ��********");
				break;
			}
		}
	}
	//���Ͽ� ���� ����
	@Override
	public String saveFile() {
		String str1 = null;
		String str2 = null;
		String strAll = null;
		if(office != null) {
			for(int i=0; i<dayPay.length; i++) {
				for(int j=0; j<dayPay[i].length; j++) {
					if(dayPay[i][j] != 0) {
						sb1.append("* "+(i+1)+"�� "+(j+1)+"��  �ϴ�= "+dayPay[i][j]+"��\r\n");
					}
				}
				monthPay[i] = 0;
				monthPayCalc(i+1);
				if(monthPay[i] != 0) {
					sb2.append("* "+(i+1)+"�� �޿� = "+monthPay[i]+"��\r\n");
				}
			}
			str1 = sb1.toString();
			str2 = sb2.toString();
			strAll = date.format(today)+"\n"+office.toString()+"\n"+"�Ǧ�������������������������\n"+"\t�ϴ� ���\n"
					+"�Ŧ�������������������������\n"+str1+"\n"+"�Ǧ�������������������������\n"
					+"\t���� ���\n"+"�Ŧ�������������������������\n"+str2+"\n��������������������������������������������������\n";
			int sb1End = sb1.length()-1;
			int sb2End = sb2.length()-1;
			sb1.delete(0, sb1End);
			sb2.delete(0, sb2End);
			return strAll;
		}
		else {
			return strAll;
		}
	}
	//��ü ���� ���
	@Override
	public void infoPrint() {
		if(!office.getName().equals(null)) {
			System.out.println("�Ǧ�������������������������������������������������");
			System.out.println("\t�� �� ��   �� ��                       ");
			System.out.println("�Ŧ�������������������������������������������������");
			System.out.println(office.toString());
			System.out.println("--------------����� �ٹ� �ð���--------------");
			for(OfficeTime oTime : officeTime) {
				System.out.println(oTime.toString());
			}
			System.out.println("�Ǧ�������������������������������������������������");
			System.out.println("\t�� ��  �� ��                         ");
			System.out.println("�Ŧ�������������������������������������������������");
			for(int i=0; i<dayPay.length; i++) {
				for(int j=0; j<dayPay[i].length; j++) {
					if(dayPay[i][j] != 0) {
						System.out.println("* "+(i+1)+"�� "+(j+1)+"��  �ϴ�= "+dayPay[i][j]+"��");
					}
				}
				monthPay[i] = 0;
				monthPayCalc(i+1);
				if(monthPay[i] != 0) {
					System.out.println("------------------------");
					System.out.println("* "+(i+1)+"�� �޿� = "+monthPay[i]+"��");
					System.out.println("������������������������������������������������");
				}
			}
		}
		else {
			System.out.println("Error : �ٹ��� ������ �����ϴ�. ���� �޴��� ���ư��ϴ�.");
		}
	}
	// �ش� ���� �ϱ� ���
	public void infoPrint(int month, int day) {
		System.out.println(month+"�� "+day+"���� �ϴ� : "+dayPay[month-1][day-1]+"��");
	}
	// �ش� ���� ���� ���
	public void infoPrint(int month) {
		monthPay[month-1] = 0;
		monthPayCalc(month);
		System.out.println(month+"�� �޿� : "+monthPay[month-1]+"��");
	}
	//�ϱ� ���
	@Override
	public int payCalc(int timePay, String timeName) {
		int oTime = 0, bTime = 0;
		for(int i=0; i<officeTime.size(); i++) {
			if(officeTime.get(i).getTimeName().equals(timeName)) {
				oTime = officeTime.get(i).getTime();
				bTime = officeTime.get(i).getBreakTime();
			}
		}
		return (oTime-bTime) * timePay;
	}
	//���� ���
	@Override
	public int monthPayCalc(int month) {
		for(int i=0; i<dayPay[month-1].length; i++) {
			monthPay[month-1] += dayPay[month-1][i];
		}
		return monthPay[month-1];
	}
	//�ϱ� ���
	@Override
	public void dayPayRegist(int month, int day, String timeName) {
		dayPay[month-1][day-1] = payCalc(office.getTimePay(), timeName);
	}
	public void payIntegragedRegist(int month, int day1, int day2, String timeName) {
		for(int i=day1; i<=day2; i++) {
			dayPay[month-1][i-1] = payCalc(office.getTimePay(), timeName); 
		}
	}
	//�ϱ� ����
	@Override
	public void deletePay(int month, int day) {
		dayPay[month-1][day-1] = 0;
	}
	
	
	// Office Ŭ���� ����
	public void officeSave() {
		try {
			oos = new ObjectOutputStream(new FileOutputStream(f));
			oos.writeObject(office);
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(oos != null) {
					oos.flush();
					oos.close();
				}
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	//Office Ŭ���� �ҷ�����
	public void officeOpen() {
		try {
			ois = new ObjectInputStream(new FileInputStream(f));
			try {
				while(true) {
					Object o = ois.readObject();
					office = (Office) o;
				}
			}catch(ClassNotFoundException e) {
				e.printStackTrace();
			}catch(EOFException e) {
				System.out.println(office.toString());
			}
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(ois != null) {
					ois.close();
				}
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		today = new Date();
	}
	//OfficeTime Ŭ���� ����
	public void officeTimeSave() {
		try {
			oos2 = new ObjectOutputStream(new FileOutputStream(f2));
			for(OfficeTime o : officeTime) {
				oos2.writeObject(o);
			}
			
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(oos2 != null) {
					oos2.flush();
					oos2.close();
				}
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	//OfficeTime Ŭ���� �ҷ�����
	public void officeTimeOpen() {
		try {
			ois2 = new ObjectInputStream(new FileInputStream(f2));
			try {
				ois2.readObject();
			}catch(ClassNotFoundException e) {
				e.printStackTrace();
			}catch(EOFException e) {
				System.out.println("�ٹ��ð��� �ҷ����� ����");
			}
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(ois2 != null) {
					ois2.close();
				}
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

/*
 * Getter Setter
 */
	public Office getOffice() {
		return office;
	}
	public ArrayList<OfficeTime> getOfficeTime() {
		return officeTime;
	}

	public int[][] getDayPay() {
		return dayPay;
	}

	public void setDayPay(int[][] dayPay) {
		this.dayPay = dayPay;
	}

	public int[] getMonthPay() {
		return monthPay;
	}

	public void setMonthPay(int[] monthPay) {
		this.monthPay = monthPay;
	}
}
