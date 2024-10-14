package admin_user.dto;

public class ProductDTO {
    private Long id;
    private String name;
    private double price;
    private int stock;
    private Long categoryId; // Ensure this field is defined

    // Default constructor
    public ProductDTO() {
    }

    // Parameterized constructor
    public ProductDTO(Long id, String name, double price, int stock, Long categoryId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.categoryId = categoryId;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Long getCategoryId() { // Add this getter
        return categoryId;
    }

    public void setCategoryId(Long categoryId) { // Add this setter
        this.categoryId = categoryId;
    }
}