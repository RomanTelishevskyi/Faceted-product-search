package amazonlike.search.controller;

import amazonlike.search.model.Categories;
import amazonlike.search.service.CategoriesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
@CrossOrigin(origins = "*")
public class CategoriesController {

    private final CategoriesService categoriesService;

    public CategoriesController(CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }

    @PostMapping("/")
    public void save(@RequestBody String name) {
        categoriesService.save(name);
    }

    @GetMapping("/")
    public List<Categories> findAll() {
        return categoriesService.findAll();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        categoriesService.deleteById(id);
    }

    @GetMapping("/name/{name}")
    public Optional<Categories> findByName(@PathVariable String name) {
        return categoriesService.findByName(name);
    }

    @GetMapping("/{id}")
    public Optional<Categories> findById(@PathVariable int id) {
        return categoriesService.findById(id);
    }
}
