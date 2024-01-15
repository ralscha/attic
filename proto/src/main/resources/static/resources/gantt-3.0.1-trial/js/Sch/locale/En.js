/**
 * English translations for the Scheduler component
 *
 * NOTE: To change locale for month/day names you have to use the corresponding Ext JS language file.
 */
Ext.define('Sch.locale.En', {
    extend      : 'Sch.locale.Locale',
    singleton   : true,

    constructor : function (config) {

        Ext.apply(this , {
            l10n        : {
                'Sch.util.Date' : {
                    unitNames : {
                        YEAR        : { single : 'year',    plural : 'years',   abbrev : 'yr' },
                        QUARTER     : { single : 'quarter', plural : 'quarters',abbrev : 'q' },
                        MONTH       : { single : 'month',   plural : 'months',  abbrev : 'mon' },
                        WEEK        : { single : 'week',    plural : 'weeks',   abbrev : 'w' },
                        DAY         : { single : 'day',     plural : 'days',    abbrev : 'd' },
                        HOUR        : { single : 'hour',    plural : 'hours',   abbrev : 'h' },
                        MINUTE      : { single : 'minute',  plural : 'minutes', abbrev : 'min' },
                        SECOND      : { single : 'second',  plural : 'seconds', abbrev : 's' },
                        MILLI       : { single : 'ms',      plural : 'ms',      abbrev : 'ms' }
                    }
                },

                'Sch.panel.TimelineGridPanel' : {
                    loadingText : 'Loading, please wait...',
                    savingText  : 'Saving changes, please wait...'
                },

                'Sch.panel.TimelineTreePanel' : {
                    loadingText : 'Loading, please wait...',
                    savingText  : 'Saving changes, please wait...'
                },

                'Sch.mixin.SchedulerView' : {
                    loadingText : 'Loading events...'
                },

                'Sch.plugin.CurrentTimeLine' : {
                    tooltipText : 'Current time'
                },

                'Sch.plugin.EventEditor' : {
                    saveText    : 'Save',
                    deleteText  : 'Delete',
                    cancelText  : 'Cancel'
                },

                'Sch.plugin.SimpleEditor' : {
                    newEventText    : 'New booking...'
                },

                'Sch.widget.ExportDialog' : {
                    generalError                : 'An error occured, try again.',
                    title                       : 'Export Settings',
                    formatFieldLabel            : 'Paper format',
                    orientationFieldLabel       : 'Orientation',
                    rangeFieldLabel             : 'Export range',
                    showHeaderLabel             : 'Add page number',
                    orientationPortraitText     : 'Portrait',
                    orientationLandscapeText    : 'Landscape',
                    completeViewText            : 'Complete schedule',
                    currentViewText             : 'Current view',
                    dateRangeText               : 'Date range',
                    dateRangeFromText           : 'Export from',
                    pickerText                  : 'Resize column/rows to desired value',
                    dateRangeToText             : 'Export to',
                    exportButtonText            : 'Export',
                    cancelButtonText            : 'Cancel',
                    progressBarText             : 'Exporting...',
                    exportersFieldLabel         : 'Export mode',
                    adjustCols                  : 'Adjust column width',
                    adjustColsAndRows           : 'Adjust column width and row height',
                    specifyDateRange            : 'Specify date range'
                },

                'Sch.plugin.Export' : {
                    fetchingRows            : 'Fetching row {0} of {1}',
                    builtPage               : 'Built page {0} of {1}',
                    requestingPrintServer   : 'Please wait...'
                },

                'Sch.plugin.exporter.AbstractExporter' : {
                    name    : 'Exporter'
                },

                'Sch.plugin.exporter.SinglePage' : {
                    name    : 'Single page'
                },

                'Sch.plugin.exporter.MultiPageVertical' : {
                    name    : 'Multiple pages (vertically)'
                },

                'Sch.plugin.exporter.MultiPage' : {
                    name    : 'Multiple pages'
                },

                // -------------- View preset date formats/strings -------------------------------------
                'Sch.preset.Manager' : {
                    hourAndDay  : {
                        displayDateFormat   : 'G:i',
                        middleDateFormat    : 'G:i',
                        topDateFormat       : 'D d/m'
                    },

                    secondAndMinute : {
                        displayDateFormat   : 'g:i:s',
                        topDateFormat       : 'D, d g:iA'
                    },

                    dayAndWeek      : {
                        displayDateFormat   : 'm/d h:i A',
                        middleDateFormat    : 'D d M'
                    },

                    weekAndDay      : {
                        displayDateFormat   : 'm/d',
                        bottomDateFormat    : 'd M',
                        middleDateFormat    : 'Y F d'
                    },

                    weekAndMonth : {
                        displayDateFormat   : 'm/d/Y',
                        middleDateFormat    : 'm/d',
                        topDateFormat       : 'm/d/Y'
                    },

                    weekAndDayLetter : {
                        displayDateFormat   : 'm/d/Y',
                        middleDateFormat    : 'D d M Y'
                    },

                    weekDateAndMonth : {
                        displayDateFormat   : 'm/d/Y',
                        middleDateFormat    : 'd',
                        topDateFormat       : 'Y F'
                    },

                    monthAndYear : {
                        displayDateFormat   : 'm/d/Y',
                        middleDateFormat    : 'M Y',
                        topDateFormat       : 'Y'
                    },

                    year : {
                        displayDateFormat   : 'm/d/Y',
                        middleDateFormat    : 'Y'
                    },

                    manyYears : {
                        displayDateFormat   : 'm/d/Y',
                        middleDateFormat    : 'Y'
                    }
                }
            }
        });

        this.callParent(arguments);
    }
});
