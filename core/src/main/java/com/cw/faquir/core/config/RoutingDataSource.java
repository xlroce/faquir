package com.cw.faquir.core.config;

import lombok.Data;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lao SiCheng
 * @version 0.1
 */
public class RoutingDataSource extends AbstractRoutingDataSource {

    /**
     * atomicInteger 用于记录轮询选择数据库
     */
    private final Map<String, DatabaseIndex> mapIndex = new HashMap<>();

    {
        mapIndex.put(Constant.MASTER, new DatabaseIndex());
        mapIndex.put(Constant.SLAVE, new DatabaseIndex());
    }

    private static ThreadLocal<String> lookupKeyLocal = new ThreadLocal<>();
    static {
        lookupKeyLocal.set(Constant.MASTER);
    }

    @Override
    protected Object determineCurrentLookupKey() {

        String lookupKey = getLookupKey();
        return lookupKey == null ? Constant.MASTER_0 : lookupKey;
    }

    private String getLookupKey() {
        String key = lookupKeyLocal.get();
        if(key == null){
            key = Constant.MASTER;
        }
        DatabaseIndex databaseIndex = mapIndex.get(key);
        String lookupKey = key;
        synchronized (mapIndex) {
            Integer index = databaseIndex.getIndex();
            index++;
            index = index % databaseIndex.length;
            databaseIndex.setIndex(index);
            lookupKey = lookupKey + "_" + index;
        }
        return lookupKey;
    }

    public static void setLookupKey(String lookupKey) {
        lookupKeyLocal.set(lookupKey);
    }

    void setLength(Integer masterLength, Integer slaveLength) {
        this.mapIndex.get(Constant.MASTER).setLength(masterLength);
        this.mapIndex.get(Constant.SLAVE).setLength(slaveLength);
    }

    @Data
    private final class DatabaseIndex {
        Integer index = 0;
        Integer length = 0;
    }
    public void remove(){
        lookupKeyLocal.remove();
    }

}
