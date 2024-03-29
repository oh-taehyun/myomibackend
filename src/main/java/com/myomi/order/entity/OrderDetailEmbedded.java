package com.myomi.order.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class OrderDetailEmbedded implements Serializable {
	// 주문 번호
	private Long orderNum;
	// 상품 번호
	private Long prodNum;

	@Builder
	public OrderDetailEmbedded(Long orderNum, Long prodNum) {
		this.orderNum = orderNum;
		this.prodNum = prodNum;
	}
}
