package com.resshare.map.model;

public class PointDegree {
	int sDegree;
	int Minute;
	String sSecond;
	String sDMS;

	public int getDegree( ) {
		return sDegree;

	}

	public int getMinute( ) {
		return Minute;

	}

	public String getSecond( ) {
		return sSecond;

	}

	public void setDegree(int sDegree) {
		this.sDegree = sDegree;

	}

	public void setMinute(int Minute) {
		this.Minute = Minute;

	}

	public void setSecond(String sSecond) {
		this.sSecond = sSecond;

	}
	// 21°12'00.0"N
	public String getDMS( ) {
		return sDMS;

	}
	// 21°12'00.0"N
	//105°46'00.0"E
	public PointDegree(String sDMS ) {
		this.sDMS=sDMS;
		String[]  sArrFirtLat = sDMS.split("°");
		String sDegree = sArrFirtLat[0];
		this.sDegree =Integer.valueOf(sDegree);
		String sMinute = sArrFirtLat[1].split("'")[0];
		this.Minute= Integer.valueOf(sMinute);
		sSecond=sArrFirtLat[1].split("'")[1];
		
//		int up1SecondLat = Integer.valueOf(sSecondLat) - 1;
//
//		String sLatMin = sFirtLat + "°" + String.valueOf(up1SecondLat) + "'" + sArrFirtLat[1].split("'")[1];
//
//		String[] sArrFirtLog = sLogMax.split("°");
//		String sFirtLog = sArrFirtLog[0];
//		String sSecondLog = sArrFirtLog[1].split("'")[0];
//		;
//
//		int up1SecondLog = Integer.valueOf(sSecondLog) - 1;

		// Degree :Minute :Second
		// TODO Auto-generated constructor stub

	}

}
