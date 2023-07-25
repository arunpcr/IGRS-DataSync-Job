package in.gov.ap.igrs.datasync.app.data;

public class TestData {
	int sno;
	String name;
	int salary;
	public int getSno() {
		return sno;
	}
	public void setSno(int sno) {
		this.sno = sno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	@Override
	public String toString() {
		return "TestData [sno=" + sno + ", name=" + name + ", salary=" + salary + "]";
	}
	
}