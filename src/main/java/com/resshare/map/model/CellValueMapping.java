package com.resshare.map.model;

import java.io.Serializable;

/**
 * Created by hoangdung1008@gmail.com on 28/02/2019.
 */
public class CellValueMapping implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int min;
	private int max;

	// Getter Methods

	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}

	// Setter Methods

	public void setMin(int min) {
		this.min = min;
	}

	public void setMax(int max) {
		this.max = max;
	}
}
