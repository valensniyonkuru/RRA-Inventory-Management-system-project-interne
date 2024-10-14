package admin_user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import admin_user.dto.ProductDTO;
import admin_user.dto.PurchaseDTO;
import admin_user.dto.SupplierDTO;
import admin_user.service.ProductService;
import admin_user.service.PurchaseService;
import admin_user.service.SupplierService;

@Controller
@RequestMapping("/admin/purchases")
public class PurchaseController {

    @Autowired
    private ProductService productService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private PurchaseService purchaseService;

    // Show list of available purchases (default)
    @GetMapping
    public String listAvailablePurchases(Model model) {
        List<PurchaseDTO> purchases = purchaseService.getAvailablePurchases();
        model.addAttribute("purchases", purchases);
        return "purchase_list"; // Default view for listing available purchases
    }

    // View report of canceled purchases
    @GetMapping("/canceled")
    public String viewCanceledPurchasesReport(Model model) {
        List<PurchaseDTO> canceledPurchases = purchaseService.getCanceledPurchases();
        model.addAttribute("purchases", canceledPurchases);
        return "canceled_purchases_report"; // View for canceled purchases report
    }

    // Show form for adding a new purchase
    @GetMapping("/new")
    public String showAddPurchaseForm(Model model) {
        List<ProductDTO> products = productService.getAllProducts();
        List<SupplierDTO> suppliers = supplierService.getAllSuppliers();
        model.addAttribute("purchase", new PurchaseDTO());  // Empty form for new purchase
        model.addAttribute("products", products);
        model.addAttribute("suppliers", suppliers);
        return "purchase_form";  // Form for adding/editing a purchase
    }

    // Handle form submission for creating a new purchase
    @PostMapping
    public String addPurchase(@ModelAttribute PurchaseDTO purchaseDTO) {
        purchaseService.createPurchase(purchaseDTO);  // Save the new purchase
        return "redirect:/admin/purchases";  // Redirect to purchase list after saving
    }

    // Cancel a purchase by providing a reason
    @PostMapping("/cancel")
    public String cancelPurchase(@RequestParam Long purchaseId, @RequestParam String reason) {
        purchaseService.cancelPurchase(purchaseId, reason);  // Cancel the purchase and set the reason
        return "redirect:/admin/purchases";  // Redirect to purchase list after canceling
    }

    // Search purchases based on a keyword
    @GetMapping("/search")
    public String searchPurchases(@RequestParam String keyword, Model model) {
        List<PurchaseDTO> purchases = purchaseService.searchPurchases(keyword);  // Perform search
        model.addAttribute("purchases", purchases);  // Add search results to the model
        return "purchase_list";  // Return the purchase list with search results
    }
}
