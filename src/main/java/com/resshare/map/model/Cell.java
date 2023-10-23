package com.resshare.map.model;

public class Cell {

	public Cell(Point sBottomLeft, Point sBottomRight, Point sTopRight, Point sTopLeft) {

		this.sBottomLeft = sBottomLeft;
		this.sBottomRight = sBottomRight;
		this.sTopRight = sTopRight;
		this.sTopLeft = sTopLeft;
	}

	Point sBottomRight;
	Point sBottomLeft;
	Point sTopRight;
	Point sTopLeft;

	public Point getTopLeft() {

		return sTopLeft;
	}

	public void setTopLeft(Point sTopLeft) {

		this.sTopLeft = sTopLeft;
	}

	public Point getTopRight() {

		return sTopRight;
	}

	public void setTopRight(Point sTopRight) {

		this.sTopRight = sTopRight;
	}

	public Point getBottomLeft() {

		return sBottomLeft;
	}

	public void setBottomLeft(Point sBottomLeft) {

		this.sBottomLeft = sBottomLeft;
	}

	public Point getBottomRight() {

		return sBottomRight;
	}

	public void setBottomRight(Point sBottomRight) {

		this.sBottomRight = sBottomRight;
	}

 

	public String getCellKey() {
		return sBottomLeft.getPointKey() + "-" + sBottomRight.getPointKey() + "-" + sTopRight.getPointKey() + "-" + sTopLeft.getPointKey();
	}
}
