/*

Ext Gantt 3.0.1
Copyright(c) 2009-2015 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
 * German translations for the Gantt component
 *
 * NOTE: To change locale for month/day names you have to use the Ext JS language pack.
 */
Ext.define('Gnt.locale.De', {
    extend      : 'Sch.locale.Locale',
    requires    : 'Sch.locale.De',
    singleton   : true,

    constructor : function (config) {

        Ext.apply(this, {
            l10n        : {
                'Gnt.util.DurationParser' : {
                    unitsRegex : {
                        YEAR        : /^Jahr$|^Jahre$|^j/i,
                        QUARTER     : /^Quartal$|^Quartale$|^q/i,
                        MONTH       : /^Monat$|^Monate$|^m/i,
                        WEEK        : /^Woche$|^Wochen$|^w/i,
                        DAY         : /^Tag$|^Tage$|^t/i,
                        HOUR        : /^Stunde$|^Stunden$|^h/i,
                        MINUTE      : /^Minute$|^Minuten$|^min/i,
                        SECOND      : /^Sekunde$|^Sekunden$|^s/i,
                        MILLI       : /^Millisekunde$|^Millisekunden$|^ms/i
                    }
                },

                'Gnt.util.DependencyParser' : {
                    typeText    : {
                        SS  : 'AA',
                        SF  : 'AE',
                        FS  : 'EA',
                        FF  : 'EE'
                    }
                },

                'Gnt.field.ConstraintType' : {
                    none : 'Keine'
                },

                'Gnt.field.Duration' : {
                    invalidText : 'Ungültiger Wert'
                },

                'Gnt.field.Effort' : {
                    invalidText : 'Ungültiger Wert'
                },

                'Gnt.field.Percent' : {
                    invalidText : 'Ungültiger Wert'
                },

                'Gnt.feature.DependencyDragDrop' : {
                    fromText    : 'Von',
                    toText      : 'Bis',
                    startText   : 'Start',
                    endText     : 'Ende'
                },

                'Gnt.Tooltip' : {
                    startText       : 'Beginnt: ',
                    endText         : 'Endet: ',
                    durationText    : 'Dauer: '
                },

                'Gnt.plugin.TaskContextMenu' : {
                    taskInformation     : 'Aufgabeninformationen...',
                    newTaskText         : 'Neue Aufgabe',
                    deleteTask          : 'Lösche Aufgabe(n)',
                    editLeftLabel       : 'Bearbeite linke Bezeichnung',
                    editRightLabel      : 'Bearbeite rechte Bezeichnung',
                    add                 : 'Hinzufügen...',
                    deleteDependency    : 'Lösche Abhängigkeit...',
                    addTaskAbove        : 'Aufgabe vor',
                    addTaskBelow        : 'Aufgabe unter',
                    addMilestone        : 'Meilenstein',
                    addSubtask          : 'Unteraufgabe',
                    addSuccessor        : 'Nachfolger',
                    addPredecessor      : 'Vorgänger',
                    convertToMilestone  : 'Zu Meilenstein konvertieren',
                    convertToRegular    : 'Zu regulärer Aufgabe konvertieren',
                    splitTask           : 'Vorgang unterbrechen'
                },

                'Gnt.plugin.DependencyEditor' : {
                    fromText            : 'Von',
                    toText              : 'Zu',
                    typeText            : 'Typ',
                    lagText             : 'Verzögern',
                    endToStartText      : 'Ende-Anfang',
                    startToStartText    : 'Anfang-Anfang',
                    endToEndText        : 'Ende-Ende',
                    startToEndText      : 'Anfang-Ende'
                },

                'Gnt.widget.calendar.Calendar' : {
                    dayOverrideNameHeaderText : 'Name',
                    overrideName        : 'Name',
                    startDate           : 'Starddatum',
                    endDate             : 'Enddatum',
                    error               : 'Fehler',
                    dateText            : 'Datum',
                    addText             : 'Hinzufügen',
                    editText            : 'Ändern',
                    removeText          : 'Entfernen',
                    workingDayText      : 'Arbeitstag',
                    weekendsText        : 'Wochenende',
                    overriddenDayText   : 'Tag mit Überschneidung',
                    overriddenWeekText  : 'Woche mit Überschneidung',
                    workingTimeText     : 'Arbeitszeit',
                    nonworkingTimeText  : 'keine Arbeitszeit',
                    dayOverridesText    : 'Überschneidungen an Tagen',
                    weekOverridesText   : 'Überschneidungen in Wochen',
                    okText              : 'OK',
                    cancelText          : 'Abbrechen',
                    parentCalendarText  : 'Ursprungskalender',
                    noParentText        : 'Kein Ursprungskalender',
                    selectParentText    : 'Ursprungskalender wählen',
                    newDayName          : '[Ohne Name]',
                    calendarNameText    : 'Kalendername',
                    tplTexts            : {
                        tplWorkingHours : 'Arbeitszeit für',
                        tplIsNonWorking : 'ist keine Arbeitszeit',
                        tplOverride     : 'Überschneidung',
                        tplInCalendar   : 'in Kalender',
                        tplDayInCalendar: 'Standardtag in Kalender',
                        tplBasedOn      : 'Basierend auf'
                    },
                    overrideErrorText   : 'Für diesen Tag existiert bereits eine Überschneidung',
                    overrideDateError   : 'In dieser Woche existiert bereits eine Überschneidung für folgendes Datum: {0}',
                    startAfterEndError  : 'Startdatum größer als Enddatum',
                    weeksIntersectError : 'Wochenüberschneidungen sollten sich nicht überlagern'
                },

                'Gnt.widget.calendar.AvailabilityGrid' : {
                    startText           : 'Start',
                    endText             : 'Ende',
                    addText             : 'Hinzufügen',
                    removeText          : 'Entfernen',
                    error               : 'Fehler'
                },

                'Gnt.widget.calendar.DayEditor' : {
                    workingTimeText     : 'Arbeitszeit',
                    nonworkingTimeText  : 'Keine Arbeitszeit'
                },

                'Gnt.widget.calendar.WeekEditor' : {
                    defaultTimeText     : 'Standardzeit',
                    workingTimeText     : 'Arbeitszeit',
                    nonworkingTimeText  : 'Keine Arbeitszeit',
                    error               : 'Fehler',
                    noOverrideError     : "Wochenüberschneidungen beinhaltet nur 'default' Tage - kann nicht gespeichert werden."
                },

                'Gnt.widget.calendar.ResourceCalendarGrid' : {
                    name        : 'Name',
                    calendar    : 'Kalender'
                },

                'Gnt.widget.calendar.CalendarWindow' : {
                    ok      : 'Ok',
                    cancel  : 'Abbrechen'
                },

                'Gnt.widget.calendar.CalendarManager' : {
                    addText         : 'Hinzufügen',
                    removeText      : 'Entfernen',
                    add_child       : 'Untergeordneten Kalender hinzufügen',
                    add_node        : 'Kalender hinzufügen',
                    add_sibling     : 'Kalender hinzufügen',
                    remove          : 'Entfernen',
                    calendarName    : 'Kalender',
                    confirm_action  : 'Aktion bestätigen',
                    confirm_message : 'Der Kalender hat ungespeicherte Änderungen. Möchten Sie Ihre Änderungen speichern?'
                },

                'Gnt.widget.calendar.CalendarManagerWindow' : {
                    ok      : 'Ok',
                    cancel  : 'Abbrechen'
                },

                'Gnt.field.Assignment' : {
                    cancelText : 'Abbrechen',
                    closeText  : 'Speichern und schliessen'
                },

                'Gnt.column.AssignmentUnits' : {
                    text : 'Einheiten'
                },

                'Gnt.column.Duration' : {
                    text : 'Dauer'
                },

                'Gnt.column.Effort' : {
                    text : 'Aufwand'
                },

                'Gnt.column.EndDate' : {
                    text : 'Fertig stellen'
                },

                'Gnt.column.PercentDone' : {
                    text : '% erledigt'
                },

                'Gnt.column.ResourceAssignment' : {
                    text : 'Zugwiesene Resourcen'
                },

                'Gnt.column.ResourceName' : {
                    text : 'Resourcenname'
                },

                'Gnt.column.Rollup' : {
                    text : 'Rollup',
                    yes  : 'Ja',
                    no   : 'Nein'
                },

                'Gnt.column.SchedulingMode' : {
                    text : 'Modus'
                },

                'Gnt.column.Predecessor' : {
                    text : 'Vorgänger'
                },

                'Gnt.column.Successor' : {
                    text : 'Nachfolger'
                },

                'Gnt.column.StartDate' : {
                    text : 'Anfang'
                },

                'Gnt.column.WBS' : {
                    text : 'WBS'
                },

                'Gnt.column.Sequence' : {
                    text : '#'
                },

                'Gnt.column.Calendar' : {
                    text : 'Kalender'
                },

                'Gnt.widget.taskeditor.TaskForm' : {
                    taskNameText            : 'Name',
                    durationText            : 'Dauer',
                    datesText               : 'Daten',
                    baselineText            : 'Baseline',
                    startText               : 'Start',
                    finishText              : 'Ende',
                    percentDoneText         : 'Abgeschlossen in Prozent',
                    baselineStartText       : 'Start',
                    baselineFinishText      : 'Ende',
                    baselinePercentDoneText : 'Prozent abgeschlossen',
                    effortText              : 'Aufwand',
                    invalidEffortText       : 'Ungültiger Wert für Aufwand',
                    calendarText            : 'Kalender',
                    schedulingModeText      : 'Planungsmodus',
                    rollupText              : 'Rollup',
                    // To translate
                    wbsCodeText             : 'PSP code',
                    "Constraint Type"       : 'Einschränkung',
                    "Constraint Date"       : 'Einschränkung Datum'
                },

                'Gnt.widget.DependencyGrid' : {
                    idText                      : 'ID',
                    snText                      : 'SN',
                    taskText                    : 'Aufgabenname',
                    blankTaskText               : 'Bitte Aufgabe wählen',
                    invalidDependencyText       : 'Ungültige Abhängigkeit',
                    parentChildDependencyText   : 'Abhängigkeit zwischen Abhängigkeit und Ursprung',
                    duplicatingDependencyText   : 'Doppelte Abhängigkeit gefunden',
                    transitiveDependencyText    : 'Transitive Abhängigkeit',
                    cyclicDependencyText        : 'Zirkuläre Referenz',
                    typeText                    : 'Typ',
                    lagText                     : 'Verzögern',
                    clsText                     : 'CSS class',
                    endToStartText              : 'Finish-To-Start',
                    startToStartText            : 'Start-To-Start',
                    endToEndText                : 'Finish-To-Finish',
                    startToEndText              : 'Start-To-Finish'
                },

                'Gnt.widget.AssignmentEditGrid' : {
                    confirmAddResourceTitle : 'Bestätigen',
                    confirmAddResourceText  : 'Resource &quot;{0}&quot; Nicht in der Liste gefunden. Möchten Sie sie hinzufügen?',
                    noValueText             : 'Bitte eine Resource für die Zuordnung auswählen',
                    noResourceText          : 'Resource &quot;{0}&quot; nicht in der Liste gefunden'
                },

                'Gnt.widget.taskeditor.TaskEditor' : {
                    generalText         : 'Generell',
                    resourcesText       : 'Resourcen',
                    dependencyText      : 'Vorgänger',
                    addDependencyText   : 'Neu hinzufügen',
                    dropDependencyText  : 'Entfernen',
                    notesText           : 'Notizen',
                    advancedText        : 'Fortgeschritten',
                    addAssignmentText   : 'Neu hinzufügen',
                    dropAssignmentText  : 'Entfernen'
                },

                'Gnt.plugin.TaskEditor' : {
                    title           : 'Aufgabeninformationen',
                    alertCaption    : 'Information',
                    alertText       : 'Bitte die markierten Fehler zum Speichern beheben',
                    okText          : 'Ok',
                    cancelText      : 'Abbrechen'
                },

                'Gnt.field.EndDate' : {
                    endBeforeStartText : 'Das Enddatum liegt vor dem Startdatum'
                },

                'Gnt.column.Note'   : {
                    text            : 'Notiz'
                },

                'Gnt.column.AddNew' : {
                    text            : 'Neue Spalte hinzufügen...'
                },

                'Gnt.column.EarlyStartDate' : {
                    text            : 'Frühes Startdatum'
                },

                'Gnt.column.EarlyEndDate' : {
                    text            : 'Frühes Ende'
                },

                'Gnt.column.LateStartDate' : {
                    text            : 'Später Start'
                },

                'Gnt.column.LateEndDate' : {
                    text            : 'Spätes Ende'
                },

                'Gnt.field.Calendar' : {
                    calendarNotApplicable : 'Aufgabenkalender hat keine Überlappung mit den Kalendern der zugwiesenen Resourcen'
                },

                'Gnt.column.Slack' : {
                    text            : 'Slack'
                },

                'Gnt.column.Name' : {
                    text            : 'Vorgangsname'
                },

                'Gnt.column.BaselineStartDate'   : {
                    text            : 'Baseline Start'
                },

                'Gnt.column.BaselineEndDate'   : {
                    text            : 'Baseline Ende'
                },

                'Gnt.column.Milestone'   : {
                    text            : 'Meilenstein'
                },

                'Gnt.field.Milestone'   : {
                    yes             : 'Ja',
                    no              : 'Nein'
                },

                'Gnt.field.Dependency'  : {
                    invalidFormatText       : 'Unzulässiges Abhängigkeitsformat',
                    invalidDependencyText   : 'Unzulässige Abhängigkeit gefunden; bitte stellen Sie sicher, dass keine Zirkelbezüge zwischen Ihren Tasks entstehen',
                    invalidDependencyType   : 'Unzulässiger Abhängigkeitstyp {0}. Zulässige Werte sind: {1}.'
                },

                'Gnt.constraint.Base' : {
                    name                                : "Eine Einschränkung",
                    "Remove the constraint"             : "Einschränkung aufheben",
                    "Cancel the change and do nothing"  : "Abbrechen der Änderung"
                },

                'Gnt.constraint.FinishNoEarlierThan' : {
                    name                                : "Ende nicht früher als",
                    // {0} constraint date
                    "Move the task to finish on {0}"    : "Verschiebe den Task um um {0} zu enden"
                },

                "Gnt.constraint.FinishNoLaterThan" : {
                    name                                : "Ende nicht später als",
                    // {0} constraint date
                    "Move the task to finish on {0}"    : "Verschiebe den Task um um {0} zu enden"
                },

                "Gnt.constraint.MustFinishOn" : {
                    name                                : "Muss enden am",
                    // {0} constraint date
                    "Move the task to finish on {0}" : "Verschiebe den Task um um {0} zu enden"
                },

                "Gnt.constraint.MustStartOn" : {
                    name                                : "Muss anfangen am",
                    // {0} constraint date
                    "Move the task to start at {0}" : "Verschiebe den Task um um {0} anzufangen"
                },

                "Gnt.constraint.StartNoEarlierThan" : {
                    name                                : "Anfang nicht früher als ",
                    // {0} constraint date
                    "Move the task to start at {0}" : "Verschiebe den Task um um {0} anzufangen"
                },

                "Gnt.constraint.StartNoLaterThan" : {
                    name                                : "Anfang nicht später als ",
                    // {0} constraint date
                    "Move the task to start at {0}" : "Verschiebe den Task um um {0} anzufangen"
                },

                "Gnt.column.ConstraintDate" : {
                    text : "Datums-Beschränkung"
                },

                "Gnt.column.ConstraintType" : {
                    text : "Einschränkung"
                },

                "Gnt.widget.ConstraintResolutionForm" : {
                    dateFormat           : "Y-m-d",
                    "OK"                 : 'OK',
                    "Cancel"             : 'Abbrechen',
                    "Resolution options" : "Auflösungsoptionen",
                    "Don't ask again"    : "Nicht mehr fragen",
                    // {0} task name, {1} constraint name
                    "Task {0} violates constraint {1}"     : "Task {0} verletzt Bedingung {1}",
                    // {0} task name, {1} constraint name, {2} constraint date
                    "Task {0} violates constraint {1} {2}" : "Task \"{0}\" verletzt die Bedingung {1}, {2}"
                },

                "Gnt.widget.ConstraintResolutionWindow" : {
                    "Constraint violation" : "Gültigkeitsbereichs-Verletzung"
                }
            }
        });

        this.callParent(arguments);
    }
});
