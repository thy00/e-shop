package cn.thyonline.taotao.service;

import cn.thyoline.taotao.mapper.TestMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TestServiceImpl implements TestService {
    @Resource
    private TestMapper mapper;
    @Override
    public String queryNow() {

        return mapper.queryNow();
    }

}

