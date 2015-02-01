Ext.define('E4ds.controller.Viewport', {
	extend: 'Deft.mvc.ViewController',
	requires: [ 'E4ds.view.UserSettings' ],

	control: {
		menuTree: {
			selectionchange: 'onSelectionChange'
		},
		tabPanel: {
			tabchange: 'onTabChange',
			remove: 'onRemove'
		},
		loggedOnLabel: true,
		settingsButton: {
			click: 'onSettingsButtonClick'
		}
	},

	init: function() {
		securityService.getLoggedOnUser(this.afterLoggedOnUserReceived, this);
	},

	afterLoggedOnUserReceived: function(user) {
		var me = this;

		E4ds.user = user;
		this.getLoggedOnLabel().setText(user.firstName + ' ' + user.name);

		History.Adapter.bind(window, 'statechange', function() {
			var state = History.getState();
			me.showTab(state.data);
		});

		Ext.direct.Manager.on('event', function(e) {
			me.handleDirectResponse(e);
		});

		var state = History.getState();
		if (state && state.data) {
			me.showTab(state.data);
		}
	},

	getPath: function(node) {
		return node.parentNode ? this.getPath(node.parentNode) + "/" + node.getId() : "/"
				+ node.getId();
	},

	onSettingsButtonClick: function() {
		if (E4ds.user) {
			var userSettingsWindow = Ext.create('E4ds.view.UserSettings');
			userSettingsWindow.down('form').loadRecord(
					Ext.create('E4ds.model.User', E4ds.user));
			userSettingsWindow.down('#formSaveButton').addListener('click',
					this.onUserSettingsSaveButtonClick, this);
		}
	},

	onUserSettingsSaveButtonClick: function(button) {
		var win = button.up('window');
		var form = win.down('form');

		userService.updateSettings(form.getForm().getFieldValues(), function(result) {
			if (result.success) {
				win.close();
				E4ds.ux.window.Notification.info(i18n.successful, i18n.settings_saved);
			}
		});
	},

	onSelectionChange: function(treePanel, selected) {
		if (selected && selected.length === 1) {
			this.pushHistoryState(selected[0]);
		}
	},

	onRemove: function() {
		if (this.getTabPanel().items.length === 0) {
			this.getMenuTree().getSelectionModel().deselectAll();
		}
	},

	onTabChange: function(tabPanel, newCard) {
		var activeTab = this.getTabPanel().getActiveTab();
		if (activeTab) {
			this.getMenuTree().suspendEvents();
			this.getMenuTree().selectPath(activeTab.treePath);
			this.getMenuTree().resumeEvents();
		}
	},

	pushHistoryState: function(record) {
		var state = {
			view: record.raw.view,
			viewConfig: {
				icon: record.get('icon'),
				treePath: this.getPath(record),
				navigationId: record.getId()
			}
		};

		History.pushState(state, i18n.app_title + ': ' + record.get('text'), "?vid="
				+ record.getId());
	},

	showTab: function(state) {
		var view = state.view;
		if (view) {
			var tab = this.getTabPanel().child(
					'panel[navigationId=' + state.viewConfig.navigationId + ']');
			if (!tab) {
				var viewObject = Ext.create(view, state.viewConfig);
				tab = this.getTabPanel().add(viewObject);
			}
			this.getTabPanel().setActiveTab(tab);
		}
	},

	handleDirectResponse: function(event) {
		var me = this;
		Ext.getBody().unmask();
		if (event.type === 'rpc') {
			if (event.result && !event.result.success) {
				if (event.result.validations) {
					me.showValidationMessage(event.result.validations,
							i18n.correct_error_dialog_title);
				}
			}
		}
	},

	showValidationMessage: function(data, message) {
		var errorString = '<ul>';
		var error, fieldMatch, i = null;
		for (i in data) {
			if (data.hasOwnProperty(i)) {
				error = data[i];
				errorString += '<li>' + error.message + '</li>';
				fieldMatch = Ext.ComponentQuery.query('field[name=' + error.field + ']');
				if (fieldMatch.length) {
					fieldMatch[0].markInvalid(error.message);
				}
			}
		}
		errorString += '</ul>';
		Ext.Msg.alert(message, errorString);
	}

});
