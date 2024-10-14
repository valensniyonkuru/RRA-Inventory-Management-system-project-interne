package admin_user.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import admin_user.dto.CategoryDTO;
import admin_user.model.Category;
import admin_user.repositories.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // Get all categories
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get a category by ID
    public CategoryDTO getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.map(this::convertToDTO).orElse(null);
    }

    // Create a new category
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = convertToEntity(categoryDTO);
        Category savedCategory = categoryRepository.save(category);
        return convertToDTO(savedCategory);
    }

    // Update a category
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        Optional<Category> categoryOpt = categoryRepository.findById(id);
        if (categoryOpt.isPresent()) {
            Category category = categoryOpt.get();
            category.setName(categoryDTO.getName());
            Category updatedCategory = categoryRepository.save(category);
            return convertToDTO(updatedCategory);
        }
        return null;
    }

    // Delete a category
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    // Search categories by keyword
    public List<CategoryDTO> searchCategories(String keyword) {
        return categoryRepository.findAll().stream()
                .filter(category -> category.getName().toLowerCase().contains(keyword.toLowerCase()))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Convert Entity to DTO
    private CategoryDTO convertToDTO(Category category) {
        return new CategoryDTO(category.getId(), category.getName());
    }

    // Convert DTO to Entity
    private Category convertToEntity(CategoryDTO categoryDTO) {
        return new Category(categoryDTO.getName());
    }
}
