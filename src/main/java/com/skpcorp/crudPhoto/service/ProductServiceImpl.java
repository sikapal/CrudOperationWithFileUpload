package com.skpcorp.crudPhoto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import com.skpcorp.crudPhoto.model.Product;
import com.skpcorp.crudPhoto.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	public ProductRepository productRepository ;
	
	
	@Override
	public List<Product> getAllProduct(String keyword) {
		if(keyword != null) {
			return productRepository.search(keyword);
		}else
		return (List<Product>) productRepository.findAll();
	}
	
	@Override
	public Product saveProduct(Product product) {
		
		return this.productRepository.save(product);
	}

	@Override
	public Product getProductById(long id) {
		Optional <Product> optional =productRepository.findById(id);
		Product product = null;
		if(optional.isPresent()) {
			product = optional.get();
		}else {
			 new RuntimeException("Product not found for id :: " +id);
		}
		
			return product;
		
	}

	@Override
	public void deleteProductById(long Id) {
		
		 this.productRepository.deleteById(Id);
		
	}

	

	@Override
	public Page<Product> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
        : Sort.by(sortField).descending();
		
	    Pageable pageable = PageRequest.of(pageNo - 1 , pageSize, sort);
		return this.productRepository.findAll(pageable);
	}

}
