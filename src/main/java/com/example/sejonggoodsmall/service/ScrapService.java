package com.example.sejonggoodsmall.service;

import com.example.sejonggoodsmall.dto.ScrapDTO;
import com.example.sejonggoodsmall.model.Item;
import com.example.sejonggoodsmall.model.Member;
import com.example.sejonggoodsmall.model.Scrap;
import com.example.sejonggoodsmall.repository.ItemRepository;
import com.example.sejonggoodsmall.repository.MemberRepository;
import com.example.sejonggoodsmall.repository.ScrapRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScrapService {

    private final ScrapRepository scrapRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Scrap insert(ScrapDTO scrapDTO) throws Exception {
        Member member = memberRepository.findById(scrapDTO.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Item item = itemRepository.findById(scrapDTO.getItemId())
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        if (scrapRepository.findByMemberAndItem(member, item) != null) {
            throw new Exception("찜하지 않은 상품 입니다.");
        }

        Scrap scrap = Scrap.builder()
                .member(member)
                .item(item)
                .build();

        Scrap savedScrap = scrapRepository.save(scrap);

        return savedScrap;
    }
}
