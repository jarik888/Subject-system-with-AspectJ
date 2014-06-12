package backend.data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Person {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long person;
	private String first_name;
	private String last_name;
	private String identity_code;
	private Date birth_date;
	private Long created_by;
	private Long updated_by;
	private Date created;
	private Date updated;

	public long getPerson() {
		return this.person;
	}

	public void setPerson(long person) {
		this.person = person;
	}

	public String getFirstName() {
		return this.first_name;
	}

	public void setFirstName(String firstName) {
		this.first_name = firstName;
	}

	public String getLastName() {
		return this.last_name;
	}

	public void setLastName(String lastName) {
		this.last_name = lastName;
	}

	public String getIdentityCode() {
		return this.identity_code;
	}

	public void setIdentityCode(String identityCode) {
		this.identity_code = identityCode;
	}

	public Date getBirthDate() {
		return this.birth_date;
	}

	public void setBirthDate(Date birthDate) {
		this.birth_date = birthDate;
	}

	public Long getCreatedBy() {
		return this.created_by;
	}

	public void setCreatedBy(Long createdBy) {
		this.created_by = createdBy;
	}

	public Long getUpdatedBy() {
		return this.updated_by;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updated_by = updatedBy;
	}

	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return this.updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

}
