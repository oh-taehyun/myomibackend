package com.myomi.admin.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.myomi.seller.dto.SellerDetailDto;
import com.myomi.seller.dto.SellerResponseDto;
import com.myomi.seller.entity.Seller;
import com.myomi.seller.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

    private final SellerRepository sellerRepository;
    
    //판매자 리스트
    public List<SellerResponseDto> getAllSeller() {

		List<Seller> sellers = sellerRepository.findAll();
		List<SellerResponseDto> list = new ArrayList<>();
		if (sellers.size() == 0) {
			log.info("판매자가 없습니다.");
		} else {

			for (Seller seller : sellers) {
				SellerResponseDto dto = SellerResponseDto.builder()
						.sellerId(seller.getId())
						.companyName(seller.getCompanyName())
						.status(seller.getStatus())
						.signoutDate(seller.getSellerId().getSignoutDate())
						.build();
				list.add(dto);
			}
		}
		return list;
    }

    //판매자 상세정보
    public List<SellerResponseDto> getAllSellerByStatus(int status) {

        List<Seller> sellers = sellerRepository.findAllByStatus(status);
        List<SellerResponseDto> list = new ArrayList<>();
        if (sellers.size() == 0) {
        	log.info("판매자가 없습니다.");
		} else {
        for (Seller seller : sellers) {
            SellerResponseDto dto = SellerResponseDto.builder().sellerId(seller.getId())
                    .companyName(seller.getCompanyName()).status(seller.getStatus())
                    .signoutDate(seller.getSellerId().getSignoutDate()).build();
            list.add(dto);
        	}
		}
        return list;
    }
    //seller정보 상세보기

    @Transactional
    public SellerDetailDto getOneSellerInfo(String sellerId) {

        Seller seller = sellerRepository.findById(sellerId).get();
        return new SellerDetailDto(seller);
    }

    //seller 승인(승인 거절)해주기
    public void modifySellerStatus(int status, String sellerId) {
        Optional<Seller> optS = sellerRepository.findById(sellerId);
        sellerRepository.updateSellerId(status, sellerId);
    }
}