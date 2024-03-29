package com.myomi.cart.dto;

import com.myomi.cart.entity.Cart;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartReadResponseDto {
    // 상품 정보
    private Long prodNum;
    private String name;
    private Long originPrice;
    private int percentage;
    private int status;
    private String productImgUrl;
    private int prodCnt;

    @Builder
    public CartReadResponseDto(Long prodNum, String name, Long originPrice, int percentage, int status, String productImgUrl, int prodCnt) {
        this.prodNum = prodNum;
        this.name = name;
        this.originPrice = originPrice;
        this.percentage = percentage;
        this.status = status;
        this.productImgUrl = productImgUrl;
        this.prodCnt = prodCnt;
    }

    public CartReadResponseDto toDto(Cart cart) {
        return CartReadResponseDto.builder()
                .prodNum(cart.getProduct().getProdNum())
                .name(cart.getProduct().getName())
                .originPrice(cart.getProduct().getOriginPrice())
                .percentage(cart.getProduct().getPercentage())
                .status(cart.getProduct().getStatus())
                .productImgUrl(cart.getProduct().getProductImgUrl())
                .prodCnt(cart.getProdCnt())
                .build();
    }
}
