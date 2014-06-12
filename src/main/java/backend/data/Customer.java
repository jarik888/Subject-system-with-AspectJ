package backend.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Customer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long customer;
	private Long subject_fk;
	private Long subject_type_fk;

	public long getCustomer() {
		return this.customer;
	}

	public void setCustomer(long customer) {
		this.customer = customer;
	}

	public Long getSubjectFk() {
		return this.subject_fk;
	}

	public void setSubjectFk(Long subjectFk) {
		this.subject_fk = subjectFk;
	}

	public Long getSubjectTypeFk() {
		return this.subject_type_fk;
	}

	public void setSubjectTypeFk(Long subjectTypeFk) {
		this.subject_type_fk = subjectTypeFk;
	}

}
