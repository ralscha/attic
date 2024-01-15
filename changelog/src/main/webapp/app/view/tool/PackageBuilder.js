Ext.define('Changelog.view.tool.PackageBuilder', {
	extend: 'Ext.panel.Panel',
	controller: 'Changelog.controller.PackageBuilderController',

	title: i18n.tool_packagebuilder,
	closable: true,

	layout: {
		type: 'border'
	},
	
	bodyStyle: {
	    background: 'white'
	},

	initComponent: function() {
		var me = this;

		Ext.applyIf(me, {
			items: [ {
				xtype: 'container',				
				region: 'north',
				layout: {
					padding: 10,
					type: 'hbox'
				},
				items: [ {
					xtype: 'combobox',
					itemId: 'customer',
					fieldLabel: i18n.customer,
					store: Ext.create('Changelog.store.BuildCustomers'),
					queryMode: 'local',
					displayField: 'longName',
					valueField: 'shortName',
					forceSelection: true,
					width: 400,
					labelWidth: 70
				}, {
					xtype: 'checkbox',
					itemId: 'createSetupPackage',
					fieldLabel: i18n.tool_packagebuilder_createsetuppackage,
					uncheckedValue: 'false',
					inputValue: 'true',
					labelWidth: 170,
					margin: '0 0 0 20'
				}, {
					xtype: 'checkbox',
					itemId: 'internalBuild',
					fieldLabel: i18n.tool_packagebuilder_internalbuild,
					uncheckedValue: 'false',
					inputValue: 'true',
					margin: '0 0 0 30'
				}, {
					xtype: 'button',
					itemId: 'buildButton',
					margins: '',
					margin: '0 0 10 20',
					disabled: true,
					text: i18n.tool_packagebuilder_build
				}, {
					xtype: 'container',
					flex: 2
				}, {
					xtype: 'displayfield',
					itemId: 'info',
					value: i18n.config_builddisabled,
					hidden: true,
					fieldStyle: {
						fontWeight: 'bold',
						color: 'red'
					}
				}, {
					xtype: 'container',
					flex: 1
				}, {
					xtype: 'image',
					itemId: 'busyImage',
					hidden: true,
					width: 32,
					height: 32,
					src: app_context_path + '/resources/images/globe32.gif',
					margin: '0, 15, 0, 0'
				}]
			}, {
				xtype: 'panel',			
				region: 'center',
				title: 'Output',
				height: '100%',
				margin: 5,
				bodyPadding: 10,
				autoScroll: true,
				items: [{
					xtype: 'dataview',
					itemId: 'buildOutput',					
					style: {
						fontFamily: 'monospace'
					},
					itemSelector: 'currentLine',
					tpl: '<tpl for="."><div>{l}</div></tpl>',
					store: Ext.create('Changelog.store.BuildMsgs')
				}]
			}]
		});

		me.callParent(arguments);
	}

});