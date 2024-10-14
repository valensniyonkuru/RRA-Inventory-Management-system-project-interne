package admin_user.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import admin_user.model.PurchaseStatus; // Import PurchaseStatus enum

public class PurchaseDTO {

    private Long id;
    private LocalDate date;
    private int quantity;
    private BigDecimal cost;
    private Long productId;
    private String productName;
    private Long supplierId;
    private String supplierName;
    private String supplierTinNumber;
    private String reason;
    private PurchaseStatus status; // Add the status field

    // Default constructor
    public PurchaseDTO() {
    }

    // Parameterized constructor
    public PurchaseDTO(Long id, LocalDate date, int quantity, BigDecimal cost, Long productId, String productName, Long supplierId, String supplierName, String supplierTinNumber, String reason, PurchaseStatus status) {
        this.id = id;
        this.date = date;
        this.quantity = quantity;
        this.cost = cost;
        this.productId = productId;
        this.productName = productName;
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.supplierTinNumber = supplierTinNumber;
        this.reason = reason;
        this.status = status; // Initialize status
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
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
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

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierTinNumber() {
        return supplierTinNumber;
    }

    public void setSupplierTinNumber(String supplierTinNumber) {
        this.supplierTinNumber = supplierTinNumber;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public PurchaseStatus getStatus() {
        return status;
    }

    public void setStatus(PurchaseStatus status) {
        this.status = status;
    }
}
