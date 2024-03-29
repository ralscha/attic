Ext.define("Ab.model.Role", {
	extend: "Ext.data.Model",
	idProperty: 'name',
	fields: [ {
		name: "name",
		type: "string"
	} ],
	proxy: {
		type: "direct",
		directFn: "userService.readRoles"
	}
});
