package admin_user.dto;

import java.time.LocalDate;

public class SaleDTO {
    private Long id;
    private LocalDate date;
    private String dateStr; // Added field for formatted date string
    private int quantity;
    private Long productId;
    private String productName; // Optional field
    private Long customerId;
    private String customerName; // Optional field
    private double totalPrice;

    // Default constructor
    public SaleDTO() {}

    // Constructor with all fields
    public SaleDTO(Long id, LocalDate date, String dateStr, int quantity, Long productId, String productName, Long customerId, String customerName, double totalPrice) {
        this.id = id;
        this.date = date;
        this.dateStr = dateStr; // Initialize formatted date string
        this.quantity = quantity;
        this.productId = productId;
        this.productName = productName;
        this.customerId = customerId;
        this.customerName = customerName;
        this.totalPrice = totalPrice;
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

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
