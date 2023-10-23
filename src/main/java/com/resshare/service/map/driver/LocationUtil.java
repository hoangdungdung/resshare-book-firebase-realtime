package com.resshare.service.map.driver;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.codec.digest.DigestUtils;

import com.resshare.map.model.Cell;
import com.resshare.map.model.CellValueMapping;
import com.resshare.map.model.Point;
 

public class LocationUtil {
	public static final int FORMAT_DEGREES = 0;
	public static final int FORMAT_MINUTES = 1;
	public static final int FORMAT_SECONDS = 2;

	public static String convertLatitudeDegree(double latitude) {
		StringBuilder builder = new StringBuilder();

		String latitudeDegrees = LocationUtil.convert(Math.abs(latitude), LocationUtil.FORMAT_SECONDS);
		String[] latitudeSplit = latitudeDegrees.split(":");
		builder.append(latitudeSplit[0]);
		builder.append("�");
		builder.append(latitudeSplit[1]);
		builder.append("'");
		builder.append(latitudeSplit[2]);
		builder.append("\"");

		if (latitude < 0) {
			builder.append("S");
		} else {
			builder.append("N");
		}

		return builder.toString();
	}

	public static String convertLongitudeDegree(double longitude) {
		StringBuilder builder = new StringBuilder();

		String longitudeDegrees = LocationUtil.convert(Math.abs(longitude), LocationUtil.FORMAT_SECONDS);
		String[] longitudeSplit = longitudeDegrees.split(":");
		builder.append(longitudeSplit[0]);
		builder.append("�");
		builder.append(longitudeSplit[1]);
		builder.append("'");
		builder.append(longitudeSplit[2]);
		builder.append("\"");

		if (longitude < 0) {
			builder.append("W");
		} else {
			builder.append("E");
		}

		return builder.toString();
	}

	// Decimal Degrees = Degrees + minutes/60 + seconds/3600
	public static String convert(double coordinate, int outputType) {
		if (coordinate < -180.0 || coordinate > 180.0 || Double.isNaN(coordinate)) {
			throw new IllegalArgumentException("coordinate=" + coordinate);
		}
		if ((outputType != FORMAT_DEGREES) && (outputType != FORMAT_MINUTES) && (outputType != FORMAT_SECONDS)) {
			throw new IllegalArgumentException("outputType=" + outputType);
		}
		StringBuilder sb = new StringBuilder();
		// Handle negative values
		if (coordinate < 0) {
			sb.append('-');
			coordinate = -coordinate;
		}
		DecimalFormat df = new DecimalFormat("###.#####");
		if (outputType == FORMAT_MINUTES || outputType == FORMAT_SECONDS) {
			int degrees = (int) Math.floor(coordinate);
			sb.append(degrees);
			sb.append(':');
			coordinate -= degrees;
			coordinate *= 60.0;
			if (outputType == FORMAT_SECONDS) {
				int minutes = (int) Math.floor(coordinate);
				sb.append(minutes);
				sb.append(':');
				coordinate -= minutes;
				coordinate *= 60.0;
			}
		}
		sb.append(df.format(coordinate));
		return sb.toString();
	}

	public static String GetNextPrePointByLatLngDegree(String preItem, String nextItem) {
		if (preItem != null) {
			StringBuilder prePoint = new StringBuilder();
			prePoint.append(preItem);
			prePoint.append(" ");
			prePoint.append(nextItem);

			return prePoint.toString();
		}

		return "";
	}

	public static String getCellMapKeyByLatLong(double lat, double lng) {
		StringBuilder str1 = new StringBuilder();
		str1.append(LocationUtil.convertLatitudeDegreeMinMax(lat, true));
		str1.append(" ");
		str1.append(LocationUtil.convertLongitudeDegreeMinMax(lng, true));
		str1.append("-");

		// 2. Lam tron xuong latitude va len longitude
		str1.append(LocationUtil.convertLatitudeDegreeMinMax(lat, true));
		str1.append(" ");
		str1.append(LocationUtil.convertLongitudeDegreeMinMax(lng, false));
		str1.append("-");

		// 3. Lam tron len latitude va xuong longitude
		str1.append(LocationUtil.convertLatitudeDegreeMinMax(lat, false));
		str1.append(" ");
		str1.append(LocationUtil.convertLongitudeDegreeMinMax(lng, true));
		str1.append("-");

		// 4. Lam tron len latitude va len longitude
		str1.append(LocationUtil.convertLatitudeDegreeMinMax(lat, false));
		str1.append(" ");
		str1.append(LocationUtil.convertLongitudeDegreeMinMax(lng, false));

		return str1.toString();
	}

	public static String getCellMapKeyByLatLong0030(double lat, double lng) {
		StringBuilder str1 = new StringBuilder();
		str1.append(LocationUtil.convertLatitudeDegreeMinMax30(lat, true));
		str1.append(" ");
		str1.append(LocationUtil.convertLongitudeDegreeMinMax30(lng, true));
		str1.append("-");

		// 2. Lam tron xuong latitude va len longitude
		str1.append(LocationUtil.convertLatitudeDegreeMinMax30(lat, true));
		str1.append(" ");
		str1.append(LocationUtil.convertLongitudeDegreeMinMax30(lng, false));
		str1.append("-");

		// 4. Lam tron len latitude va len longitude
		str1.append(LocationUtil.convertLatitudeDegreeMinMax30(lat, false));
		str1.append(" ");
		str1.append(LocationUtil.convertLongitudeDegreeMinMax30(lng, false));
		str1.append("-");
		// 3. Lam tron len latitude va xuong longitude
		str1.append(LocationUtil.convertLatitudeDegreeMinMax30(lat, false));
		str1.append(" ");
		str1.append(LocationUtil.convertLongitudeDegreeMinMax30(lng, true));

		return str1.toString();
	}
	
	public static String getCellMapKeyByLatLong2km(String location) {
		
		double lat = getLat(location);
		double lng = getLong(location);
		String s = getCellMapKeyByLatLong2km(  lat,   lng);
		 
		String rs = DigestUtils.sha256Hex(s);
		//String rs = DigestUtils.md5Hex(s)
	//	System.out.println(rs);
	return	rs;
	}

	private static double getLong(String location) {
		String sLong;
		if(location.contains(","))
		{	sLong = location.split(",")[1];
		return	Double.valueOf(sLong.trim());
		}
		// TODO Auto-generated method stub
		return 0;
	}

	private static double getLat(String location) {
		String sLat;
		if(location.contains(","))
		{	sLat = location.split(",")[0];
		return	Double.valueOf(sLat.trim());
		}
		// TODO Auto-generated method stub
		return 0;
	}

	public static String getCellMapKeyByLatLong2km(double lat, double lng) {
		StringBuilder str1 = new StringBuilder();
		str1.append(LocationUtil.convertLatitudeDegreeMinMax2km(lat, true));
		str1.append(" ");
		str1.append(LocationUtil.convertLongitudeDegreeMinMax2km(lng, true));
		str1.append("-");

		// 2. Lam tron xuong latitude va len longitude
		str1.append(LocationUtil.convertLatitudeDegreeMinMax2km(lat, true));
		str1.append(" ");
		str1.append(LocationUtil.convertLongitudeDegreeMinMax2km(lng, false));
		str1.append("-");

		// 4. Lam tron len latitude va len longitude
		str1.append(LocationUtil.convertLatitudeDegreeMinMax2km(lat, false));
		str1.append(" ");
		str1.append(LocationUtil.convertLongitudeDegreeMinMax2km(lng, false));
		str1.append("-");
		// 3. Lam tron len latitude va xuong longitude
		str1.append(LocationUtil.convertLatitudeDegreeMinMax2km(lat, false));
		str1.append(" ");
		str1.append(LocationUtil.convertLongitudeDegreeMinMax2km(lng, true));

		return str1.toString();
	}

	public static String convertLongitudeDegreeMinMax(double longitude, boolean isMin) {
		StringBuilder builder = new StringBuilder();

		String longitudeDegrees = convert(Math.abs(longitude), FORMAT_SECONDS);
		String[] longitudeSplit = longitudeDegrees.split(":");
		builder.append(longitudeSplit[0]);
		builder.append("°");
		builder.append(longitudeSplit[1]);
		builder.append("'");

		String firstStr = longitudeSplit[2].toString().substring(0, 1);
		String result;
		if (isMin) {
			result = firstStr + "0";
		} else {
			int upFirst = Integer.valueOf(firstStr) + 1;
			result = upFirst + "0";
		}
		builder.append(result);
		builder.append("\"");

		if (longitude < 0) {
			builder.append("W");
		} else {
			builder.append("E");
		}

		return builder.toString();
	}

	public static String convertLongitudeDegreeMinMax30(double longitude, boolean isMin) {
		StringBuilder builder = new StringBuilder();

		String longitudeDegrees = convert(Math.abs(longitude), FORMAT_SECONDS);
		String[] longitudeSplit = longitudeDegrees.split(":");
		builder.append(longitudeSplit[0]);
		builder.append("°");

		int up1First = Integer.valueOf(longitudeSplit[1]);
		// builder.append(longitudeSplit[1]);
		// builder.append("'");

		String firstStr = longitudeSplit[2].toString().substring(0, 1);
		String result;

		if (isMin) {
			int upFirst = Integer.valueOf(firstStr);
			if (upFirst >= 3) {
				upFirst = 3;
			} else
				upFirst = 0;

			result = String.valueOf(upFirst) + "0";
		} else {

			int upFirst = Integer.valueOf(firstStr);
			if (upFirst > 3) {
				upFirst = 0;
				up1First = up1First + 1;
			} else
				upFirst = 3;

			result = upFirst + "0";

			// int upFirst = Integer.valueOf(firstStr) + 1;
			// result = upFirst + "0";
		}
		builder.append(String.valueOf(up1First));

		builder.append("'");
		builder.append(result);
		builder.append("\"");

		if (longitude < 0) {
			builder.append("W");
		} else {
			builder.append("E");
		}

		return builder.toString();
	}

	public static String convertLongitudeDegreeMinMax2km(double longitude, boolean isMin) {
//		StringBuilder builder = new StringBuilder();
//
//		String longitudeDegrees = convert(Math.abs(longitude), FORMAT_SECONDS);
//		String[] longitudeSplit = longitudeDegrees.split(":");
//		builder.append(longitudeSplit[0]);
//		builder.append("°");
//
//		int up1First = Integer.valueOf(longitudeSplit[1]);
//		
//
//		String firstStr = longitudeSplit[2].toString().substring(0, 1);
//		String result;
//
//		if (isMin) {
//
//			result = "00";
//		} else {
//
//			up1First = up1First + 1;
//
//			result = "00";
//
//			
//		}
//		builder.append(String.valueOf(up1First));
//
//		builder.append("'");
//		builder.append(result);
//		builder.append("\"");
		
		StringBuilder builder = getStringDMS(longitude, isMin);
		
	 

		if (longitude < 0) {
			builder.append("W");
		} else {
			builder.append("E");
		}

		return builder.toString();
	}

	public static String convertLatitudeDegreeMinMax(double latitude, boolean isMin) {
		StringBuilder builder = new StringBuilder();

		String latitudeDegrees = convert(Math.abs(latitude), FORMAT_SECONDS);
		String[] latitudeSplit = latitudeDegrees.split(":");
		builder.append(latitudeSplit[0]);
		builder.append("°");
		builder.append(latitudeSplit[1]);
		builder.append("'");

		String firstStr = latitudeSplit[2].toString().substring(0, 1);
		String result;
		if (isMin) {
			result = firstStr + "0";
		} else {
			int upFirst = Integer.valueOf(firstStr) + 1;
			result = upFirst + "0";
		}

		builder.append(result);
		builder.append("\"");
		if (latitude < 0) {
			builder.append("S");
		} else {
			builder.append("N");
		}

		return builder.toString();
	}

	public static String convertLatitudeDegreeMinMax30(double latitude, boolean isMin) {
		StringBuilder builder = new StringBuilder();

		String latitudeDegrees = convert(Math.abs(latitude), FORMAT_SECONDS);
		String[] latitudeSplit = latitudeDegrees.split(":");
		builder.append(latitudeSplit[0]);
		builder.append("°");
		int up1First = Integer.valueOf(latitudeSplit[1]);
		// builder.append(latitudeSplit[1]);
		// builder.append("'");

		String firstStr = latitudeSplit[2].toString().substring(0, 1);
		String result;
		if (isMin) {
			int upFirst = Integer.valueOf(firstStr);
			if (upFirst >= 3) {
				upFirst = 3;
			} else
				upFirst = 0;

			result = String.valueOf(upFirst) + "0";
		} else {
			int upFirst = Integer.valueOf(firstStr);
			if (upFirst > 3) {
				upFirst = 0;
				up1First = up1First + 1;
			} else
				upFirst = 3;

			result = upFirst + "0";
		}
		builder.append(String.valueOf(up1First));
		builder.append("'");

		builder.append(result);
		builder.append("\"");
		if (latitude < 0) {
			builder.append("S");
		} else {
			builder.append("N");
		}

		return builder.toString();
	}

	public static String convertLatitudeDegreeMinMax2km(double latitude, boolean isMin) {
		StringBuilder builder = getStringDMS(latitude, isMin);
	
		if (latitude < 0) {
			builder.append("S");
		} else {
			builder.append("N");
		}

		return builder.toString();
	}

	private static StringBuilder getStringDMS(double latitude, boolean isMin) {
		StringBuilder builder = new StringBuilder();

		String latitudeDegrees = convert(Math.abs(latitude), FORMAT_SECONDS);
		String[] latitudeSplit = latitudeDegrees.split(":");

		int up0First = Integer.valueOf(latitudeSplit[0]);
		int up1First = Integer.valueOf(latitudeSplit[1]);


		
		String result;
		if (isMin) {
		
			result = "00";
		} else {
		
			if(up1First<59)
			{
				up1First = up1First + 1;
			
			}
			else
			{
				up0First=up0First+1;
				up1First=0;
			}

			result = "00";
		}
		
		String degree=String.valueOf(up0First);
		if(up0First<10)
			
			degree="0"+degree;
		
		builder.append(degree);
		builder.append("°");
		String minute=String.valueOf(up1First);
		if(up1First<10)
			
			minute="0"+minute;
		builder.append(minute);
		builder.append("'");

		builder.append(result);
		builder.append("\"");
		return builder;
	}

	public static String convertLatitudeDegreeMin(double latitude) {
		StringBuilder builder = new StringBuilder();

		String latitudeDegrees = convert(Math.abs(latitude), FORMAT_SECONDS);
		String[] latitudeSplit = latitudeDegrees.split(":");
		builder.append(latitudeSplit[0]);
		builder.append("°");
		builder.append(latitudeSplit[1]);
		builder.append("'");

		String firstStr = String.valueOf(Math.round(Double.parseDouble(latitudeSplit[2].toString())));

		CellValueMapping cellValueMapping = Cache.mCellValueMapping.get(firstStr);

		int min = cellValueMapping.getMin();

		builder.append(min);
		builder.append("\"");
		if (latitude < 0) {
			builder.append("S");
		} else {
			builder.append("N");
		}

		return builder.toString();
	}

	public static String convertLatitudeDegreeMax(double latitude) {
		StringBuilder builder = new StringBuilder();

		String latitudeDegrees = convert(Math.abs(latitude), FORMAT_SECONDS);
		String[] latitudeSplit = latitudeDegrees.split(":");
		builder.append(latitudeSplit[0]);
		builder.append("°");
		String sMunis = latitudeSplit[1];
		// builder.append(latitudeSplit[1]);
		// builder.append("'");

		String firstStr = String.valueOf(Math.round(Double.parseDouble(latitudeSplit[2].toString())));
		CellValueMapping cellValueMapping = Cache.mCellValueMapping.get(firstStr);

		int max = cellValueMapping.getMax();
		if (max == 60) {
			long minus = Long.parseLong(sMunis);
			minus = minus + 1;
			sMunis = String.valueOf(minus);
			max = 0;
		}
		builder.append(sMunis);
		builder.append("'");

		builder.append(max);
		builder.append("\"");
		if (latitude < 0) {
			builder.append("S");
		} else {
			builder.append("N");
		}

		return builder.toString();
	}

	public static String convertLongitudeDegreeMin(double longitude) {
		StringBuilder builder = new StringBuilder();

		String longitudeDegrees = convert(Math.abs(longitude), FORMAT_SECONDS);
		String[] longitudeSplit = longitudeDegrees.split(":");
		builder.append(longitudeSplit[0]);
		builder.append("°");
		builder.append(longitudeSplit[1]);
		builder.append("'");
		String firstStr = String.valueOf(Math.round(Double.parseDouble(longitudeSplit[2].toString())));

		CellValueMapping cellValueMapping = Cache.mCellValueMapping.get(firstStr);

		int min = cellValueMapping.getMin();
		builder.append(min);
		builder.append("\"");

		if (longitude < 0) {
			builder.append("W");
		} else {
			builder.append("E");
		}

		return builder.toString();
	}

	public static String convertLongitudeDegreeMax(double longitude) {
		StringBuilder builder = new StringBuilder();

		String longitudeDegrees = convert(Math.abs(longitude), FORMAT_SECONDS);
		String[] longitudeSplit = longitudeDegrees.split(":");
		builder.append(longitudeSplit[0]);
		builder.append("°");

		String firstStr = String.valueOf(Math.round(Double.parseDouble(longitudeSplit[2].toString())));
		CellValueMapping cellValueMapping = Cache.mCellValueMapping.get(firstStr);

		int max = cellValueMapping.getMax();

		String sMunis = longitudeSplit[1];
		if (max == 60) {
			long minus = Long.parseLong(sMunis);
			minus = minus + 1;
			sMunis = String.valueOf(minus);
			max = 0;
		}
		builder.append(sMunis);
		builder.append("'");

		builder.append(max);

		builder.append("\"");

		if (longitude < 0) {
			builder.append("W");
		} else {
			builder.append("E");
		}

		return builder.toString();
	}

	public static String getCelMap(Object object) {
		ArrayList pointLatLng = (ArrayList) object;
		Double lat = Double.parseDouble(String.valueOf(pointLatLng.get(1)));
		Double lng = Double.parseDouble(String.valueOf(pointLatLng.get(0)));
		String keyCellMap = LocationUtil.getCellMapKeyByLatLong(lat, lng);
		return keyCellMap;
	}

	public static String getCellMapDriverKeyByLatLong(double latitude, double longitude) {
		StringBuilder str1 = new StringBuilder();
		String latMin = LocationUtil.convertLatitudeDegreeMin(latitude);
		String longMin = LocationUtil.convertLongitudeDegreeMin(longitude);
		String latMax = LocationUtil.convertLatitudeDegreeMax(latitude);
		String longMax = LocationUtil.convertLongitudeDegreeMax(longitude);

		str1.append(latMin);
		str1.append(" ");
		str1.append(longMin);
		str1.append("_");
		str1.append(latMax);
		str1.append(" ");
		str1.append(longMax);

		// str1.append(LocationUtil.convertLatitudeDegreeMinMax(lat, true));
		// str1.append(" ");
		// str1.append(LocationUtil.convertLongitudeDegreeMinMax(lng, true));
		// str1.append("-");
		//
		// // 2. Lam tron xuong latitude va len longitude
		// str1.append(LocationUtil.convertLatitudeDegreeMinMax(lat, true));
		// str1.append(" ");
		// str1.append(LocationUtil.convertLongitudeDegreeMinMax(lng, false));
		// str1.append("-");
		//
		// // 3. Lam tron len latitude va xuong longitude
		// str1.append(LocationUtil.convertLatitudeDegreeMinMax(lat, false));
		// str1.append(" ");
		// str1.append(LocationUtil.convertLongitudeDegreeMinMax(lng, true));
		// str1.append("-");
		//
		// // 4. Lam tron len latitude va len longitude
		// str1.append(LocationUtil.convertLatitudeDegreeMinMax(lat, false));
		// str1.append(" ");
		// str1.append(LocationUtil.convertLongitudeDegreeMinMax(lng, false));

		return str1.toString();
	}

	public static String getBottomCell(double lat, double lng) {
		StringBuilder str1 = new StringBuilder();
		str1.append(LocationUtil.convertLatitudeDegreeMinMax2km(lat, true));
		str1.append(" ");
		str1.append(LocationUtil.convertLongitudeDegreeMinMax2km(lng, true));
		str1.append("-");

		// 2. Lam tron xuong latitude va len longitude
		str1.append(LocationUtil.convertLatitudeDegreeMinMax2km(lat, true));
		str1.append(" ");
		str1.append(LocationUtil.convertLongitudeDegreeMinMax2km(lng, false));
		str1.append("-");

		// 4. Lam tron len latitude va len longitude
		str1.append(LocationUtil.convertLatitudeDegreeMinMax2km(lat, false));
		str1.append(" ");
		str1.append(LocationUtil.convertLongitudeDegreeMinMax2km(lng, false));
		str1.append("-");
		// 3. Lam tron len latitude va xuong longitude
		str1.append(LocationUtil.convertLatitudeDegreeMinMax2km(lat, false));
		str1.append(" ");
		str1.append(LocationUtil.convertLongitudeDegreeMinMax2km(lng, true));

		return str1.toString();
	}

	public static String convertBottomCell(double latitude, boolean isMin) {
		StringBuilder builder = new StringBuilder();

		String latitudeDegrees = convert(Math.abs(latitude), FORMAT_SECONDS);
		String[] latitudeSplit = latitudeDegrees.split(":");
		builder.append(latitudeSplit[0]);
		builder.append("°");
		int up1First = Integer.valueOf(latitudeSplit[1]);
		// builder.append(latitudeSplit[1]);
		// builder.append("'");

		String firstStr = latitudeSplit[2].toString().substring(0, 1);
		String result;
		if (isMin) {
			// int upFirst = Integer.valueOf(firstStr);
			// if(upFirst>=3)
			// { upFirst=3;}
			// else
			// upFirst=0;

			// result = String.valueOf( upFirst) + "0";
			result = "00";
		} else {
			// int upFirst = Integer.valueOf(firstStr);
			// if(upFirst>3)
			// { upFirst=0;
			up1First = up1First + 1;
			// }
			// else
			// upFirst=3;

			// result = upFirst + "0";
			result = "00";
		}
		builder.append(String.valueOf(up1First));
		builder.append("'");

		builder.append(result);
		builder.append("\"");
		if (latitude < 0) {
			builder.append("S");
		} else {
			builder.append("N");
		}

		return builder.toString();
	}

	public static void main(String[] args) {
		// 21.233333, 105.766667

		double lat = 21.233333;
		double log = 105.766667;
		getListKeyCellByLatLog2km(lat, log);
		// System.out.println(sRight);

	}

	public static ArrayList<Cell>   getListKeyCellByLatLog2km(double lat, double log) {
		String sCell = LocationUtil.getCellMapKeyByLatLong2km(lat, log);
		String[] sArr = sCell.split("-");
		
		Point sBottomLeft=new Point(sArr[0]);
		Point sBottomRight=new Point(sArr[1]);
		Point sTopRight=new Point(sArr[2]);
		Point sTopLeft=new Point(sArr[3]);
		Cell cel=new Cell(sBottomLeft, sBottomRight, sTopRight, sTopLeft);
	//	System.out.println(sCell);
		
		// 21°12'00.0"N 105°46'00.0"E

		ArrayList<Point> arrBottom = new ArrayList<Point>();
		arrBottom.add(sBottomLeft);
		arrBottom.add(sBottomRight);

		ArrayList<Point> arrRight = new ArrayList<Point>();
		arrRight.add(sBottomRight);
		arrRight.add(sTopRight);

		ArrayList<Point> arrTop = new ArrayList<Point>();
		arrTop.add(sTopRight);
		arrTop.add(sTopLeft);

		ArrayList<Point> arrLeft = new ArrayList<Point>();
		arrLeft.add(sTopLeft);
		arrLeft.add(sBottomLeft);
		
		ArrayList<Cell> arrCell = new ArrayList<Cell>();
		arrCell.add(cel);
		int i = 1;
		
		// ban kinh = i*2
		while (i < 2) {// ban kinh = 10
			ArrayList<Cell> arrBottomCell = getArrBottomCell(arrBottom);
			arrCell.addAll(arrBottomCell);
			ArrayList<Cell> arrRightCell = getArrRightCell(arrRight);
			arrCell.addAll(arrRightCell);
			ArrayList<Cell> arrTopCell = getArrTopCell(arrTop);
			arrCell.addAll(arrTopCell);
			ArrayList<Cell> arrLeftCell = getArrLeftCell(arrLeft);
			arrCell.addAll(arrLeftCell);
			i++;
			arrBottom = getArrBottomPoint(arrBottomCell);
			arrRight = getArrRightPoint(arrRightCell);
			arrTop = getArrTopPoint(arrTopCell);
			arrLeft = getArrLeftPoint(arrLeftCell);

		}
		int j = 0;
		for (Iterator iterator = arrCell.iterator(); iterator.hasNext();) {
			Cell cell = (Cell) iterator.next();

			System.out.println(j + ": " + cell.getCellKey());
			j++;

		}
		return arrCell;
	}

	private static ArrayList<Point> getArrBottomPoint(ArrayList<Cell> arrBottomCell) {
		ArrayList<Point> arrPoint = new ArrayList<Point>();
		for (Iterator iterator = arrBottomCell.iterator(); iterator.hasNext();) {
			Cell cell = (Cell) iterator.next();
			arrPoint.add(cell.getBottomLeft());

		}
		Cell cell = (Cell) arrBottomCell.get(arrBottomCell.size() - 1);
		arrPoint.add(cell.getBottomRight());
		arrPoint.add(cell.getBottomRight().getRight());
		return arrPoint;
	}

	private static ArrayList<Point> getArrRightPoint(ArrayList<Cell> arrRightCell) {
		ArrayList<Point> arrPoint = new ArrayList<Point>();
		for (Iterator iterator = arrRightCell.iterator(); iterator.hasNext();) {
			Cell cell = (Cell) iterator.next();
			arrPoint.add(cell.getBottomRight());

		}
		Cell cell = (Cell) arrRightCell.get(arrRightCell.size() - 1);
		arrPoint.add(cell.getTopRight());
		arrPoint.add(cell.getTopRight().getTop());
		return arrPoint;
	}

	private static ArrayList<Point> getArrTopPoint(ArrayList<Cell> arrTopCell) {
		ArrayList<Point> arrPoint = new ArrayList<Point>();
		for (Iterator iterator = arrTopCell.iterator(); iterator.hasNext();) {
			Cell cell = (Cell) iterator.next();
			arrPoint.add(cell.getTopRight());

		}
		Cell cell = (Cell) arrTopCell.get(arrTopCell.size() - 1);
		arrPoint.add(cell.getTopLeft());
		arrPoint.add(cell.getTopLeft().getLeft());
		return arrPoint;
	}

	private static ArrayList<Point> getArrLeftPoint(ArrayList<Cell> arrLeftCell) {
		ArrayList<Point> arrPoint = new ArrayList<Point>();
		for (Iterator iterator = arrLeftCell.iterator(); iterator.hasNext();) {
			Cell cell = (Cell) iterator.next();
			arrPoint.add(cell.getTopLeft());

		}
		Cell cell = (Cell) arrLeftCell.get(arrLeftCell.size() - 1);
		arrPoint.add(cell.getBottomLeft());
		arrPoint.add(cell.getBottomLeft().getBottom());
		return arrPoint;
	}

	private static ArrayList<Cell> getArrLeftCell(ArrayList<Point> arrLeft) {
		ArrayList<Cell> arrCell = new ArrayList<Cell>();
		for (Iterator iterator = arrLeft.iterator(); iterator.hasNext();) {
			Point point = (Point) iterator.next();
			Cell cel = new Cell(point.getLeft(), point, point.getTop(), point.getLeftTop());
			arrCell.add(cel);

		}

		return arrCell;
	}

	private static ArrayList<Cell> getArrTopCell(ArrayList<Point> arrTop) {
		ArrayList<Cell> arrCell = new ArrayList<Cell>();
		for (Iterator iterator = arrTop.iterator(); iterator.hasNext();) {
			Point point = (Point) iterator.next();
			Cell cel = new Cell(point, point.getRight(), point.getRightTop(), point.getTop());
			arrCell.add(cel);

		}

		return arrCell;
	}

	private static ArrayList<Cell> getArrRightCell(ArrayList<Point> arrRight) {
		ArrayList<Cell> arrCell = new ArrayList<Cell>();
		for (Iterator iterator = arrRight.iterator(); iterator.hasNext();) {
			Point point = (Point) iterator.next();
			Cell cel = new Cell(point.getBottom(), point.getRightBottom(), point.getRight(), point);
			arrCell.add(cel);

		}

		return arrCell;
	}

	private static ArrayList<Cell> getArrBottomCell(ArrayList<Point> sArrBottom) {
		ArrayList<Cell> arrCell = new ArrayList<Cell>();
		for (Iterator iterator = sArrBottom.iterator(); iterator.hasNext();) {
			Point point = (Point) iterator.next();
			Cell cel = new Cell(point.getLeftBottom(), point.getBottom(), point, point.getLeft());
			arrCell.add(cel);

		}

		return arrCell;
	}

	// private static Cell getBottomCell(String sCell) {
	// // 21°12'00.0"N 105°46'00.0"E
	// String[] arrNE = sCell.split(" ");
	// String sLatMax = arrNE[0];
	// String sLogMax = arrNE[1];
	//
	// String[] sArrFirtLat = sLatMax.split("°");
	// String sFirtLat = sArrFirtLat[0];
	// String sSecondLat = sArrFirtLat[1].split("'")[0];
	// ;
	//
	// int up1SecondLat = Integer.valueOf(sSecondLat) - 1;
	//
	// String sLatMin = sFirtLat + "°" + String.valueOf(up1SecondLat) + "'" +
	// sArrFirtLat[1].split("'")[1];
	//
	// String[] sArrFirtLog = sLogMax.split("°");
	// String sFirtLog = sArrFirtLog[0];
	// String sSecondLog = sArrFirtLog[1].split("'")[0];
	// ;
	//
	// int up1SecondLog = Integer.valueOf(sSecondLog) - 1;
	//
	// String sLogMin = sFirtLog + "°" + String.valueOf(up1SecondLog) + "'" +
	// sArrFirtLog[1].split("'")[1];
	//
	// // Cell cellB = new Cell(sLatMin + " " + sLogMin, sLatMin + " " + sLogMax,
	// sCell, sLatMax + " " + sLogMin);
	//
	// // TODO Auto-generated method stub
	// return cellB;
	// }
}
