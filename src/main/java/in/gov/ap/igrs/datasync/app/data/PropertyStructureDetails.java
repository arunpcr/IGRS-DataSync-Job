package in.gov.ap.igrs.datasync.app.data;

import java.util.List;

public class PropertyStructureDetails {

	public String propertyId;
	public String propertyType;
	public String landUse;
	public String ward;
	public String block;
	public String doorNo;
	public String plotNo;
	public String survayNo;
	public String ptinNo;
	public String extent;
	public String extentUnit;
	public String schedulePropertyType;
	public String layoutNo;
	public String layoutName;
	public String totalFloors;
	//public Structure structure;
	public List<Structure> structure;
	public List<ConveyedExtent> conveyedExtent;
	public String appartmentName;
	public String undividedShare;
	public String undividedShareUnit;
	public String flatNo;
	public String flatNorthBoundry;
	public String flatSouthBoundry;
	public String flatEastBoundry;
	public String flatWestBoundry;
	public String northBoundry;
	public String southBoundry;
	public String eastBoundry;
	public String westBoundry;
	// public LinkedDocDetails linkedDocDetails [];
	
	public List<Structure> getStructure() {
		return structure;
	}
	public List<ConveyedExtent> getConveyedExtent() {
		return conveyedExtent;
	}
	public void setConveyedExtent(List<ConveyedExtent> conveyedExtent) {
		this.conveyedExtent = conveyedExtent;
	}
	public void setStructure(List<Structure> structure) {
		this.structure = structure;
	}
	public String getPropertyId() {
		return propertyId;
	}
	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}
	public String getPropertyType() {
		return propertyType;
	}
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}
	public String getLandUse() {
		return landUse;
	}
	public void setLandUse(String landUse) {
		this.landUse = landUse;
	}
	public String getWard() {
		return ward;
	}
	public void setWard(String ward) {
		this.ward = ward;
	}
	public String getBlock() {
		return block;
	}
	public void setBlock(String block) {
		this.block = block;
	}
	public String getDoorNo() {
		return doorNo;
	}
	public void setDoorNo(String doorNo) {
		this.doorNo = doorNo;
	}
	public String getPlotNo() {
		return plotNo;
	}
	public void setPlotNo(String plotNo) {
		this.plotNo = plotNo;
	}
	public String getSurvayNo() {
		return survayNo;
	}
	public void setSurvayNo(String survayNo) {
		this.survayNo = survayNo;
	}
	public String getPtinNo() {
		return ptinNo;
	}
	public void setPtinNo(String ptinNo) {
		this.ptinNo = ptinNo;
	}
	public String getExtent() {
		return extent;
	}
	public void setExtent(String extent) {
		this.extent = extent;
	}
	public String getExtentUnit() {
		return extentUnit;
	}
	public void setExtentUnit(String extentUnit) {
		this.extentUnit = extentUnit;
	}
	public String getSchedulePropertyType() {
		return schedulePropertyType;
	}
	public void setSchedulePropertyType(String schedulePropertyType) {
		this.schedulePropertyType = schedulePropertyType;
	}
	public String getLayoutNo() {
		return layoutNo;
	}
	public void setLayoutNo(String layoutNo) {
		this.layoutNo = layoutNo;
	}
	public String getLayoutName() {
		return layoutName;
	}
	public void setLayoutName(String layoutName) {
		this.layoutName = layoutName;
	}
	public String getTotalFloors() {
		return totalFloors;
	}
	public void setTotalFloors(String totalFloors) {
		this.totalFloors = totalFloors;
	}
	
	public String getAppartmentName() {
		return appartmentName;
	}
	public void setAppartmentName(String appartmentName) {
		this.appartmentName = appartmentName;
	}
	public String getUndividedShare() {
		return undividedShare;
	}
	public void setUndividedShare(String undividedShare) {
		this.undividedShare = undividedShare;
	}
	public String getUndividedShareUnit() {
		return undividedShareUnit;
	}
	public void setUndividedShareUnit(String undividedShareUnit) {
		this.undividedShareUnit = undividedShareUnit;
	}
	public String getFlatNo() {
		return flatNo;
	}
	public void setFlatNo(String flatNo) {
		this.flatNo = flatNo;
	}
	public String getFlatNorthBoundry() {
		return flatNorthBoundry;
	}
	public void setFlatNorthBoundry(String flatNorthBoundry) {
		this.flatNorthBoundry = flatNorthBoundry;
	}
	public String getFlatSouthBoundry() {
		return flatSouthBoundry;
	}
	public void setFlatSouthBoundry(String flatSouthBoundry) {
		this.flatSouthBoundry = flatSouthBoundry;
	}
	public String getFlatEastBoundry() {
		return flatEastBoundry;
	}
	public void setFlatEastBoundry(String flatEastBoundry) {
		this.flatEastBoundry = flatEastBoundry;
	}
	public String getFlatWestBoundry() {
		return flatWestBoundry;
	}
	public void setFlatWestBoundry(String flatWestBoundry) {
		this.flatWestBoundry = flatWestBoundry;
	}
	public String getNorthBoundry() {
		return northBoundry;
	}
	public void setNorthBoundry(String northBoundry) {
		this.northBoundry = northBoundry;
	}
	public String getSouthBoundry() {
		return southBoundry;
	}
	public void setSouthBoundry(String southBoundry) {
		this.southBoundry = southBoundry;
	}
	public String getEastBoundry() {
		return eastBoundry;
	}
	public void setEastBoundry(String eastBoundry) {
		this.eastBoundry = eastBoundry;
	}
	public String getWestBoundry() {
		return westBoundry;
	}
	public void setWestBoundry(String westBoundry) {
		this.westBoundry = westBoundry;
	}
	/*public Structure getStructure() {
		return structure;
	}
	public void setStructure(Structure structure) {
		this.structure = structure;
	}*/
	@Override
	public String toString() {
		return "PropertyStructureDetails [propertyId=" + propertyId + ", propertyType=" + propertyType + ", landUse="
				+ landUse + ", ward=" + ward + ", block=" + block + ", doorNo=" + doorNo + ", plotNo=" + plotNo
				+ ", survayNo=" + survayNo + ", ptinNo=" + ptinNo + ", extent=" + extent + ", extentUnit=" + extentUnit
				+ ", schedulePropertyType=" + schedulePropertyType + ", layoutNo=" + layoutNo + ", layoutName="
				+ layoutName + ", totalFloors=" + totalFloors + ", structure=" + structure + ", conveyedExtent="
				+ conveyedExtent + ", appartmentName=" + appartmentName + ", undividedShare=" + undividedShare
				+ ", undividedShareUnit=" + undividedShareUnit + ", flatNo=" + flatNo + ", flatNorthBoundry="
				+ flatNorthBoundry + ", flatSouthBoundry=" + flatSouthBoundry + ", flatEastBoundry=" + flatEastBoundry
				+ ", flatWestBoundry=" + flatWestBoundry + ", northBoundry=" + northBoundry + ", southBoundry="
				+ southBoundry + ", eastBoundry=" + eastBoundry + ", westBoundry=" + westBoundry + "]";
	}
	

	
	

}
