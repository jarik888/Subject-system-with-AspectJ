package backend.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Employee_role {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long employee_role;
	private Long employee_fk;
	private Long employee_role_type_fk;
	private String active;

	public long getEmployeeRole() {
		return this.employee_role;
	}

	public void setEmployeeRole(long employeeRole) {
		this.employee_role = employeeRole;
	}

	public Long getEmployeeFk() {
		return this.employee_fk;
	}

	public void setEmployeeFk(Long employeeFk) {
		this.employee_fk = employeeFk;
	}

	public Long getEmployeeRoleTypeFk() {
		return this.employee_role_type_fk;
	}

	public void setEmployeeRoleTypeFk(Long employeeRoleTypeFk) {
		this.employee_role_type_fk = employeeRoleTypeFk;
	}

	public String getActive() {
		return this.active;
	}

	public void setActive(String active) {
		this.active = active;
	}

}
