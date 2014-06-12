package backend.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Enterprise_person_relation {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long enterprise_person_relation;
	private Long person_fk;
	private Long enterprise_fk;
	private Long ent_per_relation_type_fk;

	public long getEnterprisePersonRelation() {
		return this.enterprise_person_relation;
	}

	public void setEnterprisePersonRelation(long enterprisePersonRelation) {
		this.enterprise_person_relation = enterprisePersonRelation;
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

	public Long getEntPerRelationTypeFk() {
		return this.ent_per_relation_type_fk;
	}

	public void setEntPerRelationTypeFk(Long entPerRelationTypeFk) {
		this.ent_per_relation_type_fk = entPerRelationTypeFk;
	}

}
