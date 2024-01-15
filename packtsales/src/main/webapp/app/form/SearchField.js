Ext.define('Sales.form.SearchField', {
	extend: 'Ext.form.field.Trigger',
	alias: 'widget.myapp-searchfield',
	trigger1Cls: Ext.baseCSSPrefix + 'form-clear-trigger',
	trigger2Cls: Ext.baseCSSPrefix + 'form-search-trigger',
	hasSearch: false,
	paramName: 'query',
	initComponent: function() {
		var me = this;
		me.callParent(arguments);
		me.on('specialkey', function(f, e) {
			if (e.getKey() == e.ENTER) {
				me.onTrigger2Click();
			}
		});
	},
	afterRender: function() {
		this.callParent();
		this.triggerCell.item(0).setDisplayed(false);
	},
	onTrigger1Click: function() {
		var me = this;
		if (me.hasSearch) {
			me.setValue('');
			me.hasSearch = false;
			me.triggerCell.item(0).setDisplayed(false);
			location.href = me.urlRoot;
		}
	},
	onTrigger2Click: function() {
		var me = this, value = me.getValue();
		if (value.length > 0) {
			me.triggerCell.item(0).setDisplayed(true);
			location.href = Ext.String.format('{0}q={1}', me.urlRoot, value);
		}
	}
});
