package cn.thyonline.taotao.search.listener;

import cn.thyonline.taotao.common.pojo.TaotaoResult;
import cn.thyonline.taotao.search.service.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 接收manager传来消息的监听器
 */
public class ItemChangeMessageListener implements MessageListener {

    @Autowired
    private SearchItemService service;
    @Override
    public void onMessage(Message message) {
        //判断消息是否为textmassage
        if (message instanceof TextMessage){
            try {
                //获得消息中传递过来的id
                String idStr = ((TextMessage) message).getText();
                Long id= Long.valueOf(idStr);
                TaotaoResult result=service.updateSearchItemById(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
