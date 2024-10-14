package admin_user.dto;

public class SupplierDTO {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String tinNumber; // Added TIN number field

    // Default constructor
    public SupplierDTO() {
    }

    // Parameterized constructor
    public SupplierDTO(Long id, String name, String email, String phone, String tinNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.tinNumber = tinNumber; // Initialize TIN number
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTinNumber() {
        return tinNumber;
    }

    public void setTinNumber(String tinNumber) {
        this.tinNumber = tinNumber;
    }
}