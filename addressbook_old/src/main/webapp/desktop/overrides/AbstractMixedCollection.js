Ext.define('overrides.AbstractMixedCollection', {
	override: 'Ext.util.AbstractMixedCollection',

	updateKey: function(oldKey, newKey) {
		var me = this, map = me.map, index = me.indexOfKey(oldKey),
		// Important: Take reference to indexMap AFTER indexOf call which may rebuild it.
		indexMap = me.indexMap, item;

		if (index > -1) {
			item = map[oldKey];
			delete map[oldKey];
			delete indexMap[oldKey];
			map[newKey] = item;
			indexMap[newKey] = index;
			me.keys[index] = newKey;

			// indexGeneration will be in sync since we called indexOfKey
			// And we kept it all in sync, so now generation changes we keep the indexGeneration matched
			me.indexGeneration = ++me.generation;
		}
	}
});