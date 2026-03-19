package amazonlike.search.repository;

import amazonlike.search.model.ProductCategories;
import amazonlike.search.model.ProductCategoriesId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoriesRepository extends JpaRepository<ProductCategories, ProductCategoriesId> {
}
