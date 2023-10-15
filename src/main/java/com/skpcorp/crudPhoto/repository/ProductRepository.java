package com.skpcorp.crudPhoto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.skpcorp.crudPhoto.model.Product;


public interface ProductRepository extends JpaRepository<Product, Long> {
   
	//code to search product from database
	@Query("SELECT product FROM Product product WHERE CONCAT (product.Id,' ', product.productName, ' ',product.price) LIKE %?1%")
	public List<Product> search(String Keyword);
	public Product findByProductName(String productName);
}
