import java.util.*;

public class SparseBitSet extends BitSet {

	protected Set bitset;

	protected Set getBitSet() {
		return bitset;
	}

	// Replace BitSet no-arg constructor
	public SparseBitSet() {
		bitset = new HashSet();
	}

	// Performs a logical AND of this target bit set
	// with the argument bit set
	public void and(SparseBitSet set) {
		bitset.retainAll(set.getBitSet());
	}

	// Clears all of the bits in this bit set whose
	// corresponding bit is set in the specified bit set
	public void andNot(SparseBitSet set) {
		bitset.removeAll(set.getBitSet());
	}

	// Removes the bit specified from the set
	public void clear(int bit) {
		bitset.remove(new Integer(bit));
	}

	// Clone
	public Object clone() {
		SparseBitSet set = new SparseBitSet();
		set.or(this);
		return set;
	}

	// Equality check
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof SparseBitSet)) {
			return false;
		} else {
			SparseBitSet other = (SparseBitSet) obj;
			return bitset.equals(other.getBitSet());
		}
	}

	// Checks if specific bit contained in set
	public boolean get(int bit) {
		return bitset.contains(new Integer (bit));
	}

	// Return internal set hashcode
	public int hashCode() {
		return bitset.hashCode();
	}

	// Returns the maximum element in set + 1
	public int length() {
		int returnValue = 0;

		if (!bitset.isEmpty()) {
			Integer maxElement = (Integer) Collections.max(bitset);
			returnValue = maxElement.intValue() + 1;
		}
		return returnValue;
	}

	// Performs a logical OR of this bit set
	// with the bit set argument
	public void or(SparseBitSet set) {
		bitset.addAll(set.getBitSet());
	}

	// Adds bit specified to set
	public void set(int bit) {
		bitset.add(new Integer (bit));
	}

	// Return size of internal set
	public int size() {
		return bitset.size();
	}

	// Return string representation of internal set
	public String toString() {
		return bitset.toString();
	}

	// Performs a logical XOR of this bit set
	// with the bit set argument
	public void xor(SparseBitSet set) {
		SparseBitSet dElem = (SparseBitSet)clone();
		SparseBitSet setClone = (SparseBitSet)set.clone();
		
		dElem.getBitSet().retainAll(set.getBitSet());
		
		bitset.removeAll(dElem.getBitSet());
		setClone.getBitSet().removeAll(dElem.getBitSet());
		
		or(setClone);
		
		// Find out what elements are in this set
		// but not in the other set
		// Find out what elements are in the other set
		// but not in this set
		// Combine sets
	}
}