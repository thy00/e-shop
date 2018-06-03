package cn.thyonline.taotao.controller;

import cn.thyonline.taotao.common.utils.JsonUtils;
import cn.thyonline.taotao.utils.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Controller
public class PictureController {
    /**
     * url:/pic/upload
     * 参数：uploadFile
     */
    @Value("${TAOTAO_IMAGE_SERVER_URL}")
    private String TAOTAO_IMAGE_SERVER_URL;


    @RequestMapping(value="/pic/upload",produces=MediaType.TEXT_PLAIN_VALUE+";charset=utf-8")
	@ResponseBody
    public String uploadFile(MultipartFile uploadFile){
        System.out.println("图片名称是"+uploadFile.getOriginalFilename());
        try {
            // 1.获取元文件的扩展名
            String originalFilename = uploadFile.getOriginalFilename();
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            // 2.获取文件的字节数组
            byte[] bytes = uploadFile.getBytes();
            // 3.通过fastdfsclient的方法上传图片（参数要求有 字节数组 和扩展名 不包含"."）
            FastDFSClient client = new FastDFSClient("classpath:properties/fastdfs.conf");
            // 返回值：group1/M00/00/00/wKgZhVk4vDqAaJ9jAA1rIuRd3Es177.jpg
            String string = client.uploadFile(bytes, extName);
            //拼接成完整的URL
            //"http://192.168.25.133/"
            String path = 	TAOTAO_IMAGE_SERVER_URL+string;
            // 4.成功时，设置map
            Map<String, Object> map = new HashMap<>();
            map.put("error", 0);
            map.put("url", path);
            // 6.返回map
            return JsonUtils.objectToJson(map);
        } catch (Exception e) {
            // 5.失败时，设置map
            Map<String, Object> map = new HashMap<>();
            map.put("error", 1);
            map.put("message", "上传失败!");
            return JsonUtils.objectToJson(map);
        }
    }
}
