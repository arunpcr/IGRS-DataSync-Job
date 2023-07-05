package com.ca.data;

public class ConveyedExtent {
	String extent;
	String unit;
	public String getExtent() {
		return extent;
	}
	public void setExtent(String extent) {
		this.extent = extent;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	@Override
	public String toString() {
		return "ConveyedExtent [extent=" + extent + ", unit=" + unit + "]";
	}

}
