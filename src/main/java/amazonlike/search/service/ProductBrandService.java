package amazonlike.search.service;

import amazonlike.search.model.ProductBrand;
import amazonlike.search.model.ProductBrandId;
import amazonlike.search.repository.ProductBrandRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductBrandService {

    private final ProductBrandRepository productBrandRepository;

    public ProductBrandService(ProductBrandRepository productBrandRepository) {
        this.productBrandRepository = productBrandRepository;
    }

    public void save(ProductBrand productBrand) {
        productBrandRepository.save(productBrand);
    }

    public void deleteById(ProductBrandId id) {
        productBrandRepository.deleteById(id);
    }

    public Optional<ProductBrand> findById(ProductBrandId id) {
        return productBrandRepository.findById(id);
    }
}
