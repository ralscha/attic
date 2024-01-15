package ch.ess.blankrc.remote.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ch.ess.blankrc.remote.EchoService;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2004/06/04 20:24:07 $
 * 
 * @spring.bean id="echoService"
 */

public class EchoServiceImpl implements EchoService {

  private final static Log logger = LogFactory.getLog(EchoServiceImpl.class);

  public String echo(String str) {
    logger.info("start echo method");

    return "answer from server :" + str;
  }

}