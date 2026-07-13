package com.example.backend.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend.dao.entity.ProductEntity;

public interface ProductDao extends JpaRepository<ProductEntity, Integer> {
    
    public final String GET_ALL_PRODUCTS = """
			SELECT e 
			FROM ProductEntity e 
			WHERE e.isDeleted = false
			AND (
				(:search IS NOT NULL AND :search <> '' AND (
					LOWER(e.productCode) LIKE LOWER(CONCAT('%', :search, '%')) OR
                    LOWER(e.productName) LIKE LOWER(CONCAT('%', :search, '%'))
				))
				OR (:search IS NULL OR :search = '')
			)
			""";
	
	@Query(GET_ALL_PRODUCTS)
	public Page<ProductEntity> getAllProducts(Pageable pageable, @Param("search") String search) throws DataAccessException;
}
