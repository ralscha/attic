Ext.define('Proto.base.ViewController', {
	extend: 'Ext.app.ViewController',

	onItemContextMenu: function(view, record, item, index, e, eOpts) {
		e.stopEvent();
		this.showContextMenu(record, e.getXY());
	},

	onActionColumnClick: function(grid, rowIndex, colIndex, item, e, record, row) {
		this.showContextMenu(record, null, row);
	},

	showContextMenu: function(record, xy, item) {
		var items = this.buildContextMenuItems(record);

		if (this.actionMenu) {
			this.actionMenu.destroy();
		}

		this.actionMenu = new Ext.menu.Menu({
			items: items,
			border: true
		});

		if (xy) {
			this.actionMenu.showAt(xy);
		}
		else {
			this.actionMenu.showBy(item);
		}
	},

	destroyEntity: function(record, errormsg, successCallback, scope) {
		Ext.Msg.confirm(i18n.attention, Ext.String.format(i18n.destroyConfirmMsg, errormsg), function(choice) {
			if (choice === 'yes') {
				record.erase({
					callback: function(records, operation, success) {
						if (success) {
							Proto.Util.successToast(i18n.destroysuccessful);
                                                        if (successCallback) {
							  successCallback.call(scope);
                                                        }
						}
						else {
							Proto.Util.errorToast(i18n.servererror);
						}
					},
					scope: this
				});
			}
		}, this);
	}

});