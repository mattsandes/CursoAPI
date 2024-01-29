package br.com.sandes.model;

import java.io.Serializable;

import jakarta.persistence.*;

@Entity
@Table(name = "person")
public class Person implements Serializable {

    private static final long serialVersionUID = 1l;

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY)
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
    
    
    public Person() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (!id.equals(person.id)) return false;
        if (!first_name.equals(person.first_name)) return false;
        if (!last_name.equals(person.last_name)) return false;
        if (!address.equals(person.address)) return false;
        return gender.equals(person.gender);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + first_name.hashCode();
        result = 31 * result + last_name.hashCode();
        result = 31 * result + address.hashCode();
        result = 31 * result + gender.hashCode();
        return result;
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

    public void setGender(String gender) {
        this.gender = gender;
    }
}