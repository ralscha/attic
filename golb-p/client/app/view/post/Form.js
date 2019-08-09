Ext.define('Golb.view.post.Form', {
	extend: 'Ext.form.Panel',
	requires: [ 'Ext.form.field.ComboBox', 'Ext.form.field.Tag' ],
	defaultFocus: 'textfield[name=title]',
	reference: 'editPanel',
	cls: 'shadow',
	
	layout: 'fit',
	items: [ {
		xtype: 'tabpanel',
		deferredRender: false,
		items: [ {
			xtype: 'panel',
			title: 'Header',
			bodyPadding: '0 20 0 20',

			layout: {
				type: 'vbox',
				align: 'stretch'
			},
			defaultType: 'textfield',
			defaults: {
				labelAlign: 'top'
			},

			items: [ {
				name: 'title',
				fieldLabel: 'Title',
				maxLength: 60,
				enforceMaxLength: true,
				listeners: {
					blur: 'onTitleBlur'
				},
				allowBlank: false
			}, {
				name: 'slug',
				fieldLabel: 'Slug',
				reference: 'slugTf',
				isSlugUnique: true,
				listeners: {
					blur: 'onSlugBlur'
				},
				validator: function() {
					if (!this.isSlugUnique) {
						return 'Slug is not unique';
					}
					return true;
				},
				allowBlank: false
			}, {
				xtype: 'textareafield',
				name: 'summary',
				fieldLabel: 'Summary',
				allowBlank: true
			}, {
				xtype: 'tagfield',
				fieldLabel: 'Tags',
				bind: {
					store: '{tagsForm}'
				},
				name: 'tags',
				displayField: 'name',
				valueField: 'id',
				queryMode: 'local',
				forceSelection: false,
				filterPickList: true,
				createNewOnEnter: true,
				createNewOnBlur: true
			} ]
		}, {
			xtype: 'tabpanel',
			title: 'Post',
			deferredRender: false,
			flex: 1,
			deferredRender: false,
			margin: '0 0 10 0',
			items: [ {
				title: 'Edit',
				xtype: 'textareafield',
				name: 'body',
				reference: 'bodyMarkdown',
				cls: 'markdown-edit',
				flex: 1,
				listeners: {
					change: 'onBodyChange'
				}
			}, {
				title: 'Preview',
				xtype: 'container',
				flex: 1,
				border: 1,
				style: {
					'border-width': '1px',
					'border-style': 'solid',
					'border-color': '#d0d0d0'
				},
				scrollable: {
					x: 'auto',
					y: 'auto'
				},
				layout: 'fit',
				cls: 'markdown-body',
				items: [ {
					xtype: 'displayfield',
					padding: '0 10 2 10',
					name: 'bodyHtml',
					reference: 'bodyHtml'
				} ]
			} ]
		} ]
	} ],

	dockedItems: [ {
		xtype: 'toolbar',
		dock: 'top',
		items: [ {
			text: i18n.back,
			handler: 'back',
			iconCls: 'x-fa fa-arrow-left'
		}, {
			text: Golb.Util.underline(i18n.save, 'S'),
			accessKey: 's',
			ui: 'soft-green',
			iconCls: 'x-fa fa-floppy-o',
			formBind: true,
			handler: 'save'
		}, {
			xtype: 'tbseparator',
			hidden: true,
			bind: {
				hidden: '{isPhantomObject}'
			}
		}, {
			text: i18n.destroy,
			iconCls: 'x-fa fa-trash-o',
			handler: 'erase',
			ui: 'soft-red',
			hidden: true,
			bind: {
				hidden: '{isPhantomObject}'
			}
		} ]
	} ]

});