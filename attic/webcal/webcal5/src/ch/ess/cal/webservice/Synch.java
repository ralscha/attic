package ch.ess.cal.webservice;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import ch.ess.base.model.User;
import ch.ess.cal.dao.CategoryDao;
import ch.ess.cal.dao.EventDao;
import ch.ess.cal.dao.RecurrenceExceptionDao;
import ch.ess.cal.model.Attachment;
import ch.ess.cal.model.Event;
import ch.ess.cal.model.EventCategory;
import ch.ess.cal.model.EventProperty;
import ch.ess.cal.model.Recurrence;
import ch.ess.cal.model.RecurrenceException;
import ch.ess.cal.model.Reminder;
import com.thoughtworks.xstream.XStream;

/**
 * processes outlook and webcal events and decides what to do with each
 * @author ESS Development AG
 * @version 1.0
 * 
 */
public class Synch {
	XStream xstream;
	
	/**
	 * Processes synchronisation of normal events
	 * 
	 * @param xml normal events from outlook
	 * @param eventDao EventDao instance
	 * @param user userobject of the logged in user
	 * @param categoryDao CategoryDao instance
	 * @return normal events that have to inserted or updated into outlook
	 */
	public String synchNormalEvents(String xml, EventDao eventDao, User user, CategoryDao categoryDao){
		Events events = new Events();
		events.e = new Event[]{new Event()};
		xstream = new XStream();
		events = (Events) xstream.fromXML(xml);
		List<Event> calEvents = eventDao.getNormalEventsByUser(user);
		List<Event> normalUpdate = new ArrayList<Event>();
		List<Event> normalInsert = new ArrayList<Event>();
		List<Event> normalDelete = new ArrayList<Event>();
		List<Event> outlookToSave = new ArrayList<Event>();
		List<Event> webCalMerge = new ArrayList<Event>();
		List<Event> insertWebCal = eventDao.getNormalEventsByUser(user);
		HashSet<User> users = new HashSet<User>();
		users.add(user);
	
		//dummy list
		for (Event e : events.e){
			if(e.getDeleted() == null){
				e.setDeleted(false);
			}
			outlookToSave.add(e);
		}
		
		for (Event e : events.e){
			if(e.getDeleted() == null){
				e.setDeleted(false);
			}
			e.setUsers(users);
			e.setIsSynched(true);
			
			if(e.getEventCategories() == null){
				HashSet<EventCategory> eventCategories = new HashSet<EventCategory>();
				e.setEventCategories(eventCategories);		
			}
			
			if(e.getAttachments() == null){
				HashSet<Attachment> attachments = new HashSet<Attachment>();
				e.setAttachments(attachments);		
			}
			if(e.getEventProperties() == null){
				HashSet<EventProperty> eventProperties = new HashSet<EventProperty>();
				e.setEventProperties(eventProperties);		
			}
			if(e.getRecurrences() == null){
				HashSet<Recurrence> recurrences = new HashSet<Recurrence>();
				e.setRecurrences(recurrences);		
			}
			if(e.getReminders() == null){
				HashSet<Reminder> reminders = new HashSet<Reminder>();
				e.setReminders(reminders);		
			}
			EventCategory cat = new EventCategory();
			cat.setEvent(e);
			cat.setCategory(categoryDao.findById(e.getCategoryId()));
			
			e.getEventCategories().add(cat);
			
			if(e.getDeleted() == null)
				e.setDeleted(false);
			
			if(!calEvents.isEmpty()){
				for (Event calEvent : calEvents) {

					if(calEvent.getDeleted() == null)
						calEvent.setDeleted(false);
					if(calEvent.getIsSynched() == null)
						calEvent.setIsSynched(false);
					
					if(e.getGroup() == null){
						if(checkUID(e, calEvent)){

							if(calEvent.getModificationDate() == null) calEvent.setModificationDate((long) 0);
							if(e.getModificationDate() == null) e.setModificationDate((long) 0);
							if(calEvent.getDeleted()){

								if(calEvent.getEndDate() == null){
									calEvent.setEndDate(calEvent.getStartDate());
								}
								if(calEvent.getStartDate() > calEvent.getEndDate()){
									calEvent.setEndDate(calEvent.getStartDate());
									calEvent.setAllDay(true);
								}
								
								if(calEvent.getDescription() != null){
									calEvent.setDescription(Helper.replace(calEvent.getDescription()));
								}
								if(calEvent.getSubject() != null){
									calEvent.setSubject(Helper.replace(calEvent.getSubject()));
								}
								if(calEvent.getLocation() != null){
									calEvent.setLocation(Helper.replace(calEvent.getLocation()));
								}
								
								normalDelete.add(calEvent);
								insertWebCal.remove(calEvent);
								outlookToSave.remove(e);
							}else if(calEvent.getIsSynched() && calEvent.getModificationDate() > e.getModificationDate() && !calEvent.getDeleted()){
								
								if(calEvent.getStartDate() > calEvent.getEndDate()){
									calEvent.setEndDate(calEvent.getStartDate());
									calEvent.setAllDay(true);
								}
								
								if(calEvent.getDescription() != null){
									calEvent.setDescription(Helper.replace(calEvent.getDescription()));
								}
								if(calEvent.getSubject() != null){
									calEvent.setSubject(Helper.replace(calEvent.getSubject()));
								}
								if(calEvent.getLocation() != null){
									calEvent.setLocation(Helper.replace(calEvent.getLocation()));
								}
								
								normalUpdate.add(calEvent);
								insertWebCal.remove(calEvent);
								outlookToSave.remove(e);
							}else{
								webCalMerge.add(mergeEvent(calEvent, e));
								insertWebCal.remove(calEvent);
								outlookToSave.remove(e);
							}
						}else if(checkNormalEventFields(e, calEvent)){
							if(!calEvent.getDeleted()){
								webCalMerge.add(mergeEvent(calEvent, e));
								insertWebCal.remove(calEvent);
								outlookToSave.remove(e);
							}
						}
					}
				}
			}else{
				//No WebCal events found, insert all Outlook events
				e.setIsSynched(true);
				eventDao.save(e);
			}
		}
		
		
		for(Event event : outlookToSave){
			event.setIsSynched(true);
			eventDao.save(event);
		}
		
		for(Event e : insertWebCal){
			
			if(e.getStartDate() > e.getEndDate()){
				e.setEndDate(e.getStartDate());
				e.setAllDay(true);
			}
			if(e.getDeleted() == null){
				e.setDeleted(false);
			}
			if(e.getDescription() != null){
				e.setDescription(Helper.replace(e.getDescription()));
			}
			if(e.getSubject() != null){
				e.setSubject(Helper.replace(e.getSubject()));
			}
			if(e.getLocation() != null){
				e.setLocation(Helper.replace(e.getLocation()));
			}
			if(e.getIsSynched() == null)
				e.setIsSynched(false);
			if(e.getDeleted()){
				eventDao.delete(e);
			}else{
				if(!e.getIsSynched()){
					normalInsert.add(e);
				}else{
					eventDao.delete(e);
				}
			}
		}
		
		for(Event e : webCalMerge){
			e.setIsSynched(true);
			eventDao.save(e);
			normalUpdate.add(e);
		}
		
		for(Event e : normalUpdate){
			e.setIsSynched(true);
			eventDao.save(e);
		}
		for(Event e : normalInsert){
			e.setIsSynched(true);
			eventDao.save(e);
		}
		for(Event e : normalDelete){
			e.setIsSynched(true);
			eventDao.save(e);
		}

		return XMLFactory.createNormalXML(normalUpdate, normalInsert, normalDelete);		
	}

	/**
	 * Processes synchronisation of recurring events
	 * @param xml recurring events from outlook
	 * @param eventDao EventDao instance
	 * @param user userobject of the logged in user
	 * @param categoryDao CategoryDao instance
	 * @return recurring events that have to inserted or updated into outlook
	 */
	public String synchRecurEvents(String xml, EventDao eventDao, User user, CategoryDao categoryDao){
		Events events = new Events();
		events.r = new Recurrence[]{new Recurrence()};
		xstream = new XStream();
		events = (Events) xstream.fromXML(xml);
		HashSet<User> users = new HashSet<User>();
		List<Object[]> recurUpdate = new ArrayList<Object[]>();
		List<Object[]> recurInsert = new ArrayList<Object[]>();
		List<Object[]> recurDelete = new ArrayList<Object[]>();
		List<Recurrence>  outlookToSave = new ArrayList<Recurrence>();
		List<Recurrence>  webCalMerge = new ArrayList<Recurrence>();
		List<Object[]>  insertWebCal = null;
		users.add(user);
		List<Object[]> recurList = null;
		recurList = eventDao.getRecurEventsByUser(user);
		insertWebCal = eventDao.getRecurEventsByUser(user);
		
		for (Recurrence r : events.r){
			if(r.getEvent().getDeleted() == null){
				r.getEvent().setDeleted(false);
			}

			if(r.isAlways()){
				r.setPatternEndDate(null);
			}
			//Matches outlook recurrences to the exact webcal database structure!
			if(r.getType().name().equals("DAILY")){
				r.setDayOfWeekMask(null);
				r.setMonthOfYear(null);
				r.setInstance(null);
				r.setDayOfMonth(null);
			}else if(r.getType().name().equals("WEEKLY")){
				r.setMonthOfYear(null);
				r.setInstance(null);
				r.setDayOfMonth(null);
			}else if(r.getType().name().equals("MONTHLY")){
				r.setDayOfWeekMask(null);
				r.setMonthOfYear(null);
				r.setInstance(null);
			}else if(r.getType().name().equals("MONTHLY_NTH")){
				r.setMonthOfYear(null);
				r.setDayOfMonth(null);
			}else if(r.getType().name().equals("YEARLY")){
				r.setDayOfWeekMask(null);
				r.setInterval(null);
				r.setInstance(null);
			}else if(r.getType().name().equals("YEARLY_NTH")){
				r.setInterval(null);
				r.setDayOfMonth(null);
			}
			
			outlookToSave.add(r);
		}
		
		for (Recurrence r : events.r) {
			if(r.getEvent().getDeleted() == null){
				r.getEvent().setDeleted(false);
			}
			r.getEvent().setIsSynched(true);
			r.getEvent().setUsers(users);
			
			if(r.getEvent().getRecurrences() == null){
				HashSet<Recurrence> recurrences = new HashSet<Recurrence>();
				r.getEvent().setRecurrences(recurrences);
			}

			r.getEvent().getRecurrences().add(r);

			if(r.getEvent().getEventCategories() == null){
				HashSet<EventCategory> eventCategories = new HashSet<EventCategory>();
				r.getEvent().setEventCategories(eventCategories);		
			}
			EventCategory cat = new EventCategory();
			cat.setEvent(r.getEvent());
			cat.setCategory(categoryDao.findById(r.getEvent().getCategoryId()));
			r.getEvent().getEventCategories().add(cat);
			
			if(r.getEvent().getAttachments() == null){
				HashSet<Attachment> attachments = new HashSet<Attachment>();
				r.getEvent().setAttachments(attachments);		
			}
			if(r.getEvent().getEventProperties() == null){
				HashSet<EventProperty> eventProperties = new HashSet<EventProperty>();
				r.getEvent().setEventProperties(eventProperties);		
			}
			
			if(r.getEvent().getReminders() == null){
				HashSet<Reminder> reminders = new HashSet<Reminder>();
				r.getEvent().setReminders(reminders);		
			}
			

			if(!recurList.isEmpty()){
				for(Object[] calRec : recurList){
					Event e = (Event) calRec[1];
					Recurrence rolo = (Recurrence) calRec[0];
					if(e.getDeleted() == null)
						e.setDeleted(false);
					if(e.getIsSynched() == null)
						e.setIsSynched(false);
					
					if(e.isAllDay()){
						e.setEndDate(null);
					}
					
					if(rolo.isAlways()){
						rolo.setPatternEndDate(null);
					}
					if(rolo.getType().name().equals("DAILY")){
						rolo.setDayOfWeekMask(null);
						rolo.setMonthOfYear(null);
						rolo.setInstance(null);
						rolo.setDayOfMonth(null);
					}else if(rolo.getType().name().equals("WEEKLY")){
						rolo.setMonthOfYear(null);
						rolo.setInstance(null);
						rolo.setDayOfMonth(null);
					}else if(rolo.getType().name().equals("MONTHLY")){
						rolo.setDayOfWeekMask(null);
						rolo.setMonthOfYear(null);
						rolo.setInstance(null);
					}else if(rolo.getType().name().equals("MONTHLY_NTH")){
						rolo.setMonthOfYear(null);
						rolo.setDayOfMonth(null);
					}else if(rolo.getType().name().equals("YEARLY")){
						rolo.setDayOfWeekMask(null);
						rolo.setInterval(null);
						rolo.setInstance(null);
					}else if(rolo.getType().name().equals("YEARLY_NTH")){
						rolo.setInterval(null);
						rolo.setDayOfMonth(null);
					}
					
					if(e.getGroup() == null){
						if(checkUID((Event)r.getEvent(), e)){

							//If modification date from outlook is higher, the events have to be updated on webcal side
							if(e.getModificationDate() == null) e.setModificationDate((long) 0);
							if(r.getEvent().getModificationDate() == null) r.getEvent().setModificationDate((long) 0);
							
							if(e.getDeleted()){
								if(e.getEndDate() == null)
									e.setEndDate((long )0);
								
								if(e.getStartDate() > e.getEndDate()){
									e.setEndDate(e.getStartDate());
									e.setAllDay(true);
								}
								if(rolo.getPatternEndDate() != null && rolo.getPatternEndDate()  < 0){
									rolo.setPatternEndDate(null);
								}
								
								if(rolo.getPatternStartDate() != null && rolo.getPatternStartDate() < 0){
									rolo.setPatternStartDate(null);
								}
								
								if(e.getDescription() != null){
									e.setDescription(Helper.replace(e.getDescription()));
								}
								if(e.getSubject() != null){
									e.setSubject(Helper.replace(e.getSubject()));
								}
								if(e.getLocation() != null){
									e.setLocation(Helper.replace(e.getLocation()));
								}				
								recurDelete.add(calRec);
								outlookToSave.remove(r);
								insertWebCal.remove(calRec);
							}else if(e.getIsSynched() && e.getModificationDate() > r.getEvent().getModificationDate() && !e.getDeleted()){
								if(e.getStartDate() > e.getEndDate()){
									e.setEndDate(e.getStartDate());
									e.setAllDay(true);
								}
								if(rolo.getPatternEndDate() != null && rolo.getPatternEndDate()  < 0){
									rolo.setPatternEndDate(null);
								}
								
								if(rolo.getPatternStartDate() != null && rolo.getPatternStartDate() < 0){
									rolo.setPatternStartDate(null);
								}
								
								
								if(e.getDescription() != null){
									e.setDescription(Helper.replace(e.getDescription()));
								}
								if(e.getSubject() != null){
									e.setSubject(Helper.replace(e.getSubject()));
								}
								if(e.getLocation() != null){
									e.setLocation(Helper.replace(e.getLocation()));
								}	
								recurUpdate.add(calRec);
								outlookToSave.remove(r);
								insertWebCal.remove(calRec);
		
							}else{
								webCalMerge.add(mergeRecurrencePattern((Recurrence) calRec[0], r));
								insertWebCal.remove(calRec);
								outlookToSave.remove(r);
							}							
						}else if(checkNormalEventFields((Event)r.getEvent(), e) && checkRecurrenceEventFields(r,e)){
							if(!e.getDeleted()){
								webCalMerge.add(mergeRecurrencePattern((Recurrence) calRec[0], r));
								insertWebCal.remove(calRec);
								outlookToSave.remove(r);
							}
						}
					}
				}
			}else{
				r.getEvent().setIsSynched(true);
				eventDao.save((Event) r.getEvent());
			}
		}
		
		for(Recurrence r : outlookToSave){
			r.getEvent().setIsSynched(true);
			
			if(r.getPatternStartDate() == null){
				r.setPatternStartDate(r.getEvent().getStartDate());
			}
			eventDao.save((Event) r.getEvent());
		}
		
		for(Object[] o : insertWebCal){
			Event e = (Event)o[1];
			Recurrence rolo = (Recurrence)o[0];
			if(e.getEndDate() == null)
				e.setEndDate((long) 0);
			if(e.getStartDate() > e.getEndDate()){
				e.setEndDate(e.getStartDate());
				e.setAllDay(true);
			}
			if(rolo.getPatternEndDate() != null && rolo.getPatternEndDate()  < 0){
				rolo.setPatternEndDate(null);
			}
			
			if(rolo.getPatternStartDate() != null && rolo.getPatternStartDate() < 0){
				rolo.setPatternStartDate(null);
			}
			
			if(e.getDeleted() == null){
				e.setDeleted(false);
			}
			
			if(e.getDescription() != null){
				e.setDescription(Helper.replace(e.getDescription()));
			}
			if(e.getSubject() != null){
				e.setSubject(Helper.replace(e.getSubject()));
			}
			if(e.getLocation() != null){
				e.setLocation(Helper.replace(e.getLocation()));
			}	
			if(e.getIsSynched() == null)
				e.setIsSynched(false);
			if(e.getDeleted()){
				eventDao.delete(e);
			}else{
				if(!e.getIsSynched()){
					recurInsert.add(o);
				}else{
					eventDao.delete(e);
				}
			}
		}
		
		for(Recurrence r : webCalMerge){
			r.getEvent().setIsSynched(true);
			eventDao.save((Event) r.getEvent());
			Object[] o = new Object[2];
			o[0] = r;
			o[1] = r.getEvent();
			recurUpdate.add(o);
		}
		
		for(Object[] o : recurUpdate){
			Event e = (Event)o[1];
			e.setIsSynched(true);
			eventDao.save(e);
		}
		for(Object[] o : recurInsert){
			Event e = (Event)o[1];
			e.setIsSynched(true);
			eventDao.save(e);
		}
		for(Object[] o : recurDelete){
			Event e = (Event)o[1];
			e.setIsSynched(true);
			eventDao.save(e);
		}
		return XMLFactory.createRecurXML(recurUpdate, recurInsert,recurDelete);
		
	}
	
	public void synchExceptions(String xml, RecurrenceExceptionDao recurrenceExceptionDao)
	{
		recurrenceExceptionDao.deleteAll();
		Events events = new Events();
		events.ex = new RecurrenceException[]{new RecurrenceException()};
		xstream = new XStream();
		events = (Events) xstream.fromXML(xml);
		
		for(RecurrenceException exception : events.ex)
		{
			recurrenceExceptionDao.save(exception);
		}
	}
	/**
	 * Überprüft die UID's zweier Termine auf Gleichheit
	 * @param e Zu prüfender Outlook-Termin
	 * @param calEvent Zu prüfender WebCal-Termin
	 * @return true wenn UID übereinstimmt, false wenn nicht
	 */
	private Boolean checkUID(Event e, Event calEvent){
		if(e.getUid().toString().equals(calEvent.getUid().toString())){
			return true;	
		}
		return false;
	}
	
	/**
	 * Überprüft Betreff, Startzeit, Endzeit und AllDay-Flag 
	 * auf Gleichheit zweier Termine
	 * @param e Zu prüfender Outlook-Termin
	 * @param calEvent Zu prüfender WebCal-Termin
	 * @return True wenn alle Felder übereinstimmen, False wenn eines oder mehrere falsch sind
	 */
	private Boolean checkNormalEventFields(Event e, Event calEvent){
		if(e.getStartDate().toString().equals(calEvent.getStartDate().toString()) && //Startdatum gleich
			e.getSubject().toString().equals(calEvent.getSubject().toString()) && // Subject gleich
			e.isAllDay().equals(calEvent.isAllDay())){
			return true;
		}
		return false;
	}
	
	private boolean checkModificationDate(long outlook, long webcal){
		if(outlook < webcal){
			return true;
		}
		return false;
	}
	
	/**
	 * Überprüft Wiederholungstyp zweier Serientermine auf Gleichheit
	 * @param r Zu prüfender Outlook-Serientermin
	 * @param calEvent Zu prüfender WebCal-Serientermin
	 * @return true wen Übereinstimmung, false wenn nicht
	 */
	private boolean checkRecurrenceEventFields(Recurrence r, Event calEvent){

		for (Recurrence calRec : calEvent.getRecurrences()) {
			//If same recurrence type
			if(calRec.getType().toString().equals(r.getType().toString())){
			
				switch (r.getType()) {
				case DAILY:
					if(r.getDuration().toString().equals(calRec.getDuration().toString()) &&
						r.getInterval().toString().equals(calRec.getInterval().toString())){
							return true;
					}
					return false;
				case WEEKLY:					
					if(r.getDuration().toString().equals(calRec.getDuration().toString()) &&
						r.getInterval().toString().equals(calRec.getInterval().toString()) &&
						r.getDayOfWeekMask().toString().equals(calRec.getDayOfWeekMask().toString())){
						return true;
					}
					return false;
				case MONTHLY:
					if(r.getDuration().toString().equals(calRec.getDuration().toString()) &&
						r.getInterval().toString().equals(calRec.getInterval().toString()) &&
						r.getDayOfMonth().toString().equals(calRec.getDayOfMonth().toString())){
						return true;
					}	
				case MONTHLY_NTH:
					if(
						r.getDayOfWeekMask().toString().equals(calRec.getDayOfWeekMask().toString())&&
						r.getInstance().toString().equals(calRec.getInstance().toString())){
						return true;	
					}
					return false;
				case YEARLY:
					if(r.getDayOfMonth().toString().equals(calRec.getDayOfMonth().toString()) &&
						r.getDuration().toString().equals(calRec.getDuration().toString()) &&
						r.getMonthOfYear().toString().equals(calRec.getMonthOfYear().toString())){
						return true;
					}
					return false;
				case YEARLY_NTH:
					if(r.getDayOfWeekMask().toString().equals(calRec.getDayOfWeekMask().toString()) &&
						r.getDuration().toString().equals(calRec.getDuration().toString()) &&
						r.getInstance().toString().equals(calRec.getInstance().toString()) &&
						r.getMonthOfYear().toString().equals(calRec.getMonthOfYear().toString())){
						return true;	
					}
					return false;
				default:
					return false;
				}
			}
		}
		return false;
	}

	/** 
	 * @uml.property name="events"
	 * @uml.associationEnd inverse="synch:ch.ess.cal.webservice.Events"
	 */
	private Events events;

	/** 
	 * Getter of the property <tt>events</tt>
	 * @return  Returns the events.
	 * @uml.property  name="events"
	 */
	public Events getEvents() {
		return events;
	}

	/** 
	 * Setter of the property <tt>events</tt>
	 * @param events  The events to set.
	 * @uml.property  name="events"
	 */
	public void setEvents(Events events) {
		this.events = events;
	}
	
	public Event mergeEvent(Event e1, Event e2){
		e1.setStartDate(e2.getStartDate());
		e1.setEndDate(e2.getEndDate());
		e1.setAllDay(e2.isAllDay());
		e1.setOldUid(e2.getUid());
		e1.setSensitivity(e2.getSensitivity());
		e1.setImportance(e2.getImportance());
		e1.setDescription(e2.getDescription());
		e1.setLocation(e2.getLocation());
		e1.setSubject(e2.getSubject());
		e1.setCreateDate(e2.getCreateDate());
		e1.setModificationDate(e2.getModificationDate());
		e1.setSequence(e2.getSequence());
		e1.setPriority(e2.getPriority());
		e1.setTransparency(e2.getTransparency());
		e1.setResource(e2.getResource());
		e1.setCategoryId(e2.getCategoryId());
		e1.setIsSynched(true);		
		return e1;
	}
	
	public Recurrence mergeRecurrencePattern(Recurrence r1, Recurrence r2){	
		r1.getEvent().setStartDate(r2.getEvent().getStartDate());
		r1.getEvent().setEndDate(r2.getEvent().getEndDate());
		r1.getEvent().setOldUid(r2.getEvent().getUid());
		r1.getEvent().setAllDay(r2.getEvent().isAllDay());
		r1.getEvent().setSensitivity(r2.getEvent().getSensitivity());
		r1.getEvent().setImportance(r2.getEvent().getImportance());
		r1.getEvent().setDescription(r2.getEvent().getDescription());
		r1.getEvent().setLocation(r2.getEvent().getLocation());
		r1.getEvent().setSubject(r2.getEvent().getSubject());
		r1.getEvent().setCreateDate(r2.getEvent().getCreateDate());
		r1.getEvent().setModificationDate(r2.getEvent().getModificationDate());
		r1.getEvent().setSequence(r2.getEvent().getSequence());
		r1.getEvent().setPriority(r2.getEvent().getPriority());
		r1.getEvent().setTransparency(r2.getEvent().getTransparency());
		r1.getEvent().setResource(r2.getEvent().getResource());
		r1.getEvent().setCategoryId(r2.getEvent().getCategoryId());

		r1.setActive(r2.isActive());
		r1.setExclude(r2.isExclude());
		r1.setInterval(r2.getInterval());
		r1.setDayOfWeekMask(r2.getDayOfWeekMask());
		r1.setDayOfMonth(r2.getDayOfMonth());
		r1.setMonthOfYear(r2.getMonthOfYear());
		r1.setInstance(r2.getInstance());
		r1.setOccurrences(r2.getOccurrences());
		r1.setDuration(r2.getDuration());
		r1.setAlways(r2.isAlways());
		r1.setUntil(r2.getUntil());
		r1.setType(r2.getType());
		r1.setPatternStartDate(r2.getPatternStartDate());
		r1.setPatternEndDate(r2.getPatternEndDate());
		r1.setRfcRule(r2.getRfcRule());
		r1.getEvent().setIsSynched(true);		
		return r1;
	}
}
