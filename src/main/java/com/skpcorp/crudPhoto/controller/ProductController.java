package com.skpcorp.crudPhoto.controller;


import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import com.skpcorp.crudPhoto.config.FileUploadUtil;
import com.skpcorp.crudPhoto.model.Product;
import com.skpcorp.crudPhoto.service.ProductService;



@Controller
public class ProductController {
	
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/")
	public String viewHomePage(Model model) {
		return findPaginated(1, "id", "asc", model);
	}

	@GetMapping("/index")
	public String Projectview(Model model, @Param("keyword") String keyword) {
		List<Product> listProduct = productService.getAllProduct(keyword);
		model.addAttribute("listProduct",listProduct);
		model.addAttribute("keyword",keyword);
		return "index";
	}
	
	@GetMapping("/ShowNewProductForm")
	public String ProjectProduct(Model model) {
		
		Product product = new Product();
		model.addAttribute("product",product);
		return "Add_product";
	}
	
	@PostMapping("/saveProduct")
	public RedirectView saveProduct(@ModelAttribute("product") Product product,@RequestParam("image") MultipartFile multipartFile) throws IOException{
		
		String fileName =StringUtils.cleanPath(multipartFile.getOriginalFilename());
		
		product.setPhotos(fileName);
		
		Product saveProduct =productService.saveProduct(product);
		
		String uploadDir ="product-photos/" + saveProduct.getId();
		
		FileUploadUtil.saveFile(uploadDir,fileName,multipartFile);
		
		return new RedirectView("/",true);
	}
  
	@GetMapping("/ShowFormForUpdate/{id}")
	public String UpdateImage(@PathVariable(value = "id") long id, Model model) {
  
		//get product from service
        Product product = productService.getProductById(id);
        		
        
        model.addAttribute("product",product);
		
		return "Edit_product";
	}
	
	@GetMapping("/deleteProduct/{id}")
	public String deleteProduct(@PathVariable(value = "id") long id) {
  
		//call delete product method
       
		this.productService.deleteProductById(id);
	
		return "redirect:/";
	}
	
	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable(value = "PageNo") int pageNo,
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model){
		
		int pageSize = 3;
		
		Page<Product> page =productService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Product> listProduct =page.getContent();
		
		model.addAttribute("currentPage",pageNo);
		model.addAttribute("totalPages",page.getTotalPages());
		model.addAttribute("totalItems",page.getTotalElements());

		model.addAttribute("sortField",sortField);
		model.addAttribute("sortDir",sortDir);
		model.addAttribute("reverseSortDir",sortDir.equals("asc") ? "desc" : "asc");
		
		model.addAttribute("listProduct",listProduct);
  
		return "index";
	}
	
}
