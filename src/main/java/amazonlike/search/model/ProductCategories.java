package amazonlike.search.model;

import jakarta.persistence.*;

@Entity
@Table(name = "product_categories", schema = "public")
public class ProductCategories {

    @EmbeddedId
    private ProductCategoriesId id = new ProductCategoriesId();

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @MapsId("categoryId")
    @JoinColumn(name = "category_id")
    private Categories categories;

    public ProductCategories() {
    }

    public ProductCategories(Product product, Categories categories) {
        this.product = product;
        this.categories = categories;
        this.id = new ProductCategoriesId(product.getId(), categories.getId());
    }

    public ProductCategoriesId getId() {
        return id;
    }

    public void setId(ProductCategoriesId id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public Categories getCategories() {
        return categories;
    }

    public void setProduct(Product product) {
        this.product = product;
        if (this.id == null) {
            this.id = new ProductCategoriesId();
        }
        this.id.setProductId(product.getId());
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
        if (this.id == null) {
            this.id = new ProductCategoriesId();
        }
        this.id.setCategoryId(categories.getId());
    }
}
