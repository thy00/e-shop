package cn.thyonline.taotao.sso.service;

import cn.thyonline.taotao.common.pojo.TaotaoResult;
import cn.thyonline.taotao.pojo.TbUser;

/**
 * 用户注册
 */
public interface UserRegisterService {
    /**
     * 注册校验
     * @param param 校验的数据
     * @param type 1/2/3 username/phone/email
     * @return
     */
    TaotaoResult checkData (String param,Integer type);

    /**
     * 用户注册
     * @param user
     * @return
     */
    TaotaoResult register(TbUser user);
}
