package me.chayan.springrestapi.controllers;

import me.chayan.springrestapi.models.Product;
import me.chayan.springrestapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@CrossOrigin("*")
public class ProductController {

    @Autowired
    private ProductService service;

    @RequestMapping(value = {"/product/create" }, method = POST)
    public Product create(@RequestBody Product entity){
        return this.service.create(entity);
    }

    @RequestMapping(value = {"/product/get/{id}" }, method = GET)
    public Product getProduct(@PathVariable Long id){
        return this.service.getProduct(id);
    }

    @RequestMapping(value = {"/product/get" }, method = GET)
    public List<Product> getProducts(){
        return this.service.getProducts();
    }

    @RequestMapping(value = {"/product/edit" }, method = PUT)
    public Product edit(@RequestBody Product editEntity){
        return this.service.edit(editEntity);
    }

    @RequestMapping(value = {"/product/delete/{id}" }, method = DELETE)
    public ResponseEntity<?> deleteSimBillTransaction (@PathVariable(name = "id") Long id){
        return this.service.delete(id);
    }


}
