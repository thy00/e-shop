package cn.thyonline.taotao.sso.service;

import cn.thyonline.taotao.common.pojo.TaotaoResult;

/**
 * 用户注册
 */
public interface UserRegisterService {
    /**
     *
     * @param param 校验的数据
     * @param type 1/2/3 username/phone/email
     * @return
     */
    TaotaoResult checkData (String param,Integer type);
}
