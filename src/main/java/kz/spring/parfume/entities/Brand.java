package kz.spring.parfume.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Table(name = "brands")
@Entity
public class Brand extends BaseModel{

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String image;

    @Column(name = "country")
    private String country;

    @Column(name = "rating")
    private double rating;

    @OneToMany(mappedBy = "brand")
    private Set<Product> products = new HashSet<>();


    public String loadImage(){
        if (image == null || image.isEmpty()) {
            return "/defaults/brand-default.webp";
        }
        return image;
    }

}
