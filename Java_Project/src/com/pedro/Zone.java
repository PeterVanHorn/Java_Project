package com.pedro;

public class Zone implements Comparable<Zone>{
	
	private String zoneName;
	private String zoneType;
	private Integer runTime;
	
	
	public int getRunTime() {
		return runTime;
	}

	public void setRunTime(int runTime) {
		this.runTime = runTime;
	}

	public String getZoneName() {
		return zoneName;
	}

	public String getZoneType() {
		return zoneType;
	}

	@Override
	public int compareTo(Zone z) 
	{
		int result = runTime.compareTo(z.getRunTime());
		if (result == 0) 
		{
			result = Integer.valueOf(runTime).compareTo(z.getRunTime());
		}
		return result;
	}
	
	
}
