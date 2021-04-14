package projectpart2;

public class DataPoint {
	private String label;
	private boolean isTest;
	//private String name;
	//private String gender;
	private Double f1;
	private Double f2;

	public DataPoint() {
		this.label = "";
		//this.name = "";
		//this.gender = "";
		this.isTest = (Boolean) null;
		this.f1 = 0.0;
		this.f2 = 0.0;
	}
	public DataPoint(String label, boolean iParam, Double f, Double f2) {
		this.label = label;
		this.isTest = iParam;
		//this.name = nParam;
		//this.gender = gParam;
		this.f1 = f;
		this.f2 = f2;
		

	}
	public String getLabel() {
		return this.label;
	}
	public boolean getIsTest() {
		return this.isTest;
	}
	
	public Double getF1() {
		return this.f1;
	}
	public Double getF2() {
		return this.f2;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public void setIsTest(boolean iParam) {
		this.isTest = iParam;
	}

	public void setF1(Double f) {
		this.f1 = f;
	}
	public void setF2(Double f2) {
		this.f2 = f2;
	}
	




}
