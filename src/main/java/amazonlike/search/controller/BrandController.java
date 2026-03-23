package amazonlike.search.controller;

import amazonlike.search.model.Brand;
import amazonlike.search.service.BrandService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/brand")
@CrossOrigin(origins = "http://localhost:4200")
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("/")
    public List<Brand> findAll() {
        return brandService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Brand> findById(@PathVariable int id) {
        return brandService.findById(id);
    }

    @GetMapping("/name/{name}")
    public Optional<Brand> findByName(@PathVariable String name) {
        return brandService.findByName(name);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        brandService.deleteById(id);
    }

    @PostMapping("/")
    public void save(@RequestBody String name) {
        brandService.save(name);
    }
}
