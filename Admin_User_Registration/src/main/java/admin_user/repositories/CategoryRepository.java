package admin_user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import admin_user.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Custom query methods (if needed) can be added here
}
