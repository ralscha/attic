Ext.define('Addressbook.controller.Viewport', {
	extend: 'Deft.mvc.ViewController',
	inject: [ 'messageBus', 'navigationStore', 'contactGroupsStore' ],
	requires: [ 'Addressbook.view.SideBar', 'Ext.tab.Panel', 'Addressbook.store.ContactGroups', 'Addressbook.view.OptionsList', 'Ext.ux.TabReorderer',
			'Ext.ux.TabCloseMenu', 'Addressbook.view.AppUsersList', 'Addressbook.view.ContactsList', 'Addressbook.view.ContactInfosList' ],

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
				itemclick: 'onTreeItemClick'
			}
		},
		tabPanel: {
			live: true,
			listeners: {
				tabchange: 'onTabChange'
			}
		}
	},

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
				Addressbook.ux.window.Notification.error('Error', 'Access denied');
			} else {
				Addressbook.ux.window.Notification.error('Error', e.message);
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
		}, Ext.create('Addressbook.view.SideBar', {
			region: 'west',
			width: 180
		}));
		Ext.resumeLayouts(true);

		Ext.direct.Manager.addProvider(this.heartbeat);
		this.contactGroupsStore.load();
		navigationService.getLoggedOnUsername(this.showLoggedOnUser, this);
	},

	onNavigationStoreLoad: function() {
		var state = History.getState();
		if (state && state.data) {
			this.showTab(state.data);
		}
	},

	showLoggedOnUser: function(fullname) {
		this.getLoggedOnLabel().setText(fullname);
	},

	getPath: function(node) {
		return node.parentNode ? this.getPath(node.parentNode) + "/" + node.getId() : "/" + node.getId();
	},

	onTreeItemClick: function(treeview, record, item, index, event, options) {
		this.pushHistoryState(record);
	},

	onTabChange: function(tabPanel, newCard) {
		var record = this.syncNavigation();
		if (record) {
			this.pushHistoryState(record);
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

		History.pushState(state, "Addressbook: " + record.get('text'), "?aid=" + record.getId());
	},

	showTab: function(state) {
		var view = state.view;
		var viewObject;
		if (view) {
			var tab = this.getTabPanel().child('panel[navigationId=' + state.viewConfig.navigationId + ']');
			if (!tab) {
				if (view === 'ContactGroups') {
					viewObject = state.viewConfig;
					viewObject.xtype = 'optionslist';
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
		var record = null;
		var activeTab = this.getTabPanel().getActiveTab();

		var selectionModel = this.getMenuTree().getSelectionModel();
		this.getMenuTree().expandPath(activeTab.treePath);

		var activeTabId = activeTab.navigationId;
		var selection = selectionModel.getLastSelected();
		var currentId = selection && selection.raw.id;

		if (activeTabId !== currentId) {
			record = this.getMenuTree().getStore().getNodeById(activeTabId);
			selectionModel.select(record);
		}

		//activeTab.fireEvent('activated');
		return record;
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

		for (var i in data) {
			var error = data[i];
			errorString += '<li>' + error.message + '</li>';
			var fieldMatch = Ext.ComponentQuery.query('field[name=' + error.field + ']');
			if (fieldMatch.length) {
				fieldMatch[0].markInvalid(error.message);
			}
		}
		errorString += '</ul>';
		Ext.Msg.alert(message, errorString);
	}
});
