package ch.ess.cal.webservice;

import java.util.List;

import ch.ess.cal.model.Event;
import ch.ess.cal.model.EventCategory;
import ch.ess.cal.model.Recurrence;

public class XMLFactory {
	public static String createNormalXML(List<Event> normalUpdate, List<Event> normalInsert, List<Event> normalDelete){
		String xml = "";
		if(!normalUpdate.isEmpty() || !normalInsert.isEmpty() || !normalDelete.isEmpty()){
			xml = "<SynchroCal.WebCalEvents assembly=\"SynchroCal\"><e>";
			
			if(!normalUpdate.isEmpty()){
				for (Event event : normalUpdate) {
					xml += "<SynchroCal.Event assembly=\"SynchroCal\">";
					xml += "<toUpdate>true</toUpdate>";
					xml += "<toDelete>false</toDelete>";
					xml += createNormalEventFields(event);
					xml += "</SynchroCal.Event>";
				}
			}
			if(!normalInsert.isEmpty()){
				for (Event event : normalInsert){
					xml += "<SynchroCal.Event assembly=\"SynchroCal\">";
					xml += "<toUpdate>false</toUpdate>";
					xml += "<toDelete>false</toDelete>";
					xml += createNormalEventFields(event);
					xml += "</SynchroCal.Event>";
				}
			}
			if(!normalDelete.isEmpty()){
				for (Event event : normalDelete){
					xml += "<SynchroCal.Event assembly=\"SynchroCal\">";
					xml += "<toUpdate>false</toUpdate>";
					xml += "<toDelete>true</toDelete>";
					xml += createNormalEventFields(event);
					xml += "</SynchroCal.Event>";
				}
			}
			xml += "</e></SynchroCal.WebCalEvents>";
		
		}
		return xml;	
	}
	
	public static String createRecurXML(List<Object[]> recUpdate, List<Object[]> recInsert, List<Object[]> recDelete){
		String xml = "";
		if(!recUpdate.isEmpty() || !recInsert.isEmpty() || !recDelete.isEmpty()){
			xml = "<SynchroCal.WebCalEvents assembly=\"SynchroCal\"><r>";
			
			if(!recUpdate.isEmpty()){
				for (Object[] o : recUpdate) {
					Recurrence rec = (Recurrence) o[0];
					Event e = (Event)o[1];
					xml += "<SynchroCal.Recurrence assembly=\"SynchroCal\">";
					xml += "<toUpdate>true</toUpdate>";
					xml += "<toDelete>false</toDelete>";
					xml += createNormalEventFields(e);
					xml += createRecEventFields(rec);
					
					xml += "</SynchroCal.Recurrence>";
				}
			}
			
			if(!recInsert.isEmpty()){
				for (Object[] o : recInsert) {
					Recurrence rec = (Recurrence) o[0];
					Event e = (Event) o[1];
					xml += "<SynchroCal.Recurrence assembly=\"SynchroCal\">";
					xml += "<toUpdate>false</toUpdate>";
					xml += "<toDelete>false</toDelete>";
					xml += createNormalEventFields(e);
					xml += createRecEventFields(rec);
					xml += "</SynchroCal.Recurrence>";
				}
			}
			
			if(!recDelete.isEmpty()){
				for (Object[] o : recDelete) {
					Recurrence rec = (Recurrence) o[0];
					Event e = (Event) o[1];
					xml += "<SynchroCal.Recurrence assembly=\"SynchroCal\">";
					xml += "<toUpdate>false</toUpdate>";
					xml += "<toDelete>true</toDelete>";
					xml += createNormalEventFields(e);
					xml += createRecEventFields(rec);
					xml += "</SynchroCal.Recurrence>";
				}
			}
			xml += "</r></SynchroCal.WebCalEvents>";
		}
		return xml;	
	}
	
	private static String createNormalEventFields(Event event){
		String xml ="";
		
		if(event.getStartDate() != null) xml+= "<startDate>"+event.getStartDate()+"</startDate>";
		if(event.getEndDate() != null) xml+= "<endDate>"+event.getEndDate()+"</endDate>";
		if(event.getOldUid() != null) xml+= "<oldUid>"+event.getOldUid()+"</oldUid>";
		if(event.getModificationDate() != null) xml+= "<modificationDate>"+event.getModificationDate()+"</modificationDate>";
		if(event.isAllDay() != null) xml+= "<allDay>"+event.isAllDay()+"</allDay>";
		if(event.getSensitivity() != null) xml+= "<sensitivity>"+event.getSensitivity()+"</sensitivity>";
		if(event.getImportance() != null) xml+= "<importance>"+event.getImportance()+"</importance>";
		if(event.getLocation() != null) xml+= "<location>"+event.getLocation()+"</location>";
		if(event.getSubject() != null) xml+= "<subject>"+event.getSubject()+"</subject>";
		if(event.getDescription() != null) xml+= "<description>"+event.getDescription()+"</description>";
		if(event.getUid() != null) xml+= "<uid>"+event.getUid()+"</uid>";
		
		for (EventCategory c : event.getEventCategories()) {
			if(c.getCategory().getId() != null) xml+= "<categoryId>"+c.getCategory().getId()+"</categoryId>";
		}
		return xml;
	}
	
	private static String createRecEventFields(Recurrence rec){
		String xml ="";
		
		if(rec.getType() != null) xml += "<patternType>"+rec.getType()+"</patternType>";
		if(rec.isAlways() != null) xml += "<always>"+rec.isAlways()+"</always>";
		
		if(rec.getType().name().equals("DAILY")){
			if(rec.getDuration() != null) xml += "<duration>"+rec.getDuration()+"</duration>";
			if(rec.getOccurrences() != null) xml += "<occurrences>"+rec.getOccurrences()+"</occurrences>";
			if(rec.getInterval() != null) xml += "<interval>"+rec.getInterval()+"</interval>";
			if(rec.getPatternStartDate() != null) xml += "<patternStartDate>"+rec.getPatternStartDate()+"</patternStartDate>";
			if(rec.getPatternEndDate() != null) xml += "<patternEndDate>"+rec.getPatternEndDate()+"</patternEndDate>";
		}else if(rec.getType().name().equals("WEEKLY")){
			if(rec.getDayOfWeekMask() != null) xml += "<dayOfWeekMask>"+rec.getDayOfWeekMask()+"</dayOfWeekMask>";
			if(rec.getDuration() != null) xml += "<duration>"+rec.getDuration()+"</duration>";
			if(rec.getOccurrences() != null) xml += "<occurrences>"+rec.getOccurrences()+"</occurrences>";
			if(rec.getInterval() != null) xml += "<interval>"+rec.getInterval()+"</interval>";
			if(rec.getPatternStartDate() != null) xml += "<patternStartDate>"+rec.getPatternStartDate()+"</patternStartDate>";
			if(rec.getPatternEndDate() != null) xml += "<patternEndDate>"+rec.getPatternEndDate()+"</patternEndDate>";
		}else if(rec.getType().name().equals("MONTHLY")){
			if(rec.getDayOfMonth() != null) xml += "<dayOfMonth>"+rec.getDayOfMonth()+"</dayOfMonth>";
			if(rec.getDuration()!= null) xml += "<duration>"+rec.getDuration()+"</duration>";
			if(rec.getOccurrences() != null) xml += "<occurrences>"+rec.getOccurrences()+"</occurrences>";
			if(rec.getInterval() != null) xml += "<interval>"+rec.getInterval()+"</interval>";
			if(rec.getPatternStartDate() != null) xml += "<patternStartDate>"+rec.getPatternStartDate()+"</patternStartDate>";
			if(rec.getPatternEndDate() != null) xml += "<patternEndDate>"+rec.getPatternEndDate()+"</patternEndDate>";
		}else if(rec.getType().name().equals("MONTHLY_NTH")){
			if(rec.getDayOfWeekMask() != null) xml += "<dayOfWeekMask>"+rec.getDayOfWeekMask()+"</dayOfWeekMask>";
			if(rec.getDuration() != null) xml += "<duration>"+rec.getDuration()+"</duration>";
			if(rec.getOccurrences() != null) xml += "<occurrences>"+rec.getOccurrences()+"</occurrences>";
			if(rec.getInterval() != null) xml += "<interval>"+rec.getInterval()+"</interval>";
			if(rec.getInstance() != null) xml += "<instance>"+rec.getInstance()+"</instance>";
			if(rec.getPatternStartDate()!= null) xml += "<patternStartDate>"+rec.getPatternStartDate()+"</patternStartDate>";
			if(rec.getPatternEndDate() != null) xml += "<patternEndDate>"+rec.getPatternEndDate()+"</patternEndDate>";
		}else if(rec.getType().name().equals("YEARLY")){
			if(rec.getDayOfMonth() != null) xml += "<dayOfMonth>"+rec.getDayOfMonth()+"</dayOfMonth>";
			if(rec.getMonthOfYear() != null) xml += "<monthOfYear>"+rec.getMonthOfYear()+"</monthOfYear>";
			if(rec.getDuration() != null) xml += "<duration>"+rec.getDuration()+"</duration>";
			if(rec.getOccurrences() != null) xml += "<occurrences>"+rec.getOccurrences()+"</occurrences>";
			if(rec.getInterval()!= null) xml += "<interval>"+rec.getInterval()+"</interval>";
			if(rec.getPatternStartDate()!= null) xml += "<patternStartDate>"+rec.getPatternStartDate()+"</patternStartDate>";
			if(rec.getPatternEndDate() != null) xml += "<patternEndDate>"+rec.getPatternEndDate()+"</patternEndDate>";
		}else if(rec.getType().name().equals("YEARLY_NTH")){
			if(rec.getDayOfWeekMask() != null) xml += "<dayOfWeekMask>"+rec.getDayOfWeekMask()+"</dayOfWeekMask>";
			if(rec.getDuration() != null) xml += "<duration>"+rec.getDuration()+"</duration>";
			if(rec.getOccurrences() != null) xml += "<occurrences>"+rec.getOccurrences()+"</occurrences>";
			if(rec.getInterval() != null) xml += "<interval>"+rec.getInterval()+"</interval>";
			if(rec.getMonthOfYear() != null) xml += "<monthOfYear>"+rec.getMonthOfYear()+"</monthOfYear>";
			if(rec.getInstance() != null) xml += "<instance>"+rec.getInstance()+"</instance>";
			if(rec.getPatternStartDate() != null) xml += "<patternStartDate>"+rec.getPatternStartDate()+"</patternStartDate>";
			if(rec.getPatternEndDate() != null) xml += "<patternEndDate>"+rec.getPatternEndDate()+"</patternEndDate>";
		}else{
			return "";
		}
		
		return xml;
	}
}
