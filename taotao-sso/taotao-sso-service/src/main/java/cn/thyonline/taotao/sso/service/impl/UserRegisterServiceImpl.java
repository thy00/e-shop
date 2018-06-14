package cn.thyonline.taotao.sso.service.impl;

import cn.thyonline.taotao.common.pojo.TaotaoResult;
import cn.thyonline.taotao.mapper.TbUserMapper;
import cn.thyonline.taotao.pojo.TbUser;
import cn.thyonline.taotao.pojo.TbUserExample;
import cn.thyonline.taotao.sso.service.UserRegisterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@Service
public class UserRegisterServiceImpl implements UserRegisterService {
    @Autowired
    private TbUserMapper userMapper;

    @Override
    public TaotaoResult register(TbUser user) {
        //1、校验数据
        if (StringUtils.isEmpty(user.getUsername())){//用户名不能为空
            return TaotaoResult.build(400,"注册失败，请校验之后提交数据");
        }
        if (StringUtils.isEmpty(user.getPassword())){//密码不能为空
            return TaotaoResult.build(400,"注册失败，请校验之后提交数据");
        }
        TaotaoResult result = checkData(user.getUsername(), 1);
        if (!(boolean)result.getData()){//校验用户名返回false
            return TaotaoResult.build(400,"注册失败，请校验之后提交数据");
        }
        if (StringUtils.isNotBlank(user.getPhone())){
            TaotaoResult result1 = checkData(user.getPhone(), 2);
            if (!(boolean)result1.getData()){//校验电话返回false
                return TaotaoResult.build(400,"注册失败，请校验之后提交数据");
            }
        }
        if (StringUtils.isNotBlank(user.getEmail())){
            TaotaoResult result2 = checkData(user.getEmail(), 3);
            if (!(boolean)result2.getData()){//校验电话返回false
                return TaotaoResult.build(400,"注册失败，请校验之后提交数据");
            }
        }
        //2、校验成功补全数据
        Date date=new Date();
        user.setCreated(date);
        user.setUpdated(date);
        String s = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());//对数据加密
        user.setPassword(s);
        userMapper.insert(user);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult checkData(String param, Integer type) {
        //1、根据参数查询对应的条件
        TbUserExample example=new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        if (type==1){//username
            if (StringUtils.isEmpty(param)){
                return TaotaoResult.ok(false);//为空，表示不可用
            }
            criteria.andUsernameEqualTo(param);
        }else if (type==2){//phone
            criteria.andPhoneEqualTo(param);//可以为空
        }else if (type==3){//email
            criteria.andEmailEqualTo(param);//可以为空
        }else {
            return TaotaoResult.build(400,"非法的参数");
        }
        //2、调用方法获取查询结果
        List<TbUser> users = userMapper.selectByExample(example);
        //3、判断是否查到数据
        if (users!=null&&users.size()>0){
            return TaotaoResult.ok(false);
        }
        return TaotaoResult.ok(true);
    }
}
