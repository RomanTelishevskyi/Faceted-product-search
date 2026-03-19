package amazonlike.search;

import amazonlike.search.DTO.ResponseDTO;
import amazonlike.search.model.*;
import amazonlike.search.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class Yes implements CommandLineRunner {

    private final RestClient restClient;
    private final ProductRepository productRepository;
    private final CategoriesRepository categoriesRepository;
    private final ProductCategoriesRepository productCategoriesRepository;
    private final BrandRepository brandRepository;
    private final ProductBrandRepository productBrandRepository;

    public Yes(RestClient.Builder builder,
               ProductRepository productRepository,
               CategoriesRepository categoriesRepository,
               ProductCategoriesRepository productCategoriesRepository,
               BrandRepository brandRepository,
               ProductBrandRepository productBrandRepository) {
        this.restClient = builder.build();
        this.productRepository = productRepository;
        this.categoriesRepository = categoriesRepository;
        this.productCategoriesRepository = productCategoriesRepository;
        this.brandRepository = brandRepository;
        this.productBrandRepository = productBrandRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        ResponseDTO response = restClient.get()
                .uri("https://world.openfoodfacts.org/api/v2/search?countries=uk&page_size=100")
                .retrieve()
                .body(ResponseDTO.class);

        if (response == null || response.getProducts() == null) {
            System.out.println("No products found");
            return;
        }

        response.getProducts().forEach(dto -> {
            if (dto.getProductName() == null || dto.getProductName().isBlank()) {
                return;
            }

            Product product = new Product();
            product.setName(dto.getProductName());
            product.setImage(dto.getImageFrontUrl());
            product = productRepository.save(product);

            saveBrands(product, dto.getBrands());
            saveCategories(product, dto.getCategories());

            System.out.println("Saved: " + product.getName());
        });
    }

    private void saveBrands(Product product, String brandsText) {
        if (brandsText == null || brandsText.isBlank()) {
            return;
        }

        String[] brands = brandsText.split(",");
        for (String rawBrand : brands) {
            String brandName = rawBrand.trim();
            if (brandName.isBlank()) {
                continue;
            }

            Brand brand = brandRepository.findByName(brandName)
                    .orElseGet(() -> {
                        Brand newBrand = new Brand();
                        newBrand.setName(brandName);
                        return brandRepository.save(newBrand);
                    });

            ProductBrand link = new ProductBrand();
            link.setProduct(product);
            link.setBrand(brand);
            productBrandRepository.save(link);
        }
    }

    private void saveCategories(Product product, String categoriesText) {
        if (categoriesText == null || categoriesText.isBlank()) {
            return;
        }

        String[] categories = categoriesText.split(",");
        for (String rawCategory : categories) {
            String categoryName = rawCategory.trim();
            if (categoryName.isBlank()) {
                continue;
            }

            Categories category = categoriesRepository.findByName(categoryName)
                    .orElseGet(() -> {
                        Categories newCategory = new Categories();
                        newCategory.setName(categoryName);
                        return categoriesRepository.save(newCategory);
                    });

            ProductCategories link = new ProductCategories();
            link.setProduct(product);
            link.setCategories(category);
            productCategoriesRepository.save(link);
        }
    }
}