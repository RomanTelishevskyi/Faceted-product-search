package amazonlike.search.controller;

import amazonlike.search.model.ProductCategories;
import amazonlike.search.model.ProductCategoriesId;
import amazonlike.search.service.ProductCategoriesService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/product-categories")
@CrossOrigin(origins = "*")
public class ProductCategoriesController {

    private final ProductCategoriesService productCategoriesService;

    public ProductCategoriesController(ProductCategoriesService productCategoriesService) {
        this.productCategoriesService = productCategoriesService;
    }

    @GetMapping("/{id}")
    public Optional<ProductCategories> findById(@PathVariable ProductCategoriesId id) {
        return productCategoriesService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable ProductCategoriesId id) {
        productCategoriesService.deleteById(id);
    }

    @PostMapping("/")
    public void save(@RequestBody ProductCategories product) {
        productCategoriesService.save(product);
    }
}
