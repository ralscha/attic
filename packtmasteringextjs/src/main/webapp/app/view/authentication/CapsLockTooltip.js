Ext.define('Packt.view.authentication.CapsLockTooltip', {
	extend: 'Ext.tip.QuickTip',
	alias: 'widget.capslocktooltip',
	target: 'password',
	anchor: 'top',
	anchorOffset: 60,
	width: 300,
	dismissDelay: 0,
	autoHide: false,
	title: '<div class="capslock">'+i18n.capsLockTitle+'</div>',
	html: '<div>'+i18n.capsLockMsg1+'</div>' + '<div>'+i18n.capsLockMsg2+'</div><br/>'
			+ '<div>'+i18n.capsLockMsg3+'</div>' + '<div>'+i18n.capsLockMsg4+'</div>'
});