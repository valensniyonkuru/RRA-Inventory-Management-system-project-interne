package admin_user.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import admin_user.dto.ProductDTO;
import admin_user.model.Category;
import admin_user.model.Product;
import admin_user.repositories.CategoryRepository;
import admin_user.repositories.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // Get all products
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get a product by ID
    public ProductDTO getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(this::convertToDTO).orElse(null);
    }

    // Create a new product
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = convertToEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return convertToDTO(savedProduct);
    }

    // Update a product
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            product.setName(productDTO.getName());
            product.setPrice(productDTO.getPrice());
            product.setStock(productDTO.getStock());

            if (productDTO.getCategoryId() != null) {
                Optional<Category> category = categoryRepository.findById(productDTO.getCategoryId());
                category.ifPresent(product::setCategory);
            }

            Product updatedProduct = productRepository.save(product);
            return convertToDTO(updatedProduct);
        }
        return null;
    }

    // Delete a product
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // Search products by keyword
    public List<ProductDTO> searchProducts(String keyword) {
        return productRepository.findAll().stream()
                .filter(product -> product.getName().toLowerCase().contains(keyword.toLowerCase()))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Convert Entity to DTO
    private ProductDTO convertToDTO(Product product) {
        return new ProductDTO(product.getId(), product.getName(),
                product.getPrice(), product.getStock(),
                product.getCategory() != null ? product.getCategory().getId() : null);
    }

    // Convert DTO to Entity
    private Product convertToEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());

        if (productDTO.getCategoryId() != null) {
            Optional<Category> category = categoryRepository.findById(productDTO.getCategoryId());
            category.ifPresent(product::setCategory);
        }

        return product;
    }
}
