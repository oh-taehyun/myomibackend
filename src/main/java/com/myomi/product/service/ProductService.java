package com.myomi.product.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.myomi.product.dto.ProductDto;
import com.myomi.product.dto.ProductReadOneDto;
import com.myomi.product.dto.ProductSaveDto;
import com.myomi.product.dto.ProductUpdateDto;
import com.myomi.product.entity.Product;
import com.myomi.product.repository.ProductRepository;
import com.myomi.review.entity.Review;
import com.myomi.review.repository.ReviewRepository;
import com.myomi.seller.entity.Seller;
import com.myomi.seller.repository.SellerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
	private final ProductRepository productRepository;
	private final SellerRepository sellerRepository;
	private final ReviewRepository reviewRepository;
	/**
	 * TODO:
	 * 1. 셀러 등록 후 상품 등록하기
	 * 2. 등록된 상품 리스트 조회
	 * 3. 셀러별 상품 리스트 조회
	 * 4. 상품 상세 조회
	 * 5. 상품 수정
	 * 6. 상품 삭제
	 */
	
	@Transactional
	public ResponseEntity<ProductSaveDto> addProduct(ProductSaveDto productSaveDto, Authentication seller) {
		Seller s = sellerRepository.findById(seller.getName())
				.orElseThrow(() -> new IllegalArgumentException("판매자만 상품 등록이 가능합니다"));
		
		//상품등록
		Product product = productSaveDto.toEntity(productSaveDto, s);
		
		productRepository.save(product);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@Transactional
	public List<ProductDto> selectBySellerId(String sellerId) {
		List<Product> prods = productRepository.findAllBySellerId(sellerId);
		List<ProductDto> list = new ArrayList<>();
		if (prods.size() == 0) {
		        log.info("등록된 상품이 없습니다.");
		} else {
		    for (Product p : prods) {
		    	ProductDto dto = ProductDto.builder()
			            .prodNum(p.getProdNum())
			            .seller(p.getSeller())
			            .category(p.getCategory())
			            .name(p.getName())
			            .originPrice(p.getOriginPrice())
			            .percentage(p.getPercentage())
			            .week(p.getWeek())
			            .status(p.getStatus())
			            .detail(p.getDetail())
		                .build();
		        list.add(dto);
		    }
		}
		return list;
	}
	
	
	@Transactional
	public ResponseEntity<ProductReadOneDto> sellectOneProd(Long prodNum) {
		Optional<Product> optP = productRepository.findProdInfo(prodNum);
		optP.get().getQnas(); //이방법이 훨씬 좋아
//		List<Qna> qnas = qnaRepository.findByProdNum(optP.get());
		//리뷰 DTO 받으면 태리님 방식처럼 해보기 
		List<Review> reviews = reviewRepository.findAllReviewByProd(prodNum);
		
		if(optP.get().getQnas().size() == 0) {	
			log.info("상품관련 문의가 없습니다.");
		}
		
		if(reviews.size() == 0) {	
			log.info("상품에 대한리뷰가 없습니다.");
		}
		return new ResponseEntity<>(
				ProductReadOneDto.builder()
					.prodNum(prodNum)
					.seller(optP.get().getSeller())
					.category(optP.get().getCategory())
					.name(optP.get().getName())
					.originPrice(optP.get().getOriginPrice())
		            .percentage(optP.get().getPercentage())
		            .week(optP.get().getWeek())
		            .status(optP.get().getStatus())
		            .detail(optP.get().getDetail())
		            .reviews(reviews)
//		            .qnas(qnas)
		            .qnas(optP.get().getQnas())
					.build(), HttpStatus.OK);
	}
	
	@Transactional //성공
	public ResponseEntity<ProductUpdateDto> updateProd(Long prodNum, ProductUpdateDto productUpdateDto, Authentication seller) {
		Optional<Product> p = productRepository.findBySellerIdAndProdNum(seller.getName(), prodNum);
//				.orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));
		
		Product prod = productUpdateDto.toEntity(prodNum, productUpdateDto, p.get().getSeller());
		if(p != null) {
			productRepository.save(prod);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@Transactional //성공
	public ResponseEntity<?> deleteProd(Long prodNum, Authentication seller) {
		Optional<Product> p = productRepository.findById(prodNum);
		Optional<Seller> s = sellerRepository.findById(seller.getName());
		if(p.get().getSeller().getId() == s.get().getId()) {
			productRepository.deleteById(prodNum);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//상품 모든 리스트 뿌려주기. 단, status가 1인걸 기본으로 뿌려줌. <- 프론트에서 설정해야함.
	@Transactional
	public ResponseEntity<?> sellectAllProd(int status) { 
		List<Product> pList = productRepository.findAllByStatusOrderByWeek(status);
		List<ProductDto> list = new ArrayList<>();
		for(Product p : pList) {
			ProductDto dto = ProductDto.builder()
		            .prodNum(p.getProdNum())
		            .seller(p.getSeller())
		            .category(p.getCategory())
		            .name(p.getName())
		            .originPrice(p.getOriginPrice())
		            .percentage(p.getPercentage())
		            .week(p.getWeek())
		            .status(p.getStatus())
		            .detail(p.getDetail())
	                .build();
	        list.add(dto);
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@Transactional
	public ResponseEntity<?> sellectAllprod(String keyword) {
		List<Product> pList = productRepository.findAllByNameContaining(keyword);
		List<ProductDto> list = new ArrayList<>();
		if(pList.size() == 0) {
			log.error("해당 키워드가 포함된 상품이 없습니다.");
		}
		for(Product p : pList) {
			ProductDto dto = ProductDto.builder()
		            .prodNum(p.getProdNum())
		            .seller(p.getSeller())
		            .category(p.getCategory())
		            .name(p.getName())
		            .originPrice(p.getOriginPrice())
		            .percentage(p.getPercentage())
		            .week(p.getWeek())
		            .status(p.getStatus())
		            .detail(p.getDetail())
	                .build();
	        list.add(dto);
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
}