package amazonlike.search.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductBrandId implements Serializable {

    @Column(name = "productId")
    private Integer productId;

    @Column(name = "brandId")
    private Integer brandId;

    public ProductBrandId() {
    }

    public ProductBrandId(Integer productId, Integer brandId) {
        this.productId = productId;
        this.brandId = brandId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductBrandId that)) return false;
        return Objects.equals(productId, that.productId) && Objects.equals(brandId, that.brandId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, brandId);
    }
}