package br.com.sandes.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.*;

@Entity
@Table(name = "person")
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(
            name = "person_sequence",
            sequenceName = "person_sequence",
            allocationSize = 1)

    @GeneratedValue(
            generator = "person_sequence",
            strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(
    		name = "first_name",
    		nullable = false,
    		length = 80)
    private String first_name;

    @Column(
    		name = "last_name",
    		nullable = false,
    		length = 80)
    private String last_name;
    
    @Column(
    		nullable = false,
    		length = 80)
    private String address;
    
    @Column(
    		nullable = false,
    		length = 6)
    private String gender;
    
    @Column(
    		nullable = false)
    private Boolean enabled;
    
    public Person() {
    }

    @Override
	public int hashCode() {
		return Objects.hash(address, first_name, gender, id, last_name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		return Objects.equals(address, other.address) && Objects.equals(first_name, other.first_name)
				&& Objects.equals(gender, other.gender) && Objects.equals(id, other.id)
				&& Objects.equals(last_name, other.last_name);
	}



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

    public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public void setGender(String gender) {
        this.gender = gender;
    }
}