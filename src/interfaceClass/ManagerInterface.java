package interfaceClass;

public interface ManagerInterface {
	public void regist(String name, int timePay);
	public void delete(String name);
	public void modify(String timeName, String changeName, int time, int breakTime);
	public String saveFile();
	public void infoPrint();
	public int payCalc(int timePay, String timeName);
	public int monthPayCalc(int month);
	public void dayPayRegist(int month, int day, String timeName);
	public void deletePay(int month, int day);
}
