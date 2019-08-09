Ext.define('Golb.view.binary.Controller', {
	extend: 'Golb.view.base.ViewController',

	config: {
		formClassName: 'Golb.view.binary.Form',
		objectName: 'Binary',
		objectNamePlural: 'Binaries',
		reloadAfterEdit: false,
		backAfterSave: true
	},

	erase: function() {
		this.eraseObject(this.getSelectedObject().get('name'));
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

	onDownloadClick: function(widget) {
		var record = widget.getWidgetRecord();
		var name = encodeURI(record.get('name'));
		var id = record.getId();
		window.open('binary/' + id + '/' + name, '_blank');
	},

	onCopyUrlClick: function(widget) {
		var record = widget.getWidgetRecord();
		var link = 'binary/' + record.getId() + '/' + record.get('name');

		var fake = document.createElement('textarea');
		fake.value = link;

		document.body.appendChild(fake);
		fake.select();
		document.execCommand('copy');
		document.body.removeChild(fake);
		fake = null;
		Golb.Util.successToast('URL copied');
	},

	onFileFieldChange: function(field, value) {
		var me = this;
		this.getView().mask(i18n.working);
		var reader = new FileReader();
		var file = field.fileInputEl.dom.files[0];

		var so = this.getSelectedObject();
		so.set('name', file.name);
		so.set('type', file.type);
		so.set('size', file.size);
		so.set('lastModifiedDate', file.lastModifiedDate);
		
		this.lookup('editPanel').getForm().loadRecord(so);
		
		reader.onload = function(e) {
			so.set('data', e.target.result);
			me.getView().unmask();
		};

		reader.readAsDataURL(file);
	}

});