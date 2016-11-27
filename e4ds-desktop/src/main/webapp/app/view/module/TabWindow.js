Ext.define('E4desk.view.module.TabWindow', {
	extend: 'Ext.window.Window',

	title: 'Tab Window',
	width: 740,
	height: 480,
	iconCls: 'tabs-icon',
	layout: 'fit',

	initComponent: function() {

		this.items = [ {
			xtype: 'tabpanel',
			activeTab: 0,
			bodyStyle: 'padding: 5px;',

			items: [ {
				title: 'Tab Text 1',
				header: false,
				html: '<p>Tab1: Something useful would be in here.</p>',
				border: false
			}, {
				title: 'Tab Text 2',
				header: false,
				html: '<p>Tab2: Something useful would be in here.</p>',
				border: false
			}, {
				title: 'Tab Text 3',
				header: false,
				html: '<p>Tab3: Something useful would be in here.</p>',
				border: false
			}, {
				title: 'Tab Text 4',
				header: false,
				html: '<p>Tab4: Something useful would be in here.</p>',
				border: false
			} ]
		} ];

		this.callParent(arguments);
	}

});
