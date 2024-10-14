package admin_user.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import admin_user.dto.SaleDTO;
import admin_user.model.Customer;
import admin_user.model.Product;
import admin_user.model.Sale;
import admin_user.repositories.CustomerRepository;
import admin_user.repositories.ProductRepository;
import admin_user.repositories.SaleRepository;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public List<SaleDTO> getAllSales() {
        return saleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public SaleDTO getSaleById(Long id) {
        Optional<Sale> sale = saleRepository.findById(id);
        return sale.map(this::convertToDTO).orElse(null);
    }

    public SaleDTO createSale(SaleDTO saleDTO) {
        Sale sale = convertToEntity(saleDTO);

        Product product = productRepository.findById(saleDTO.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID " + saleDTO.getProductId()));

        if (product.getStock() < saleDTO.getQuantity()) {
            throw new IllegalArgumentException("Not enough stock for product ID " + saleDTO.getProductId() +
                    ". Remaining stock: " + product.getStock());
        }

        product.setStock(product.getStock() - saleDTO.getQuantity());
        productRepository.save(product);

        Sale savedSale = saleRepository.save(sale);
        return convertToDTO(savedSale);
    }

    public SaleDTO updateSale(Long id, SaleDTO saleDTO) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sale not found with ID " + id));

        Product oldProduct = sale.getProduct();

        if (oldProduct != null) {
            oldProduct.setStock(oldProduct.getStock() + sale.getQuantity());
            productRepository.save(oldProduct);
        }

        if (saleDTO.getProductId() != null && !saleDTO.getProductId().equals(sale.getProduct().getId())) {
            Product newProduct = productRepository.findById(saleDTO.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found with ID " + saleDTO.getProductId()));

            if (newProduct.getStock() < saleDTO.getQuantity()) {
                throw new IllegalArgumentException("Not enough stock for product ID " + saleDTO.getProductId() +
                        ". Remaining stock: " + newProduct.getStock());
            }

            newProduct.setStock(newProduct.getStock() - saleDTO.getQuantity());
            productRepository.save(newProduct);
            sale.setProduct(newProduct);
        }

        if (saleDTO.getProductId() == null || saleDTO.getProductId().equals(sale.getProduct().getId())) {
            Product currentProduct = sale.getProduct();
            int stockDifference = sale.getQuantity() - saleDTO.getQuantity();
            currentProduct.setStock(currentProduct.getStock() + stockDifference);
            productRepository.save(currentProduct);
        }

        sale.setQuantity(saleDTO.getQuantity());
        sale.setDate(saleDTO.getDate());

        if (saleDTO.getCustomerId() != null) {
            Customer customer = customerRepository.findById(saleDTO.getCustomerId())
                    .orElseThrow(() -> new IllegalArgumentException("Customer not found with ID " + saleDTO.getCustomerId()));
            sale.setCustomer(customer);
        }

        Sale updatedSale = saleRepository.save(sale);
        return convertToDTO(updatedSale);
    }

    public void deleteSale(Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sale not found with ID " + id));

        Product product = sale.getProduct();
        if (product != null) {
            product.setStock(product.getStock() + sale.getQuantity());
            productRepository.save(product);
        }

        saleRepository.deleteById(id);
    }

    public List<SaleDTO> searchSales(String keyword) {
        return saleRepository.findAll().stream()
                .filter(sale -> sale.getProduct() != null && sale.getProduct().getName().toLowerCase().contains(keyword.toLowerCase()))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private SaleDTO convertToDTO(Sale sale) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateStr = sale.getDate() != null ? sale.getDate().format(formatter) : null;

        return new SaleDTO(
                sale.getId(),
                sale.getDate(),
                dateStr,
                sale.getQuantity(),
                sale.getProduct() != null ? sale.getProduct().getId() : null,
                sale.getProduct() != null ? sale.getProduct().getName() : null,
                sale.getCustomer() != null ? sale.getCustomer().getId() : null,
                sale.getCustomer() != null ? sale.getCustomer().getName() : null,
                sale.getTotalPrice()
        );
    }

    private Sale convertToEntity(SaleDTO saleDTO) {
        Sale sale = new Sale();
        sale.setDate(saleDTO.getDate());
        sale.setQuantity(saleDTO.getQuantity());

        if (saleDTO.getProductId() != null) {
            Product product = productRepository.findById(saleDTO.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found with ID " + saleDTO.getProductId()));
            sale.setProduct(product);
        }

        if (saleDTO.getCustomerId() != null) {
            Customer customer = customerRepository.findById(saleDTO.getCustomerId())
                    .orElseThrow(() -> new IllegalArgumentException("Customer not found with ID " + saleDTO.getCustomerId()));
            sale.setCustomer(customer);
        }

        return sale;
    }
}
