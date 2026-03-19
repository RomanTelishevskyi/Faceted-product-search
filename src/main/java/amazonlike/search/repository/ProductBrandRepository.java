package amazonlike.search.repository;

import amazonlike.search.model.ProductBrand;
import amazonlike.search.model.ProductBrandId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductBrandRepository extends JpaRepository<ProductBrand, ProductBrandId> {
}
