package amazonlike.search.controller;

import amazonlike.search.model.Product;
import amazonlike.search.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public List<Product> findAll() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Product> findById(@PathVariable int id) {
        return productService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        productService.deleteById(id);
    }

    @PostMapping("/")
    public void save(@RequestBody Product product) {
        productService.save(product);
    }

    /*
    findByName
    findByPartName
    findByCategory
    findByBrand
     */
}
