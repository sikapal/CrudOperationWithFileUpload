package com.skpcorp.crudPhoto.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.skpcorp.crudPhoto.model.Product;


public interface ProductService {
  
	//save products in database
	Product saveProduct(Product product);
	
	//get a product by id
	Product getProductById(long id);
	
	//delete a product by id
	void deleteProductById(long id);
	
	//list all products
	List<Product> getAllProduct(String keyword);
	
	//pagination code
	Page<Product> findPaginated (int pageNo, int pageSize, String sortField,String sortDir);
}
