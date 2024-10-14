package admin_user.controller;

import admin_user.dto.ProductDTO;
import admin_user.dto.CategoryDTO;
import admin_user.service.ProductService;
import admin_user.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    
    @Autowired
    private CategoryService categoryService;

    // Display list of products
    @GetMapping
    public String listProducts(Model model) {
        List<ProductDTO> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "product_list";
    }

    // Show form to add a new product
    @GetMapping("/new")
    public String showAddProductForm(Model model) {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        model.addAttribute("product", new ProductDTO());
        model.addAttribute("categories", categories);
        return "product_form";
    }

    // Handle the form submission for creating a new product
    @PostMapping("/save")
    public String addProduct(@ModelAttribute ProductDTO productDTO) {
        if (productDTO.getCategoryId() == null) {
            return "redirect:/admin/products/new?error=CategoryNotSelected"; // Handle error if no category is selected
        }

        productService.createProduct(productDTO);
        return "redirect:/admin/products";
    }

    // Show form to edit an existing product
    @GetMapping("/edit/{id}")
    public String showEditProductForm(@PathVariable Long id, Model model) {
        ProductDTO product = productService.getProductById(id);
        if (product == null) {
            // Log an error or redirect to an error page
            return "redirect:/admin/products?error=ProductNotFound";
        }
        List<CategoryDTO> categories = categoryService.getAllCategories();
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "product_form";
    }


    // Handle the form submission for updating a product
    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute ProductDTO productDTO) {
        productService.updateProduct(id, productDTO);
        return "redirect:/admin/products";
    }

    // Delete a product
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }

    // Search products
    @GetMapping("/search")
    public String searchProducts(@RequestParam String keyword, Model model) {
        List<ProductDTO> products = productService.searchProducts(keyword);
        model.addAttribute("products", products);
        return "product_list";
    }
}
