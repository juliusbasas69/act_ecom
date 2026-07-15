package com.example.backend.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend.dao.entity.ProductEntity;
import com.example.backend.dao.projection.ProductData;

public interface ProductDao extends JpaRepository<ProductEntity, Integer> {
    
    public static final String GET_ALL_PRODUCTS = """
			SELECT new com.example.backend.dao.projection.ProductData(
				p.id,
				p.productCode,
				p.productName,
				p.price,
				p.quantity,
				p.description,
				c.categoryName,
				c.color,
				p.status,
				p.isFeatured,
				p.image,
				p.createdAt,
				p.updatedAt,
				p.isDeleted
			)
			FROM ProductEntity p
			LEFT JOIN CategoryEntity c
				ON p.category = c.categoryCode
			WHERE p.isDeleted = false
			AND (
				(:search IS NULL OR :search = '')
				OR (
					LOWER(p.productCode) LIKE LOWER(CONCAT('%', :search, '%'))
					OR LOWER(p.productName) LIKE LOWER(CONCAT('%', :search, '%'))
					OR LOWER(c.categoryName) LIKE LOWER(CONCAT('%', :search, '%'))
				)
			)
			AND (
				:category IS NULL
				OR :category = ''
				OR p.category = :category
			)
			AND (
				:price IS NULL
				OR :price = ''

				OR (:price = '0-500'
					AND p.price BETWEEN 0 AND 500)

				OR (:price = '500-1000'
					AND p.price BETWEEN 500 AND 1000)

				OR (:price = '1000-5000'
					AND p.price BETWEEN 1000 AND 5000)

				OR (:price = '5000'
					AND p.price >= 5000)
			)
			AND (
				:stock IS NULL
				OR :stock = ''

				OR (:stock = 'IN_STOCK'
					AND p.quantity > 10)

				OR (:stock = 'LOW_STOCK'
					AND p.quantity BETWEEN 1 AND 10)

				OR (:stock = 'OUT_OF_STOCK'
					AND p.quantity = 0)
			)
    """;
	
	
	@Query(GET_ALL_PRODUCTS)
	public Page<ProductData> getAllProducts(
		Pageable pageable, 
		@Param("search") String search, 
		@Param("category") String category, 
		@Param("price") String price, 
		@Param("stock") String stock) throws DataAccessException;

	public static final String GET_FEATURED_PRODUCTS = """
		SELECT new com.example.backend.dao.projection.ProductData(
			p.id,
			p.productCode,
			p.productName,
			p.price,
			p.quantity,
			p.description,
			c.categoryName,
			c.color,
			p.status,
			p.isFeatured,
			p.image,
			p.createdAt,
			p.updatedAt,
			p.isDeleted
		)
		FROM ProductEntity p
		LEFT JOIN CategoryEntity c ON p.category = c.categoryCode
		WHERE p.isDeleted = false
		AND p.isFeatured = true
	""";

	@Query(GET_FEATURED_PRODUCTS)
	public List<ProductData> getFeaturedProducts() throws DataAccessException;
}
