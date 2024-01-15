Ext.define('Changelog.controller.CustomerBuildController', {
	extend: 'Deft.mvc.ViewController',

	control: {
		view: {
			close: 'onCustomerBuildsClose'
		},
		buildGrid: {
			itemclick: 'enableBuildActions',
			cancelEdit: 'onCancelEdit',
			edit: 'onEdit'
		},
		deleteButton: {
			click: 'deleteBuild'
		},
		editButton: {
			click: 'editBuild'
		},
		addButton: {
			click: 'addBuild'
		}
	},	
	
	updateBuilds: function(listController, customerId, customerName) {
		this.getView().setTitle(i18n.customer_build_versions + ' : ' + customerName);
		this.listController = listController;
		this.customerId = customerId;
		
		this.getBuildGrid().getStore().load({
			params: {
				customerId: this.customerId
			}
		});
	},
	
	onCustomerBuildsClose: function() {
		this.listController.doGridRefresh();	
	},
	
	deleteBuild: function(button) {
		var record = this.getBuildGrid().getSelectionModel().getSelection()[0];
		if (record) {
			Ext.Msg.confirm(i18n.customer_build_delete + '?', i18n.delete_confirm + ' ' + record.data.versionNumber,
					this.afterConfirmDeleteCustomerBuild, this);
		}		
	},
	
	afterConfirmDeleteCustomerBuild: function(btn) {
		if (btn === 'yes') {
			var buildGrid = this.getBuildGrid();
			var record = buildGrid.getSelectionModel().getSelection()[0];
			if (record) {
				buildGrid.getStore().remove(record);
				this.toggleBuildEditButtons(false);
				Changelog.ux.window.Notification.info(i18n.successful, i18n.customer_build_deleted);
			}
		}
	},

	editBuild: function() {
		var buildGrid = this.getBuildGrid();
		var record = buildGrid.getSelectionModel().getSelection()[0];
		if (record) {
			var rowEditing = buildGrid.plugins[0];
			rowEditing.cancelEdit();			
			rowEditing.startEdit(record, 0);
		}
	},
	
	addBuild: function() {
		var buildGrid = this.getBuildGrid();
		var rowEditing = buildGrid.plugins[0];
		var store = buildGrid.getStore();
		var newVersionNumber = 'NEW';
		
		rowEditing.cancelEdit();

		if (store.getTotalCount() > 0) {
			var model = store.getAt(0);
			var lastVersionNumber = model.data.versionNumber;
			var pos = lastVersionNumber.lastIndexOf('.');
			newVersionNumber = lastVersionNumber.substring(0, pos) + '.' + (Number(lastVersionNumber.substring(pos+1))+1);			
		}
		
		var newBuild = Ext.create('Changelog.model.CustomerBuild', {
			customerId: this.customerId,
			versionNumber: newVersionNumber,
			versionDate: new Date()			
		});		
		buildGrid.getStore().autoSync = false;
		buildGrid.getStore().insert(0, newBuild);
		rowEditing.startEdit(newBuild, 0);
		buildGrid.getStore().autoSync = true;
	},
	
	onCancelEdit: function(editor, e) {
		if (e.record.phantom) {
			this.getBuildGrid().getStore().removeAt(0);
		}
	},
	
	onEdit: function(editor, e) {
		if (e.record.dirty) {
			Changelog.ux.window.Notification.info(i18n.successful, i18n.customer_build_saved);
		} 
	},
	
	enableBuildActions: function(button, record) {
		this.toggleBuildEditButtons(true);
	},
		
	toggleBuildEditButtons: function(enable) {
		this.toggleButton(enable, this.getEditButton());
		this.toggleButton(enable, this.getDeleteButton());
	},
	
	toggleButton: function(enable, button) {
		if (enable) {
			button.enable();
		} else {
			button.disable();
		}
	}	

});