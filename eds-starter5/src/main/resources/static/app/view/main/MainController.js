Ext.define('Starter.view.main.MainController', {
	extend: 'Ext.app.ViewController',
	requires: [ 'Starter.view.usersettings.Window' ],

	config: {
		listen: {
			global: {
				redirectTo: 'onRedirectTo'
			}
		}
	},

	routes: {
		':navigationId': {
			action: 'onNavigationRoute',
			conditions: {
				':navigationId': '([0-9]+)'
			}
		}
	},

	onRedirectTo: function(target, argument) {
		var record = this.getStore('navigationStore').findRecord('view', target);
		this.redirectTo('' + record.getId(), true);
	},

	onNavigationRoute: function(navigationId) {
		if (this.ignoreHashChange) {
			this.ignoreHashChange = false;
			return;
		}

		var record = this.getStore('navigationStore').getById(navigationId);
		if (record) {
			Ext.suspendLayouts();
			var view = record.data.view;
			if (view) {
				var tabPanel = this.lookupReference('centerTabPanel');
				var tab = tabPanel.child('panel[navigationId=' + record.getId() + ']');
				if (!tab) {
					var viewObject = Ext.create(view, {
						icon: record.data.icon,
						treePath: this.getPath(record),
						navigationId: record.getId(),
						stateId: view,
						stateful: true
					});
					tab = tabPanel.add(viewObject);
				}
				this.activeTab = tab;
				tabPanel.setActiveTab(tab);
			}
			Ext.resumeLayouts(true);
		}
	},

	init: function() {
		securityService.getLoggedOnUser(this.afterLoggedOnUserReceived, this);
	},

	onNavigationStoreLoad: function(store) {
		if (this.autoOpenView) {
			var record = store.findRecord('view', this.autoOpenView);
			if (record) {
				var navigationTree = this.lookupReference('navigationTree');
				var path = this.getPath(record);
				Ext.defer(function() {
					navigationTree.selectPath(path);
				}, 1);
			}
		}
	},

	afterLoggedOnUserReceived: function(user) {
		this.getViewModel().set('loggedOnUser', user.firstName + ' ' + user.lastName);
		this.autoOpenView = user.autoOpenView;
	},

	onNavigationTreeSelectionchange: function(tree, selections) {
		if (selections && selections.length === 1) {
			var selected = selections[0];
			this.redirectTo('' + selected.getId(), true);
		}
	},

	getPath: function(node) {
		return node.parentNode ? this.getPath(node.parentNode) + '/' + node.getId() : '/' + node.getId();
	},

	onTabChange: function(tabPanel, newCard) {
		if (!this.activeTab || this.activeTab.getId() !== newCard.getId()) {
			this.activeTab = newCard;
			var navigationTree = this.lookupReference('navigationTree');
			navigationTree.suspendEvents();
			navigationTree.selectPath(newCard.treePath);
			navigationTree.resumeEvents();
			this.ignoreHashChange = true;
			this.redirectTo('' + newCard.navigationId);
		}
	},

	onTabRemove: function(tabPanel) {
		var navigationTree = this.lookupReference('navigationTree');
		if (tabPanel.items.length === 0) {
			navigationTree.getSelectionModel().deselectAll();
		}
		this.activeTab = null;
	},

	onUserSettingsClick: function() {
		var win = new Starter.view.usersettings.Window();
		win.show();
	}

});
