package admin_user.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "purchases")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private int quantity;
    private BigDecimal cost;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @Column(length = 255)
    @Size(max = 255, message = "Reason must be less than or equal to 255 characters")
    private String reason;

    // Enum to track purchase status
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PurchaseStatus status;

    public Purchase() {
        // Default status as AVAILABLE when a new purchase is created
        this.status = PurchaseStatus.AVAILABLE;
    }

    public Purchase(LocalDate date, int quantity, BigDecimal cost, Product product, Supplier supplier, String reason, PurchaseStatus status) {
        this.date = date;
        this.quantity = quantity;
        this.cost = cost;
        this.product = product;
        this.supplier = supplier;
        this.reason = reason;
        this.status = status != null ? status : PurchaseStatus.AVAILABLE;  // Default to AVAILABLE if not provided
    }

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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
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

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", date=" + date +
                ", quantity=" + quantity +
                ", cost=" + cost +
                ", product=" + (product != null ? product.getName() : "null") +
                ", supplier=" + (supplier != null ? supplier.getName() : "null") +
                ", reason='" + reason + '\'' +
                ", status=" + status +
                '}';
    }
}
