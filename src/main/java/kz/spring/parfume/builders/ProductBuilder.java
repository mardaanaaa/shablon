package kz.spring.parfume.builders;

import kz.spring.parfume.entities.Brand;
import kz.spring.parfume.entities.Product;

public abstract class ProductBuilder {

    protected String name;
    protected String description;
    protected String imageUrl;
    protected double price;
    protected Brand brand;

    public Product build() {
        Product product = createProduct();
        product.setName(this.name);
        product.setDescription(this.description);
        product.setImage(this.imageUrl);
        product.setPrice(this.price);
        product.setBrand(this.brand);
        return product;
    }

    protected abstract Product createProduct();

    public ProductBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public ProductBuilder setImage(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public ProductBuilder setPrice(double price) {
        this.price = price;
        return this;
    }

    public ProductBuilder setBrand(Brand brand) {
        this.brand = brand;
        return this;
    }
}
