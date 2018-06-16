package cn.thyonline.taotao.sso.service.impl;

import cn.thyonline.taotao.common.pojo.TaotaoResult;
import cn.thyonline.taotao.common.utils.JsonUtils;
import cn.thyonline.taotao.mapper.TbUserMapper;
import cn.thyonline.taotao.pojo.TbUser;
import cn.thyonline.taotao.pojo.TbUserExample;
import cn.thyonline.taotao.sso.jedis.JedisClient;
import cn.thyonline.taotao.sso.service.UserLoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;

@Service
public class UserLoginServiceImpl implements UserLoginService {
    @Autowired
    private TbUserMapper userMapper;
    @Autowired
    private JedisClient client;
    @Value("${USER_INFO}")
    private String USER_INFO;
    @Value("${EXPIRE_TIME}")
    private Integer EXPIRE_TIME;

    @Override
    public TaotaoResult logOut(String token) {
        //退出redis缓存
        Boolean exists = client.exists(USER_INFO + ":" + token);
        if (exists){
            client.set(USER_INFO+":"+token,"");
        }
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult getUserByToken(String token) {
        //1、根据token查询信息
        String s = client.get(USER_INFO + ":" + token);
        //2、判断查询结果并设置过期时间
        if (StringUtils.isNotBlank(s)){
            TbUser user = JsonUtils.jsonToPojo(s, TbUser.class);
            client.expire(USER_INFO+":"+token,EXPIRE_TIME);
            return TaotaoResult.ok(user);
        }
        return TaotaoResult.build(400,"用户已过期，请重新登录");
    }

    @Override
    public TaotaoResult login(TbUser user) {
        //1、校验用户名和密码
        if (StringUtils.isEmpty(user.getUsername())||StringUtils.isEmpty(user.getPassword())){
            return TaotaoResult.build(400,"用户名或密码错误");
        }
        //校验用户名，并得到集合
        TbUserExample example=new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(user.getUsername());
        List<TbUser> users = userMapper.selectByExample(example);
        if (users==null&&users.size()==0){
            return TaotaoResult.build(400,"用户名或密码错误");
        }
        //校验密码
        TbUser user1 = users.get(0);
        String s = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        if (!s.equals(user1.getPassword())){
            return TaotaoResult.build(400,"用户名或密码错误");
        }
        //2、将用户信息以值的形式储存到redis中
        String token = UUID.randomUUID().toString();
        user1.setPassword(null);
        client.set(USER_INFO+":"+token,JsonUtils.objectToJson(user1));
        client.expire(USER_INFO+":"+token,EXPIRE_TIME);
        return TaotaoResult.ok(token);
    }
}
