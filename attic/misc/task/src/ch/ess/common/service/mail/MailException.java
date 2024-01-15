 
package ch.ess.common.service.mail;

import org.apache.commons.lang.exception.NestableException;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2003/11/15 10:31:08 $ 
  */
public class MailException extends NestableException {

	public MailException(String msg) {
		super(msg);
	}

	public MailException(String msg, Throwable t) {
		super(msg, t);
	}

}
