Ext.define('Golb.view.tag.Controller', {
	extend: 'Golb.view.base.ViewController',

	config: {
		formClassName: 'Golb.view.tag.Form',
		objectName: 'Tag',
		objectNamePlural: 'Tags',
		reloadAfterEdit: false,
		backAfterSave: true
	},

	erase: function() {
		this.eraseObject(this.getSelectedObject().get('name'), function() {
			Ext.getStore('tags').load();
		});
	},

	onNameFilter: function(tf, newValue) {
		var store = this.getStore(this.getObjectStoreName());
		store.removeFilter('nameFilter');
		if (newValue) {
			var filterValue = newValue.toLowerCase();
			store.addFilter({
				id: 'nameFilter',
				filterFn: function(item) {
					var name = item.get('name');
					if (name && name.toLowerCase().indexOf(filterValue) >= 0) {
						return true;
					}
					return false;
				}
			});
		}
	},
	
	afterSuccessfulSave: function() {
		Ext.getStore('tags').load();
	}

});