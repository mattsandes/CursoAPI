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
    		name = "firstName",
    		nullable = false,
    		length = 80)
    private String firstName;

    @Column(
    		name = "lastName",
    		nullable = false,
    		length = 80)
    private String lastName;
    
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
		return Objects.hash(address, firstName, gender, id, lastName);
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
		return Objects.equals(address, other.address) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(gender, other.gender) && Objects.equals(id, other.id)
				&& Objects.equals(lastName, other.lastName);
	}



	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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