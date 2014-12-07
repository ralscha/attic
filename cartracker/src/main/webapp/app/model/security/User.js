/**
 * {@link Ext.data.Model} for Security User
 */
Ext.define('CarTracker.model.security.User', {
	extend: 'Ext.data.Model',
	fields: [
	// non-relational properties
	{
		name: 'firstName',
		type: 'string',
		persist: false
	}, {
		name: 'lastName',
		type: 'string',
		persist: false
	}, {
		name: 'username',
		type: 'string',
		persist: false
	}, {
		name: 'roles',
		type: 'any',
		persist: false
	} ],

	inRole: function(roleName) {
		var me = this;
		if (roleName) {
			if (Ext.isArray(roleName)) {
				for (var i = 0; i < roleName.length; i++) {					
					if (Ext.Array.contains(me.get('roles'), roleName[i])) {
						return true;
					}
				}
				return false;
			}
			return Ext.Array.contains(me.get('roles'), roleName);
		}
		return true;
	}
});