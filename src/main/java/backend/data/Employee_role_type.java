package backend.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Employee_role_type {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long employee_role_type;
	private String type_name;

	public long getEmployeeRoleType() {
		return this.employee_role_type;
	}

	public void setEmployeeRoleType(long employeeRoleType) {
		this.employee_role_type = employeeRoleType;
	}

	public String getTypeName() {
		return this.type_name;
	}

	public void setTypeName(String typeName) {
		this.type_name = typeName;
	}

}
