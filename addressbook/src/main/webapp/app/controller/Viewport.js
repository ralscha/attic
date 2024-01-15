Ext.define('Ab.controller.Viewport', {
	extend: 'Deft.mvc.ViewController',
	requires: [ 'Ab.view.UserSettings' ],
	inject: [ 'messageBus', 'navigationStore', 'contactGroupsStore' ],

	requires: [ 'Ab.view.SideBar', 'Ab.store.ContactGroups' ],

	observe: {
		messageBus: {
			loginsuccessful: 'onLoginSuccessful'
		},
		navigationStore: {
			load: 'onNavigationStoreLoad'
		}
	},

	control: {
		login: true,
		loggedOnLabel: true,
		menuTree: {
			live: true,
			listeners: {
				selectionchange: 'onSelectionChange'
			}
		},
		tabPanel: {
			live: true,
			listeners: {
				tabchange: 'onTabChange',
				remove: 'onRemove'
			}
		},
		settingsButton: {
			click: 'onSettingsButtonClick'
		}
	},

//	init: function() {
//		securityService.getLoggedOnUser(this.afterLoggedOnUserReceived, this);
//	},
//
//	afterLoggedOnUserReceived: function(user) {
//		var me = this;
//
//		Ab.user = user;
//		this.getLoggedOnLabel().setText(user.firstName + ' ' + user.name);
//
//		History.Adapter.bind(window, 'statechange', function() {
//			var state = History.getState();
//			me.showTab(state.data);
//		});
//
//		Ext.direct.Manager.on('event', function(e) {
//			me.handleDirectResponse(e);
//		});
//
//	},
	
	init: function() {
		var me = this;

		me.heartbeat = new Ext.direct.PollingProvider({
			type: 'polling',
			interval: 5 * 60 * 1000, // 5 minutes
			url: POLLING_URLS.heartbeat
		});

		Ext.direct.Manager.on('event', function(e) {
			me.handleDirectResponse(e);
		});

		Ext.direct.Manager.on('exception', function(e) {
			if (e.message === 'accessdenied') {
				Ab.ux.window.Notification.error(i18n.error, i18n.error_accessdenied);
			} else {
				Ab.ux.window.Notification.error(i18n.error, e.message);
			}
		});

		History.Adapter.bind(window, 'statechange', function() {
			var state = History.getState();
			me.showTab(state.data);
		});

		loginService.getStatus(function(detail) {
			if (detail.loggedIn) {
				me.onLoginSuccessful();
			}
		});
	},	
		
	onLoginSuccessful: function() {
		Ext.suspendLayouts();
		this.getLoggedOnLabel().setVisible(true);
		this.getView().remove(this.getLogin());
		this.getView().add({
			region: 'center',
			xtype: 'tabpanel',
			itemId: 'tabPanel',
			plugins: [ Ext.create('Ext.ux.TabReorderer'), Ext.create('Ext.ux.TabCloseMenu') ],
			plain: true
		}, Ext.create('Ab.view.SideBar', {
			region: 'west',
			width: 180
		}));
		Ext.resumeLayouts(true);

		Ext.direct.Manager.addProvider(this.heartbeat);
		this.contactGroupsStore.load();
		securityService.getLoggedOnUser(this.afterLoggedOnUserReceived, this);
	},	
	
	afterLoggedOnUserReceived: function(user) {
		Ab.user = user;
		this.getLoggedOnLabel().setText(user.firstName + ' ' + user.name);
	},
	
	onNavigationStoreLoad: function() {
		var state = History.getState();
		if (state && state.data) {
			this.showTab(state.data);
		}
	},

	getPath: function(node) {
		return node.parentNode ? this.getPath(node.parentNode) + "/" + node.getId() : "/" + node.getId();
	},

	onSettingsButtonClick: function() {
		if (Ab.user) {
			var userSettingsWindow = Ext.create('Ab.view.UserSettings');
			userSettingsWindow.down('form').loadRecord(Ext.create('Ab.model.User', Ab.user));
			userSettingsWindow.down('#editFormSaveButton').addListener('click', this.onUserSettingsSaveButtonClick, this);
		}
	},

	onUserSettingsSaveButtonClick: function(button) {
		var win = button.up('window');
		var form = win.down('form');

		userService.updateSettings(form.getForm().getFieldValues(), function(result) {
			if (result.success) {
				win.close();
				Ab.ux.window.Notification.info(i18n.successful, i18n.settings_saved);
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
		this.syncNavigation();
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

		History.pushState(state, i18n.app_title + ': ' + record.get('text'), "?vid=" + record.getId());
	},

	showTab: function(state) {
		var view = state.view;
		var viewObject;
		if (view) {
			var tab = this.getTabPanel().child('panel[navigationId=' + state.viewConfig.navigationId + ']');
			if (!tab) {
				if (view === 'ContactGroups') {
					viewObject = state.viewConfig;
					viewObject.xtype = 'optionlist';
					viewObject.title = 'Contact Groups';
					viewObject.store = this.contactGroupsStore;
				} else {
					viewObject = Ext.create(view, state.viewConfig);
				}
				tab = this.getTabPanel().add(viewObject);
			}
			this.getTabPanel().setActiveTab(tab);
		}
	},

	syncNavigation: function(e) {
		var activeTab = this.getTabPanel().getActiveTab();
		if (activeTab) {
			this.getMenuTree().selectPath(activeTab.treePath);
		}
	},

	handleDirectResponse: function(event) {
		var me = this;
		Ext.getBody().unmask();
		if (event.type === 'rpc') {
			if (event.result && !event.result.success) {
				if (event.result.validations) {
					me.showValidationMessage(event.result.validations, 'Please correct the following errors');
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
