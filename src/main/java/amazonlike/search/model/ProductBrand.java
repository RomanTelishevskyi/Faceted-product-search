package amazonlike.search.model;

import jakarta.persistence.*;

@Entity
@Table(name = "product_brands", schema = "public")
public class ProductBrand {

    @EmbeddedId
    private ProductBrandId id;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @MapsId("brandId")
    @JoinColumn(name = "brand_id")
    private Brand brand;

    public ProductBrand() {
    }

    public ProductBrand(Product product, Brand brand) {
        this.product = product;
        this.brand = brand;
        this.id = new ProductBrandId(product.getId(), brand.getId());
    }

    public ProductBrandId getId() {
        return id;
    }

    public void setId(ProductBrandId id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setProduct(Product product) {
        this.product = product;
        if (this.id == null) {
            this.id = new ProductBrandId();
        }
        this.id.setProductId(product.getId());
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
        if (this.id == null) {
            this.id = new ProductBrandId();
        }
        this.id.setBrandId(brand.getId());
    }
}
