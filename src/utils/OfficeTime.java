package utils;

import java.io.Serializable;

public class OfficeTime implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String timeName;
	private int time;
	private int breakTime;
	
	public OfficeTime(String timeName, int time, int breakTime) {
		this.timeName = timeName;
		this.time = time;
		this.breakTime = breakTime;
	}

	public String getTimeName() {
		return timeName;
	}


	public void setTimeName(String timeName) {
		this.timeName = timeName;
	}


	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getBreakTime() {
		return breakTime;
	}

	public void setBreakTime(int breakTime) {
		this.breakTime = breakTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((timeName == null) ? 0 : timeName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OfficeTime other = (OfficeTime) obj;
		if (timeName == null) {
			if (other.timeName != null)
				return false;
		} else if (!timeName.equals(other.timeName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "[근무 시간대 이름=" + timeName + ", 근무 시간=" + time + "시간, 휴식 시간=" + breakTime + "시간]";
	}
	
}
