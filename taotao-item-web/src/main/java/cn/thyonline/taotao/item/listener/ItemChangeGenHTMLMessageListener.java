package cn.thyonline.taotao.item.listener;

import cn.thyonline.taotao.item.pojo.Item;
import cn.thyonline.taotao.pojo.TbItem;
import cn.thyonline.taotao.pojo.TbItemDesc;
import cn.thyonline.taotao.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class ItemChangeGenHTMLMessageListener implements MessageListener {

    @Resource
    private ItemService service;
    @Autowired
    private FreeMarkerConfigurer configurer;
    @Override
    public void onMessage(Message message) {
        //接收textmessage消息
        if (message instanceof TextMessage){

            try {
                //获得信息中的id
                String text = ((TextMessage) message).getText();
                //调用service查询信息
                if (StringUtils.isNotBlank(text)){
                    Long id= Long.valueOf(text);
                    TbItem tbItem = service.getItemById(id);
                    System.out.println("生成的商品名字："+tbItem.getTitle());
                    Item item=new Item(tbItem);
                    TbItemDesc itemDesc = service.getItemDescById(id);
                    //生成静态页面
                    genHTML(item,itemDesc);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 使用数据和模板生成静态页面
     * @param item
     * @param itemDesc
     */
    private void genHTML(Item item, TbItemDesc itemDesc) throws Exception {
        //1、获得连接对象configuration
        Configuration configuration = configurer.getConfiguration();
        //2、由连接对象创建模板（使用模板对象，由springMVC管理）
        Template template = configuration.getTemplate("item.ftl");
        //3、创建数据集
        Map model=new HashMap();
        model.put("item",item);
        model.put("itemDesc",itemDesc);
        //4、将数据集和模板输出到本地
        Writer writer = new FileWriter(new File("D:\\freemarker\\item\\" + item.getId() + ".html"));
        template.process(model,writer);
        writer.close();
    }
}
