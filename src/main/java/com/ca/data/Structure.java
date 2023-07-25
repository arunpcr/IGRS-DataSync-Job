package com.ca.data;

public class Structure {

	public String floorNo;
	public String structureType;
    public String plinth;
    public String plinthUnit;
    public String stageOfCons;
    public String $oid;

	public String get$oid() {
		return $oid;
	}

	public void set$oid(String $oid) {
		this.$oid = $oid;
	}
    public String getStageOfCons() {
		return stageOfCons;
	}
	public void setStageOfCons(String stageOfCons) {
		this.stageOfCons = stageOfCons;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String age;
	public String getFloorNo() {
		return floorNo;
	}
	public void setFloorNo(String floorNo) {
		this.floorNo = floorNo;
	}
	public String getStructureType() {
		return structureType;
	}
	public void setStructureType(String structureType) {
		this.structureType = structureType;
	}
	public String getPlinth() {
		return plinth;
	}
	public void setPlinth(String plinth) {
		this.plinth = plinth;
	}
	public String getPlinthUnit() {
		return plinthUnit;
	}
	public void setPlinthUnit(String plinthUnit) {
		this.plinthUnit = plinthUnit;
	}
	@Override
	public String toString() {
		return "Structure [floorNo=" + floorNo + ", structureType=" + structureType + ", plinth=" + plinth
				+ ", plinthUnit=" + plinthUnit + ", stageOfCons=" + stageOfCons + ", age=" + age + "]";
	}
	
    
}
