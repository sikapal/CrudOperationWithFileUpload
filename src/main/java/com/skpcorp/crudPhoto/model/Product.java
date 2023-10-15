package com.skpcorp.crudPhoto.model;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name ="product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
   
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Setter
	private long id;
	
	@Getter
	@Setter
	@Column(name ="product_name")
	private String productName;
	
	@Getter
	@Setter
	@Column(name ="price")
	private Double price;
	
	@Getter
	@Setter
	@Column(nullable = true, length = 64)
	private String photos;
	
	@Transient
	public String getPhotosImagePath() {
		
		if(photos==null) {
			return null;
		} return "/product-photos/" + id + "/" + photos;
	}
}
