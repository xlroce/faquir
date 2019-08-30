package com.cw.faquir.core.domain.pojo;

import lombok.Data;

/**
 * Return json format data
 * @author Lao SiCheng
 * @version 0.1
 */
@Data
public class Result {
    /**
     * 返回状态码
     */
    private Integer code;
    /**
     * 返回标题
     */
    private String title;
    /**
     * 返回信息, 包括错误信息 如(系统异常, 请联系管理员), (用户密码不存在)
     */
    private String message;
    /**
     * 返回具体内容, 包括查询内容,列表信息
     */
    private String content;
}
