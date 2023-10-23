package com.resshare.map.model;

public class Point {
	PointDegree latDegree;
	PointDegree logDegree;
	private String latDegreeDMS;
	private String logDegreeDMS;

	public PointDegree getLatDegree() {
		return latDegree;
	}

	public PointDegree getLogDegree() {
		return logDegree;
	}

	public void setLatDegree(PointDegree latDegree) {
		this.latDegree = latDegree;
	}

	public void setLogDegree(PointDegree logDegree) {
		this.logDegree = logDegree;
	}

	public Point(PointDegree latDegree, PointDegree logDegree) {
		this.latDegree = latDegree;
		this.logDegree = logDegree;
		this.latDegreeDMS = this.latDegree.getDMS();
		this.logDegreeDMS = this.logDegree.getDMS();

		// Degree:Minute:Second
		// TODO Auto-generated constructor stub

	}

	public Point(String sDegreeDMS) {
		String[] arrNE = sDegreeDMS.split(" ");

		this.latDegreeDMS = arrNE[0];
		this.logDegreeDMS = arrNE[1];
		this.latDegree = new PointDegree(latDegreeDMS);
		this.logDegree = new PointDegree(logDegreeDMS);

		// Degree:Minute:Second
		// TODO Auto-generated constructor stub

	}

	public Point(String latDegreeDMS, String logDegreeDMS) {
		this.latDegreeDMS = latDegreeDMS;
		this.logDegreeDMS = logDegreeDMS;
		this.latDegree = new PointDegree(latDegreeDMS);
		this.logDegree = new PointDegree(logDegreeDMS);

		// Degree:Minute:Second
		// TODO Auto-generated constructor stub

	}

	public String getPointKey() {
		return this.latDegree.getDMS() + " " + this.logDegree.getDMS();

	}

	public Point getTop()

	{
		PointDegree latDegreeTop = getDegreeTop();

		Point p = new Point(latDegreeTop, logDegree);
		return p;

	}

	private PointDegree getDegreeTop() {
		int degree = latDegree.getDegree();
		int minute = latDegree.getMinute();
		if (minute < 59)
			minute = minute + 1;
		else {
			degree = degree + 1;
			minute = 0;
		}
		String sMinute = String.valueOf(minute);
		if (minute < 10)
			sMinute = "0" + sMinute;
		String sDegree = String.valueOf(degree);
		if (degree < 10)
			sDegree = "0" + sDegree;
		 
		String sDMS = sDegree +"°"+ sMinute+"'" + latDegree.getSecond();

		PointDegree latDegreeTop = new PointDegree(sDMS);
		return latDegreeTop;
	}

	public Point getBottom()

	{

		PointDegree latDegreeBottom = getDegreeBottom();

		Point p = new Point(latDegreeBottom, logDegree);
		return p;

	}

	private PointDegree getDegreeBottom() {
		int degree = latDegree.getDegree();
		int minute = latDegree.getMinute();
		if (minute > 0)
			minute = minute - 1;
		else {
			degree = degree - 1;
			minute = 59;
		}
		String sMinute = String.valueOf(minute);
		if (minute < 10)
			sMinute = "0" + sMinute;
		String sDegree = String.valueOf(degree);
		if (degree < 10)
			sDegree = "0" + sDegree;
		String sDMS = sDegree + "°"+sMinute +"'"+ latDegree.getSecond();

		PointDegree latDegreeBottom = new PointDegree(sDMS);
		return latDegreeBottom;
	}

	public Point getLeft()

	{
		PointDegree logDegreeLeft = getDegreeLeft();

		Point p = new Point(latDegree, logDegreeLeft);
		return p;

	}

	private PointDegree getDegreeLeft() {
		int degree = logDegree.getDegree();
		int minute = logDegree.getMinute();
		if (minute > 0)
			minute = minute - 1;
		else {
			degree = degree - 1;
			minute = 59;
		}
		String sMinute = String.valueOf(minute);
		if (minute < 10)
			sMinute = "0" + sMinute;
		String sDegree = String.valueOf(degree);
		if (degree < 10)
			sDegree = "0" + sDegree;
		String sDMS = sDegree +"°"+ sMinute+"'" + logDegree.getSecond();

		PointDegree logDegreeLeft = new PointDegree(sDMS);
		return logDegreeLeft;
	}

	public Point getRight()

	{

		PointDegree logDegreeRight = getDegreeRight();

		Point p = new Point(latDegree, logDegreeRight);
		return p;

	}

	private PointDegree getDegreeRight() {
		int degree = logDegree.getDegree();
		int minute = logDegree.getMinute();
		if (minute < 59)
			minute = minute + 1;
		else {
			degree = degree + 1;
			minute = 0;
		}
		String sMinute = String.valueOf(minute);
		if (minute < 10)
			sMinute = "0" + sMinute;
		String sDegree = String.valueOf(degree);
		if (degree < 10)
			sDegree = "0" + sDegree;
 
		String sDMS = sDegree +"°"+ sMinute+"'" + logDegree.getSecond();

		PointDegree logDegreeRight = new PointDegree(sDMS);
		return logDegreeRight;
	}

	public Point getRightTop()

	{

		PointDegree logDegreeRight = getDegreeRight();
		PointDegree latDegreeTop = getDegreeTop();

		Point p = new Point(latDegreeTop, logDegreeRight);
		return p;

	}

	public Point getRightBottom()

	{

		PointDegree logDegreeRight = getDegreeRight();
		PointDegree latDegreeBottom = getDegreeBottom();

		Point p = new Point(latDegreeBottom, logDegreeRight);
		return p;

	}

	public Point getLeftTop()

	{
		PointDegree logDegreeLeft = getDegreeLeft();
		PointDegree latDegreeTop = getDegreeTop();

		Point p = new Point(latDegreeTop, logDegreeLeft);
		return p;

	}

	public Point getLeftBottom()

	{
		PointDegree logDegreeLeft = getDegreeLeft();
		PointDegree latDegreeBottom = getDegreeBottom();

		Point p = new Point(latDegreeBottom, logDegreeLeft);
		return p;

	}
}
