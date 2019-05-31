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
		
		System.out.println("���ѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѦ�");
		System.out.println("\t  �� ��   �� ��   �� �� �� ��");
		System.out.println("���ѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѦ�");
		
		while(true) {
			System.out.println("========================Main  Menu========================");
			System.out.println("[ 1.�ٹ��� ���� ���  2.�޿� ����  3.����  4.�ٹ� �ð��� ����  5.���   6.���� ���� ���  0.���� ]");
			System.out.print("�޴� ���� : ");
			menu = Integer.parseInt(sc.nextLine());
			switch(menu) {
			case 0:
				System.out.println("~~~~~~~~~~�����մϴ�~~~~~~~~~~");
				System.exit(0);
				break;
			case 1:
				if(ma.getOffice() == null) {
					System.out.println("[::::::::::: �ٹ��� ���� ��� ����Դϴ� :::::::::::]");
					System.out.println("*** ����� �ٹ��� �̸��� �ñ��� �Է����ּ��� ***");
					System.out.print("�ٹ��� �̸� : ");
					name = sc.nextLine();
					System.out.print("�ñ� : ");
					timePay = Integer.parseInt(sc.nextLine());
					ma.regist(name, timePay);
				}
				if(ma.getOffice() != null) {
					System.out.println("[::::::::::: �ٹ� �ð��� ��� ����Դϴ� :::::::::::]");
					System.out.println("*** ����� �ٹ� �ð��� �̸��� �ٹ� �ð� �� �޽� �ð��� �Է����ּ��� ***");
					System.out.print("�ٹ� �ð��� �̸� : ");
					timeName = sc.nextLine();
					System.out.print("�ٹ� �ð� (�� ����) : ");
					time = Integer.parseInt(sc.nextLine());
					if(time <= 0 || time >= 24) {
						System.out.println("[Error : 1�ð�~23�ð� ������ �ð��� �Է����ּ���.]");
						break;
					}
					System.out.print("�޽� �ð� (�� ����) : ");
					breakTime = Integer.parseInt(sc.nextLine());
					if(breakTime < 0 || breakTime >= 24) {
						System.out.println("[Error : 0~23�ð� ������ �ð��� �Է����ּ���.]");
						break;
					}
					ma.regist(timeName, time, breakTime);
				}
				break;
			case 2:
				System.out.println("[::::::::::: �޿� ���� ����Դϴ� :::::::::::]");
				System.out.println("1�� = �޿� ���\t 2�� = �ϰ� ���\t 3�� = �޿� ����");
				System.out.print("��ȣ ���� : ");
				num = Integer.parseInt(sc.nextLine());
				if(num == 1) {
					if(ma.getOffice() != null) {
						System.out.println("[::::::::::: �޿� ��� ����Դϴ� :::::::::::]");
						System.out.print("�� : ");
						month = Integer.parseInt(sc.nextLine());
						if(month <= 0 || month > 12) {
							System.out.println("[Error : 1��~12�� ���̷� �Է����ּ���.]");
							break;
						}
						cal.set(2019, month-1, 1); // ���� ������ ���� ���ϱ����� ����
						System.out.print("�� : ");
						day = Integer.parseInt(sc.nextLine());
						if(day <= 0 || day > cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
							System.out.println("[Error : "+month+"���� ���� �� or ������ ���� �ʰ��Ͽ����ϴ�.]");
							break;
						}
						System.out.print("�ٹ� �ð��� �̸� : ");
						timeName = sc.nextLine();
						ma.dayPayRegist(month, day, timeName);
					}else {
						System.out.println("[Error : �ٹ��� ������ �����ϴ�.]");
					}
				}
				else if(num == 2) {
					System.out.println("[::::::::::: �ϰ� ��� ����Դϴ� :::::::::::]");
					System.out.println("*** �ϰ� ��� �ÿ��� �� ������ �ð��븸 ����� �� �ֽ��ϴ�. ***");
					System.out.print("�� : ");
					month = Integer.parseInt(sc.nextLine());
					if(month <= 0 || month > 12) {
						System.out.println("[Error : 1��~12�� ���̷� �Է����ּ���.]");
						break;
					}
					cal.set(2019, month-1, 1); // ���� ������ ���� ���ϱ����� ����
					System.out.print("���� �� : ");
					int day1 = Integer.parseInt(sc.nextLine());
					if(day1 <= 0 || day1 > cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
						System.out.println("[Error : "+month+"���� ���� �� or ������ ���� �ʰ��Ͽ����ϴ�.]");
						break;
					}
					System.out.print("�� �� : ");
					int day2 = Integer.parseInt(sc.nextLine());
					if(day2 <= 0 || day2 > cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
						System.out.println("[Error : "+month+"���� ���� �� or ������ ���� �ʰ��Ͽ����ϴ�.]");
						break;
					}
					System.out.print("�ٹ� �ð��� �̸� : ");
					timeName = sc.nextLine();
					ma.payIntegragedRegist(month, day1, day2, timeName);
				}
				else if(num == 3) {
					System.out.println("[::::::::::: �޿� ���� ����Դϴ� :::::::::::]");
					System.out.println("*** ���� �� ��¥ �Է� ***");
					System.out.print("�� : ");
					month = Integer.parseInt(sc.nextLine());
					if(ma.getMonthPay()[month-1] == 0) {
						System.out.println("[Error : "+month+"���� ��ϵ� �޿��� �����ϴ�.]");
						break;
					}
					if(month <= 0 || month > 12) {
						System.out.println("[Error : 1��~12�� ���̷� �Է����ּ���.]");
						break;
					}
					cal.set(2019, month-1, 1); // ���� ������ ���� ���ϱ����� ����
					System.out.print("�� : ");
					day = Integer.parseInt(sc.nextLine());
					if(ma.getDayPay()[month-1][day-1] == 0) {
						System.out.println("[Error : �ش� ��¥�� ��ϵ� �޿��� �����ϴ�.]");
						break;
					}
					if(day <= 0 || day > cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
						System.out.println("[Error : "+month+"���� ���� �� or ������ ���� �ʰ��Ͽ����ϴ�.]");
						break;
					}
					ma.deletePay(month, day);
				}
				else {System.out.println("Error : �ش��ȣ ����. ���θ޴��� ���ư��ϴ�!");}
				break;
				
			case 3:
				System.out.println("[::::::::::: ���� ����Դϴ� :::::::::::]");
				System.out.println("1�� = ��ü ���� ����\t 2�� = �ð��� ���� ����");
				System.out.print("��ȣ ���� : ");
				num = Integer.parseInt(sc.nextLine());
				if(num == 1) {
					if(ma.getOffice() != null) {
						System.out.print("���� �� �ٹ��� �̸� : ");
						name = sc.nextLine();
						ma.delete(name);
						break;
					}
					else {
						System.out.println("[Error : ��ϵ� �ٹ����� �����ϴ�.]");
						break;
					}
				}
				else if(num == 2) {
					if(ma.getOfficeTime().isEmpty() == false) {
						System.out.print("���� �� �ٹ� �ð��� �̸� : ");
						timeName = sc.nextLine();
						ma.timeDelete(timeName);
						break;
					}
					else {
						System.out.println("[Error : ��ϵ� �ð��밡 �����ϴ�.]");
						break;
					}
				}
				else {System.out.println("Error : �ش��ȣ ����. ���θ޴��� ���ư��ϴ�!");}
				break;
			case 4:
				System.out.println("[::::::::::: ���� ����Դϴ� :::::::::::]");
				if(ma.getOfficeTime().isEmpty() == false) {
					System.out.print("���� �� �ٹ� �ð��� �̸� �Է� : ");
					timeName = sc.nextLine();
					System.out.print("���� �� �̸� : ");
					changeName = sc.nextLine();
					System.out.print("���� �� �ٹ� �ð� : ");
					time = Integer.parseInt(sc.nextLine());
					if(time <= 0 || time >= 24) {
						System.out.println("[Error : 1�ð�~23�ð� ������ �ð��� �Է����ּ���.]");
						break;
					}
					System.out.print("���� �� �޽� �ð� : ");
					breakTime = Integer.parseInt(sc.nextLine());
					if(breakTime < 0 || breakTime >= 24) {
						System.out.println("[Error : 0~23�ð� ������ �ð��� �Է����ּ���.]");
						break;
					}
					ma.modify(timeName, changeName, time, breakTime);
					break;
				}
				else {
					System.out.println("[Error : ��ϵ� �ð��밡 �����ϴ�.]");
					break;
				}
			case 5:
				System.out.println("[::::::::::: ��� ����Դϴ� :::::::::::]");
				System.out.println("1�� = ��ü ���� ���\t 2��= �ϴ� ���\t 3��= ���� ���");
				System.out.print("��ȣ ���� : ");
				num = Integer.parseInt(sc.nextLine());
				if(num == 1) {
					if(ma.getOfficeTime().isEmpty() == false) {
						ma.infoPrint();
					}
					else {System.out.println("[Error : ����� ������ �����ϴ�.]");}
				}
				else if(num == 2) {
					System.out.print("�� : ");
					month = Integer.parseInt(sc.nextLine());
					if(month <= 0 || month > 12) {
						System.out.println("[Error : 1��~12�� ���̷� �Է����ּ���.]");
						break;
					}
					cal.set(2019, month-1, 1); // ���� ������ ���� ���ϱ����� ����
					System.out.print("�� : ");
					day = Integer.parseInt(sc.nextLine());
					if(day <= 0 || day > cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
						System.out.println("[Error : "+month+"���� ���� �� or ������ ���� �ʰ��Ͽ����ϴ�.]");
						break;
					}
					if(ma.getDayPay()[month-1][day-1] != 0) {
						ma.infoPrint(month, day);
					}
					else {System.out.println("[Error : �ش� ��¥�� ��ϵ� �޿��� �����ϴ�.]");}
				}
				else if(num == 3) {
					System.out.print("�� : ");
					month = Integer.parseInt(sc.nextLine());
					if(month <= 0 || month > 12) {
						System.out.println("[Error : 1��~12�� ���̷� �Է����ּ���.]");
						break;
					}
					ma.infoPrint(month);
				}
				else {System.out.println("Error : �ش� ��ȣ ����. ���� �޴��� ���ư��ϴ�!");}
				break;
				
			case 6:
				System.out.println("[::::::::::: ���� ���� ����Դϴ�. :::::::::::]");
				System.out.println("1�� = ���� ����\t 2�� = ���� �ҷ�����");
				System.out.print("��ȣ ���� : ");
				num = Integer.parseInt(sc.nextLine());
				if(num == 1) {
					if(ma.getOffice() == null) {
						System.out.println("[Error : ���� �� ������ �����ϴ�.]");
					}
					else {
						System.out.println("-----------------------------");
						System.out.println("������ �ð� : "+date.format(today));
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
						System.out.println("######### ���� �Ϸ� ^^ #########");
					}
				}
				if(num == 2) {
					if(!openF.exists()) {
						System.out.println("[Error : �ҷ��� ������ �����ϴ�.]");
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
							System.out.println("######### �ҷ����� �Ϸ�  #########");
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
				System.out.println("Error : �ش� ��ȣ ����. �ٽ� �Է����ּ���!!");
			}
		}
	}
}
