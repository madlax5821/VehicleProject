package com.ascending.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "order_number")
    private String orderNumber;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "purchase_date")
    private java.sql.Date purchaseDate;
    @Column(name = "requirement")
    private String requirement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "config_id")
    private Config config;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.ALL)
    //@OneToOne(fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.ALL)
    private Customer customer;

    @Transient
    @Column(name = "config_id")
    private long configId;

    public Order() {
    }

    public Order(long id, String orderNumber, BigDecimal price, Date purchaseDate, String requirement) {
        this.setId(id);
        this.setOrderNumber(orderNumber);
        this.setPrice(price);
        this.setPurchaseDate(purchaseDate);
        this.setRequirement(requirement);
    }

    public Order(String orderNumber, BigDecimal price, Date purchaseDate, String requirement) {
        this.setOrderNumber(orderNumber);
        this.setPrice(price);
        this.setPurchaseDate(purchaseDate);
        this.setRequirement(requirement);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public long getConfigId() {
        return configId;
    }

    public void setConfigId(long configId) {
        this.configId = configId;
    }

    public void addCustomer(Customer customer){
        this.setCustomer(customer);
        this.getCustomer();
        customer.setOrder(this);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderNumber='" + orderNumber + '\'' +
                ", price=" + price +
                ", purchaseDate=" + purchaseDate +
                ", requirement='" + requirement + '\'' +
                '}';
    }
}
