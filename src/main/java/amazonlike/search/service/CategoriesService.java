package amazonlike.search.service;

import amazonlike.search.model.Categories;
import amazonlike.search.repository.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriesService {

    private final CategoriesRepository categoriesRepository;

    @Autowired
    public CategoriesService(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    public void save(String name) {
        categoriesRepository.save(new Categories(name));
    }

    public List<Categories> findAll() {
        return categoriesRepository.findAll();
    }

    public Optional<Categories> findById(int id) {
        return categoriesRepository.findById(id);
    }

    public void deleteById(int id) {
        categoriesRepository.deleteById(id);
    }

}
