package backend.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Ent_per_relation_type {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long ent_per_relation_type;
	private String type_name;

	public long getEntPerRelationType() {
		return this.ent_per_relation_type;
	}

	public void setEntPerRelationType(long entPerRelationType) {
		this.ent_per_relation_type = entPerRelationType;
	}

	public String getTypeName() {
		return this.type_name;
	}

	public void setTypeName(String typeName) {
		this.type_name = typeName;
	}

}
