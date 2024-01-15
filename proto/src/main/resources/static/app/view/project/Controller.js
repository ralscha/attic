Ext.define('Proto.view.project.Controller', {
	extend: 'Proto.base.ViewController',
	requires: [ 'Proto.view.project.Window' ],

	init: function() {
		new Ext.LoadMask({
			target: this.getView(),
			msg: i18n.loading,
			store: this.getStore('projects')
		});
	},

	onFilterSpecialKey: function(tf, e) {
		if (e.getKey() === e.ENTER) {
			this.onFilter(tf);
		}
	},

	onFilter: function(tf, trigger) {
		if (trigger && trigger.id === 'clear') {
			tf.setValue('');
		}

		var value = tf.getValue();
		var store = this.getStore('projects');
		store.clearFilter();
		if (value) {
			this.getViewModel().set('filter', value);

			var nameFilter = new Ext.util.Filter({
				filterFn: function(r) {
					return (r.data.name.toLowerCase().indexOf(value) !== -1);
				}
			});
			store.addFilter(nameFilter);
		}
		else {
			this.getViewModel().set('filter', null);
		}
	},

	onItemDoubleClick: function(grid, record) {
		this.editProject('Edit Project', this.getViewModel().get('selectedProject'));
	},

	newProject: function() {
		this.getViewModel().set('selectedProject', null);
		this.editProject('New Project', new Proto.model.Project());
	},

	editProject: function(title, record) {
		var editWin = new Proto.view.project.Window({
			title: title
		});
		this.getView().add(editWin);
		editWin.show();

		var form = editWin.down('form');
		form.loadRecord(record);
		form.isValid();
	},

	destroyProject: function(record) {
		this.destroyEntity(record, record.get('lastName'), function() {
			this.getViewModel().set('selectedProject', null);
			this.getStore('projects').reload();
		}, this);
	},

	buildContextMenuItems: function(record) {
		return [ {
			text: i18n.edit,
			glyph: 0xe803,
			handler: this.editProject.bind(this, i18n.project_edit, record)
		}, {
			text: i18n.destroy,
			glyph: 0xe806,
			handler: this.destroyProject.bind(this, record)
		} ];
	}

});