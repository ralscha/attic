/**
 * 
 *     This WSDL definition describes the UPI Declaration Web Service interface.
 *     The interface is supposed to be compliant to the WS-I Basic Profile 1.1
 *     for interoperable Web Services (cf. http://www.ws-i.org/Profiles/BasicProfile-1.1.html).
 * 
 *     Author: Igor Metz, Glue Software Engineering AG
 * 
 *     Change history:
 *     2007-11-04 created (Igor Metz)
 *     2007-11-12 (Igor Metz, Glue AG)
 *     - replaced operation modifyPerson by operations updateLocalPersonId,
 *       updateCurrentValues and addEntryToHistory.
 *     2007-12-21 (Igor Metz)
 *     - operation updateCurrentValuesOrAddEntryToHistory added
 *     2008-02-21 (Igor Metz, Glue AG)
 *     - adapted to renamed XML Schema
 * 	2010-02-16 (Nguyen The Quang, ELCA)
 * 	- changed to eCH-0084-1-3.xsd
 * 	2010-02-17 (DBN, ELCA)
 * 	- new service eCH-0084 : eraseLocalPersonId
 * 	2010-09-09 (DBN, ELCA)
 * 	- changed to eCH-0084-1-4.xsd
 * 	2012-06-29 (DBN, ELCA)
 * 	- changed to eCH-0084-1-5.xsd
 * 	2013-02-20 (DBN, ELCA)
 * 	- changed to eCH-0084-1-6.xsd
 * 	2015-05-12 (DBN, ELCA)
 * 	- changed to eCH-0084-1-7.xsd 
 * 	- new service eCH-0084 : mergePersonOrUpdateLocalPersonIdRequest
 * 
 *   
 * 
 */
package ch.admin.ws.zas.regcent.upi._0;
