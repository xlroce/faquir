package com.cw.faquir.core.config;

/**
 * 枚举, 用于读写分离
 * @author Lao SiCheng
 * @version 0.1
 */
public enum RouteDataSourceEnum {
    /**
     * 主库
     */
    MATER,
    /**
     * 从库
     */
    SLAVE
}
