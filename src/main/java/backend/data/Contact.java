package backend.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Contact {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long contact;
	private Long subject_fk;
	private Long contact_type_fk;
	private String value_text;
	private Long orderby;
	private Long subject_type_fk;
	private String note;

	public long getContact() {
		return this.contact;
	}

	public void setContact(long contact) {
		this.contact = contact;
	}

	public Long getSubjectFk() {
		return this.subject_fk;
	}

	public void setSubjectFk(Long subjectFk) {
		this.subject_fk = subjectFk;
	}

	public Long getContactTypeFk() {
		return this.contact_type_fk;
	}

	public void setContactTypeFk(Long contactTypeFk) {
		this.contact_type_fk = contactTypeFk;
	}

	public String getValueText() {
		return this.value_text;
	}

	public void setValueText(String valueText) {
		this.value_text = valueText;
	}

	public Long getOrderby() {
		return this.orderby;
	}

	public void setOrderby(Long orderby) {
		this.orderby = orderby;
	}

	public Long getSubjectTypeFk() {
		return this.subject_type_fk;
	}

	public void setSubjectTypeFk(Long subjectTypeFk) {
		this.subject_type_fk = subjectTypeFk;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
