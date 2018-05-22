//Dynamic array is a grower, not a shower.
//grows by 1.5 times when full, and shrinks when less than half capacity
package rup.tools;
import java.util.*;
public class DynamicArray<T> {
	private T[] storage;
	private int size;

	public DynamicArray(T... a) {
		storage = a;
		int s = 0;
		for (int i = 0; i < a.length; i++){
			if (a[i] != null) {
				s++;
			}
		}
		size = s;
	}


	//makes sure storage is at least as big as minCap, or else expands.
	public void ensureCapacity(int minCap) {
		if (minCap > storage.length) {
			int newCap = (storage.length*3)/2;
			if (newCap < minCap) {
				newCap = minCap;
			}
			storage = Arrays.copyOf(storage, newCap);
		}
	}

	//shrinks storage if it is half empty (half full?)
	private void pack() {
		if (size <= storage.length/2) {
			int newCap = (size*3)/2;
			storage = Arrays.copyOf(storage, newCap);
		}
	}

	//sets capacity to size
	public void trim() {
		storage = Arrays.copyOf(storage, size);
	}

	//check for index out of bounds
	public void indexCheck(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}
	}

	//return things

	//potentially dangerous function
	public T[] storage() {
		return this.storage;
	}

	public T storage(int index) {
		indexCheck(index);
		return this.storage[index];
	}

	public int size() {
		return this.size;
	}

	public String toString() {
		String s = "[";
		for (int i = 0; i < size; i++) {
			if (storage[i] != null) {
				s += storage[i].toString();
			} else {
				s+= "null";
			}
			if (i < size - 1) {
				s += ", ";
			}
		}
		s += "]";
		return s;
	}

	//set things

	//add objects to end of array.
	public void add(T... a) {
		ensureCapacity(size + a.length);
		for (int i = 0; i < a.length; i++) {
			storage[size + i] = a[i];
		}
		size += a.length;
	}

	//inserts an object at index
	public void insert(T a, int index) {
		ensureCapacity(size + 1);
		indexCheck(index);
		int moveCount = index;
		for (int i = 0; i < size - index; i++){
			storage[size - i] = storage[size - i - 1];
		}
		storage[index] = a;
		size++;
	}

	//removes values at specified index
	public void remove(int index) {
		indexCheck(index);
		int moveCount = index + 1;
		for (int i = moveCount; i < size; i++){
			storage[i - 1] = storage[i];
		}
		size--;
		pack();
	}

	//removes value if it equals input value
	public void remove(T val) {
		for (int i = 0; i < size; i++) {
			if (val == storage[i]) {
				remove(i);
				break;
			}
		}
	}


	public static void main(String[] args) {
		Character A = new Character('A');
		Character B = new Character('B');
		Character C = new Character('C');
		Character D = new Character('D');
		Character E = new Character('E');
		Character F = new Character('F');
		Character G = new Character('G');
		DynamicArray DA = new DynamicArray(A, B, C, D);
		System.out.println(DA);
		DA.add(E, F, G);
		System.out.println(DA);
		System.out.println("DA.storage().length = " + DA.storage().length);
		System.out.println("current size = " + DA.size());
		DA.remove(3);
		DA.remove(0);
		System.out.println(DA);
		System.out.println("DA.storage().length = " + DA.storage().length);
		System.out.println("current size = " + DA.size());
		DA.insert(D, 2);
		System.out.println(DA);
		DA.insert(A, 0);
		System.out.println(DA);
		System.out.println("DA.storage().length = " + DA.storage().length);
		System.out.println("current size = " + DA.size());
	}
}