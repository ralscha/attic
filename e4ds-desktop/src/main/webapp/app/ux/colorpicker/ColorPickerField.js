/**
 * @class Ext.ux.form.ColorPickerField
 * @extends Ext.form.field.Picker This class makes Ext.ux.AdvancedColorPicker
 *          available as a form field.
 * @license: BSD
 * @author: Olav Snoek (extjs id: osnoekie)
 * @constructor Creates a new ColorPickerField
 * @param {Object}
 *            config Configuration options
 * @version 1.0.0
 */

Ext.define('E4desk.ux.colorpicker.ColorPickerField', {
	extend : 'Ext.form.field.Picker',
	requires : [ 'E4desk.ux.colorpicker.ColorPicker' ],
	alias : 'widget.ux.colorpickerfield',
	matchFieldWidth : false,

	createPicker : function() {
		var me = this;

		return Ext.create('E4desk.ux.colorpicker.ColorPicker', {
			floating : true,
			focusOnShow : true,
			baseCls : Ext.baseCSSPrefix + 'colorpicker',
			listeners : {
				scope : me,
				select : me.onSelect
			}
		});
	},
	onSelect : function(picker, value) {
		var me = this, hex = '#' + value;

		me.setValue(hex);
		me.fireEvent('select', me, hex);
		me.collapse();
	},

	onExpand : function(picker) {
		var me = this, value = me.getValue();
		me.picker.setValue(value);
	}
});