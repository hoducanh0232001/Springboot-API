package com.tutorial.apidemo.Springboot.tutorial.models;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Objects;

//POJO = Plain object java object
@Entity
@Table(name="tblProduct")
public class Product {
    @Id
   // @GeneratedValue(strategy = GenerationType.AUTO)// auto in-crement
    @SequenceGenerator(
            name = "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1 //in-crement by 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_sequece"
    )
    private Long id;
    @Column(nullable = false,unique = true,length = 300)
    private String productName;
    private int year;
    private Double price;
    private String url;
    //default constructor
    public Product(){

    }
    //calculated field = trasient no exist in Mysql
    @Transient
    private int age; //age is calculated from "year"

    public int getAge() {
        return Calendar.getInstance().get(Calendar.YEAR) - year;
    }

    public Product(String productName, int year, Double price, String url) {

        this.productName = productName;
        this.year = year;
        this.price = price;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", year=" + year +
                ", pirce=" + price +
                ", url='" + url + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return year == product.year && age == product.age && Objects.equals(id, product.id) &&
                Objects.equals(productName, product.productName) &&
                Objects.equals(price, product.price) && Objects.equals(url, product.url);
    }
/*
    @Override
    public int hashCode() {
        return Objects.hash(id, productName, year, price, url, age);
    }

 */
}
