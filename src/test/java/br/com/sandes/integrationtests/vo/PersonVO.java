package br.com.sandes.integrationtests.vo;

import java.io.Serializable;
import java.util.Objects;

public class PersonVO implements Serializable {

    private static final long serialVersionUID = 1l;

    private Long id;
    private String first_name;
    private String last_name;
    private String address;
    private String gender;
    private Boolean enabled;
    
	public Long getId() {
    	return id;
    }
    
    public void setId(Long id) {
    	this.id = id;
    }
    
    public String getFirst_name() {
    	return first_name;
    }
    
    public void setFirst_name(String first_name) {
    	this.first_name = first_name;
    }
    
    public String getLast_name() {
    	return last_name;
    }
    
    public void setLast_name(String last_name) {
    	this.last_name = last_name;
    }
    
    public String getAddress() {
    	return address;
    }
    
    public void setAddress(String address) {
    	this.address = address;
    }
    
    public String getGender() {
    	return gender;
    }
    
    public void setGender(String gender) {
    	this.gender = gender;
    }
    
    public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
    
    public PersonVO() {
    }

	@Override
	public int hashCode() {
		return Objects.hash(address, enabled, first_name, gender, id, last_name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PersonVO other = (PersonVO) obj;
		return Objects.equals(address, other.address) && Objects.equals(enabled, other.enabled)
				&& Objects.equals(first_name, other.first_name) && Objects.equals(gender, other.gender)
				&& Objects.equals(id, other.id) && Objects.equals(last_name, other.last_name);
	}
}