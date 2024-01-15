Ext.define('Proto.view.person.Controller', {
	extend: 'Proto.base.ViewController',
	requires: [ 'Proto.view.person.Window' ],

	init: function() {
		new Ext.LoadMask({
			target: this.getView(),
			msg: i18n.loading,
			store: this.getStore('persons')
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
		var store = this.getStore('persons');
		store.clearFilter();
		if (value) {
			this.getViewModel().set('filter', value);

			var nameFilter = new Ext.util.Filter({
				filterFn: function(r) {
					var str = ';' + r.data.lastName + ';' + r.data.firstName;
					return (str.toLowerCase().indexOf(value) !== -1);
				}
			});
			store.addFilter(nameFilter);
		}
		else {
			this.getViewModel().set('filter', null);
		}
	},

	onItemDoubleClick: function(grid, record) {
		this.editPerson('Edit Person', this.getViewModel().get('selectedPerson'));
	},

	newPerson: function() {
		this.getViewModel().set('selectedPerson', null);
		this.editPerson('New Person', new Proto.model.Person());
	},

	editPerson: function(title, record) {
		var editWin = new Proto.view.person.Window({
			title: title
		});
		this.getView().add(editWin);
		editWin.show();

		var form = editWin.down('form');
		form.loadRecord(record);
		form.isValid();
	},

	destroyPerson: function(record) {
		this.destroyEntity(record, record.get('lastName'), function() {
			this.getViewModel().set('selectedPerson', null);
			this.getStore('persons').reload();
		}, this);
	},

	buildContextMenuItems: function(record) {
		return [ {
			text: i18n.edit,
			glyph: 0xe803,
			handler: this.editPerson.bind(this, i18n.person_edit, record)
		}, {
			text: i18n.destroy,
			glyph: 0xe806,
			handler: this.destroyPerson.bind(this, record)
		} ];
	}

});