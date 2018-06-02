package cn.thyonline.taotao.service;

import cn.thyonline.taotao.common.pojo.PictureResult;
import org.springframework.web.multipart.MultipartFile;

public interface PictureService {
    PictureResult uploadPic(MultipartFile picFile);
}
