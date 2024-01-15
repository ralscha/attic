package ch.ess.cal.webservice;

import ch.ess.cal.model.Event;
import ch.ess.cal.model.Recurrence;
import ch.ess.cal.model.RecurrenceException;
/**
 * Datamodel-Class to read the xml coming from CalService client
 * @author  ESS Development AG
 * @version  1.0
 */
public class Events{
	/**
	 * @uml.property  name="e"
	 * @uml.associationEnd  multiplicity="(0 -1)"
	 */
	public Event[] e;
	/**
	 * @uml.property  name="r"
	 * @uml.associationEnd  multiplicity="(0 -1)"
	 */
	public Recurrence[] r;
	
	public RecurrenceException[] ex;
}