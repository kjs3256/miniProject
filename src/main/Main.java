package main;

import java.util.*;
import manager.Manager;
import java.io.*;
import java.text.SimpleDateFormat;

public class Main {
	public static void main(String[] args){
		Manager ma = new Manager();
		Scanner sc = new Scanner(System.in);
		
		File f = new File(".//src//saveInfo//pay_information.txt");
		File openF = new File(".//src//saveClass//Manager_Class.txt");
		FileWriter fw = null;
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		
		int menu = 0;
		String name = null, timeName = null, changeName = null;
		int time = 0, breakTime = 0, timePay = 0, num = 0, month = 0, day = 0;
		
		Date today = new Date();
		SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd / a hh:mm");
		Calendar cal = Calendar.getInstance();
		
		System.out.println("┌ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ┐");
		System.out.println("\t  급 여   관 리   프 로 그 램");
		System.out.println("└ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ┘");
		
		while(true) {
			System.out.println("========================Main  Menu========================");
			System.out.println("[ 1.근무지 정보 등록  2.급여 관리  3.삭제  4.근무 시간대 수정  5.출력   6.파일 관리 모드  0.종료 ]");
			System.out.print("메뉴 선택 : ");
			menu = Integer.parseInt(sc.nextLine());
			switch(menu) {
			case 0:
				System.out.println("~~~~~~~~~~종료합니다~~~~~~~~~~");
				System.exit(0);
				break;
			case 1:
				if(ma.getOffice() == null) {
					System.out.println("[::::::::::: 근무지 정보 등록 모드입니다 :::::::::::]");
					System.out.println("*** 등록할 근무지 이름과 시급을 입력해주세요 ***");
					System.out.print("근무지 이름 : ");
					name = sc.nextLine();
					System.out.print("시급 : ");
					timePay = Integer.parseInt(sc.nextLine());
					ma.regist(name, timePay);
				}
				if(ma.getOffice() != null) {
					System.out.println("[::::::::::: 근무 시간대 등록 모드입니다 :::::::::::]");
					System.out.println("*** 등록할 근무 시간대 이름과 근무 시간 및 휴식 시간을 입력해주세요 ***");
					System.out.print("근무 시간대 이름 : ");
					timeName = sc.nextLine();
					System.out.print("근무 시간 (시 단위) : ");
					time = Integer.parseInt(sc.nextLine());
					if(time <= 0 || time >= 24) {
						System.out.println("[Error : 1시간~23시간 사이의 시간을 입력해주세요.]");
						break;
					}
					System.out.print("휴식 시간 (시 단위) : ");
					breakTime = Integer.parseInt(sc.nextLine());
					if(breakTime < 0 || breakTime >= 24) {
						System.out.println("[Error : 0~23시간 사이의 시간을 입력해주세요.]");
						break;
					}
					ma.regist(timeName, time, breakTime);
				}
				break;
			case 2:
				System.out.println("[::::::::::: 급여 관리 모드입니다 :::::::::::]");
				System.out.println("1번 = 급여 등록\t 2번 = 일괄 등록\t 3번 = 급여 삭제");
				System.out.print("번호 선택 : ");
				num = Integer.parseInt(sc.nextLine());
				if(num == 1) {
					if(ma.getOffice() != null) {
						System.out.println("[::::::::::: 급여 등록 모드입니다 :::::::::::]");
						System.out.print("월 : ");
						month = Integer.parseInt(sc.nextLine());
						if(month <= 0 || month > 12) {
							System.out.println("[Error : 1월~12월 사이로 입력해주세요.]");
							break;
						}
						cal.set(2019, month-1, 1); // 월별 마지막 일을 구하기위한 세팅
						System.out.print("일 : ");
						day = Integer.parseInt(sc.nextLine());
						if(day <= 0 || day > cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
							System.out.println("[Error : "+month+"월의 시작 일 or 마지막 일을 초과하였습니다.]");
							break;
						}
						System.out.print("근무 시간대 이름 : ");
						timeName = sc.nextLine();
						ma.dayPayRegist(month, day, timeName);
					}else {
						System.out.println("[Error : 근무지 정보가 없습니다.]");
					}
				}
				else if(num == 2) {
					System.out.println("[::::::::::: 일괄 등록 모드입니다 :::::::::::]");
					System.out.println("*** 일괄 등록 시에는 한 가지의 시간대만 등록할 수 있습니다. ***");
					System.out.print("월 : ");
					month = Integer.parseInt(sc.nextLine());
					if(month <= 0 || month > 12) {
						System.out.println("[Error : 1월~12월 사이로 입력해주세요.]");
						break;
					}
					cal.set(2019, month-1, 1); // 월별 마지막 일을 구하기위한 세팅
					System.out.print("시작 일 : ");
					int day1 = Integer.parseInt(sc.nextLine());
					if(day1 <= 0 || day1 > cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
						System.out.println("[Error : "+month+"월의 시작 일 or 마지막 일을 초과하였습니다.]");
						break;
					}
					System.out.print("끝 일 : ");
					int day2 = Integer.parseInt(sc.nextLine());
					if(day2 <= 0 || day2 > cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
						System.out.println("[Error : "+month+"월의 시작 일 or 마지막 일을 초과하였습니다.]");
						break;
					}
					System.out.print("근무 시간대 이름 : ");
					timeName = sc.nextLine();
					ma.payIntegragedRegist(month, day1, day2, timeName);
				}
				else if(num == 3) {
					System.out.println("[::::::::::: 급여 삭제 모드입니다 :::::::::::]");
					System.out.println("*** 삭제 할 날짜 입력 ***");
					System.out.print("월 : ");
					month = Integer.parseInt(sc.nextLine());
					if(ma.getMonthPay()[month-1] == 0) {
						System.out.println("[Error : "+month+"월에 등록된 급여가 없습니다.]");
						break;
					}
					if(month <= 0 || month > 12) {
						System.out.println("[Error : 1월~12월 사이로 입력해주세요.]");
						break;
					}
					cal.set(2019, month-1, 1); // 월별 마지막 일을 구하기위한 세팅
					System.out.print("일 : ");
					day = Integer.parseInt(sc.nextLine());
					if(ma.getDayPay()[month-1][day-1] == 0) {
						System.out.println("[Error : 해당 날짜에 등록된 급여가 없습니다.]");
						break;
					}
					if(day <= 0 || day > cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
						System.out.println("[Error : "+month+"월의 시작 일 or 마지막 일을 초과하였습니다.]");
						break;
					}
					ma.deletePay(month, day);
				}
				else {System.out.println("Error : 해당번호 없음. 메인메뉴로 돌아갑니다!");}
				break;
				
			case 3:
				System.out.println("[::::::::::: 삭제 모드입니다 :::::::::::]");
				System.out.println("1번 = 전체 정보 삭제\t 2번 = 시간대 정보 삭제");
				System.out.print("번호 선택 : ");
				num = Integer.parseInt(sc.nextLine());
				if(num == 1) {
					if(ma.getOffice() != null) {
						System.out.print("삭제 할 근무지 이름 : ");
						name = sc.nextLine();
						ma.delete(name);
						break;
					}
					else {
						System.out.println("[Error : 등록된 근무지가 없습니다.]");
						break;
					}
				}
				else if(num == 2) {
					if(ma.getOfficeTime().isEmpty() == false) {
						System.out.print("삭제 할 근무 시간대 이름 : ");
						timeName = sc.nextLine();
						ma.timeDelete(timeName);
						break;
					}
					else {
						System.out.println("[Error : 등록된 시간대가 없습니다.]");
						break;
					}
				}
				else {System.out.println("Error : 해당번호 없음. 메인메뉴로 돌아갑니다!");}
				break;
			case 4:
				System.out.println("[::::::::::: 수정 모드입니다 :::::::::::]");
				if(ma.getOfficeTime().isEmpty() == false) {
					System.out.print("수정 할 근무 시간대 이름 입력 : ");
					timeName = sc.nextLine();
					System.out.print("변경 할 이름 : ");
					changeName = sc.nextLine();
					System.out.print("변경 할 근무 시간 : ");
					time = Integer.parseInt(sc.nextLine());
					if(time <= 0 || time >= 24) {
						System.out.println("[Error : 1시간~23시간 사이의 시간을 입력해주세요.]");
						break;
					}
					System.out.print("변경 할 휴식 시간 : ");
					breakTime = Integer.parseInt(sc.nextLine());
					if(breakTime < 0 || breakTime >= 24) {
						System.out.println("[Error : 0~23시간 사이의 시간을 입력해주세요.]");
						break;
					}
					ma.modify(timeName, changeName, time, breakTime);
					break;
				}
				else {
					System.out.println("[Error : 등록된 시간대가 없습니다.]");
					break;
				}
			case 5:
				System.out.println("[::::::::::: 출력 모드입니다 :::::::::::]");
				System.out.println("1번 = 전체 정보 출력\t 2번= 일당 출력\t 3번= 월급 출력");
				System.out.print("번호 선택 : ");
				num = Integer.parseInt(sc.nextLine());
				if(num == 1) {
					if(ma.getOfficeTime().isEmpty() == false) {
						ma.infoPrint();
					}
					else {System.out.println("[Error : 출력할 정보가 없습니다.]");}
				}
				else if(num == 2) {
					System.out.print("월 : ");
					month = Integer.parseInt(sc.nextLine());
					if(month <= 0 || month > 12) {
						System.out.println("[Error : 1월~12월 사이로 입력해주세요.]");
						break;
					}
					cal.set(2019, month-1, 1); // 월별 마지막 일을 구하기위한 세팅
					System.out.print("일 : ");
					day = Integer.parseInt(sc.nextLine());
					if(day <= 0 || day > cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
						System.out.println("[Error : "+month+"월의 시작 일 or 마지막 일을 초과하였습니다.]");
						break;
					}
					if(ma.getDayPay()[month-1][day-1] != 0) {
						ma.infoPrint(month, day);
					}
					else {System.out.println("[Error : 해당 날짜에 등록된 급여가 없습니다.]");}
				}
				else if(num == 3) {
					System.out.print("월 : ");
					month = Integer.parseInt(sc.nextLine());
					if(month <= 0 || month > 12) {
						System.out.println("[Error : 1월~12월 사이로 입력해주세요.]");
						break;
					}
					ma.infoPrint(month);
				}
				else {System.out.println("Error : 해당 번호 없음. 메인 메뉴로 돌아갑니다!");}
				break;
				
			case 6:
				System.out.println("[::::::::::: 파일 관리 모드입니다. :::::::::::]");
				System.out.println("1번 = 파일 저장\t 2번 = 파일 불러오기");
				System.out.print("번호 선택 : ");
				num = Integer.parseInt(sc.nextLine());
				if(num == 1) {
					if(ma.getOffice() == null) {
						System.out.println("[Error : 저장 할 정보가 없습니다.]");
					}
					else {
						System.out.println("-----------------------------");
						System.out.println("저장한 시간 : "+date.format(today));
						System.out.println("-----------------------------");
						try {
							fw = new FileWriter(f, true);
							fw.write(ma.saveFile());
							fos = new FileOutputStream(openF);
							oos = new ObjectOutputStream(fos);
							oos.writeObject(ma);
						}catch(IOException e) {
							e.printStackTrace();
						}
						finally {
							try {
								if(fw != null) {
									fw.close();
								}
								if(oos != null) {
									oos.flush();
									oos.close();
								}
							}catch(IOException e) {
							e.printStackTrace();
							}
						}
						ma.officeSave();
						ma.officeTimeSave();
						System.out.println("######### 저장 완료 ^^ #########");
					}
				}
				if(num == 2) {
					if(!openF.exists()) {
						System.out.println("[Error : 불러올 파일이 없습니다.]");
						break;
					}
					try {
						ois = new ObjectInputStream(new FileInputStream(openF));
						try {
							while(true) {
								Object o = ois.readObject();
								ma = (Manager) o;
							}
						}catch(ClassNotFoundException e) {
							e.printStackTrace();
						}catch(EOFException e) {
							System.out.println("######### 불러오기 완료  #########");
						}
					}catch(IOException e) {
						e.printStackTrace();
					}
					finally {
						try {
							if(ois != null) {
								ois.close();
							}
						}catch(IOException e) {
						e.printStackTrace();
						}
					}
					today = new Date();
					ma.officeOpen();
					ma.officeTimeOpen();
				}
				break;
				
			default:
				System.out.println("Error : 해당 번호 없음. 다시 입력해주세요!!");
			}
		}
	}
}
