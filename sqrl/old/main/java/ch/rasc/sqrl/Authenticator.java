package ch.rasc.sqrl;

import ch.rasc.sqrl.auth.SqrlIdentity;

public interface Authenticator {

	/**
	 * Called when a SQRL identity has been successfully authenticated. It should return a
	 * URL that will finish authentication to create a logged in session. This is also
	 * called for a new user. If an error occurs this should return an error page
	 * redirection
	 */
	String authenticateIdentity(SqrlIdentity identity);

	/**
	 * When an identity is rekeyed, it's necessary to swap the identity associated with a
	 * given user. This callback happens when a user wishes to swap their previous
	 * identity for a new one.
	 */
	void swapIdentities(SqrlIdentity previousIdentity, SqrlIdentity newIdentity);

	/**
	 * This denotes an identity is now removed and this identity should be disassociated
	 * with a user. This does not necessarily mean the user should be deleted though. The
	 * SQRL spec mentions being able to re-associate another identity at a later time
	 * (possibly during the same login session)
	 */
	void removeIdentity(SqrlIdentity identity);

	/**
	 * Send an ask response back to the SQRL client. Since this is triggered on query and
	 * not ident, the identity may only contain Idk. Ask responses will be included as
	 * part of the SqrlIdentity sent via AuthenticateIdentity
	 */
	Ask askResponse(SqrlIdentity identity);

}
