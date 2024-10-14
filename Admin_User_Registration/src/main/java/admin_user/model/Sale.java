package admin_user.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "sales")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // Optional: Add totalPrice field if needed
    private double totalPrice;

    // Default constructor
    public Sale() {
    }

    // Parameterized constructor
    public Sale(LocalDate date, int quantity, Product product, Customer customer) {
        this.date = date;
        this.quantity = quantity;
        this.product = product;
        this.customer = customer;
        // Set totalPrice if required
        this.totalPrice = product != null ? product.getPrice() * quantity : 0.0;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        // Update totalPrice when quantity changes
        if (product != null) {
            this.totalPrice = product.getPrice() * quantity;
        }
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        // Update totalPrice when product changes
        if (product != null) {
            this.totalPrice = product.getPrice() * quantity;
        } else {
            this.totalPrice = 0.0;
        }
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public double getTotalPrice() { // Add this method
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) { // Add this method
        this.totalPrice = totalPrice;
    }
}
