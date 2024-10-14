package admin_user.repositories;

import admin_user.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    // Custom query methods (if needed) can be added here
}
