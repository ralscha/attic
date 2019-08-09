Ext.define('Golb.view.post.Controller', {
	extend: 'Golb.view.base.TagObjectViewController',

	config: {
		formClassName: 'Golb.view.post.Form',
		objectName: 'Post',
		objectNamePlural: 'Posts',
		reloadAfterEdit: false,
		backAfterSave: false
	},

	constructor: function() {
		this.md = window.markdownit({
			linkify: true,
			html: true,
			highlight: function(str, lang) {
				if (lang && hljs.getLanguage(lang)) {
					try {
						return hljs.highlight(lang, str).value;
					}
					catch (__) {
					}
				}

				return '';
			}
		});
		this.callParent(arguments);
	},

	destroy: function() {
		if (this.md) {
			this.md = null;
		}
	},

	onTitleBlur: function(tf) {
		var slugTf = this.lookup('slugTf');
		if (Ext.isEmpty(slugTf.getValue())) {
			slugTf.setValue(Golb.Util.slugify(tf.getValue()));
		}
	},

	onSlugBlur: function() {
		var postId = this.getSelectedObject().getId();
		var slugTf = this.lookup('slugTf');
		this.getSelectedObject().set('slug', slugTf.getValue());
		postService.isSlugUnique(postId, slugTf.getValue(), function(isUnique) {
			slugTf.isSlugUnique = isUnique;
			this.lookup('editPanel').getForm().isValid();
		}, this);
	},

	erase: function() {
		this.eraseObject(this.getSelectedObject().get('subject'));
	},

	onTitleFilter: function(tf, newValue) {
		var store = this.getStore(this.getObjectStoreName());
		store.removeFilter('titleFilter');
		if (newValue) {
			var filterValue = newValue.toLowerCase();
			store.addFilter({
				id: 'titleFilter',
				filterFn: function(item) {
					var title = item.get('title');
					if (title && title.toLowerCase().indexOf(filterValue) >= 0) {
						return true;
					}
					var body = item.get('body');
					if (body && body.toLowerCase().indexOf(filterValue) >= 0) {
						return true;
					}
					return false;
				}
			});
		}
	},

	beforeLoadRecord: function(record) {
		record.set('bodyHtml', this.md.render(record.get('body')));
	},

	onBodyChange: function(tf, value) {
		this.lookup('bodyHtml').setValue(this.md.render(value));
	},

	onOpenPostClick: function(widget) {
		var record = widget.getWidgetRecord();
		window.open(window.location.origin + '/preview/' + record.get('slug'), '_blank');
	},

	onPublishClick: function(widget) {
		var record = widget.getWidgetRecord();
		postService.publish(record.getId(), function(time) {
			if (time) {
				record.set('published', new Date(time));
				record.commit();
				Golb.Util.successToast('Successfully published');
			}
			else {
				Golb.Util.errorToast(i18n.servererror);
			}
		});
	},

	onUnpublishClick: function(widget) {
		var record = widget.getWidgetRecord();
		postService.unpublish(record.getId(), function(flag) {
			if (flag) {
				record.set('published', null);
				record.commit();
				Golb.Util.successToast('Successfully unpublished');
			}
			else {
				Golb.Util.errorToast(i18n.servererror);
			}
		});
	}

});