Ext.define('E4ds.model.PollChart', {
	extend: 'Ext.data.Model',
	fields: [ 'id', {
		name: 'time',
		type: 'string'
	}, {
		name: 'processCpuLoad',
		type: 'int'
	}, {
		name: 'systemCpuLoad',
		type: 'int'
	}, {
		name: 'freePhysicalMemorySize',
		type: 'float'
	}, {
		name: 'totalPhysicalMemorySize',
		type: 'float'		
	}, {
		name: 'usedHeapMemory',
		type: 'float'			
	}, {
		name: 'committedHeapMemory',
		type: 'float'			
	}, {
		name: 'maxHeapMemory',
		type: 'float'			
	}]
});