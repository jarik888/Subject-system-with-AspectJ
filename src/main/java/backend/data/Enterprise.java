package backend.data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Enterprise {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long enterprise;
	private String name;
	private String full_name;
	private Long created_by;
	private Long updated_by;
	private Date created;
	private Date updated;

	public long getEnterprise() {
		return this.enterprise;
	}

	public void setEnterprise(long enterprise) {
		this.enterprise = enterprise;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return this.full_name;
	}

	public void setFullName(String fullName) {
		this.full_name = fullName;
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
