package com.student.ecommerce.student.repository;

import com.student.ecommerce.student.entity.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchHistoryRepository extends JpaRepository<SearchHistory, String> {
    List<SearchHistory> findByUserIdOrderByCreatedAtDesc(String userId);
    List<SearchHistory> findTop10ByUserIdOrderByCreatedAtDesc(String userId);
}
