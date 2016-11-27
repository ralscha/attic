package ch.ralscha.wsdemo.je;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.StoreConfig;

@Configuration
public class BerkeleyDBConfiguration {

  @Value("#{appProperties.dbDirectory}")
  private String dbDirectory;
  
  @Bean
  public Environment environment() {  
    Environment myDbEnvironment = null;
    try {
      EnvironmentConfig envConfig = new EnvironmentConfig();
      envConfig.setAllowCreate(true);
      envConfig.setTransactional(true);
      File dbDir = new File(dbDirectory);
      FileUtils.forceMkdir(dbDir);
      myDbEnvironment = new Environment(dbDir, envConfig);
    } catch (IOException e) {
      LoggerFactory.getLogger(getClass()).error("environment", e);
    }
    
    return myDbEnvironment;    
  }
  
  @Bean
  public EntityStore entityStore() {
    EntityStore store = null;

    StoreConfig storeConfig = new StoreConfig();
    storeConfig.setAllowCreate(true);
    storeConfig.setTransactional(true);
    store = new EntityStore(environment(), "EntityStore", storeConfig);

    return store;
  }
  
  @Bean
  public PrimaryIndex<Long, Document> documentPrimaryIndex() {
    return entityStore().getPrimaryIndex(Long.class, Document.class);
  }
  
  
  
}
