Ext.define('Starter.view.department.Form',{
   extend:'Ext.form.Panel',

   requires:['Starter.Util'],

   glyph: 0xe803,
   defaultType: 'textfield',
   defaults: {
      anchor: '40%'
   },
   bodyPadding: 10,
   defaultFocus:'textfield[name=departmentName]',

   items: [{

       name:'departmentName',
       fieldLabel: 'Department Name',
       allowBlank: false,
       emptyText: 'Create department  name'
   }, {
       xtype:'textareafield',
       name:'notes',
       fieldLabel: 'Note',
       maxLength: 255,
       enforceMaxLength: true,
       allowBlank: true
   }],

   dockedItems: [ {
      xtype: 'toolbar',
      dock: 'top',
      items: [ {
         text: i18n.setup_department,
         handler: 'back',
         glyph: 0xe818
      }, '->',{
         text: i18n.destroy,
         glyph: 0xe806,
         handler: 'destroyDepartment',
         bind: {
            hidden: '{newDepartment}'
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