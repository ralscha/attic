package com.holub.asynch;

import java.util.*;

/** A counting-semaphore.
 *
 * <table border=1 cellspacing=0 cellpadding=5><tr><td><font size=-1><i>
 * <center>(c) 2000, Allen I. Holub.</center>
 * <p>
 * This code may not be distributed by yourself except in binary form,
 * incorporated into a java .class file. You may use this code freely
 * for personal purposes, but you may not incorporate it into any
 * commercial product without
 * the express written permission of Allen I. Holub.
 * </font></i></td></tr></table>
 *
 * <table>
 *	<tr><td>6-14-00</td><td>Fixed bug in
 *							<code>remove_slots_for_current_thread(...)</code>
 *							(slot count wasn't being restored to the
 *							original value if the Ownership exception
 *							was thrown).
 *	</td></tr>
 * </table>
 *
 * @author Allen I. Holub
 */

public final class Counting_semaphore implements Semaphore {
  private int available_slots; // Currently available slots.
  private int maximum_slots; // Maximum   available slots.
  private Runnable notify_on_empty;
  private boolean enforce_ownership;

  /*****************************************************************
   * The "owners" list keeps track of the number of slots
   * allocated by a given thread. It's used only if enforce_ownership
   * is true. The class keeps a hash table keyed by Thread object,
   * where the associated value number of slots allocated
   * by that thread. If a given thread isn't in the table, then it
   * has no slots allocated to it.
   */

  private final class Owner_list {
    private Map owners = new HashMap();

    /** HashMap objects must contain Object values, not raw ints,
     *  so wrap the int in a class so that it can go into the table.
     */
    private class Count {
      public int count = 0;
    }

    /** Increment the slot count associated with the current thread
    */

    public void add_slot_for_current_thread() {
      Thread requester = Thread.currentThread();
      Count current = (Count) owners.get(requester);

      if (current == null) // thread hasn't allocated any slots
        owners.put(requester, current = new Count());

      ++current.count;
    }

    /** Reduce the slot count associated with the current thread by
     *  number_of_slots and throw an exception if the count goes
     *  negative.
     */

    public void remove_slots_for_current_thread(int number_of_slots) throws Semaphore.Ownership {
      Thread requester = Thread.currentThread();
      Count current = (Count) owners.get(requester);

      if (current == null) // all slots associated with thread
        { // have been freed or thread never
        // had any slots to begin with.
        throw new Semaphore.Ownership();
      }

      if ((current.count -= number_of_slots) == 0)
        owners.remove(requester);

      if (current.count < 0) // Too many slots were freed.
        {
        current.count += number_of_slots;
        throw new Semaphore.Ownership();
      }
    }
  }

  Owner_list owners = new Owner_list();

  /*****************************************************************
   *	Create a counting semaphore with the specified initial and
   *	maximum counts. The <code>release()</code> method,
   *	which increments the count, is not
   *  permitted to increment it past the maximum. If the initial_count
   *  is larger than the maximum_slots, it is silently truncated.
   *
   *	@param initial_count	 The number of elements initially in the
   *							 pool
   *	@param maximum_slots	 The maximum number of elements in the
   *							 pool.
   *	@param notify_on_empty	 The <code>run()</code> method of this
   *							 object when the count goes to zero.
   *							 You can use this facility to implement
   *							 pools that grow when all resources are
   *							 in use. (See {@link #increase_maximum_slots()})
   *  @param enforce_ownership If true, then a given thread can release
   *							 only the number of slots that it has
   *							 previously acquired. One thread cannot
   *							 release a slot acquired by another
   *							 thread.
   *  @see release
   */

  public Counting_semaphore(
    int initial_count,
    int maximum_slots,
    boolean enforce_ownership,
    Runnable notify_on_empty) {
    this.notify_on_empty = notify_on_empty;
    this.maximum_slots = maximum_slots;
    this.enforce_ownership = enforce_ownership;
    this.available_slots = (initial_count > maximum_slots) ? maximum_slots : initial_count;
  }

  /*****************************************************************
   *	Create a counting semaphore with a maximum count of
   *  Integer.MAX_VALUE. Strict ownership is enforced.
   */
  public Counting_semaphore(int initial_count) {
    this(initial_count, Integer.MAX_VALUE, true, null);
  }

  /*****************************************************************
   *	Required override of Semaphore.id(). Don't call this function.
   *  @see Lock_manager
   */

  public int id() {
    return _id;
  }
  private final int _id = Lock_manager.new_id();

  /*****************************************************************
   *	Acquire the semaphore, decrementing the count of available slots.
   *	Block if the count goes to zero.
   *	<p>
   *	If this call acquires the last available slot, the <code>run()</code>
   *	method of the <code>Runnable</code> object passed to the 
   *	constructor is called. You can use this method to increase the
   *	pool size on an as-needed basis.
   *	(See ({@link #increase_maximum_slots()}.)
   *
   *  @throws InterruptedException if interrupted while waiting
   *			for the semaphore.
   *  @return false if timeout is 0 and we didn't get the slot.
   *			Otherwise, return true;
   */
  public synchronized boolean acquire(long timeout) throws InterruptedException, Semaphore.Timed_out {
    if (timeout == 0 && available_slots <= 0)
      return false;

    long expiration = System.currentTimeMillis() + timeout;
    while (available_slots <= 0) {
      long time_remaining = expiration - System.currentTimeMillis();
      if (time_remaining <= 0)
        throw new Semaphore.Timed_out("Timed out waiting to acquire Condition Variable");

      wait(time_remaining); //#waiting
    }

    if (enforce_ownership)
      owners.add_slot_for_current_thread();

    if (--available_slots == 0 && notify_on_empty != null)
      notify_on_empty.run();

    return true;
  }

  /*****************************************************************
   * Acquire the specified number of slots. If you need to acquire
   *		  multiple slots and some other semaphore as well, use
   *		  <code>Lock_manger.acquire_multiple()</code>, and put multiple references
   *		  to a single <code>Counting_semaphore</code> in the array
   *		  passed to <code>acquire_multiple()</code>.
   * @param timeout maximum time (milliseconds) to wait for any
   *		  <i>one</i> of the slots. The total time to wait
   *		  is (<i>timeout * number_of_slots</i>).
   * @throw Semaphore.Timed_out if a timeout is encountered while
   *		  waiting for any of the slots.
   */

  public synchronized boolean acquire_multiple_slots(int slots, long timeout)
    throws InterruptedException, Semaphore.Timed_out {
    if (timeout == 0) {
      if (available_slots < slots)
        return false;

      if ((available_slots -= slots) <= 0 && notify_on_empty != null)
        notify_on_empty.run();
    } else {
      while (--slots >= 0)
        acquire(timeout);
    }

    return true;
  }

  /*****************************************************************
   *	Increase the pool size by <code>this_many</code> slots.
   *	If the available-slots count is zero, then waiting threads
   *	are released (up to the maximum specified by
   *	the new size). That is, this call modifies the
   *	maximum-available-slots count and then effectively performs a
   *	<code>release(this_many)</code> operation.
   *
   *	@param this_many	Number of slots to add to the pool. An
   *						<code>IllegalArgumentException</code> is
   *						thrown if this number is negative.
   */

  public synchronized void increase_maximum_slots_by(int this_many) {
    if (this_many < 0)
      throw new IllegalArgumentException("Negative Argument");

    maximum_slots += this_many;
    release(this_many);
  }

  /*****************************************************************
   *	Return the current pool size (the maximum count), as passed into
   *	the constructor or as modified by {@link #increase_maximum_slots()}.
   */

  public synchronized int maximum_slots() {
    return maximum_slots;
  }

  // The foregoing method must be synchronized in order to
  // gurantee visiblity of maximum_slots in an SMP environment.
  // Simple volatility isn't sufficient.

  /*****************************************************************
   *	Release the semaphore and increment the count.
   *  This one is the generic release required by the Semaphore
   *  interface, so all it can do is throw an exception if
   *  there's an error.
   *	@throws Counting_semaphore.TooManyReleases (a RuntimeException)
   * 		if you try to release a semaphore whose count is already
   *		at the maximum value.
   */
  public synchronized void release() {
    release(1);
  }

  /*****************************************************************
   *	Release "increment" slots in the semaphore all at once.
   *	@param <b>increment</b> The amount to increment the count.
   *		If this	value is zero, the current count is returned and
   *		no threads are released. An {@link IllegalArgumentException}
   *		is thrown if <code>increment</code> is negative.
   *	@throws Counting_semaphore.TooManyReleases (a RuntimeException)
   * 		if the current value + count is greater than the maximum.
   *		The semaphore will not have been modified in this case.
   *	@throws Semaphore.Ownership (a RuntimeException) if a given
   *		thread tries to free up more slots than it has acquired
   *		(and enforce_ownership was specified in the constructor).
   *	@return the value of the count after the increment is added.
   */
  public synchronized int release(int increment) {
    if (increment < 0)
      throw new IllegalArgumentException("Negative Argument");

    if (increment > 0) {
      int original_count = available_slots;
      int new_count = available_slots + increment;

      if (enforce_ownership)
        owners.remove_slots_for_current_thread(increment);

      if (new_count > maximum_slots) {
        throw new TooManyReleases();
      }

      available_slots = new_count;
      if (original_count == 0)
        notifyAll();
    }
    return available_slots;
  }

  /** Thrown if you try to release more than the maximum number
   * of slots.
   */
  public static final class TooManyReleases extends RuntimeException {
    TooManyReleases() {
      super("Released semaphore that was at capacity");
    }
  }
}
