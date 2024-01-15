Ext.define('Starter.view.location.Form',{
   extend:'Ext.form.Panel',

   requires:['Starter.Util'],

   glyph: 0xe803,
   defaultType: 'textfield',
   defaults: {
      anchor: '40%'
   },
   bodyPadding: 10,
   defaultFocus:'textfield[name=locationName]',

   items: [{

       name:'locationName',
       fieldLabel: 'Location Name',
       allowBlank: false,
       emptyText: 'Create location  name'
   }, {
       xtype:'textareafield',
       name:'notes',
       fieldLabel: 'Note',
       allowBlank: true,
       maxLength: 255,
       enforceMaxLength: true
   }],

   dockedItems: [ {
      xtype: 'toolbar',
      dock: 'top',
      items: [ {
         text: i18n.setup_location,
         handler: 'back',
         glyph: 0xe818
      }, '->',{
         text: i18n.destroy,
         glyph: 0xe806,
         handler: 'destroyLocation',
         bind: {
            hidden: '{newLocation}'
         }
      }, {
         text: Starter.Util.underline(i18n.save, 'S'),
         accessKey: 's',
         glyph: 0xe80d,
         formBind: true,
         handler: 'save'
      } ]
   } ]

});