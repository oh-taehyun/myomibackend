package com.myomi.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.myomi.product.entity.Product;

public interface ProductRepository extends CrudRepository<Product, Long>{
	//셀러가 등록한 모든 상품 찾기
	List<Product> findAllBySellerId(String sellerId);
	//셀러가 등록한 특정 상품 찾기
	Optional<Product> findBySellerIdAndProdNum(String sellerId, Long prodNum);
	//특정 상품 조회하기
	Optional<Product> findById(Long prodNum);
	// 상품 조회시 상품에 따른 문의, 리뷰 받아오기
	@Query(value = "select distinct p.* , r.user_id, r.sort, r.title, r.content, r.created_date, r.stars"
			+ " from product p FULL OUTER JOIN orders_detail od ON p.num = od.prod_num"
			+ " FULL OUTER join review r ON od.order_num = r.order_num"
			+ " LEFT JOIN best_review br ON r.num = br.review_num"
			+ " where p.num = ?", nativeQuery = true)
	Optional<Product> findProdInfo(Long prodNum);
	

	
}


//select p.* , r.user_id, r.sort, r.title, r.content, r.created_date, r.stars
//from product p 
//FULL OUTER JOIN orders_detail od ON p.num = od.prod_num
//FULL OUTER join review r ON od.order_num = r.order_num
//LEFT JOIN best_review br ON r.num = br.review_num
//where p.num=2;