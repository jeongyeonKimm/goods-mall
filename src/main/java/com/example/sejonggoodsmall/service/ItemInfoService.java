package com.example.sejonggoodsmall.service;

import com.example.sejonggoodsmall.model.ItemImage;
import com.example.sejonggoodsmall.model.ItemInfo;
import com.example.sejonggoodsmall.repository.ItemInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemInfoService {

    private final ItemInfoRepository itemInfoRepository;

    private final FileService fileService;

    public void saveItemInfo(ItemInfo itemInfo, MultipartFile itemInfoFile) throws Exception{
        String oriInfoName = itemInfoFile.getOriginalFilename();
        String infoName = "";
        String infoUrl = "";

        //파일 업로드
        if(!StringUtils.isEmpty(oriInfoName)){
            infoName = fileService.uploadFile(itemInfoFile);
            infoUrl = "/images/item/" + infoName;
        }

        //상품 이미지 정보 저장
        itemInfo.updateItemInfo(oriInfoName, infoName, infoUrl);
        itemInfoRepository.save(itemInfo);
    }
}
