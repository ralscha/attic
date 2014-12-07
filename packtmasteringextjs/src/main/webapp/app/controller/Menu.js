Ext.define('Packt.controller.Menu', {
	extend: 'Ext.app.Controller',
	requires: ['Packt.model.menu.Root', 'Packt.model.menu.Item', 'Packt.store.Menu', 'Packt.view.menu.Accordion', 'Packt.view.menu.Item'],
	models: [ 'menu.Root', 'menu.Item' ],
	stores: [ 'Menu' ],

	views: [ 'menu.Accordion', 'menu.Item' ],

	refs: [ {
		ref: 'mainPanel',
		selector: 'mainpanel'
	}, {
		ref: 'mainMenu',
		selector: 'mainmenu'
	}],

	init: function(application) {
		this.control({
			"mainmenu": {
				render: this.onPanelRender
			},
			"mainmenuitem": {
				select: this.onTreepanelSelect,
				itemclick: this.onTreepanelItemClick
			}
		});
	},

	onPanelRender: function(abstractcomponent, options) {
		var me = this;
		this.getMenuStore().load(function(records, op, success) {

			Ext.each(records, function(root) {

				var menu = Ext.create('Packt.view.menu.Item', {
					title: i18n[root.get('text')],
					iconCls: root.get('iconCls')
				});

				Ext.each(root.children(), function(items) {

					Ext.each(items.data.items, function(item) {

						menu.getRootNode().appendChild({
							text: i18n[item.get('text')],
							leaf: true,
							iconCls: item.get('iconCls'),
							id: item.get('id'),
							className: item.get('className')
						});
					});
				});

				me.getMainMenu().add(menu);
			});
		});
	},

	onTreepanelSelect: function(selModel, record, index, options) {
		var mainPanel = this.getMainPanel();

		var newTab = mainPanel.items.findBy(function(tab) {
			return tab.title === record.get('text');
		});

		if (!newTab) {
			newTab = mainPanel.add({
				xtype: record.raw.className,
				closable: true,
				iconCls: record.get('iconCls'),
				title: record.get('text')
			});
		}

		mainPanel.setActiveTab(newTab);
	},

	onTreepanelItemClick: function(view, record, item, index, event, options) {
		this.onTreepanelSelect(view, record, index, options);
	}
});