package com.cw.faquir.core.service.impl;


import com.cw.faquir.core.dao.SingleMapper;
import com.cw.faquir.core.domain.pojo.entity.TSingle;
import com.cw.faquir.core.service.SingleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author Lao SiCheng
 * @version 0.1
 */
@Service
public class SingleServiceImpl implements SingleService {

    @Resource
    SingleMapper singleMapper;

    @Transactional
    @Override
    public TSingle getSingle(Integer id) {
        return singleMapper.getSingleById(id);
    }

    @Override
    public int updateSingle(TSingle single) {
        return singleMapper.updateSingle(single);
    }
}
