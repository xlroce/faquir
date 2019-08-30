package com.cw.faquir.core.domain.pojo.database;

import lombok.Data;

import java.util.List;

/**
 * @author Lao SiCheng
 * @version 0.1
 */
@Data
public abstract class AbstractDataBase {

    private List<DataBaseMaster.DataBase> database;

    @Data
    public static class DataBase {
        String username;
        String password;
        String driver;
        String url;
    }

}
