/*

Ext Gantt 3.0.1
Copyright(c) 2009-2015 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
Ext.define('Gnt.locale.Pl', {
    extend      : 'Sch.locale.Locale',
    requires    : 'Sch.locale.Pl',
    singleton   : true,

    constructor : function (config) {

        Ext.apply(this, {
            l10n        : {
                'Gnt.util.DurationParser' : {
                    unitsRegex : {
                        MILLI       : /^ms$|^mil/i,
                        SECOND      : /^s$|^sek/i,
                        MINUTE      : /^min/i,
                        HOUR        : /^g$|^godzin/i,
                        DAY         : /^d$|^dni|^dzie/i,
                        WEEK        : /^t$|^tydzie|^tygodni/i,
                        MONTH       : /^m$|^miesi/i,
                        QUARTER     : /^kw$|^kwarta/i,
                        YEAR        : /^r$|^rok$|^lat/i
                    }
                },

                'Gnt.util.DependencyParser' : {
                    typeText    : {
                        SS  : 'RR',
                        SF  : 'RZ',
                        FS  : 'ZR',
                        FF  : 'ZZ'
                    }
                },

                'Gnt.field.ConstraintType' : {
                    none : 'Brak'
                },

                'Gnt.field.Duration' : {
                    invalidText : "Nieprawidłowa wartość czasu"
                },

                'Gnt.field.Effort' : {
                    invalidText : 'Niepoprawna wartość pracy'
                },

                'Gnt.field.Percent' : {
                    invalidText : 'Niepoprawna wartość procentowa'
                },

                'Gnt.feature.DependencyDragDrop' : {
                    fromText    : 'Od',
                    toText      : 'Do',
                    startText   : 'Początek',
                    endText     : 'Koniec'
                },

                'Gnt.Tooltip' : {
                    startText       : 'Rozpoczyna się: ',
                    endText         : 'Kończy: ',
                    durationText    : 'Trwa: '
                },

                'Gnt.plugin.TaskContextMenu' : {
                    taskInformation     : 'Informacje o zadaniu...',
                    newTaskText         : 'Nowe zadanie',
                    deleteTask          : 'Usuń zadanie(a)',
                    editLeftLabel       : 'Edytuj lewą etykietę',
                    editRightLabel      : 'Edytuj prawą etykietę',
                    add                 : 'Dodaj...',
                    deleteDependency    : 'Usuń zależność...',
                    addTaskAbove        : 'Zadanie wyżej',
                    addTaskBelow        : 'Zadanie poniżej',
                    addMilestone        : 'Kamień milowy',
                    addSubtask          : 'Pod-zadanie',
                    addSuccessor        : 'Następce',
                    addPredecessor      : 'Poprzednika',
                    convertToMilestone  : 'Konwertuj na kamień milowy',
                    convertToRegular    : 'Konwertuj na normalne zadanie',
                    splitTask           : 'Podziel zadanie'
                },

                'Gnt.plugin.DependencyEditor' : {
                    fromText            : 'Od',
                    toText              : 'Do',
                    typeText            : 'Typ',
                    lagText             : 'Opóźnienie',
                    endToStartText      : 'Koniec-Do-Początku',
                    startToStartText    : 'Początek-Do-Początku',
                    endToEndText        : 'Koniec-Do-Końca',
                    startToEndText      : 'Początek-Do-Końca'
                },

                'Gnt.widget.calendar.Calendar' : {
                    dayOverrideNameHeaderText : 'Nazwa',
                    overrideName        : 'Nazwa',
                    startDate           : 'Początek',
                    endDate             : 'Koniec',
                    error               : 'Błąd',
                    dateText            : 'Data',
                    addText             : 'Dodaj',
                    editText            : 'Edytuj',
                    removeText          : 'Usuń',
                    workingDayText      : 'Dzień pracujący',
                    weekendsText        : 'Weekend',
                    overriddenDayText   : 'Nadpisany dzień',
                    overriddenWeekText  : 'Nadpisany tydzień',
                    workingTimeText     : 'Czas pracy',
                    nonworkingTimeText  : 'Czas niepracujący',
                    dayOverridesText    : 'Nadpisane dni',
                    weekOverridesText   : 'Nadpisane tygodnie',
                    okText              : 'OK',
                    cancelText          : 'Anuluj',
                    parentCalendarText  : 'Kalendarz-rodzic',
                    noParentText        : 'Brak rodzica',
                    selectParentText    : 'Wybierz rodzica',
                    newDayName          : '[Bez nazwy]',
                    calendarNameText    : 'Nazwa kalendarza',
                    tplTexts            : {
                        tplWorkingHours : 'Godziny pracujące dla',
                        tplIsNonWorking : 'jest niepracujący',
                        tplOverride     : 'nadpisane',
                        tplInCalendar   : 'w kalendarzu',
                        tplDayInCalendar: 'normalny dzień w kalendarzu',
                        tplBasedOn      : 'W oparciu'
                    },
                    overrideErrorText   : 'Ten dzień został juz nadpisany',
                    overrideDateError   : 'Istnieje już nadpisany tydzień dla tej daty: {0}',
                    startAfterEndError  : 'Data początku musi być wcześniejsza od daty końca',
                    weeksIntersectError : 'Nadpisania tygodnia nie powinny się nakładać'
                },

                'Gnt.widget.calendar.AvailabilityGrid' : {
                    startText          : 'Początek',
                    endText            : 'Koniec',
                    addText            : 'Dodaj',
                    removeText         : 'Usuń',
                    error              : 'Błąd'
                },

                'Gnt.widget.calendar.DayEditor' : {
                    workingTimeText    : 'Czas pracujący',
                    nonworkingTimeText : 'Czas niepracujący'
                },

                'Gnt.widget.calendar.WeekEditor' : {
                    defaultTimeText    : 'Domyślny czas',
                    workingTimeText    : 'Czas pracujący',
                    nonworkingTimeText : 'Czas niepracujący',
                    error              : 'Błąd',
                    noOverrideError    : "Nadpisania tygodnia zawierają tylko 'domyślne' dni - nie można zapisać"
                },

                'Gnt.widget.calendar.ResourceCalendarGrid' : {
                    name        : 'Nazwa',
                    calendar    : 'Kalendarz'
                },

                'Gnt.widget.calendar.CalendarWindow' : {
                    ok      : 'Ok',
                    cancel  : 'Anuluj'
                },

                'Gnt.widget.calendar.CalendarManager' : {
                    addText         : 'Dodaj',
                    removeText      : 'Usuń',
                    add_child       : 'Dodaj kalendarz podrzędny',
                    add_node        : 'Dodaj kalendarz',
                    add_sibling     : 'Dodaj kalendarz',
                    remove          : 'Usuń',
                    calendarName    : 'Kalendarz',
                    confirm_action  : 'Potwierdź akcję',
                    confirm_message : 'Kalendarz ma niezapisane zmiany. Czy chcesz je zachować?'
                },

                'Gnt.widget.calendar.CalendarManagerWindow' : {
                    ok     : 'Zastosuj zmiany',
                    cancel : 'Anuluj'
                },


                'Gnt.field.Assignment' : {
                    cancelText : 'Anuluj',
                    closeText  : 'Zapisz i zamknij'
                },

                'Gnt.column.AssignmentUnits' : {
                    text : 'Jednostki'
                },

                'Gnt.column.Duration' : {
                    text : 'Czas trwania'
                },

                'Gnt.column.Effort' : {
                    text : 'Praca'
                },

                'Gnt.column.EndDate' : {
                    text : 'Koniec'
                },

                'Gnt.column.PercentDone' : {
                    text : '% Wykonania'
                },

                'Gnt.column.ResourceAssignment' : {
                    text : 'Przypisane zasoby'
                },

                'Gnt.column.ResourceName' : {
                    text : 'Nazwa zasobu'
                },

                'Gnt.column.Rollup' : {
                    text : 'Rzutowanie',
                    yes  : 'Tak',
                    no   : 'Nie'
                },

                'Gnt.column.SchedulingMode' : {
                    text : 'Tryb'
                },

                'Gnt.column.Predecessor' : {
                    text : 'Poprzednicy'
                },

                'Gnt.column.Successor' : {
                    text : 'Następcy'
                },

                'Gnt.column.StartDate' : {
                    text : 'Początek'
                },

                'Gnt.column.WBS' : {
                    text : '#'
                },

                'Gnt.column.Sequence' : {
                    text : '#'
                },

                'Gnt.column.Calendar' : {
                    text : 'Kalendarz'
                },

                'Gnt.widget.taskeditor.TaskForm' : {
                    taskNameText            : 'Nazwa',
                    durationText            : 'Długość',
                    datesText               : 'Daty',
                    baselineText            : 'Baseline',
                    startText               : 'Początek',
                    finishText              : 'Koniec',
                    percentDoneText         : '% Wykonano',
                    baselineStartText       : 'Początek',
                    baselineFinishText      : 'Koniec',
                    baselinePercentDoneText : '% Wykonano',
                    effortText              : 'Praca',
                    invalidEffortText       : 'Niepoprawna wartość pracy',
                    calendarText            : 'Kalendarz',
                    schedulingModeText      : 'Tryb kalendarza',
                    rollupText              : 'Rzutowanie',
                    // To translate
                    wbsCodeText             : 'Kod WBS',
                    "Constraint Type"       : 'Typ ograniczenia',
                    "Constraint Date"       : 'Data ograniczenia'
                },

                'Gnt.widget.DependencyGrid' : {
                    idText                      : 'ID',
                    snText                      : 'SN',
                    taskText                    : 'Nazwa zadania',
                    blankTaskText               : 'Proszę wybrać zadanie',
                    invalidDependencyText       : 'Nieprawidłowa zależność',
                    parentChildDependencyText   : 'Zależność pomiędzy dzieckiem a rodzicem znaleziona',
                    duplicatingDependencyText   : 'Zduplikowana zależność znaleziona',
                    transitiveDependencyText    : 'Przechodnia zależność',
                    cyclicDependencyText        : 'Cykliczna zależność',
                    typeText                    : 'Typ',
                    lagText                     : 'Opóźnienie',
                    clsText                     : 'Klasa CSS',
                    endToStartText              : 'Koniec-Do-Początku',
                    startToStartText            : 'Początek-Do-Początku',
                    endToEndText                : 'Koniec-Do-Końca',
                    startToEndText              : 'Początek-Do-Końca'
                },

                'Gnt.widget.AssignmentEditGrid' : {
                    confirmAddResourceTitle : 'Potwierdz',
                    confirmAddResourceText  : 'Nie ma jeszcze zasobu &quot;{0}&quot; . Czy chcesz go dodać?',
                    noValueText             : 'Proszę wybrać zasób do przypisania',
                    noResourceText          : 'Brak zasobu &quot;{0}&quot;'
                },

                'Gnt.widget.taskeditor.TaskEditor' : {
                    generalText         : 'Ogólne',
                    resourcesText       : 'Zasoby',
                    dependencyText      : 'Poprzednicy',
                    addDependencyText   : 'Dodaj',
                    dropDependencyText  : 'Usuń',
                    notesText           : 'Notatki',
                    advancedText        : 'Zaawansowane',
                    addAssignmentText   : 'Dodaj',
                    dropAssignmentText  : 'Usuń'
                },

                'Gnt.plugin.TaskEditor' : {
                    title           : 'Informacje o zadaniu',
                    alertCaption    : 'Informacje',
                    alertText       : 'Proszę poprawić zaznaczone błędy aby zapisać zmiany',
                    okText          : 'Ok',
                    cancelText      : 'Anuluj'
                },

                'Gnt.field.EndDate' : {
                    endBeforeStartText : 'Data końca jest przed datą początku'
                },

                'Gnt.column.Note'   : {
                    text            : 'Notatki'
                },

                'Gnt.column.AddNew' : {
                    text            : 'Dodaj kolumnę...'
                },

                'Gnt.column.EarlyStartDate' : {
                    text            : 'Wczesny Start'
                },

                'Gnt.column.EarlyEndDate' : {
                    text            : 'Wczesny Koniec'
                },

                'Gnt.column.LateStartDate' : {
                    text            : 'Późny Start'
                },

                'Gnt.column.LateEndDate' : {
                    text            : 'Późny Koniec'
                },

                'Gnt.field.Calendar' : {
                    calendarNotApplicable : 'Kalendarz zadań nie nakłada się z przypisanymi kalendarzami zasobów'
                },

                'Gnt.column.Slack' : {
                    text            : 'Wolne'
                },

                'Gnt.column.Name' : {
                    text            : 'Nazwa zadania'
                },

                'Gnt.column.BaselineStartDate'   : {
                    text            : 'Data rozpoczęcia lini bazowej'
                },

                'Gnt.column.BaselineEndDate'   : {
                    text            : 'Data zakończenia lini bazowej'
                },

                'Gnt.column.Milestone'   : {
                    text            : 'Kamień milowy'
                },

                'Gnt.field.Milestone'   : {
                    yes             : 'Tak',
                    no              : 'Nie'
                },

                'Gnt.field.Dependency'  : {
                    invalidFormatText       : 'Nieprawidłowy format zależności',
                    invalidDependencyText   : 'Zaleziono nieprawidłową zależność, proszę upewnij się że nie masz zapętleń pomiędzy zadaniami',
                    invalidDependencyType   : 'Nieprawidłowy typ zależności {0}. Dozwolone wartości to: {1}.'
                },

                'Gnt.constraint.Base' : {
                    name                                : "Ograniczenie",
                    "Remove the constraint"             : "Usunąć ograniczenie",
                    "Cancel the change and do nothing"  : "Anuluj zmiany"
                },

                'Gnt.constraint.FinishNoEarlierThan' : {
                    name                                : "Zakończ nie wcześniej niż",
                    // {0} constraint date
                    "Move the task to finish on {0}"    : "Przesuń zadanie aby kończyło się {0}"
                },

                "Gnt.constraint.FinishNoLaterThan" : {
                    name                                : "Zakończ nie później niż ",
                    // {0} constraint date
                    "Move the task to finish on {0}" : "Przesuń zadanie aby kończyło się {0}"
                },

                "Gnt.constraint.MustFinishOn" : {
                    name                                : "Musi zakończyć się",
                    // {0} constraint date
                    "Move the task to finish on {0}" : "Przesuń zadanie aby kończyło się {0}"
                },

                "Gnt.constraint.MustStartOn" : {
                    name                                : "Musi rozpocząć się",
                    // {0} constraint date
                    "Move the task to start at {0}" : "Przesuń zadanie aby zaczynało się {0}"
                },

                "Gnt.constraint.StartNoEarlierThan" : {
                    name                            : "Rozpocznij nie wcześniej niż ",
                    // {0} constraint date
                    "Move the task to start at {0}" : "Przesuń zadanie aby zaczynało się {0}"
                },

                "Gnt.constraint.StartNoLaterThan" : {
                    name                            : "Rozpocznij nie później niż ",
                    // {0} constraint date
                    "Move the task to start at {0}" : "Przesuń zadanie aby zaczynało się {0}"
                },

                "Gnt.column.ConstraintDate" : {
                    text : "Data ograniczenia"
                },

                "Gnt.column.ConstraintType" : {
                    text : "Typ ograniczenia"
                },

                "Gnt.widget.ConstraintResolutionForm" : {
                    dateFormat           : "Y-m-d",
                    "OK"                 : 'OK',
                    "Cancel"             : 'Anuluj',
                    "Resolution options" : "Opcje rozdzielczości",
                    "Don't ask again"    : "Nie pytaj ponownie",
                    // {0} task name, {1} constraint name
                    "Task {0} violates constraint {1}"     : "Zadanie \"{0}\" narusza ograniczenie ",
                    // {0} task name, {1} constraint name, {2} constraint date
                    "Task {0} violates constraint {1} {2}" : "Zadanie \"{0}\" narusza ograniczenie {1} {2}"
                },

                "Gnt.widget.ConstraintResolutionWindow" : {
                    "Constraint violation" : "Naruszenie ograniczenia"
                }
            }
        });

        this.callParent(arguments);
    }
});
