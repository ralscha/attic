package com.holub.tools;

/** A generic interface for implementing the Command pattern
 *	with a single-argument method. Use Runnamble for no-argument
 *	methods.
 *
 *	This interface is also implemented by subscribers to a "multicaster"
 *	In the Multicaster model, a "publisher" can also be a "subscriber,"
 *	so the Subscriber interface extends EventListener to give you
 *	some flexibility. This way the top-level publisher can choose
 *	to "be" a Subsriber (i.e. implement Subscriber) or to "use" a
 *	Subscriber (i.e. contain a reference to a Multicaster, which is
 *	a Subscriber).
 *
 * @see Multicaster
 * @author Allen I. Holub
 */
public interface Command {
  void fire(Object argument);
}
