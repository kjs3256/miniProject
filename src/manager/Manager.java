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
	
	//transient : 출력 시 제외할 변수에 사용.
	transient ObjectOutputStream oos = null;
	transient ObjectInputStream ois = null;
	transient ObjectOutputStream oos2 = null;
	transient ObjectInputStream ois2 = null;
	
	File f = new File(".//src//saveClass//office.txt");
	File f2 = new File(".//src//saveClass//officeTime.txt");
	
	private int[][] dayPay; // 월,일에 일급 저장
	private int[] monthPay; // 그 달의 월급 저장
	
	private StringBuffer sb1 = new StringBuffer();
	private StringBuffer sb2 = new StringBuffer();
	
	Date today = new Date();
	SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd / a hh:mm");
	
	public Manager(){ // 생성자(dayPay배열과 monthPay배열, officeTime 리스트 객체들을 생성)
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
		System.out.println("근무지 ["+office.getName()+"]이(가) 등록되었습니다.");
	}	// 근무지 및 시급 동록
	
	public void regist(String timeName, int time, int breakTime) {
		OfficeTime oTime = new OfficeTime(timeName, time, breakTime);
		for(OfficeTime ofTime : officeTime) {
			if(ofTime.getTimeName().equals(timeName)) {
				System.out.println("[Error : 중복된 이름이 있습니다.]");
				return;
			}
		}
		officeTime.add(oTime);
		System.out.println("["+oTime.getTimeName()+"] 시간대가 등록되었습니다.");
	}	// 근무 시간대 등록
	
	//근무 정보 모두 삭제
	@Override
	public void delete(String name) {
		if(office.getName().equals(name)) {
			System.out.println("["+office.getName()+"]정보가 모두 삭제되었습니다.");
			office.setName(null);
			office.setTimePay(0);
			officeTime.clear();
		}
		else {
			System.out.println("[Error : 해당 근무지가 없습니다.]");
		}
	}
	//근무 시간대 삭제
	public void timeDelete(String timeName) {
		for(int i=0; i<officeTime.size(); i++) {
			if(officeTime.get(i).getTimeName().equals(timeName)) {
				System.out.println("["+officeTime.get(i).getTimeName()+"] 시간대 정보가 삭제되었습니다.");
				officeTime.remove(i);
				break;
			}
		}
	}
	//근무 시간대 정보 수정
	@Override
	public void modify(String timeName, String changeName, int time, int breakTime) {
		for(int i=0; i<officeTime.size(); i++) {
			if(officeTime.get(i).getTimeName().equals(timeName)) {
				officeTime.get(i).setTimeName(changeName);
				officeTime.get(i).setTime(time);
				officeTime.get(i).setBreakTime(breakTime);
				System.out.println("********수 정  완 료********");
				break;
			}
		}
	}
	//파일에 정보 저장
	@Override
	public String saveFile() {
		String str1 = null;
		String str2 = null;
		String strAll = null;
		if(office != null) {
			for(int i=0; i<dayPay.length; i++) {
				for(int j=0; j<dayPay[i].length; j++) {
					if(dayPay[i][j] != 0) {
						sb1.append("* "+(i+1)+"월 "+(j+1)+"일  일당= "+dayPay[i][j]+"원\r\n");
					}
				}
				monthPay[i] = 0;
				monthPayCalc(i+1);
				if(monthPay[i] != 0) {
					sb2.append("* "+(i+1)+"월 급여 = "+monthPay[i]+"원\r\n");
				}
			}
			str1 = sb1.toString();
			str2 = sb2.toString();
			strAll = date.format(today)+"\n"+office.toString()+"\n"+"┎────────────┒\n"+"\t일당 목록\n"
					+"┖────────────┚\n"+str1+"\n"+"┎────────────┒\n"
					+"\t월급 목록\n"+"┖────────────┚\n"+str2+"\n━━━━━━━━━━━━━━━━━━━━━━━━━\n";
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
	//전체 정보 출력
	@Override
	public void infoPrint() {
		if(!office.getName().equals(null)) {
			System.out.println("┎────────────────────────┒");
			System.out.println("\t근 무 지   정 보                       ");
			System.out.println("┖────────────────────────┚");
			System.out.println(office.toString());
			System.out.println("--------------저장된 근무 시간대--------------");
			for(OfficeTime oTime : officeTime) {
				System.out.println(oTime.toString());
			}
			System.out.println("┎────────────────────────┒");
			System.out.println("\t급 여  정 보                         ");
			System.out.println("┖────────────────────────┚");
			for(int i=0; i<dayPay.length; i++) {
				for(int j=0; j<dayPay[i].length; j++) {
					if(dayPay[i][j] != 0) {
						System.out.println("* "+(i+1)+"월 "+(j+1)+"일  일당= "+dayPay[i][j]+"원");
					}
				}
				monthPay[i] = 0;
				monthPayCalc(i+1);
				if(monthPay[i] != 0) {
					System.out.println("------------------------");
					System.out.println("* "+(i+1)+"월 급여 = "+monthPay[i]+"원");
					System.out.println("────────────────────────");
				}
			}
		}
		else {
			System.out.println("Error : 근무지 정보가 없습니다. 메인 메뉴로 돌아갑니다.");
		}
	}
	// 해당 날의 일급 출력
	public void infoPrint(int month, int day) {
		System.out.println(month+"월 "+day+"일의 일당 : "+dayPay[month-1][day-1]+"원");
	}
	// 해당 월의 월급 출력
	public void infoPrint(int month) {
		monthPay[month-1] = 0;
		monthPayCalc(month);
		System.out.println(month+"월 급여 : "+monthPay[month-1]+"원");
	}
	//일급 계산
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
	//월급 계산
	@Override
	public int monthPayCalc(int month) {
		for(int i=0; i<dayPay[month-1].length; i++) {
			monthPay[month-1] += dayPay[month-1][i];
		}
		return monthPay[month-1];
	}
	//일급 등록
	@Override
	public void dayPayRegist(int month, int day, String timeName) {
		dayPay[month-1][day-1] = payCalc(office.getTimePay(), timeName);
	}
	public void payIntegragedRegist(int month, int day1, int day2, String timeName) {
		for(int i=day1; i<=day2; i++) {
			dayPay[month-1][i-1] = payCalc(office.getTimePay(), timeName); 
		}
	}
	//일급 삭제
	@Override
	public void deletePay(int month, int day) {
		dayPay[month-1][day-1] = 0;
	}
	
	
	// Office 클래스 저장
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
	//Office 클래스 불러오기
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
	//OfficeTime 클래스 저장
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
	//OfficeTime 클래스 불러오기
	public void officeTimeOpen() {
		try {
			ois2 = new ObjectInputStream(new FileInputStream(f2));
			try {
				ois2.readObject();
			}catch(ClassNotFoundException e) {
				e.printStackTrace();
			}catch(EOFException e) {
				System.out.println("근무시간대 불러오기 성공");
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
