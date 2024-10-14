package admin_user.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import admin_user.dto.PurchaseDTO;
import admin_user.model.Product;
import admin_user.model.Purchase;
import admin_user.model.PurchaseStatus;
import admin_user.model.Supplier;
import admin_user.repositories.ProductRepository;
import admin_user.repositories.PurchaseRepository;
import admin_user.repositories.SupplierRepository;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    // Get all purchases and convert them to DTOs
    public List<PurchaseDTO> getAllPurchases() {
        return purchaseRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get only available purchases (for the main list)
    public List<PurchaseDTO> getAvailablePurchases() {
        return purchaseRepository.findAll().stream()
                .filter(purchase -> purchase.getStatus() == PurchaseStatus.AVAILABLE)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get only canceled purchases (for the report)
    public List<PurchaseDTO> getCanceledPurchases() {
        return purchaseRepository.findAll().stream()
                .filter(purchase -> purchase.getStatus() == PurchaseStatus.CANCELED)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Create a new purchase and update stock
    public PurchaseDTO createPurchase(PurchaseDTO purchaseDTO) {
        Purchase purchase = convertToEntity(purchaseDTO);
        purchase.setStatus(PurchaseStatus.AVAILABLE); // Default status is AVAILABLE
        Purchase savedPurchase = purchaseRepository.save(purchase);

        // Update product stock
        if (purchase.getProduct() != null) {
            Product product = purchase.getProduct();
            product.setStock(product.getStock() + purchase.getQuantity());
            productRepository.save(product);
        }

        return convertToDTO(savedPurchase);
    }

    // Cancel a purchase and update its status and reason
    public void cancelPurchase(Long purchaseId, String reason) {
        Optional<Purchase> purchaseOpt = purchaseRepository.findById(purchaseId);
        if (purchaseOpt.isPresent()) {
            Purchase purchase = purchaseOpt.get();
            purchase.setReason(reason);
            purchase.setStatus(PurchaseStatus.CANCELED); // Update status to CANCELED
            purchaseRepository.save(purchase);

            // Optionally, update product stock if needed when canceling the purchase
            if (purchase.getProduct() != null) {
                Product product = purchase.getProduct();
                product.setStock(product.getStock() - purchase.getQuantity()); // Decrease stock accordingly
                productRepository.save(product);
            }
        }
    }

    // Convert a Purchase entity to PurchaseDTO
    private PurchaseDTO convertToDTO(Purchase purchase) {
        return new PurchaseDTO(
                purchase.getId(),
                purchase.getDate(),
                purchase.getQuantity(),
                purchase.getCost(),
                purchase.getProduct() != null ? purchase.getProduct().getId() : null,
                purchase.getProduct() != null ? purchase.getProduct().getName() : null,
                purchase.getSupplier() != null ? purchase.getSupplier().getId() : null,
                purchase.getSupplier() != null ? purchase.getSupplier().getName() : null,
                purchase.getSupplier() != null ? purchase.getSupplier().getTinNumber() : null,
                purchase.getReason(),
                purchase.getStatus() // Add status to DTO
        );
    }

    // Convert PurchaseDTO to Purchase entity
    private Purchase convertToEntity(PurchaseDTO purchaseDTO) {
        Purchase purchase = new Purchase();
        purchase.setDate(purchaseDTO.getDate());
        purchase.setQuantity(purchaseDTO.getQuantity());
        purchase.setCost(purchaseDTO.getCost());
        purchase.setReason(purchaseDTO.getReason());

        if (purchaseDTO.getProductId() != null) {
            Optional<Product> product = productRepository.findById(purchaseDTO.getProductId());
            product.ifPresent(purchase::setProduct);
        }

        if (purchaseDTO.getSupplierId() != null) {
            Optional<Supplier> supplier = supplierRepository.findById(purchaseDTO.getSupplierId());
            supplier.ifPresent(purchase::setSupplier);
        }

        return purchase;
    }

    // Search for purchases based on product name keyword
    public List<PurchaseDTO> searchPurchases(String keyword) {
        return purchaseRepository.findAll().stream()
                .filter(purchase -> purchase.getProduct() != null &&
                        purchase.getProduct().getName().toLowerCase().contains(keyword.toLowerCase()))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
