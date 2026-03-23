package amazonlike.search.service;

import amazonlike.search.model.Brand;
import amazonlike.search.repository.BrandRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandService {

    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public void save(String name) {
        brandRepository.save(new Brand(name));
    }

    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    public Optional<Brand> findById(int id) {
        return brandRepository.findById(id);
    }

    public void deleteById(int id) {
        brandRepository.deleteById(id);
    }

    public Optional<Brand> findByName(String name) {
        return brandRepository.findByName(name);
    }
}
