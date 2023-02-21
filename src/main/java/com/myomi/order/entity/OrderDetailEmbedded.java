package com.myomi.order.entity;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class OrderDetailEmbedded implements Serializable{
	// 주문 번호
	private Long oNum;
	// 상품 번호
	private Long pNum;
}
