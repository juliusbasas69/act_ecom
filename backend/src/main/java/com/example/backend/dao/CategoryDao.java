package com.example.backend.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend.dao.entity.CategoryEntity;

public interface CategoryDao extends JpaRepository<CategoryEntity, Integer> {
    
    public final String GET_ALL_CATEGORY = """
			SELECT e 
			FROM CategoryEntity e 
			WHERE e.isDeleted = false
			AND (
				(:search IS NOT NULL AND :search <> '' AND (
					LOWER(e.categoryCode) LIKE LOWER(CONCAT('%', :search, '%')) OR
                    LOWER(e.categoryName) LIKE LOWER(CONCAT('%', :search, '%'))
				))
				OR (:search IS NULL OR :search = '')
			)
			""";
	
	@Query(GET_ALL_CATEGORY)
	public Page<CategoryEntity> getAllCategories(Pageable pageable, @Param("search") String search) throws DataAccessException;
}
