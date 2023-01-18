package com.myomi.product.vo;

import java.util.List;

import com.myomi.qna.vo.QnaVo;
import com.myomi.review.vo.BestReviewVo;
import com.myomi.review.vo.ReviewVo;
import com.myomi.seller.vo.SellerVo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter
@NoArgsConstructor
//@AllArgsConstructor
@ToString
public class ProductVo {
	private int num;
	private SellerVo seller;
	private String category;
	private String name;
	private int originPrice;
	private int percentage;
	private int week;
	private int status;
	private String detail;
	private int reviewCnt;
	private int stars;
	private int fee;
	private List<QnaVo> qna;
	private List<ReviewVo> review;
	private List<BestReviewVo> bestReview;
	
	public ProductVo(SellerVo seller, String category, String name, int originPrice, int percentage, int week,
			int status, String detail, int fee) {
//		super();
//		this.num = num;
		this.seller = seller;
		this.category = category;
		this.name = name;
		this.originPrice = originPrice;
		this.percentage = percentage;
		this.week = week;
		this.status = status;
		this.detail = detail;
		this.fee = fee;
	}
}