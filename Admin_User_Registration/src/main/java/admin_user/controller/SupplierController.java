package admin_user.controller;

import admin_user.dto.SupplierDTO;
import admin_user.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/suppliers")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    // Display list of suppliers
    @GetMapping
    public String listSuppliers(Model model) {
        List<SupplierDTO> suppliers = supplierService.getAllSuppliers();
        model.addAttribute("suppliers", suppliers);
        return "supplier_list";
    }

    // Show form to add a new supplier
    @GetMapping("/new")
    public String showAddSupplierForm(Model model) {
        model.addAttribute("supplier", new SupplierDTO());
        return "supplier_form";
    }

    // Handle the form submission for creating a new supplier
    @PostMapping("/save")
    public String addSupplier(@ModelAttribute SupplierDTO supplierDTO) {
        supplierService.createSupplier(supplierDTO);
        return "redirect:/admin/suppliers";
    }

    // Show form to edit an existing supplier
    @GetMapping("/edit/{id}")
    public String showEditSupplierForm(@PathVariable Long id, Model model) {
        SupplierDTO supplier = supplierService.getSupplierById(id);
        model.addAttribute("supplier", supplier);
        return "supplier_form";
    }

    // Handle the form submission for updating a supplier
    @PostMapping("/update/{id}")
    public String updateSupplier(@PathVariable Long id, @ModelAttribute SupplierDTO supplierDTO) {
        supplierService.updateSupplier(id, supplierDTO);
        return "redirect:/admin/suppliers";
    }

    // Delete a supplier
    @GetMapping("/delete/{id}")
    public String deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return "redirect:/admin/suppliers";
    }

    // Search suppliers
    @GetMapping("/search")
    public String searchSuppliers(@RequestParam String keyword, Model model) {
        List<SupplierDTO> suppliers = supplierService.searchSuppliers(keyword);
        model.addAttribute("suppliers", suppliers);
        return "supplier_list";
    }
}