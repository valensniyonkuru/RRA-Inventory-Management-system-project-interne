package admin_user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.validation.Valid;

import admin_user.dto.CustomerDTO;
import admin_user.dto.ProductDTO;
import admin_user.dto.SaleDTO;
import admin_user.service.CustomerService;
import admin_user.service.ProductService;
import admin_user.service.SaleService;

@Controller
@RequestMapping("/admin/sales")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public String listSales(Model model) {
        List<SaleDTO> sales = saleService.getAllSales();
        model.addAttribute("sales", sales);
        return "sale_list";
    }

    @GetMapping("/new")
    public String showAddSaleForm(Model model) {
        List<ProductDTO> products = productService.getAllProducts();
        List<CustomerDTO> customers = customerService.getAllCustomers();
        model.addAttribute("sale", new SaleDTO());
        model.addAttribute("products", products);
        model.addAttribute("customers", customers);
        return "sale_form";
    }

    @PostMapping
    public String saveSale(@Valid @ModelAttribute SaleDTO saleDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<ProductDTO> products = productService.getAllProducts();
            List<CustomerDTO> customers = customerService.getAllCustomers();
            model.addAttribute("products", products);
            model.addAttribute("customers", customers);
            return "sale_form";
        }

        try {
            // Check product availability
            ProductDTO product = productService.getProductById(saleDTO.getProductId());
            if (product.getStock() < saleDTO.getQuantity()) {
                model.addAttribute("error", "Not enough stock for the selected product. Remaining stock: " + product.getStock());
                List<ProductDTO> products = productService.getAllProducts();
                List<CustomerDTO> customers = customerService.getAllCustomers();
                model.addAttribute("products", products);
                model.addAttribute("customers", customers);
                return "sale_form";
            }

            // Proceed to create or update the sale
            if (saleDTO.getId() != null && saleService.getSaleById(saleDTO.getId()) != null) {
                saleService.updateSale(saleDTO.getId(), saleDTO);
            } else {
                saleService.createSale(saleDTO);
            }

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            List<ProductDTO> products = productService.getAllProducts();
            List<CustomerDTO> customers = customerService.getAllCustomers();
            model.addAttribute("products", products);
            model.addAttribute("customers", customers);
            return "sale_form";
        }

        return "redirect:/admin/sales";
    }

    @GetMapping("/edit/{id}")
    public String showEditSaleForm(@PathVariable Long id, Model model) {
        SaleDTO sale = saleService.getSaleById(id);
        if (sale == null) {
            return "redirect:/admin/sales";
        }

        List<ProductDTO> products = productService.getAllProducts();
        List<CustomerDTO> customers = customerService.getAllCustomers();
        model.addAttribute("sale", sale);
        model.addAttribute("products", products);
        model.addAttribute("customers", customers);
        return "sale_form";
    }

    @GetMapping("/delete/{id}")
    public String deleteSale(@PathVariable Long id) {
        saleService.deleteSale(id);
        return "redirect:/admin/sales";
    }

    @GetMapping("/search")
    public String searchSales(@RequestParam String keyword, Model model) {
        List<SaleDTO> sales = saleService.searchSales(keyword);
        model.addAttribute("sales", sales);
        return "sale_list";
    }

    @GetMapping("/details/{id}")
    @ResponseBody
    public SaleDTO getSaleDetails(@PathVariable Long id) {
        return saleService.getSaleById(id);
    }
}
