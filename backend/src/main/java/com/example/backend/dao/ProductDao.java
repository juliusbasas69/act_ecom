package com.example.backend.dao;

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
				p.status,
				p.createdAt,
				p.updatedAt,
				p.isDeleted
			)
			FROM ProductEntity p
			LEFT JOIN CategoryEntity c
				ON p.category = c.categoryCode
			WHERE p.isDeleted = false
			AND (
				(:search IS NOT NULL AND :search <> '' AND (
					LOWER(p.productCode) LIKE LOWER(CONCAT('%', :search, '%')) OR
					LOWER(p.productName) LIKE LOWER(CONCAT('%', :search, '%')) OR
					LOWER(c.categoryName) LIKE LOWER(CONCAT('%', :search, '%'))
				))
				OR (:search IS NULL OR :search = '')
			)
			""";
	
	
	@Query(GET_ALL_PRODUCTS)
	public Page<ProductData> getAllProducts(Pageable pageable, @Param("search") String search) throws DataAccessException;
}
