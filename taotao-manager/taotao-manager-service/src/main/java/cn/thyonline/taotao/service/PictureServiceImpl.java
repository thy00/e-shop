package cn.thyonline.taotao.service;

import cn.thyonline.taotao.common.pojo.PictureResult;
import cn.thyonline.taotao.common.utils.FastDFSClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PictureServiceImpl implements PictureService{
    @Override
    public PictureResult uploadPic(MultipartFile picFile) {
        //判断图片是否为空
        PictureResult result=new PictureResult();
        if (picFile.isEmpty()){
            result.setError(1);
            result.setMessage("图片为空！");
            return result;
        }
        // 图片不为空上传
        try {
            String originalFilename = picFile.getOriginalFilename();
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            FastDFSClient client=new FastDFSClient("classpath:properties/fastdfs.conf");
            String url = client.uploadFile(picFile.getBytes(), extName);
            result.setError(0);
            result.setUrl(url);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("图片上传失败！");
            result.setError(1);
        }

        return result;
    }
}
