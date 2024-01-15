/**
@class Sch.locale.Locale

Base locale class. You need to subclass it, when creating new locales for Bryntum components. Usually subclasses of this class
will be singletones.

See <a href="#!/guide/gantt_scheduler_localization">Localization guide</a> for additional details.

*/
Ext.define('Sch.locale.Locale', {

    /**
     * @cfg {Object} l10n An object with the keys corresponding to class names and values are in turn objects with "phraseName/phraseTranslation"
     * key/values. For example:
     *
    l10n        : {
        'Sch.plugin.EventEditor' : {
            saveText: 'Speichern',
            deleteText: 'Löschen',
            cancelText: 'Abbrechen'
        },
        'Sch.plugin.CurrentTimeLine' : {
            tooltipText : 'Aktuelle Zeit'
        },

        ...
    }

     */
    l10n            : null,

    legacyMode      : true,

    localeName      : null,
    namespaceId     : null,


    constructor     : function () {
        if (!Sch.locale.Active) {
            Sch.locale.Active = {};
            this.bindRequire();
        }

        var name            = this.self.getName().split('.');
        var localeName      = this.localeName = name.pop();
        this.namespaceId    = name.join('.');

        var currentLocale   = Sch.locale.Active[ this.namespaceId ];

        // let's localize all the classes that are loaded
        // except the cases when English locale is being applied over some non-english locale
        if (!(localeName == 'En' && currentLocale && currentLocale.localeName != 'En')) this.apply();
    },

    bindRequire : function () {
        // OVERRIDE
        // we need central hook to localize class once it's been created
        // to achieve it we override Ext.ClassManager.triggerCreated
        var _triggerCreated = Ext.ClassManager.triggerCreated;

        Ext.ClassManager.triggerCreated = function(className) {
            _triggerCreated.apply(this, arguments);

            var cls     = Ext.ClassManager.get(className);
            // trying to apply locales for the loaded class
            for (var namespaceId in Sch.locale.Active) {
                Sch.locale.Active[namespaceId].apply(cls);
            }
        };
    },

    /**
     * Apply this locale to classes.
     * @param {String[]/Object[]} [classNames] Array of class names (or classes themself) to localize.
     * If no classes specified then will localize all exisiting classes.
     */
    apply       : function (classNames) {
        if (this.l10n) {
            var me              = this, i, l;
            var localeId        = this.self.getName();

            var applyToClass    = function (clsName, cls) {
                cls = cls || Ext.ClassManager.get(clsName);

                if (cls && (cls.activeLocaleId !== localeId)) {
                    var locale = me.l10n[clsName];

                    // if it's procedural localization - run provided callback
                    if (typeof locale === 'function') {
                        locale(clsName);

                    // if it's a singleton - apply to it
                    } else if (cls.singleton) {
                        cls.l10n = Ext.apply({}, locale, cls.prototype && cls.prototype.l10n);

                    // otherwise we override class
                    } else {
                        Ext.override(cls, { l10n : locale });
                    }

                    // if we support old locales approach let's duplicate locale to old places
                    if (me.legacyMode) {
                        var target;

                        // we update either class prototype
                        if (cls.prototype) {
                            target = cls.prototype;
                        // or object itself in case of singleton
                        } else if (cls.singleton) {
                            target = cls;
                        }

                        if (target && target.legacyMode) {

                            if (target.legacyHolderProp) {
                                if (!target[target.legacyHolderProp]) target[target.legacyHolderProp] = {};

                                target = target[target.legacyHolderProp];
                            }

                            for (var p in locale) {
                                if (typeof target[p] !== 'function') target[p] = locale[p];
                            }
                        }
                    }

                    // keep applied locale
                    cls.activeLocaleId  = localeId;

                    // for singletons we can have some postprocessing
                    if (cls.onLocalized) cls.onLocalized();
                }
            };

            // if class name is specified
            if (classNames) {
                if (!Ext.isArray(classNames)) classNames = [classNames];

                var name, cls;
                for (i = 0, l = classNames.length; i < l; i++) {
                    if (Ext.isObject(classNames[i])) {
                        if (classNames[i].singleton) {
                            cls     = classNames[i];
                            name    = Ext.getClassName(Ext.getClass(cls));
                        } else {
                            cls     = Ext.getClass(classNames[i]);
                            name    = Ext.getClassName(cls);
                        }
                    } else {
                        cls     = null;
                        name    = 'string' === typeof classNames[i] ? classNames[i] : Ext.getClassName(classNames[i]);
                    }

                    if (name && name in this.l10n) {
                        applyToClass(name, cls);
                    }
                }

            // localize all the classes that we know about
            } else {
                // update active locales
                Sch.locale.Active[this.namespaceId] = this;

                for (var clsName in this.l10n) {
                    applyToClass(clsName);
                }
            }
        }
    }
});
