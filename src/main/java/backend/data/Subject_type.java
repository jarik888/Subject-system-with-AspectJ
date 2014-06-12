package backend.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Subject_type {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long subject_type;
	private String type_name;

	public long getSubjectType() {
		return this.subject_type;
	}

	public void setSubjectType(long subjectType) {
		this.subject_type = subjectType;
	}

	public String getTypeName() {
		return this.type_name;
	}

	public void setTypeName(String type_name) {
		this.type_name = type_name;
	}

}
