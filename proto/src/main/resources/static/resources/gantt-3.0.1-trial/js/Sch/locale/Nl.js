/*

Ext Gantt 3.0.1
Copyright(c) 2009-2015 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
 * Dutch translations for the Scheduler component
 *
 * NOTE: To change locale for month/day names you have to use the corresponding Ext JS language file.
 */
Ext.define('Sch.locale.Nl', {
    extend      : 'Sch.locale.Locale',
    singleton   : true,

    constructor : function (config) {

        Ext.apply(this, {
            l10n        : {
                'Sch.util.Date' : {
                    unitNames : {
                        YEAR        : { single : 'jaar',    plural : 'jaren',   abbrev : 'j' },
                        QUARTER     : { single : 'kwartaal', plural : 'kwartalen',abbrev : 'kw' },
                        MONTH       : { single : 'maand',   plural : 'maanden',  abbrev : 'm' },
                        WEEK        : { single : 'week',    plural : 'weken',   abbrev : 'w' },
                        DAY         : { single : 'dag',     plural : 'dagen',    abbrev : 'd' },
                        HOUR        : { single : 'uur',    plural : 'uren',   abbrev : 'u' },
                        MINUTE      : { single : 'minuut',  plural : 'minuten', abbrev : 'm' },
                        SECOND      : { single : 'seconde',  plural : 'seconden', abbrev : 's' },
                        MILLI       : { single : 'ms',      plural : 'ms',      abbrev : 'ms' }
                    }
                },

                'Sch.panel.TimelineGridPanel' : {
                    loadingText : 'Bezig met laden...',
                    savingText  : 'Bezig met opslaan...'
                },

                'Sch.panel.TimelineTreePanel' : {
                    loadingText : 'Bezig met laden...',
                    savingText  : 'Bezig met opslaan...'
                },

                'Sch.mixin.SchedulerView' : {
                    loadingText : 'Events laden...'
                },

                'Sch.plugin.CurrentTimeLine' : {
                    tooltipText : 'Huidige tijd'
                },

                'Sch.plugin.EventEditor' : {
                    saveText    : 'Opslaan',
                    deleteText  : 'Verwijderen',
                    cancelText  : 'Annuleer'
                },

                'Sch.plugin.SimpleEditor' : {
                    newEventText    : 'Nieuwe boeking...'
                },

                'Sch.widget.ExportDialog' : {
                    generalError                : 'Er is een fout opgetreden, probeer nogmaals.',
                    title                       : 'Export instellingen',
                    formatFieldLabel            : 'Papier formaat',
                    orientationFieldLabel       : 'Oriëntatatie',
                    rangeFieldLabel             : 'Export bereik',
                    showHeaderLabel             : 'Toevoegen paginanummer',
                    orientationPortraitText     : 'Portret',
                    orientationLandscapeText    : 'Landschap',
                    completeViewText            : 'Compleet schema',
                    currentViewText             : 'Huidige weergave',
                    dateRangeText               : 'Periode',
                    dateRangeFromText           : 'Exporteer vanaf',
                    pickerText                  : 'Wijzig formaat van kolommen en/of rijen',
                    dateRangeToText             : 'Exporteer naar',
                    exportButtonText            : 'Exporteer',
                    cancelButtonText            : 'Annuleer',
                    progressBarText             : 'Bezig met exporteren...',
                    exportersFieldLabel         : 'Export modus',
                    adjustCols                  : 'Wijzig kolom breedte',
                    adjustColsAndRows           : 'Wijzig kolom breedte en rij hoogte',
                    specifyDateRange            : 'Specificeer periode'
                },

                'Sch.plugin.Export' : {
                    fetchingRows            : 'Record {0} van {1}',
                    builtPage               : 'Pagina {0} van {1}',
                    requestingPrintServer   : 'Rapport bewerken op server...'
                },

                'Sch.plugin.exporter.AbstractExporter' : {
                    name    : 'Exporter'
                },

                'Sch.plugin.exporter.SinglePage' : {
                    name    : 'Enkele pagina'
                },

                'Sch.plugin.exporter.MultiPageVertical' : {
                    name    : 'Meerdere pagina\'s (verticaal)'
                },

                'Sch.plugin.exporter.MultiPage' : {
                    name    : 'Meerdere pagina\'s'
                },

                // -------------- View preset date formats/strings -------------------------------------
                'Sch.preset.Manager' : {

                    hourAndDay : {
                        displayDateFormat : 'G:i',
                        middleDateFormat : 'G:i',
                        topDateFormat : 'd-m-Y'
                    },

                    secondAndMinute : {
                        displayDateFormat : 'G:i:s',
                        topDateFormat : 'D, d G:i'
                    },

                    dayAndWeek : {
                        displayDateFormat : 'd-m H:i',
                        middleDateFormat : 'D d M'
                    },

                    weekAndDay : {
                        displayDateFormat : 'd-m',
                        bottomDateFormat : 'd M',
                        middleDateFormat : 'j-F Y'
                    },

                    weekAndMonth : {
                        displayDateFormat : 'd-m-Y',
                        middleDateFormat : 'd-m',
                        topDateFormat : 'd-m-Y'
                    },

                    weekAndDayLetter : {
                        displayDateFormat : 'd-m-Y',
                        middleDateFormat : 'D j M Y'
                    },

                    weekDateAndMonth : {
                        displayDateFormat : 'd-m-Y',
                        middleDateFormat : 'd',
                        topDateFormat : 'F Y'
                    },

                    monthAndYear : {
                        displayDateFormat : 'd-m-Y',
                        middleDateFormat : 'M Y',
                        topDateFormat : 'Y'
                    },

                    year : {
                        displayDateFormat : 'd-m-Y',
                        middleDateFormat : 'Y'
                    },

                    manyYears : {
                        displayDateFormat : 'd-m-Y',
                        middleDateFormat : 'Y'
                    }
                }
            }
        });

        this.callParent(arguments);
    }

});
