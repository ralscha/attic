/*

Ext Gantt 3.0.1
Copyright(c) 2009-2015 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
 * German translations for the Scheduler component
 *
 * NOTE: To change locale for month/day names you have to use the corresponding Ext JS language file.
 */
Ext.define('Sch.locale.De', {
    extend      : 'Sch.locale.Locale',
    singleton   : true,

    constructor : function (config) {

        Ext.apply(this, {
            l10n        : {
                'Sch.util.Date' : {
                    unitNames : {
                        YEAR        : { single : 'Jahr',        plural : 'Jahre',           abbrev : 'j' },
                        QUARTER     : { single : 'Quartal',     plural : 'Quartale',        abbrev : 'q' },
                        MONTH       : { single : 'Monat',       plural : 'Monate',          abbrev : 'm' },
                        WEEK        : { single : 'Woche',       plural : 'Wochen',          abbrev : 'w' },
                        DAY         : { single : 'Tag',         plural : 'Tage',            abbrev : 't' },
                        HOUR        : { single : 'Stunde',      plural : 'Stunden',         abbrev : 'h' },
                        MINUTE      : { single : 'Minute',      plural : 'Minuten',         abbrev : 'min' },
                        SECOND      : { single : 'Sekunde',     plural : 'Sekunden',        abbrev : 's' },
                        MILLI       : { single : 'Millisekunde',plural : 'Millisekunden',   abbrev : 'ms' }
                    }
                },

                'Sch.panel.TimelineGridPanel' : {
                    loadingText : 'Geladen, bitte warten...',
                    savingText  : 'Speichern von Änderungen, bitte warten...'
                },

                'Sch.panel.TimelineTreePanel' : {
                    loadingText : 'Geladen, bitte warten...',
                    savingText  : 'Speichern von Änderungen, bitte warten...'
                },

                'Sch.mixin.SchedulerView' : {
                    loadingText : 'Lade daten...'
                },

                'Sch.plugin.CurrentTimeLine' : {
                    tooltipText : 'Aktuelle Zeit'
                },

                'Sch.plugin.EventEditor' : {
                    saveText    : 'Speichern',
                    deleteText  : 'Löschen',
                    cancelText  : 'Abbrechen'
                },

                'Sch.plugin.SimpleEditor' : {
                    newEventText    : 'Neue Buchung...'
                },

                'Sch.widget.ExportDialog' : {
                    generalError                : 'Ein Fehler ist aufgetreten, bitte versuchen Sie es erneut.',
                    title                       : 'Export-Einstellungen',
                    formatFieldLabel            : 'Papierformat',
                    orientationFieldLabel       : 'Ausrichtung',
                    rangeFieldLabel             : 'Auswahl exportieren',
                    showHeaderLabel             : 'Seitennummer hinzuf&uuml;gen',
                    orientationPortraitText     : 'Hochformat',
                    orientationLandscapeText    : 'Querformat',
                    completeViewText            : 'Vollständige Ansicht',
                    currentViewText             : 'Aktuelle Ansicht',
                    dateRangeText               : 'Zeitraum',
                    dateRangeFromText           : 'Exportieren ab',
                    pickerText                  : 'Spalten/Reihen auf gew&uuml;nschten Wert &auml;ndern.',
                    dateRangeToText             : 'Exportieren bis',
                    exportButtonText            : 'Exportieren',
                    cancelButtonText            : 'Abbrechen',
                    progressBarText             : 'Exportiere...',
                    adjustCols                  : 'Spaltenbreite anpassen',
                    adjustColsAndRows           : 'Spaltenbreite und Höhe anpassen',
                    exportersFieldLabel         : 'Modus exportieren',
                    specifyDateRange            : 'Datumsbereich festlegen'
                },

                'Sch.plugin.Export' : {
                    fetchingRows            : 'Abrufreihen {0} von {1}',
                    builtPage               : 'Seite {0} von {1}',
                    requestingPrintServer   : 'Senden von Daten...'
                },

                'Sch.plugin.exporter.AbstractExporter' : {
                    name    : 'Exporter'
                },

                'Sch.plugin.exporter.SinglePage' : {
                    name    : 'Einzelne seite'
                },

                'Sch.plugin.exporter.MultiPageVertical' : {
                    name    : 'Mehrere seiten (vertikal)'
                },

                'Sch.plugin.exporter.MultiPage' : {
                    name    : 'Mehrere seiten'
                },
                // -------------- View preset date formats/strings -------------------------------------
                'Sch.preset.Manager' : {
                    hourAndDay : {
                        displayDateFormat : 'G:i',
                        middleDateFormat : 'H',
                        topDateFormat : 'D, d. M. Y'
                    },

                    secondAndMinute : {
                        displayDateFormat : 'G:i:s',
                        topDateFormat : 'D, d H:i'
                    },

                    dayAndWeek : {
                        displayDateFormat : 'd.m. G:i',
                        middleDateFormat : 'd.m.Y'
                    },

                    weekAndDay : {
                        displayDateFormat : 'd.m.',
                        bottomDateFormat : 'd. M',
                        middleDateFormat : 'Y F d'
                    },

                    weekAndMonth : {
                        displayDateFormat : 'd.m.Y',
                        middleDateFormat : 'd.m.',
                        topDateFormat : 'd.m.Y'
                    },

                    weekAndDayLetter : {
                        displayDateFormat : 'd.m.Y',
                        middleDateFormat : 'D, d. M. Y'
                    },

                    weekDateAndMonth : {
                        displayDateFormat : 'd.m.Y',
                        middleDateFormat : 'd',
                        topDateFormat : 'Y F'
                    },

                    monthAndYear : {
                        displayDateFormat : 'd.m.Y',
                        middleDateFormat : 'M. Y',
                        topDateFormat : 'Y'
                    },

                    manyYears : {
                        displayDateFormat : 'd.m.Y',
                        middleDateFormat : 'Y'
                    }
                }
            }
        });

        this.callParent(arguments);
    }

});
