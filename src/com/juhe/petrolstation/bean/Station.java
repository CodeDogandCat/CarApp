package com.juhe.petrolstation.bean;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class Station implements Parcelable{
	
	private String name;
	private String addr;
	private String area;
	private String brand;
	private double lat;
	private double lon;
	private int distance;
	private ArrayList<Petrol> gastPriceList;
	private ArrayList<Petrol> priceList;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public ArrayList<Petrol> getGastPriceList() {
		return gastPriceList;
	}
	public void setGastPriceList(ArrayList<Petrol> gastPriceList) {
		this.gastPriceList = gastPriceList;
	}
	public ArrayList<Petrol> getPriceList() {
		return priceList;
	}
	public void setPriceList(ArrayList<Petrol> priceList) {
		this.priceList = priceList;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		// TODO Auto-generated method stub
		arg0.writeString(name);
		arg0.writeString(addr);
		arg0.writeString(area);
		arg0.writeString(brand);
		arg0.writeDouble(lat);
		arg0.writeDouble(lon);
		arg0.writeInt(distance);
		arg0.writeList(gastPriceList);
		arg0.writeList(priceList);
	}
	
	public static final Parcelable.Creator<Station> CREATOR = new Parcelable.Creator<Station>() {

		@SuppressWarnings("unchecked")
		@Override
		public Station createFromParcel(Parcel arg0) {
			// TODO Auto-generated method stub
			Station s = new Station();
			s.name = arg0.readString();
			s.addr = arg0.readString();
			s.area = arg0.readString();
			s.brand = arg0.readString();
			s.lat = arg0.readDouble();
			s.lon = arg0.readDouble();
			s.distance = arg0.readInt();
			s.gastPriceList = arg0.readArrayList(Petrol.class.getClassLoader());
			s.priceList = arg0.readArrayList(Petrol.class.getClassLoader());
			return s;
		}

		@Override
		public Station[] newArray(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}
		
		
	};
	
}
