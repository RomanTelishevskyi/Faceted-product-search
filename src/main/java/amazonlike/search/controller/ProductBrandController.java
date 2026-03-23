package amazonlike.search.controller;

import amazonlike.search.model.ProductBrand;
import amazonlike.search.model.ProductBrandId;
import amazonlike.search.service.ProductBrandService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/product-brand")
public class ProductBrandController {

    private final ProductBrandService productBrandService;

    public ProductBrandController(ProductBrandService productBrandService) {
        this.productBrandService = productBrandService;
    }

    @GetMapping("/{id}")
    public Optional<ProductBrand> findById(@PathVariable ProductBrandId id) {
        return productBrandService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable ProductBrandId id) {
        productBrandService.deleteById(id);
    }

    @PostMapping("/")
    public void save(@RequestBody ProductBrand product) {
        productBrandService.save(product);
    }
}
