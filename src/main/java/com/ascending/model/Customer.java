package com.ascending.model;

import javax.persistence.*;

@Entity
@Table(name ="customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;
    @Column(name ="name")
    private String name;
    @Column(name= "cell_number")
    private String phoneNumber;
    @Column(name = "email")
    private String email;
    @Column(name = "information")
    private String information;
    @OneToOne
    @JoinColumn(name="order_id")
    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Customer(){};
    public Customer(long id, String name, String phoneNumber, String email, String information){
        this.setId(id);this.setName(name);this.setPhoneNumber(phoneNumber);this.setEmail(email);this.setInformation(information);
    }
    public Customer(String name, String phoneNumber, String email, String information){
        this.setName(name);this.setPhoneNumber(phoneNumber);this.setEmail(email);this.setInformation(information);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", information='" + information + '\'' +
                '}';
    }
}
