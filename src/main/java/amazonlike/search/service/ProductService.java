package amazonlike.search.service;

import amazonlike.search.DTO.FacetDTO;
import amazonlike.search.DTO.ProductToClientDTO;
import amazonlike.search.model.Product;
import amazonlike.search.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductService(ProductRepository productRepository, JdbcTemplate jdbcTemplate) {
        this.productRepository = productRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(int id) {
        return productRepository.findById(id);
    }

    public void deleteById(int id) {
        productRepository.deleteById(id);
    }

    public List<ProductToClientDTO> searchProducts(List<Integer> brandIds, List<Integer> categoryIds, String partialName, int offset) {
    String[][] filters = convertFilters(brandIds, categoryIds);

    return jdbcTemplate.query(
            con -> {
                var ps = con.prepareStatement("SELECT * FROM search_products(?::text[][], ?,?)");
                ps.setArray(1, con.createArrayOf("text", filters));
                ps.setString(2, partialName);
                ps.setInt(3, offset);
                return ps;
            },
            (rs, rowNum) -> {
                ProductToClientDTO dto = new ProductToClientDTO();
                dto.setId(rs.getInt("product_id"));
                dto.setProductName(rs.getString("product_name"));
                dto.setImageFrontUrl(rs.getString("image_front_url"));
                dto.setBrands(rs.getString("brands"));
                dto.setCategories(rs.getString("categories"));
                return dto;
            }
    );
}

    public List<FacetDTO> searchFacets(List<Integer> brandIds, List<Integer> categoryIds, String partialName) {
        String[][] filters = convertFilters(brandIds, categoryIds);

        return jdbcTemplate.query(
                con -> {
                    var ps = con.prepareStatement("SELECT * FROM search_facets(?::text[][], ?)");
                    ps.setArray(1, con.createArrayOf("text", filters));
                    ps.setString(2, partialName);
                    return ps;
                },
                (rs, rowNum) -> {
                    FacetDTO dto = new FacetDTO();
                    dto.setFacetType(rs.getString("facet_type"));
                    dto.setFacetId(rs.getInt("facet_id"));
                    dto.setFacetName(rs.getString("facet_name"));
                    dto.setProductCount(rs.getInt("product_count"));
                    return dto;
                }
        );
    }

    private String[][] convertFilters(List<Integer> brandIds, List<Integer> categoryIds) {
        List<String[]> result = new ArrayList<>();

        if (brandIds != null) {
            for (Integer brandId : brandIds) {
                if (brandId != null) {
                    String[] filter = new String[2];
                    filter[0] = String.valueOf(brandId);
                    filter[1] = "b";
                    result.add(filter);
                }
            }
        }

        if (categoryIds != null) {
            for (Integer categoryId : categoryIds) {
                if (categoryId != null) {
                    String[] filter = new String[2];
                    filter[0] = String.valueOf(categoryId);
                    filter[1] = "c";
                    result.add(filter);
                }
            }
        }

        return result.toArray(new String[0][0]);
    }
}
