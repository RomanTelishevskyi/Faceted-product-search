package amazonlike.search.service;

import amazonlike.search.model.ProductCategories;
import amazonlike.search.model.ProductCategoriesId;
import amazonlike.search.repository.ProductCategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductCategoriesService {

    private final ProductCategoriesRepository productCategoriesRepository;

    @Autowired
    public ProductCategoriesService(ProductCategoriesRepository productCategoriesRepository) {
        this.productCategoriesRepository = productCategoriesRepository;
    }

    public void save(ProductCategories productCategories) {
        productCategoriesRepository.save(productCategories);
    }

    public void deleteById(ProductCategoriesId id) {
        productCategoriesRepository.deleteById(id);
    }

    public Optional<ProductCategories> findById(ProductCategoriesId id) {
        return productCategoriesRepository.findById(id);
    }


}
