package com.cw.faquir.core.dao;


import com.cw.faquir.core.domain.pojo.entity.TSingle;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author Lao SiCheng
 * @version 0.1
 */
public interface SingleMapper {

    /**
     * 查询用户信息
     * @param id 用户id
     * @return 用户信息
     */
    @Select("SELECT id, field FROM t_single WHERE id= #{id}")
    TSingle getSingleById(Integer id);

    @Update("update t_single set field = #{field} where id= #{id}")
    int updateSingle(TSingle single);
}
