package admin_user.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import admin_user.dto.SupplierDTO;
import admin_user.model.Supplier;
import admin_user.repositories.SupplierRepository;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    // Get all suppliers
    public List<SupplierDTO> getAllSuppliers() {
        return supplierRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get a supplier by ID
    public SupplierDTO getSupplierById(Long id) {
        Optional<Supplier> supplier = supplierRepository.findById(id);
        return supplier.map(this::convertToDTO).orElse(null);
    }

    // Create a new supplier
    public SupplierDTO createSupplier(SupplierDTO supplierDTO) {
        Supplier supplier = convertToEntity(supplierDTO);
        Supplier savedSupplier = supplierRepository.save(supplier);
        return convertToDTO(savedSupplier);
    }

    // Update a supplier
    public SupplierDTO updateSupplier(Long id, SupplierDTO supplierDTO) {
        Optional<Supplier> supplierOpt = supplierRepository.findById(id);
        if (supplierOpt.isPresent()) {
            Supplier supplier = supplierOpt.get();
            supplier.setName(supplierDTO.getName());
            supplier.setEmail(supplierDTO.getEmail());
            supplier.setPhone(supplierDTO.getPhone());
            supplier.setTinNumber(supplierDTO.getTinNumber()); // Set the TIN number
            Supplier updatedSupplier = supplierRepository.save(supplier);
            return convertToDTO(updatedSupplier);
        }
        return null;
    }

    // Delete a supplier
    public void deleteSupplier(Long id) {
        supplierRepository.deleteById(id);
    }

    // Search suppliers by keyword in name
    public List<SupplierDTO> searchSuppliers(String keyword) {
        return supplierRepository.findAll().stream()
                .filter(supplier -> supplier.getName().toLowerCase().contains(keyword.toLowerCase()))
                .map(supplier -> new SupplierDTO(
                        supplier.getId(), 
                        supplier.getName(), 
                        supplier.getEmail(), 
                        supplier.getPhone(), 
                        supplier.getTinNumber())) // Ensure DTO has the TIN number
                .collect(Collectors.toList());
    }

    // Convert Entity to DTO
    private SupplierDTO convertToDTO(Supplier supplier) {
        return new SupplierDTO(
                supplier.getId(), 
                supplier.getName(), 
                supplier.getEmail(), 
                supplier.getPhone(), 
                supplier.getTinNumber()); // Include TIN number
    }

    // Convert DTO to Entity
 // Convert DTO to Entity
    private Supplier convertToEntity(SupplierDTO supplierDTO) {
        Supplier supplier = new Supplier();
        supplier.setName(supplierDTO.getName());
        supplier.setEmail(supplierDTO.getEmail());
        supplier.setPhone(supplierDTO.getPhone());
        supplier.setTinNumber(supplierDTO.getTinNumber()); // Include TIN number
        return supplier;
    }

}