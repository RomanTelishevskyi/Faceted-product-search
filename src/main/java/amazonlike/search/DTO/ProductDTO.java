package amazonlike.search.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductDTO {
    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("brands")
    private String brands;

    @JsonProperty("image_front_url")
    private String imageFrontUrl;

    @JsonProperty("categories")
    private String categories;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBrands() {
        return brands;
    }

    public void setBrands(String brands) {
        this.brands = brands;
    }

    public String getImageFrontUrl() {
        return imageFrontUrl;
    }

    public void setImageFrontUrl(String imageFrontUrl) {
        this.imageFrontUrl = imageFrontUrl;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }
}
