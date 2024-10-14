package admin_user.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import admin_user.dto.CustomerDTO;
import admin_user.model.Customer;
import admin_user.repositories.CustomerRepository;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // Get all customers
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get a customer by ID
    public CustomerDTO getCustomerById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.map(this::convertToDTO).orElse(null);
    }

    // Create a new customer
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = convertToEntity(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return convertToDTO(savedCustomer);
    }

    // Update a customer
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        Optional<Customer> customerOpt = customerRepository.findById(id);
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            customer.setName(customerDTO.getName());
            customer.setEmail(customerDTO.getEmail());
            customer.setPhone(customerDTO.getPhone());
            Customer updatedCustomer = customerRepository.save(customer);
            return convertToDTO(updatedCustomer);
        }
        return null; // Return null if customer not found
    }

    // Delete a customer
    public void deleteCustomer(Long id) {
        if (customerRepository.existsById(id)) {  // Check if the customer exists before deleting
            customerRepository.deleteById(id);
        }
    }

    // Search customers by keyword
    public List<CustomerDTO> searchCustomers(String keyword) {
        return customerRepository.findAll().stream()
                .filter(customer -> customer.getName().toLowerCase().contains(keyword.toLowerCase()))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Convert Entity to DTO
    private CustomerDTO convertToDTO(Customer customer) {
        return new CustomerDTO(customer.getId(), customer.getName(), customer.getEmail(), customer.getPhone());
    }

    // Convert DTO to Entity
    private Customer convertToEntity(CustomerDTO customerDTO) {
        return new Customer(customerDTO.getName(), customerDTO.getEmail(), customerDTO.getPhone());
    }
}
