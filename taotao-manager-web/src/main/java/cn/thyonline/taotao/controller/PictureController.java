package cn.thyonline.taotao.controller;

import cn.thyonline.taotao.common.pojo.PictureResult;
import cn.thyonline.taotao.service.PictureService;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class PictureController {
    /**
     * url:/pic/upload
     * 参数：uploadFile
     */

    private PictureService pictureService;
    public PictureResult uploadFile(MultipartFile uploadFile){
        PictureResult result = pictureService.uploadPic(uploadFile);
        return result;
    }
}
