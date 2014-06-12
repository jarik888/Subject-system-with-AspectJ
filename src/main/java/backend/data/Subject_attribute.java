package backend.data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Subject_attribute {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long subject_attribute;
	private Long subject_fk;
	private Long subject_attribute_type_fk;
	private Long subject_type_fk;
	private Long orderby;
	private String value_text;
	private Long value_number;
	private Date value_date;
	private int data_type;

	public long getSubjectAttribute() {
		return this.subject_attribute;
	}

	public void setSubjectAttribute(long subjectAttribute) {
		this.subject_attribute = subjectAttribute;
	}

	public Long getSubjectFk() {
		return this.subject_fk;
	}

	public void setSubjectFk(Long subjectFk) {
		this.subject_fk = subjectFk;
	}

	public Long getSubjectAttributeTypeFk() {
		return this.subject_attribute_type_fk;
	}

	public void setSubjectAttributeTypeFk(Long subjectAttributeTypeFk) {
		this.subject_attribute_type_fk = subjectAttributeTypeFk;
	}

	public Long getSubjectTypeFk() {
		return this.subject_type_fk;
	}

	public void setSubjectTypeFk(Long subjectTypeFk) {
		this.subject_type_fk = subjectTypeFk;
	}

	public Long getOrderby() {
		return this.orderby;
	}

	public void setOrderby(Long orderby) {
		this.orderby = orderby;
	}

	public String getValueText() {
		return this.value_text;
	}

	public void setValueText(String valueText) {
		this.value_text = valueText;
	}

	public Long getValueNumber() {
		return this.value_number;
	}

	public void setValueNumber(Long valueNumber) {
		this.value_number = valueNumber;
	}

	public Date getValueDate() {
		return this.value_date;
	}

	public void setValueDate(Date valueDate) {
		this.value_date = valueDate;
	}

	public int getDataType() {
		return this.data_type;
	}

	public void setDataType(int dataType) {
		this.data_type = dataType;
	}

}
