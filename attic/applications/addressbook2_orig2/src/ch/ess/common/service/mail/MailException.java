 
package ch.ess.common.service.mail;

import org.apache.commons.lang.exception.NestableException;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.2 $ $Date: 2003/11/11 19:10:52 $ 
  */
public class MailException extends NestableException {

	public MailException(String msg) {
		super(msg);
	}

	public MailException(String msg, Throwable t) {
		super(msg, t);
	}

}
