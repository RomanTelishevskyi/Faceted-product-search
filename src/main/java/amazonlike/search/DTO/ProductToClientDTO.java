package amazonlike.search.DTO;

public class ProductToClientDTO {

    private Integer id;
    private String productName;
    private String brands;
    private String imageFrontUrl;
    private String categories;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImageFrontUrl() {
        return imageFrontUrl;
    }

    public void setImageFrontUrl(String imageFrontUrl) {
        this.imageFrontUrl = imageFrontUrl;
    }

    public String getBrands() {
        return brands;
    }

    public void setBrands(String brands) {
        this.brands = brands;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public ProductToClientDTO(String productName, Integer id, String brands, String imageFrontUrl, String categories) {
        this.productName = productName;
        this.id = id;
        this.brands = brands;
        this.imageFrontUrl = imageFrontUrl;
        this.categories = categories;
    }

    public ProductToClientDTO() {
    }
}
