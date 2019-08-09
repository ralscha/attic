Ext.define('Golb.view.base.TagObjectViewController', {
	extend: 'Golb.view.base.FilterStoreViewController',

	onTagChange: function(cmp, newValue) {
		var store = this.getStore(this.getObjectStoreName());
		store.removeFilter('tagsFilter');
		if (newValue && newValue.length > 0) {
			store.addFilter({
				id: 'tagsFilter',
				filterFn: function(item) {
					var tags = item.get('tags');
					if (tags) {
						var i;
						for (i = 0; i < newValue.length; i++) {
							if (tags.indexOf(newValue[i]) >= 0) {
								return true;
							}
						}
					}
					return false;
				}
			});
		}
	},

	afterSuccessfulSave: function() {
		Ext.getStore('tags').load({
			scope: this,
			callback: function() {	
				this.getView().down('grid').getView().refresh();
			}
		});
	},

	tagsRenderer: function(value) {
		if (!Ext.isEmpty(value)) {
			var tagStore = Ext.getStore('tags');
			var result = "";
			for (var i = 0; i < value.length; i++) {
				var tagId = value[i];
				var tag = tagStore.getById(tagId);
				result += "<span class=\"label label-info\">";
				if (tag) {
					result += tag.get('name');
				}
				else {
					result += '...';
				}
				result += "</span>&nbsp;";
			}
			return result;
		}
		return value;
	}
});