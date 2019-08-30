package com.cw.faquir.core.service;


import com.cw.faquir.core.domain.pojo.entity.TSingle;

/**
 * @author Lao SiCheng
 * @version 0.1
 */
public interface SingleService {

    TSingle getSingle(Integer id);

    int updateSingle(TSingle single);
}
