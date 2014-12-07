/**
 * Controller for all workflow-related management functionality
 */
Ext.define('CarTracker.controller.Workflows', {
	extend: 'CarTracker.controller.Base',
	stores: [ 'Workflows' ],
	views: [ 'workflow.List' ],
	refs: [ {
		ref: 'WorkflowList',
		selector: '[xtype=workflow.list]'
	} ],
	init: function() {
		this.listen({
			controller: {
				'#Workflows': {
					approve: this.approveWorkflow,
					reject: this.rejectWorkflow,
					restart: this.restartWorkflow,
					view: this.showHistory
				}
			},
			component: {
				'grid[xtype=workflow.list]': {
					beforerender: this.loadWorkflowHistory
				}
			},
			global: {},
			store: {}
		});
	},

	/**
	 * Loads workflow history store
	 * 
	 * @param {Ext.grid.Panel}
	 * @param {Object}
	 *            eOpts The options object passed to {@link Ext.util.Observable.addListener}
	 */
	loadWorkflowHistory: function(grid, eOpts) {
		var store = grid.getStore();
		store.load({
			params: {
				carId: grid.carId
			}
		});
	},

	/**
	 * Displays all workflow history for the selected Car
	 * 
	 * @param {Ext.view.View}
	 *            view
	 * @param {Ext.data.Record}
	 *            record The record that belongs to the item
	 * @param {HTMLElemen}
	 *            item The item's element
	 * @param {Number}
	 *            index The item's index
	 * @param {Ext.EventObject}
	 *            e The raw event object
	 * @param {Object}
	 *            eOpts The options object passed to {@link Ext.util.Observable.addListener}
	 */
	showHistory: function(view, record, item, index, e, eOpts) {
		// create ad-hoc window
		Ext.create('Ext.window.Window', {
			title: 'Workflow History',
			iconCls: 'icon_workflow',
			width: 600,
			maxHeight: 600,
			autoScroll: true,
			modal: true,
			y: 100,
			items: [ {
				xtype: 'workflow.list',
				carId: record.get('id')
			} ]
		}).show();
	},

	/**
	 * Submits an "Approve" workflow action
	 * 
	 * @param {Ext.view.View}
	 *            view
	 * @param {Ext.data.Record}
	 *            record The record that belongs to the item
	 * @param {HTMLElemen}
	 *            item The item's element
	 * @param {Number}
	 *            index The item's index
	 * @param {Ext.EventObject}
	 *            e The raw event object
	 * @param {Object}
	 *            eOpts The options object passed to {@link Ext.util.Observable.addListener}
	 */
	approveWorkflow: function(view, record, item, index, e, eOpts) {
		var me = this;
		me.handleWorkflowAction('Approve', record);
	},

	/**
	 * Submits a "Reject" workflow action
	 * 
	 * @param {Ext.view.View}
	 *            view
	 * @param {Ext.data.Record}
	 *            record The record that belongs to the item
	 * @param {HTMLElemen}
	 *            item The item's element
	 * @param {Number}
	 *            index The item's index
	 * @param {Ext.EventObject}
	 *            e The raw event object
	 * @param {Object}
	 *            eOpts The options object passed to {@link Ext.util.Observable.addListener}
	 */
	rejectWorkflow: function(view, record, item, index, e, eOpts) {
		var me = this;
		me.handleWorkflowAction('Reject', record);
	},

	/**
	 * Submits a "Restart" workflow action
	 * 
	 * @param {Ext.view.View}
	 *            view
	 * @param {Ext.data.Record}
	 *            record The record that belongs to the item
	 * @param {HTMLElemen}
	 *            item The item's element
	 * @param {Number}
	 *            index The item's index
	 * @param {Ext.EventObject}
	 *            e The raw event object
	 * @param {Object}
	 *            eOpts The options object passed to {@link Ext.util.Observable.addListener}
	 */
	restartWorkflow: function(view, record, item, index, e, eOpts) {
		var me = this;
		me.handleWorkflowAction('Restart', record);
	},

	/**
	 * Common interface for submitting workflow action to the server
	 * 
	 * @param {String}
	 *            action
	 * @param {Ext.data.Record}
	 *            record
	 */
	handleWorkflowAction: function(action, record) {
		var me = this, msg;
		switch (action) {
		case 'Approve':
		case 'Reject':
			msg = 'To <strong>' + action + '</strong> this workflow step, please enter a justification below.';
			break;
		case 'Restart':
			msg = 'To <strong>' + action + '</strong> the workflow for this record, please enter a justification below.';
			break;
		}

		Ext.Msg.minWidth = 300;
		Ext.Msg.show({
			title: 'Workflow Management',
			msg: msg,
			fn: function(buttonId, text, opt) {
				if (buttonId == 'ok') {
					// make sure a message was entered
					if (Ext.isEmpty(text)) {
						Ext.Msg.alert('Attention', 'Please enter a justification for your action', function() {
							me.handleWorkflowAction(action, record);
						});
						return false;
					}
					
					workflowService.updateStatus(record.get('id'), action, text, function(result) {
						record.set('statusId', result.statusId);
						record.set('statusName', result.statusName);
					});

				}
			},
			scope: this,
			width: 350,
			multiline: true,
			buttons: Ext.MessageBox.OKCANCEL
		});
	},

	/**
	 * Handy method for checking whether the authenticated user has workflow permissions at a particular status
	 * 
	 * @param {Number}
	 *            status
	 */
	hasWorkflowPermission: function(status) {
		var hasPermission = false, user = CarTracker.loggedInUser;
		switch (status) {
		case 'Initiated':
			hasPermission = user.inRole('ADMIN') || user.inRole('CONTENT_MANAGER') || user.inRole('AUDITOR') ? true : false;
			break;
		case 'In-Audit':
			hasPermission = user.inRole('ADMIN') || user.inRole('AUDITOR') ? true : false;
			break;
		case 'In-Review':
		case 'Approved':
		case 'Rejected':
			hasPermission = user.inRole('ADMIN') ? true : false;
			break;
		}
		return hasPermission;
	}
});