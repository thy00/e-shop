package cn.thyonline.taotao.service;

import cn.thyonline.taotao.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private TestMapper mapper;
    @Override
    public String queryNow() {

        return mapper.queryNow();
    }

}

