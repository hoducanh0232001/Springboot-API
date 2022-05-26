package com.tutorial.apidemo.Springboot.tutorial.controllers;

import com.tutorial.apidemo.Springboot.tutorial.models.Product;
import com.tutorial.apidemo.Springboot.tutorial.models.ResponseObject;
import com.tutorial.apidemo.Springboot.tutorial.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/Products")
public class ProductController {
    @Autowired
    private ProductRepository repository;
    @GetMapping("")
    //this request is http://localhost:8080/api/v1/Products
    List<Product> getAllProduct(){
       return repository.findAll(); //where is data
    }
    //Get detail product
    @GetMapping("/{id}")
    // let return an object with data, massage , status
    ResponseEntity<ResponseObject> findById(@PathVariable Long id){
        Optional<Product> foundProduct = repository.findById(id);
        if(foundProduct.isPresent()){

            return ResponseEntity.status(HttpStatus.OK).body(
            new ResponseObject("ok","Query product successfully",foundProduct)
            );
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
              new ResponseObject("failed", "Cannot found product with id="+id,"")
            );
        }
    }
    //insert product with POST method
    @PostMapping("/insert")
    ResponseEntity<ResponseObject> insertProduct(@RequestBody Product newProduct){
        //2 product not same name
        List<Product> foundProducts = repository.findByProductName(newProduct.getProductName().trim());
        if(foundProducts.size()>0){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed","Product name already taken","")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok","Insert product successfully",repository.save(newProduct))
        );
    }
    //update, upsert = update + insert
    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateProduct (@RequestBody Product newProduct,@PathVariable Long id){
       Product updateProduct = repository.findById(id)
                .map(product -> {
            product.setProductName(newProduct.getProductName());
            product.setPrice(newProduct.getPrice());
            product.setYear(newProduct.getYear());
            product.setUrl(newProduct.getUrl());
            return repository.save(product);
                }).orElseGet(()->{
                    newProduct.setId(id);
                    return repository.save(newProduct);
               });
       return ResponseEntity.status(HttpStatus.OK).body(
               new ResponseObject("Ok","Update product successfully",updateProduct)
       );
    }
    //delete product
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteProduct(@PathVariable Long id){
        boolean exists = repository.existsById(id);
        if(exists){
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok","Delete product successfully","")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Cannot found product to delete","")
        );
    }
}
