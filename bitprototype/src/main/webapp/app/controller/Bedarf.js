Ext.define('BitP.controller.Bedarf', {
	extend: 'BitP.controller.CrudBase',
	requires: [ 'BitP.view.bedarf.Edit', 'BitP.view.workflow.List' ],

	control: {
		filterField: false
	},

	destroyConfirmMsg: function(record) {
		return record.get('titel') + ' ' + i18n.reallyDestroy;
	},

	editWindowClass: 'BitP.view.bedarf.Edit',

	createModel: function() {
		return Ext.create('BitP.model.Bedarf', {
			status: 'ENTWURF'
		});
	},

	isReadonly: function(record) {
		if (record) {
			var status = record.get('status');
			return !(BitP.user.role === 'BEDARF' && (status === 'ENTWURF' || status === 'ZURUECKGEWIESEN'));
		}

		return false;
	},

	buildContextMenuItems: function(record) {
		var me = this;
		var items = this.callParent(arguments);
		var status = record.get('status');

		items.push({
			xtype: 'menuseparator'
		});

		if (BitP.user.role === 'BEDARF') {
			if (status === 'ENTWURF' || status === 'ZURUECKGEWIESEN') {
				items.push({
					text: 'Freigeben',
					handler: Ext.bind(me.bedarfFreigeben, me, [ record ])
				});
			}
		} else if (BitP.user.role === 'EINKAUF') {
			if (status === 'BEDARF') {
				items.push({
					text: 'Anfragen',
					handler: Ext.bind(me.bedarfAnfragen, me, [ record ])
				});
				items.push({
					text: 'Zurückweisen',
					handler: Ext.bind(me.bedarfZurueckweisen, me, [ record ])
				});
			} else if (status === 'ANFRAGE') {
				items.push({
					text: 'Abschliessen',
					handler: Ext.bind(me.bedarfAbschliessen, me, [ record ])
				});
			}
		}

		items.push({
			text: 'Workflow History anzeigen',
			handler: Ext.bind(me.showWorkflowHistory, me, [ record ])
		});

		return items;
	},

	showWorkflowHistory: function(record) {

		var bedarfStore = Ext.create('BitP.store.BedarfWorkflows');
		bedarfStore.load({
			params: {
				bedarfId: record.getId()
			}
		});

		Ext.create('Ext.window.Window', {
			title: 'Workflow History',
			width: 600,
			maxHeight: 600,
			autoScroll: true,
			modal: true,
			y: 100,
			items: [ Ext.create('BitP.view.workflow.List', {
				store: bedarfStore
			}) ]
		}).show();
	},

	bedarfFreigeben: function(record) {
		var id = record.getId();
		this.bedarfStatusChange(record, 'Bedarf freigeben', 'Bedarf Nr. ' + id + ' freigeben', 'BEDARF');
	},

	bedarfAnfragen: function(record) {
		var id = record.getId();
		this.bedarfStatusChange(record, 'Bedarf anfragen', 'Bedarf Nr. ' + id + ' an die Liefernaten freigeben', 'ANFRAGE');
	},

	bedarfZurueckweisen: function(record) {
		var id = record.getId();
		this.bedarfStatusChange(record, 'Bedarf zurückweisen', 'Bedarf Nr. ' + id + ' zurückweisen', 'ZURUECKGEWIESEN');
	},

	bedarfAbschliessen: function(record) {
		var id = record.getId();
		this.bedarfStatusChange(record, 'Bedarf abschliessen', 'Bedarf Nr. ' + id + ' abschliessen', 'ABGESCHLOSSEN');
	},

	bedarfStatusChange: function(record, title, msg, status) {
		var id = record.getId();
		Ext.Msg.minWidth = 300;
		Ext.Msg.show({
			title: title,
			msg: msg,
			fn: function(buttonId, text, opt) {
				if (buttonId == 'ok') {
					bedarfService.updateStatus(id, status, text, function(result) {
						record.set('status', result.status);
						record.commit();
					});
				}
			},
			scope: this,
			width: 350,
			multiline: true,
			buttons: Ext.MessageBox.OKCANCEL
		});
	}

});