package com.myomi.order.entity;

import com.myomi.product.entity.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "orders_detail")
public class OrderDetail {
    @EmbeddedId
    private OrderDetailEmbedded id = new OrderDetailEmbedded();

    @MapsId("orderNum")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_num", referencedColumnName = "num")
    private Order order;

    @MapsId("prodNum") // 복합키
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prod_num", referencedColumnName = "num")
    private Product product;

    @Column(name = "prod_cnt", nullable = false)
    private int prodCnt;

    @Builder
    private OrderDetail(int prodCnt) {
        this.prodCnt = prodCnt;
    }

    // 연관관계 편의 메소드
    public void registerOrderAndProduct(Order order, Product product) {
        this.order = order;
        this.product = product;
        order.addOrderDetail(this);
    }
}
