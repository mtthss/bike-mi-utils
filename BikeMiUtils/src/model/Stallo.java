package model;

import com.google.android.gms.maps.model.LatLng;

public class Stallo {
	
	private String name;
	private int emptySlots; 
	private int freeBikes;
	private LatLng position;
	private double distance;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getEmptySlots() {
		return emptySlots;
	}
	public void setEmptySlots(int emptySlots) {
		this.emptySlots = emptySlots;
	}
	public int getFreeBikes() {
		return freeBikes;
	}
	public void setFreeBikes(int freeBikes) {
		this.freeBikes = freeBikes;
	}
	public LatLng getPosition() {
		return position;
	}
	public void setPosition(LatLng position) {
		this.position = position;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(LatLng target) {
		distance=Math.sqrt(Math.pow((target.latitude-position.latitude),2)+Math.pow((target.longitude-position.longitude), 2));
	}

}
