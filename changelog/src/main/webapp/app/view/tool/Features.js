Ext.define('Changelog.view.tool.Features', {
	extend: 'Ext.form.Panel',
	controller: 'Changelog.controller.FeaturesController',

	title: i18n.tool_features,
	closable: true,

	bodyPadding: 10,

	initComponent: function() {
		var me = this;

		Ext.applyIf(me, {
			defaults: {
			},
			items: [ {
				xtype: 'checkbox',
				name: 'alogin',
				boxLabel: i18n.tool_features_autologin
			}, {
				xtype: 'checkbox',
				name: 'langdeCH',
				boxLabel: i18n.tool_features_langDE
			}, {
				xtype: 'checkbox',
				name: 'langfr',
				boxLabel: i18n.tool_features_langFR
			}, {
				xtype: 'checkbox',
				name: 'langen',
				boxLabel: i18n.tool_features_langEN
			}, {
				xtype: 'checkbox',
				name: 'ct',
				boxLabel: i18n.tool_features_ct
			}, {
				xtype: 'checkbox',
				name: 'lis',
				boxLabel: i18n.tool_features_lis
			}, {
				xtype: 'checkbox',
				name: 'lms',
				boxLabel: i18n.tool_features_lms
			}, {
				xtype: 'checkbox',
				name: 'lic',
				boxLabel: i18n.tool_features_lic
			}, {
				xtype: 'checkbox',
				name: 'clm',
				boxLabel: i18n.tool_features_clm
			}, {
				xtype: 'checkbox',
				name: 'inv',
				boxLabel: i18n.tool_features_inv
			}, {
				xtype: 'checkbox',
				name: 'par',
				boxLabel: i18n.tool_features_par
			}, {
				xtype: 'checkbox',
				name: 'vertragAutoNr',
				boxLabel: i18n.tool_features_autoNo
			}, {
				xtype: 'checkbox',
				name: 'gen',
				boxLabel: i18n.tool_features_gen
			}, {
				xtype: 'textarea',
				itemId: 'featuresKey',
				width : 700,
				grow: 'true',
				readOnly: 'true',
				submitValue: 'false',
				fieldLabel: i18n.tool_features_key,
				margin: '20 0 0 0'
			}, {
				xtype: 'fieldcontainer',
				fieldLabel: '&nbsp;',
                labelSeparator: '',
				layout: 'hbox',
				items: [ {
					xtype: 'button',
					text: i18n.tool_features_clipboardcopy,
					itemId: 'copyButton'
				} ]
			} ]
		});

		me.callParent(arguments);
	}

});