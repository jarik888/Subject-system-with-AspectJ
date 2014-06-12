package backend.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Subject_attribute_type {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long subject_attribute_type;
	private Long subject_type_fk;
	private String type_name;
	private int data_type;
	private Long orderby;
	private String required;
	private String multiple_attributes;
	private String created_by_default;

	public long getSubjectAttributeType() {
		return this.subject_attribute_type;
	}

	public void setSubjectAttributeType(long subjectAttributeType) {
		this.subject_attribute_type = subjectAttributeType;
	}

	public Long getSubjectTypeFk() {
		return this.subject_type_fk;
	}

	public void setSubjectTypeFk(Long subjectTypeFk) {
		this.subject_type_fk = subjectTypeFk;
	}

	public String getTypeName() {
		return this.type_name;
	}

	public void setTypeName(String typeName) {
		this.type_name = typeName;
	}

	public int getDataType() {
		return this.data_type;
	}

	public void setDataType(int dataType) {
		this.data_type = dataType;
	}

	public Long getOrderby() {
		return this.orderby;
	}

	public void setOrderby(Long orderby) {
		this.orderby = orderby;
	}

	public String getRequired() {
		return this.required;
	}

	public void setRequired(String required) {
		this.required = required;
	}

	public String getMultipleAttributes() {
		return this.multiple_attributes;
	}

	public void setMultipleAttributes(String multipleAttributes) {
		this.multiple_attributes = multipleAttributes;
	}

	public String getCreatedByDefault() {
		return this.created_by_default;
	}

	public void setCreatedByDefault(String createdByDefault) {
		this.created_by_default = createdByDefault;
	}

}
