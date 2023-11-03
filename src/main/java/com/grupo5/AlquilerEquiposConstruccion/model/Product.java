package com.grupo5.AlquilerEquiposConstruccion.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name="product")

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @Column(columnDefinition="TEXT")
    private String description;
    @Column(columnDefinition="TEXT")
    private String specifications;
    private boolean active;

    private boolean available;
    private Double average_score;
    private Double costPerDay;

    @ManyToOne
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;

    @OneToMany (mappedBy = "product", cascade = CascadeType.ALL)
    private List<Image> image;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name= "cancellation_policies")
    private String policiesCancellation;


    public Product() {
    }

    public Product(Integer id, String name, String description, String specifications, boolean active, boolean available, Double costPerDay, Double average_score, City city, List<Image> image, Category category, String policiesCancellation) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.specifications = specifications;
        this.active = active;
        this.available = available;
        this.costPerDay = costPerDay;
        this.average_score = average_score;
        this.city = city;
        this.image = image;
        this.category = category;
        this.policiesCancellation = policiesCancellation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Double getCostPerDay() {
        return costPerDay;
    }

    public void setCostPerDay(Double costPerDay) {
        this.costPerDay = costPerDay;
    }

    public Double getAverage_score() {
        return average_score;
    }

    public void setAverage_score(Double average_score) {
        this.average_score = average_score;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Image> getImage() {
        return image;
    }

    public void setImage(List<Image> image) {
        this.image = image;
    }


    public String getPoliciesCancellation() {
        return policiesCancellation;
    }

    public void setPoliciesCancellation(String policiesCancellation) {
        this.policiesCancellation = policiesCancellation;
    }

}
