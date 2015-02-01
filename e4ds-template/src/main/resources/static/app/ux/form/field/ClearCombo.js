Ext.define('E4ds.ux.form.field.ClearCombo', {
	extend: 'Ext.form.field.ComboBox',
	alias: 'widget.clearcombo',

	trigger2Cls: 'x-form-clear-trigger',

	onTrigger2Click: function() {
		this.clearValue();
	}
});