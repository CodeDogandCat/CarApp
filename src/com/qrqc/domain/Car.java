package com.qrqc.domain;

import java.io.Serializable;
import java.util.Arrays;

import android.R.string;

public class Car implements Serializable {
	/**
	 * 
	 */
	private static long serialVersionUID = 1L;
	private int id;
	private String maintype;
	private String secondtype;
	private int compareCars[];
	private string picurls[];
	private final int draw;

	public int getDraw() {
		return draw;
	}

	public string[] getPicurls() {
		return picurls;
	}

	public void setPicurls(string[] picurls) {
		this.picurls = picurls;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMaintype() {
		return maintype;
	}

	public void setMaintype(String maintype) {
		this.maintype = maintype;
	}

	public String getSecondtype() {
		return secondtype;
	}

	public void setSecondtype(String secondtype) {
		this.secondtype = secondtype;
	}

	public int[] getCompareCars() {
		return compareCars;
	}

	public void setCompareCars(int[] compareCars) {
		this.compareCars = compareCars;
	}

	public Car(int id, String maintype, String secondtype, int[] compareCars, int draw) {
		super();
		this.id = id;
		this.maintype = maintype;
		this.secondtype = secondtype;
		this.compareCars = compareCars;
		this.draw = draw;
	}

	@Override
	public String toString() {
		return "Car [id=" + id + ", maintype=" + maintype + ", secondtype=" + secondtype + ", compareCars="
				+ Arrays.toString(compareCars) + ", draw=" + draw + "]";
	}

}
