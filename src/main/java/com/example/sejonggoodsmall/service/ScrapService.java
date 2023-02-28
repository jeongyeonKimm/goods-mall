package com.example.sejonggoodsmall.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.example.sejonggoodsmall.dto.ItemDTO;
import com.example.sejonggoodsmall.dto.ItemImageDTO;
import com.example.sejonggoodsmall.dto.ScrapDTO;
import com.example.sejonggoodsmall.dto.ScrapItemDTO;
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

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
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
            throw new Exception("이미 찜한 상품 입니다.");
        }

        Scrap scrap = Scrap.builder()
                .member(member)
                .item(item)
                .build();

        Scrap savedScrap = scrapRepository.save(scrap);
        item.addScrapCount();

        return savedScrap;
    }

    @Transactional
    public void delete(ScrapDTO scrapDTO) {
        Member member = memberRepository.findById(scrapDTO.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Item item = itemRepository.findById(scrapDTO.getItemId())
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        Scrap scrap = scrapRepository.findByMemberAndItem(member, item);
        if (scrap == null) {
            throw new NotFoundException("찜하지 않은 상품 입니다.");
        }

        scrapRepository.delete(scrap);
        item.subtractScrapCount();
    }

    public List<ScrapItemDTO> getScrapList(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        List<Scrap> scrapList = scrapRepository.findByMember(member);

        List<ScrapItemDTO> scrapItemDTOList = new ArrayList<>();
        for (Scrap scrap : scrapList) {
            ScrapItemDTO scrapItemDTO =
                    new ScrapItemDTO(scrap.getItem().getTitle(), scrap.getItem().getDescription(), scrap.getItem().getPrice(), ItemImageDTO.of(scrap.getItem().getItemImages().get(0)));
            scrapItemDTOList.add(scrapItemDTO);
        }
        return scrapItemDTOList;
    }
}
