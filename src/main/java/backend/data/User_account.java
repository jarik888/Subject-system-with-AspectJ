package backend.data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User_account {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long user_account;
	private Long subject_type_fk;
	private Long subject_fk;
	private String username;
	private String passw;
	private Long status;
	private Date valid_from;
	private Date valid_to;
	private Long created_by;
	private Date created;
	private String password_never_expires;

	public long getUserAccount() {
		return this.user_account;
	}

	public void setUserAccount(long userAccount) {
		this.user_account = userAccount;
	}

	public Long getSubjectFk() {
		return this.subject_fk;
	}

	public void setSubjectFk(Long subjectFk) {
		this.subject_fk = subjectFk;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassw() {
		return this.passw;
	}

	public void setPassw(String passw) {
		this.passw = passw;
	}

	public long getStatus() {
		return this.status;
	}

	public void setStatus(long status) {
		this.status = status;
	}

	public Date getValidFrom() {
		return this.valid_from;
	}

	public void setValidFrom(Date validFrom) {
		this.valid_from = validFrom;
	}

	public Date getValidTo() {
		return this.valid_to;
	}

	public void setValidTo(Date validTo) {
		this.valid_to = validTo;
	}

	public Long getCreatedBy() {
		return this.created_by;
	}

	public void setCreatedBy(Long createdBy) {
		this.created_by = createdBy;
	}

	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getPasswordNeverExpires() {
		return this.password_never_expires;
	}

	public void setPasswordNeverExpires(String passwordNeverExpires) {
		this.password_never_expires = passwordNeverExpires;
	}

	public long getSubjectTypeFk() {
		return subject_type_fk;
	}

	public void setSubjectTypeFk(long subjectTypeFk) {
		this.subject_type_fk = subjectTypeFk;
	}

}
