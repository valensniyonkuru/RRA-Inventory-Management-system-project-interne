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

import admin_user.dto.CustomerDTO;
import admin_user.service.CustomerService;

@Controller
@RequestMapping("/admin/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // Display list of customers
    @GetMapping
    public String listCustomers(Model model) {
        List<CustomerDTO> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);
        return "customer_list";  // Corresponds to customer_list.html
    }

    // Show form to add a new customer
    @GetMapping("/new")
    public String showAddCustomerForm(Model model) {
        model.addAttribute("customer", new CustomerDTO());
        return "customer_form";  // Corresponds to customer_form.html
    }

    // Handle the form submission for creating a new customer
    @PostMapping("/new")
    public String addCustomer(@ModelAttribute CustomerDTO customerDTO) {
        customerService.createCustomer(customerDTO);
        return "redirect:/admin/customers";
    }

 // Show form to edit an existing customer
    @GetMapping("/edit/{id}")
    public String showEditCustomerForm(@PathVariable Long id, Model model) {
        CustomerDTO customer = customerService.getCustomerById(id);
        if (customer != null) {
            model.addAttribute("customer", customer);
            return "customer_form";  // Corresponds to customer_form.html
        } else {
            return "redirect:/admin/customers"; // If customer not found, redirect to list
        }
    }

    // Handle the form submission for updating a customer
    @PostMapping("/update/{id}")
    public String updateCustomer(@PathVariable Long id, @ModelAttribute CustomerDTO customerDTO) {
        CustomerDTO updatedCustomer = customerService.updateCustomer(id, customerDTO);
        if (updatedCustomer != null) {
            return "redirect:/admin/customers";
        } else {
            return "redirect:/admin/customers/edit/" + id; // If update failed, redirect back to edit form
        }
    }


    // Delete a customer
    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/admin/customers";
    }

    // Search customers
    @GetMapping("/search")
    public String searchCustomers(@RequestParam String keyword, Model model) {
        List<CustomerDTO> customers = customerService.searchCustomers(keyword);
        model.addAttribute("customers", customers);
        return "customer_list";  // Corresponds to customer_list.html
    }
}
