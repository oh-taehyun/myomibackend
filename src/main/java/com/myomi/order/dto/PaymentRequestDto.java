package com.myomi.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentRequestDto {
    private String merchant_uid; // 상점 거래 id, 'orderNum_?' 형식
    private String impUid;  // 고유 id
    private Long totalPrice; // 아임포트에서 결제완료된 금액
    private Long payCreatedDate; // timestamped 형태로 받아옴
}
