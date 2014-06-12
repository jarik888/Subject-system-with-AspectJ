package backend.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Address_type {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long address_type;
	private String type_name;

	public long getAddressType() {
		return this.address_type;
	}

	public void setAddressType(long addressType) {
		this.address_type = addressType;
	}

	public String getTypeName() {
		return this.type_name;
	}

	public void setTypeName(String typeName) {
		this.type_name = typeName;
	}

}
