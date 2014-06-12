package backend.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Address {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long address;
	private Long subject_type_fk;
	private Long address_type_fk;
	private Long subject_fk;
	private String country;
	private String county;
	private String town_village;
	private String street_address;
	private String zipcode;

	public long getAddress() {
		return this.address;
	}

	public void setAddress(long address) {
		this.address = address;
	}
	
	public void setAddressType(Long address_type_fk) {
		this.address_type_fk = address_type_fk;
	}
	
	public Long getAddressType() {
		return this.address_type_fk;
	}

	public Long getSubjectFk() {
		return this.subject_fk;
	}

	public void setSubjectFk(Long subjectFk) {
		this.subject_fk = subjectFk;
	}

	public Long getSubjectTypeFk() {
		return this.subject_type_fk;
	}

	public void setSubjectTypeFk(Long subjectTypeFk) {
		this.subject_type_fk = subjectTypeFk;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCounty() {
		return this.county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getTownVillage() {
		return this.town_village;
	}

	public void setTownVillage(String townVillage) {
		this.town_village = townVillage;
	}

	public String getStreetAddress() {
		return this.street_address;
	}

	public void setStreetAddress(String streetAddress) {
		this.street_address = streetAddress;
	}

	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

}
