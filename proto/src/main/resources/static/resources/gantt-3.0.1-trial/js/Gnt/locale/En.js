/*

Ext Gantt 3.0.1
Copyright(c) 2009-2015 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
Ext.define('Gnt.locale.En', {
    extend    : 'Sch.locale.Locale',
    requires  : 'Sch.locale.En',
    singleton : true,

    constructor : function (config) {

        Ext.apply(this, {
            l10n : {
                'Gnt.util.DurationParser' : {
                    unitsRegex : {
                        MILLI   : /^ms$|^mil/i,
                        SECOND  : /^s$|^sec/i,
                        MINUTE  : /^m$|^min/i,
                        HOUR    : /^h$|^hr$|^hour/i,
                        DAY     : /^d$|^day/i,
                        WEEK    : /^w$|^wk|^week/i,
                        MONTH   : /^mo|^mnt/i,
                        QUARTER : /^q$|^quar|^qrt/i,
                        YEAR    : /^y$|^yr|^year/i
                    }
                },

                'Gnt.util.DependencyParser' : {
                    typeText : {
                        SS : 'SS',
                        SF : 'SF',
                        FS : 'FS',
                        FF : 'FF'
                    }
                },

                'Gnt.field.ConstraintType' : {
                    none : 'None'
                },

                'Gnt.field.Duration' : {
                    invalidText : 'Invalid value'
                },

                'Gnt.field.Effort' : {
                    invalidText : 'Invalid value'
                },

                'Gnt.field.Percent' : {
                    invalidText : 'Invalid value'
                },

                'Gnt.feature.DependencyDragDrop' : {
                    fromText  : 'From',
                    toText    : 'To',
                    startText : 'Start',
                    endText   : 'End'
                },

                'Gnt.Tooltip' : {
                    startText    : 'Starts: ',
                    endText      : 'Ends: ',
                    durationText : 'Duration: '
                },

                'Gnt.plugin.TaskContextMenu' : {
                    taskInformation    : 'Task information...',
                    newTaskText        : 'New task',
                    deleteTask         : 'Delete task(s)',
                    editLeftLabel      : 'Edit left label',
                    editRightLabel     : 'Edit right label',
                    add                : 'Add...',
                    deleteDependency   : 'Delete dependency...',
                    addTaskAbove       : 'Task above',
                    addTaskBelow       : 'Task below',
                    addMilestone       : 'Milestone',
                    addSubtask         : 'Sub-task',
                    addSuccessor       : 'Successor',
                    addPredecessor     : 'Predecessor',
                    convertToMilestone : 'Convert to milestone',
                    convertToRegular   : 'Convert to regular task',
                    splitTask          : 'Split task'
                },

                'Gnt.plugin.DependencyEditor' : {
                    fromText         : 'From',
                    toText           : 'To',
                    typeText         : 'Type',
                    lagText          : 'Lag',
                    endToStartText   : 'Finish-To-Start',
                    startToStartText : 'Start-To-Start',
                    endToEndText     : 'Finish-To-Finish',
                    startToEndText   : 'Start-To-Finish'
                },

                'Gnt.widget.calendar.Calendar' : {
                    dayOverrideNameHeaderText : 'Name',
                    overrideName              : 'Name',
                    startDate                 : 'Start Date',
                    endDate                   : 'End Date',
                    error                     : 'Error',
                    dateText                  : 'Date',
                    addText                   : 'Add',
                    editText                  : 'Edit',
                    removeText                : 'Remove',
                    workingDayText            : 'Working day',
                    weekendsText              : 'Weekends',
                    overriddenDayText         : 'Overridden day',
                    overriddenWeekText        : 'Overridden week',
                    workingTimeText           : 'Working time',
                    nonworkingTimeText        : 'Non-working time',
                    dayOverridesText          : 'Day overrides',
                    weekOverridesText         : 'Week overrides',
                    okText                    : 'OK',
                    cancelText                : 'Cancel',
                    parentCalendarText        : 'Parent calendar',
                    noParentText              : 'No parent',
                    selectParentText          : 'Select parent',
                    newDayName                : '[Without name]',
                    calendarNameText          : 'Calendar name',
                    tplTexts                  : {
                        tplWorkingHours  : 'Working hours for',
                        tplIsNonWorking  : 'is non-working',
                        tplOverride      : 'override',
                        tplInCalendar    : 'in calendar',
                        tplDayInCalendar : 'standard day in calendar',
                        tplBasedOn       : 'Based on'
                    },
                    overrideErrorText         : 'There is already an override for this day',
                    overrideDateError         : 'There is already a week override on this date: {0}',
                    startAfterEndError        : 'Start date should be less than end date',
                    weeksIntersectError       : 'Week overrides should not intersect'
                },

                'Gnt.widget.calendar.AvailabilityGrid' : {
                    startText  : 'Start',
                    endText    : 'End',
                    addText    : 'Add',
                    removeText : 'Remove',
                    error      : 'Error'
                },

                'Gnt.widget.calendar.DayEditor' : {
                    workingTimeText    : 'Working time',
                    nonworkingTimeText : 'Non-working time'
                },

                'Gnt.widget.calendar.WeekEditor' : {
                    defaultTimeText    : 'Default time',
                    workingTimeText    : 'Working time',
                    nonworkingTimeText : 'Non-working time',
                    error              : 'Error',
                    noOverrideError    : "Week override contains only 'default' days - can't save it"
                },

                'Gnt.widget.calendar.ResourceCalendarGrid' : {
                    name     : 'Name',
                    calendar : 'Calendar'
                },

                'Gnt.widget.calendar.CalendarWindow' : {
                    ok     : 'Ok',
                    cancel : 'Cancel'
                },

                'Gnt.widget.calendar.CalendarManager' : {
                    addText         : 'Add',
                    removeText      : 'Remove',
                    add_child       : 'Add child',
                    add_node        : 'Add calendar',
                    add_sibling     : 'Add sibling',
                    remove          : 'Remove',
                    calendarName    : 'Calendar',
                    confirm_action  : 'Confirm action',
                    confirm_message : 'Calendar has unsaved changes. Would you like to save your changes?'
                },

                'Gnt.widget.calendar.CalendarManagerWindow' : {
                    ok     : 'Apply changes',
                    cancel : 'Close'
                },

                'Gnt.field.Assignment' : {
                    cancelText : 'Cancel',
                    closeText  : 'Save and Close'
                },

                'Gnt.column.AssignmentUnits' : {
                    text : 'Units'
                },

                'Gnt.column.Duration' : {
                    text : 'Duration'
                },

                'Gnt.column.Effort' : {
                    text : 'Effort'
                },

                'Gnt.column.EndDate' : {
                    text : 'Finish'
                },

                'Gnt.column.PercentDone' : {
                    text : '% Done'
                },

                'Gnt.column.ResourceAssignment' : {
                    text : 'Assigned Resources'
                },

                'Gnt.column.ResourceName' : {
                    text : 'Resource Name'
                },

                'Gnt.column.Rollup' : {
                    text : 'Rollup task',
                    no   : 'No',
                    yes  : 'Yes'
                },

                'Gnt.column.SchedulingMode' : {
                    text : 'Mode'
                },

                'Gnt.column.Predecessor' : {
                    text : 'Predecessors'
                },

                'Gnt.column.Successor' : {
                    text : 'Successors'
                },

                'Gnt.column.StartDate' : {
                    text : 'Start'
                },

                'Gnt.column.WBS' : {
                    text : 'WBS'
                },

                'Gnt.column.Sequence' : {
                    text : '#'
                },

                'Gnt.column.Calendar' : {
                    text : 'Calendar'
                },

                'Gnt.widget.taskeditor.TaskForm' : {
                    taskNameText            : 'Name',
                    durationText            : 'Duration',
                    datesText               : 'Dates',
                    baselineText            : 'Baseline',
                    startText               : 'Start',
                    finishText              : 'Finish',
                    percentDoneText         : 'Percent Complete',
                    baselineStartText       : 'Start',
                    baselineFinishText      : 'Finish',
                    baselinePercentDoneText : 'Percent Complete',
                    effortText              : 'Effort',
                    invalidEffortText       : 'Invalid effort value',
                    calendarText            : 'Calendar',
                    schedulingModeText      : 'Scheduling Mode',
                    rollupText              : 'Rollup',
                    wbsCodeText             : 'WBS code',
                    "Constraint Type"       : "Constraint Type",
                    "Constraint Date"       : "Constraint Date"
                },

                'Gnt.widget.DependencyGrid' : {
                    idText                    : 'ID',
                    snText                    : 'SN',
                    taskText                  : 'Task Name',
                    blankTaskText             : 'Please select task',
                    invalidDependencyText     : 'Invalid dependency',
                    parentChildDependencyText : 'Dependency between child and parent found',
                    duplicatingDependencyText : 'Duplicate dependency found',
                    transitiveDependencyText  : 'Transitive dependency',
                    cyclicDependencyText      : 'Cyclic dependency',
                    typeText                  : 'Type',
                    lagText                   : 'Lag',
                    clsText                   : 'CSS class',
                    endToStartText            : 'Finish-To-Start',
                    startToStartText          : 'Start-To-Start',
                    endToEndText              : 'Finish-To-Finish',
                    startToEndText            : 'Start-To-Finish'
                },

                'Gnt.widget.AssignmentEditGrid' : {
                    confirmAddResourceTitle : 'Confirm',
                    confirmAddResourceText  : 'Resource &quot;{0}&quot; not found in list. Would you like to add it?',
                    noValueText             : 'Please select resource to assign',
                    noResourceText          : 'No resource &quot;{0}&quot; found in the list'
                },

                'Gnt.widget.taskeditor.TaskEditor' : {
                    generalText        : 'General',
                    resourcesText      : 'Resources',
                    dependencyText     : 'Predecessors',
                    addDependencyText  : 'Add new',
                    dropDependencyText : 'Remove',
                    notesText          : 'Notes',
                    advancedText       : 'Advanced',
                    addAssignmentText  : 'Add new',
                    dropAssignmentText : 'Remove'
                },

                'Gnt.plugin.TaskEditor' : {
                    title        : 'Task Information',
                    alertCaption : 'Information',
                    alertText    : 'Please correct marked errors to save changes',
                    okText       : 'Ok',
                    cancelText   : 'Cancel'
                },

                'Gnt.field.EndDate' : {
                    endBeforeStartText : 'End date is before start date'
                },

                'Gnt.column.Note' : {
                    text : 'Note'
                },

                'Gnt.column.AddNew' : {
                    text : 'Add new column...'
                },

                'Gnt.column.EarlyStartDate' : {
                    text : 'Early Start'
                },

                'Gnt.column.EarlyEndDate' : {
                    text : 'Early Finish'
                },

                'Gnt.column.LateStartDate' : {
                    text : 'Late Start'
                },

                'Gnt.column.LateEndDate' : {
                    text : 'Late Finish'
                },

                'Gnt.field.Calendar' : {
                    calendarNotApplicable : 'Task calendar has no overlapping with assigned resources calendars'
                },

                'Gnt.column.Slack' : {
                    text : 'Slack'
                },

                'Gnt.column.Name' : {
                    text : 'Task Name'
                },

                'Gnt.column.BaselineStartDate' : {
                    text : 'Baseline Start Date'
                },

                'Gnt.column.BaselineEndDate' : {
                    text : 'Baseline End Date'
                },

                'Gnt.column.Milestone' : {
                    text : 'Milestone'
                },

                'Gnt.field.Milestone' : {
                    yes : 'Yes',
                    no  : 'No'
                },

                'Gnt.field.Dependency' : {
                    invalidFormatText     : 'Invalid dependency format',
                    invalidDependencyText : 'Invalid dependency found, please make sure you have no cyclic paths between your tasks',
                    invalidDependencyType : 'Invalid dependency type {0}. Allowed values are: {1}.'
                },

                'Gnt.constraint.Base' : {
                    name                               : "A constraint",
                    "Remove the constraint"            : "Remove the constraint",
                    "Cancel the change and do nothing" : "Cancel the change and do nothing"
                },

                'Gnt.constraint.FinishNoEarlierThan' : {
                    name                             : "Finish no earlier than",
                    // {0} constraint date
                    "Move the task to finish on {0}" : "Move the task to finish on {0}"
                },

                "Gnt.constraint.FinishNoLaterThan" : {
                    name                             : "Finish no later than",
                    // {0} constraint date
                    "Move the task to finish on {0}" : "Move the task to finish on {0}"
                },

                "Gnt.constraint.MustFinishOn" : {
                    name                             : "Must finish on",
                    // {0} constraint date
                    "Move the task to finish on {0}" : "Move the task to finish on {0}"
                },

                "Gnt.constraint.MustStartOn" : {
                    name                            : "Must start on",
                    // {0} constraint date
                    "Move the task to start at {0}" : "Move the task to start at {0}"
                },

                "Gnt.constraint.StartNoEarlierThan" : {
                    name                            : "Start no earlier than",
                    // {0} constraint date
                    "Move the task to start at {0}" : "Move the task to start at {0}"
                },

                "Gnt.constraint.StartNoLaterThan" : {
                    name                            : "Start no later than",
                    // {0} constraint date
                    "Move the task to start at {0}" : "Move the task to start at {0}"
                },

                "Gnt.column.ConstraintDate" : {
                    text : "Constraint date"
                },

                "Gnt.column.ConstraintType" : {
                    text : "Constraint"
                },

                "Gnt.widget.ConstraintResolutionForm" : {
                    dateFormat                             : "m/d/Y",
                    "OK"                                   : 'OK',
                    "Cancel"                               : 'Cancel',
                    "Resolution options"                   : "Resolution options",
                    "Don't ask again"                      : "Don't ask again",
                    // {0} task name, {1} constraint name
                    "Task {0} violates constraint {1}"     : "Task \"{0}\" violates constraint {1}",
                    // {0} task name, {1} constraint name, {2} constraint date
                    "Task {0} violates constraint {1} {2}" : "Task \"{0}\" violates constraint {1} {2}"
                },

                "Gnt.widget.ConstraintResolutionWindow" : {
                    "Constraint violation" : "Constraint violation"
                }
            }
        });
        
        this.callParent(arguments);
    }
});
