Ext.define('overrides.Window', {
	override: 'Ext.window.Window',

	/**
	 * Fix for stateful windows position being messed up See:
	 * http://www.sencha.com/forum/showthread.php?249459-4.1.3-Stateful-window-position-is-STILL-incorrect.
	 */
	getState: function() {
		var me = this, state = me.callSuper(arguments) || {}, maximized = !!me.maximized, ghostBox = me.ghostBox, pos;

		state.maximized = maximized;
		if (maximized) {
			pos = me.restorePos;
		} else if (ghostBox) {
			// If we're animating a show, it will be from offscreen, so
			// grab the position from the final box
			pos = [ ghostBox.x, ghostBox.y ];

			// <WestyFix>
			var isContainedFloater = me.isContainedFloater(), floatParentBox;

			if (isContainedFloater) {
				floatParentBox = me.floatParent.getTargetEl().getViewRegion();
				pos[0] -= floatParentBox.left;
				pos[1] -= floatParentBox.top;
			}
			// </WestyFix>
		} else {
			// <WestyFix>
			pos = me.getPosition(true);
			// </WestyFix>
		}
		Ext.apply(state, {
			size: maximized ? me.restoreSize : me.getSize(),
			pos: pos
		});
		return state;
	}
});
