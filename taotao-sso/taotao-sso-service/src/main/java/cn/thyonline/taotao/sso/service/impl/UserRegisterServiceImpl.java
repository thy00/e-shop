package cn.thyonline.taotao.sso.service.impl;

import cn.thyonline.taotao.common.pojo.TaotaoResult;
import cn.thyonline.taotao.mapper.TbUserMapper;
import cn.thyonline.taotao.pojo.TbUser;
import cn.thyonline.taotao.pojo.TbUserExample;
import cn.thyonline.taotao.sso.service.UserRegisterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRegisterServiceImpl implements UserRegisterService {
    @Autowired
    private TbUserMapper userMapper;
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
