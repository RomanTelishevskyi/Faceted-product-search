package amazonlike.search.DTO;

import java.util.List;

public class ResponseDTO {
    private List<ProductFromApiDTO> products;

    public List<ProductFromApiDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductFromApiDTO> products) {
        this.products = products;
    }
}
