package amazonlike.search.DTO;

public class FacetDTO {
    private String facetType;
    private int facetId;
    private String facetName;
    private int productCount;

    public String getFacetType() {
        return facetType;
    }

    public void setFacetType(String facetType) {
        this.facetType = facetType;
    }

    public int getFacetId() {
        return facetId;
    }

    public void setFacetId(int facetId) {
        this.facetId = facetId;
    }

    public String getFacetName() {
        return facetName;
    }

    public void setFacetName(String facetName) {
        this.facetName = facetName;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }
}