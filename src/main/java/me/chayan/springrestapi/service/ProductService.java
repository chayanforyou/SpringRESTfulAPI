package me.chayan.springrestapi.service;

import me.chayan.springrestapi.exception.ResourceNotFoundException;
import me.chayan.springrestapi.models.Product;
import me.chayan.springrestapi.payload.response.BaseResponse;
import me.chayan.springrestapi.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    public Product create(Product entity) {
        return this.repository.save(entity);
    }

    public Product getProduct(Long id) {
        return this.repository.findById(id).get();
    }

    public List<Product> getProducts() {
        return this.repository.findAll();
    }

    public Product edit(Product editEntity) {

        Optional<Product> dbEntityInstOp = this.repository.findById(editEntity.getId());

        if (dbEntityInstOp.isPresent()) {
            Product dbEntityInst = dbEntityInstOp.get();
            dbEntityInst.setName(editEntity.getName());
            dbEntityInst.setPrice(editEntity.getPrice());
            dbEntityInst.setImageUrl(editEntity.getImageUrl());
            dbEntityInst.setAvailable(editEntity.isAvailable());
            return this.repository.save(dbEntityInst);
        }

        return null;
    }

    public ResponseEntity<?> delete(Long id) {
        Product deleteInst = repository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Resource not found for this id :: " + id));
        repository.delete(deleteInst);
        return ResponseEntity.ok(new BaseResponse(true,"Successfully Deleted Data"));
    }
}
