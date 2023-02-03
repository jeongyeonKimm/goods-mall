package com.example.sejonggoodsmall.service;

import com.example.sejonggoodsmall.model.ItemImage;
import com.example.sejonggoodsmall.repository.ItemImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemImageService {

    @Value("${itemImgLocation}")
    private String itemImgLocation;

    private final ItemImageRepository itemImgRepository;

    private final FileService fileService;

    public void saveItemImg(ItemImage itemImg, MultipartFile itemImgFile) throws Exception{
        String oriImgName = itemImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        //파일 업로드
        if(!StringUtils.isEmpty(oriImgName)){
            imgName = fileService.uploadFile(itemImgFile);
            imgUrl = "/images/item/" + imgName;
        }

        //상품 이미지 정보 저장
        itemImg.updateItemImage(oriImgName, imgName, imgUrl);
        itemImgRepository.save(itemImg);
    }

}