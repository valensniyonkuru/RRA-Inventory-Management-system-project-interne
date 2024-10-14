package admin_user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import admin_user.dto.CategoryDTO;
import admin_user.service.CategoryService;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Display list of categories
    @GetMapping
    public String listCategories(Model model) {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "category_list";  // Corresponds to category_list.html
    }

    // Show form to add a new category
    @GetMapping("/new")
    public String showAddCategoryForm(Model model) {
        model.addAttribute("category", new CategoryDTO());
        return "category_form";  // Corresponds to category_form.html
    }

    // Handle the form submission for creating a new category
    @PostMapping
    public String addCategory(@ModelAttribute("category") CategoryDTO categoryDTO) {
        categoryService.createCategory(categoryDTO);
        return "redirect:/admin/categories";
    }

    // Show form to edit an existing category
    @GetMapping("/edit/{id}")
    public String showEditCategoryForm(@PathVariable Long id, Model model) {
        CategoryDTO category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);
        return "category_form";  // Corresponds to category_form.html
    }

    // Handle the form submission for updating a category
    @PostMapping("/update/{id}")
    public String updateCategory(@PathVariable Long id, @ModelAttribute("category") CategoryDTO categoryDTO) {
        categoryService.updateCategory(id, categoryDTO);
        return "redirect:/admin/categories";
    }

    // Delete a category
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/admin/categories";
    }

    // Search categories
    @GetMapping("/search")
    public String searchCategories(@RequestParam String keyword, Model model) {
        List<CategoryDTO> categories = categoryService.searchCategories(keyword);
        model.addAttribute("categories", categories);
        return "category_list";  // Corresponds to category_list.html
    }
}
