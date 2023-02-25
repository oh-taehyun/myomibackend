package com.myomi.order.entity;

import com.myomi.user.entity.User;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "orders")
@SequenceGenerator(name = "ORDERS_SEQ_GENERATOR", sequenceName = "ORDERS_SEQ"
        , initialValue = 1, allocationSize = 1)
public class Order {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE, generator = "ORDERS_SEQ_GENERATOR"
    )
    @Column(name = "num")
    private Long orderNum;

    @ManyToOne(fetch = FetchType.LAZY) // 양방향
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    private String msg;

    @Column(name = "coupon_num")
    private Long couponNum;

    @Column(name = "used_point")
    private Long usedPoint;

    @Column(name = "pay_created_date")
    private LocalDateTime payCreatedDate;

    @Column(name = "canceled_date")
    private LocalDateTime canceledDate;

    @Column(name = "total_price")
    private Long totalPrice;

    @Column(name = "save_point")
    private Long savePoint;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderDetail> orderDetails = new ArrayList<>(); // 양방향

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "order")
    private Delivery delivery = new Delivery();

    @Builder
    public Order(Long orderNum, User user, LocalDateTime createdDate, String msg, Long couponNum, Long usedPoint, LocalDateTime payCreatedDate,
                 LocalDateTime canceledDate, Long totalPrice, Long savePoint, List<OrderDetail> orderDetails,
                 Delivery delivery) {
        this.orderNum = orderNum;
        this.user = user;
        this.createdDate = createdDate;
        this.msg = msg;
        this.couponNum = couponNum;
        this.usedPoint = usedPoint;
        this.payCreatedDate = payCreatedDate;
        this.canceledDate = canceledDate;
        this.totalPrice = totalPrice;
        this.savePoint = savePoint;
        this.delivery = delivery; // 필수
    }

    // 연관관계 편의 메소드
    public void addOrderDetail(OrderDetail orderDetail) {
        this.orderDetails.add(orderDetail);
//        if (orderDetail.getOrder() != this) {
//            orderDetail.setOrder(this);
//        }
    }
    public void addOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }
    public void addDelivery(Delivery delivery) {
        this.delivery = delivery;
    }
    public void registerDelivery(Delivery delivery) {
        delivery.registerOrder(this);
    }
}
