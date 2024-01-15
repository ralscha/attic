Ext.define('Changelog.controller.NavigationController', {
	extend: 'Deft.mvc.ViewController',
	
	control: {
		menuTree: {
			itemclick: 'onTreeItemClick'
		},
		tabPanel: {
			tabchange: 'syncNavigation'			
		},
		loggedOnLabel: true,
		optionButton: {
			click: 'getUser'
		}
	},

	init: function() {
		securityService.getLoggedOnUsername(this.showLoggedOnUser, this);
		configService.getValue('infoTxt', this.showUserInfo, this);
	},

	showUserInfo: function(info) {
		if(info) {
			Ext.Msg.alert(i18n.config_alert, info);
		}
	},
	
	showLoggedOnUser: function(fullname) {
		this.getLoggedOnLabel().setText(fullname);
	},

	getPath: function(node) {
		return node.parentNode ? this.getPath(node.parentNode) + "/" + node.getId() : "/" + node.getId();
	},

	getUser: function() {
		userService.getLoggedOnUser(this.openOptionsWindow, this);
	},

	openOptionsWindow: function(result) {
		if (result) {
			var userOptionWindow = Ext.create('Changelog.view.navigation.UserOptions');
			userOptionWindow.controller = this;
			userOptionWindow.getForm().loadRecord(Ext.create('Changelog.model.User', result));
		}
	},

	updateUser: function(editWindow) {
		var form = editWindow.getForm(), record = form.getRecord();

		form.submit({
			params: {
				id: record ? record.data.id : ''
			},
			scope: this,
			success: function() {
				editWindow.close();
				Changelog.ux.window.Notification.info(i18n.successful, i18n.options_saved);
			}
		});
	},

	onTreeItemClick: function(treeview, record, item, index, event, options) {
		var view = record.raw.view, tab = this.getTabPanel().child('panel[navigationId='+record.raw.id+']');
		if (view) {
			if (!tab) {
				var viewObject = Ext.create(view, {
					icon: record.raw.icon,
					treePath: this.getPath(record),
					navigationId: record.raw.id
				});
				
				tab = this.getTabPanel().add(viewObject);
			}
			this.getTabPanel().setActiveTab(tab);
		}
	},

	syncNavigation: function() {
		var activeTab = this.getTabPanel().getActiveTab();
		var selectionModel = this.getMenuTree().getSelectionModel();
		this.getMenuTree().expandPath(activeTab.treePath);

		var activeTabId = activeTab.navigationId;
		var selection = selectionModel.getLastSelected();
		var currentId = selection && selection.raw.id;

		if (activeTabId !== currentId) {
			selectionModel.select(this.getMenuTree().getStore().getNodeById(activeTabId));
		}

		activeTab.fireEvent('activated');
	}

});
