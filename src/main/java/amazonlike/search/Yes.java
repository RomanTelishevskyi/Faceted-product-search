package amazonlike.search;
/*
import amazonlike.search.model.*;
import amazonlike.search.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

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
        importFromCsvDump();
    }

    private void importFromCsvDump() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new ClassPathResource("en.openfoodfacts.org.products.csv").getInputStream(),
                StandardCharsets.UTF_8))) {

            String headerLine = reader.readLine();
            if (headerLine == null || headerLine.isBlank()) {
                System.out.println("file is empty.");
                return;
            }

            String[] headers = headerLine.split("\t", -1);
            Map<String, Integer> indexByName = new HashMap<>();
            for (int i = 0; i < headers.length; i++) {
                indexByName.put(headers[i].trim(), i);
            }

            int savedCount = 0;
            String line;

            while ((line = reader.readLine()) != null) {
                if (savedCount >= 10_000) {
                    break;
                }

                if (line.isBlank()) {
                    continue;
                }

                String[] columns = line.split("\t", -1);

                String productName = getColumn(columns, indexByName, "product_name");
                if (productName.isBlank()) {
                    continue;
                }

                Product product = new Product();
                product.setName(productName);

                String imageUrl = getColumn(columns, indexByName, "image_url");
                if (imageUrl.isBlank()|| imageUrl.contains("invalid")) {
                    continue;
                }

                product.setImage(imageUrl);
                product = productRepository.save(product);

                saveBrands(product, getColumn(columns, indexByName, "brands"));
                saveCategories(product, getColumn(columns, indexByName, "categories"));

                savedCount++;
                System.out.println("Saved " + savedCount + ": " + product.getName());
            }

            System.out.println("Finished TSV import. Total saved: " + savedCount);

        } catch (Exception e) {
            throw new RuntimeException("Failed to import Open Food Facts TSV dump", e);
        }
    }

    private String getColumn(String[] columns, Map<String, Integer> indexByName, String columnName) {
        Integer index = indexByName.get(columnName);
        if (index == null || index < 0 || index >= columns.length) {
            return "";
        }
        return unquote(columns[index]).trim();
    }

    private String unquote(String value) {
        if (value == null) {
            return "";
        }
        String trimmed = value.trim();
        if (trimmed.length() >= 2 && trimmed.startsWith("\"") && trimmed.endsWith("\"")) {
            trimmed = trimmed.substring(1, trimmed.length() - 1);
        }
        return trimmed.replace("\"\"", "\"");
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
*/