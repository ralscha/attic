Ext.define('Starter.view.main.MainController', {
	extend: 'Ext.app.ViewController',
	requires: [ 'Starter.view.usersettings.Panel' ],

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
		this.redirectTo(record.getId().toString(), true);
	},

	onNavigationRoute: function(navigationId) {
		if (this.currentNavigationId === navigationId) {
			return;
		}

		var centerContainer, navigationTree;

		var navigationStore = this.getStore('navigationStore');
		var record = navigationStore.getById(navigationId);
		if (record) {

			var view = record.data.view;
			if (view) {
				Ext.suspendLayouts();
				centerContainer = this.getView().child('[region=center]');
				this.getView().remove(centerContainer);

				this.currentNavigationId = record.getId().toString();

				this.getView().add({
					xclass: view,
					region: 'center'
				});

				navigationTree = this.lookupReference('navigationTree');
				navigationTree.suspendEvents();
				navigationTree.selectPath(this.getPath(record));
				navigationTree.resumeEvents();

				Ext.resumeLayouts(true);
			}

		}
		else if (navigationId === '999' && navigationStore.getCount() > 0) {
			this.currentNavigationId = navigationId;

			Ext.suspendLayouts();
			centerContainer = this.getView().child('[region=center]');
			this.getView().remove(centerContainer);

			this.getView().add({
				xclass: 'Starter.view.usersettings.Panel',
				region: 'center'
			});

			navigationTree = this.lookupReference('navigationTree');
			navigationTree.getSelectionModel().deselectAll(true);
			Ext.resumeLayouts(true);

		}
		else {
			this.requestNavigationId = navigationId;
		}
	},

	init: function() {
		securityService.getLoggedOnUser(this.afterLoggedOnUserReceived, this);
	},

	onNavigationStoreLoad: function(store) {
		if (this.autoOpenView) {
			var record;

			if (this.requestNavigationId) {
				if (this.requestNavigationId === '999') {
					this.redirectTo('999', true);
					return;
				}

				record = store.getById(this.requestNavigationId);
				this.requestNavigationId = null;
			}
			else {
				record = store.findRecord('view', this.autoOpenView);
			}

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
			this.redirectTo(selected.getId().toString(), true);
		}
	},

	getPath: function(node) {
		return node.parentNode ? this.getPath(node.parentNode) + '/' + node.getId() : '/' + node.getId();
	},

	onUserSettingsClick: function() {
		this.redirectTo('999', true);
	}

});
