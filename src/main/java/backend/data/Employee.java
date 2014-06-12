package backend.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Employee {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long employee;
	private Long person_fk;
	private Long enterprise_fk;
	private Long struct_unit_fk;
	private String active;

	public long getEmployee() {
		return this.employee;
	}

	public void setEmployee(long employee) {
		this.employee = employee;
	}

	public Long getPersonFk() {
		return this.person_fk;
	}

	public void setPersonFk(Long personFk) {
		this.person_fk = personFk;
	}

	public Long getEnterpriseFk() {
		return this.enterprise_fk;
	}

	public void setEnterpriseFk(Long enterpriseFk) {
		this.enterprise_fk = enterpriseFk;
	}

	public Long getStructUnitFk() {
		return this.struct_unit_fk;
	}

	public void setStructUnitFk(Long structUnitFk) {
		this.struct_unit_fk = structUnitFk;
	}

	public String getActive() {
		return this.active;
	}

	public void setActive(String active) {
		this.active = active;
	}
	

}
