package dummy;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

@Component
@XmlRootElement(name = "student")
public class Student {

	  private int rollNo;
	  private String name;
	  private String department;
	  
	  
	  
	 public Student() {
		super();
	}

	public Student(int rollNo, String name, String department) {
		this.rollNo = rollNo;
		this.name = name;
		this.department = department;
	}

	@XmlElement(name = "rollNo")
	public int getRollNo() {
			return rollNo;
	}
		
	public void setRollNo(int rollNo) {
			this.rollNo = rollNo;
	}
	
	@XmlElement(name = "name")
	public String getName() {
			return name;
	}
	
	public void setName(String name) {
			this.name = name;
	}
	
	@XmlElement(name = "department")
	public String getDepartment() {
			return department;
	}
	
	public void setDepartment(String department) {
			this.department = department;
	}

	@Override
	public String toString() {
		return "Student [rollNo=" + rollNo + ", name=" + name + ", department=" + department + "]";
	}
	  
}
