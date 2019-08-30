package com.cw.faquir.core.domain.pojo.database;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author Lao SiCheng
 * @version 0.1
 */
@Component
@ConfigurationProperties(prefix = "faquir.slave")
@PropertySource("classpath:databases/database-dev.properties")
public class DataBaseSlave extends AbstractDataBase{


}
