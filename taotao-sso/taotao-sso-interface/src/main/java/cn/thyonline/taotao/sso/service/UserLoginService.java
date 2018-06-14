package cn.thyonline.taotao.sso.service;

import cn.thyonline.taotao.common.pojo.TaotaoResult;
import cn.thyonline.taotao.pojo.TbUser;

/**
 * 用户登录
 */
public interface UserLoginService {
    /**
     * 用户登录
     * @param user 包含用户名和密码
     * @return
     */
    TaotaoResult login(TbUser user);

    /**
     * 使用token获取用户信息
     * @param token
     * @return
     */
    TaotaoResult getUserByToken(String token);

    /**
     * 用户退出登录
     * @param token
     * @return
     */
    TaotaoResult logOut(String token);
}
