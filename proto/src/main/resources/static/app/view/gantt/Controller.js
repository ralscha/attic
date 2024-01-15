Ext.define('Proto.view.gantt.Controller', {
	extend: 'Ext.app.ViewController',

	init: function() {

		var resourceStore = new Gnt.data.ResourceStore({
			autoLoad: true,
			proxy: {
				type: 'direct',
				directFn: 'ganttService.readResources'
			}
		});

		var assignmentStore = new Gnt.data.AssignmentStore({
			resourceStore: resourceStore
		});

		var taskStore = new Gnt.data.TaskStore({
			assignmentStore: assignmentStore,
			resourceStore: resourceStore,
			proxy: {
				type: 'direct',
				directFn: 'ganttService.readTasks'
			}
		});

		var g = new Gnt.panel.Gantt({
			// multiSelect: true,

			 // Object with editor and dataIndex defined
			 leftLabelField: {
			 dataIndex: 'Name'
			 },

						
			 // ... or an object with editor and renderer defined
			rightLabelField: {
				dataIndex: 'Id',
				renderer: function(value, record) {
					return '';
				}
			},

			eventRenderer: function(task) {
				if (assignmentStore.findExact('TaskId', task.data.Id) >= 0) {
					// This task has resources assigned, show a little icon
					return {
						ctcls: 'resources-assigned'
					};
				}
			},

			highlightWeekends: true,
			showTodayLine: true,
			loadMask: true,
			enableDependencyDragDrop: false,
			snapToIncrement: true,
			plugins: {
				ptype: 'scheduler_treecellediting',
				clicksToEdit: 1
			},

			startDate: new Date(2015, 1, 15),
			endDate: Sch.util.Date.add(new Date(2015, 1, 15), Sch.util.Date.WEEK, 20),
			viewPreset: 'weekAndDayLetter',

			columns: [ {
				xtype: 'namecolumn',
				width: 250
			}, {
				xtype: 'resourceassignmentcolumn',
				header: 'Assigned Resources',
				width: 150
			} ],
			taskStore: taskStore,
			stripeRows: true
		});

		this.getView().add(g);

	}

});