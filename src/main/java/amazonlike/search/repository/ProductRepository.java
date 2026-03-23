package amazonlike.search.repository;

import amazonlike.search.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = """
            SELECT
                
            """, nativeQuery = true)
    List<Object[]> findAllFullProducts(String[][] ids);
}
