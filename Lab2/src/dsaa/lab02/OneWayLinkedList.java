package dsaa.lab02;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class OneWayLinkedList<E> implements IList<E>{

	private class Element{
		public Element(E e) {
			this.object=e;
		}
		E object;
		Element next=null;
	}

	Element sentinel;

	private class InnerIterator implements Iterator<E>{
		Element element;
		public InnerIterator() {
			element = sentinel;
		}
		@Override
		public boolean hasNext() {
			return element.next != null;
		}

		@Override
		public E next() {
			element = element.next;
			return element.object;
		}
	}

	public OneWayLinkedList() {
		sentinel = new Element(null);
	}

	@Override
	public Iterator<E> iterator() {
		return new InnerIterator();
	}

	@Override
	public ListIterator<E> listIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean add(E e) {
		InnerIterator iterator = new InnerIterator();
		boolean exists = contains(e);
		if (!exists) {
			while (iterator.hasNext())
				iterator.next();
			iterator.element.next = new Element(e);
		}
		return !exists;
	}

	@Override
	public void add(int index, E element) throws NoSuchElementException {
		InnerIterator iterator = new InnerIterator();
		for (int i = 0; i < index; i++) {
			if (iterator.hasNext())
				iterator.next();
			else
				throw new NoSuchElementException();
		}
		Element next = iterator.element.next;
		iterator.element.next = new Element(element);
		iterator.element.next.next = next;
	}

	@Override
	public void clear() {
		InnerIterator iterator = new InnerIterator();
		iterator.element.next = null;
	}

	@Override
	public boolean contains(E element) {
		InnerIterator iterator = new InnerIterator();
		boolean exists = false;
		while(iterator.hasNext() && !exists) {
			if (iterator.next().equals(element)) exists = true;
		}
		return exists;
	}

	@Override
	public E get(int index) throws NoSuchElementException {
		InnerIterator iterator = new InnerIterator();
		for (int i = 0; i < index; i++) {
			if (iterator.hasNext())
				iterator.next();
			else
				throw new NoSuchElementException();
		}
		return iterator.next();
	}

	@Override
	public E set(int index, E element) throws NoSuchElementException {
		InnerIterator iterator = new InnerIterator();
		for (int i = 0; i < index; i++) {
			if (iterator.hasNext())
				iterator.next();
			else
				throw new NoSuchElementException();
		}
		E old = iterator.element.next.object;
		iterator.element.next.object = element;
		return old;
	}

	@Override
	public int indexOf(E element) {
		InnerIterator iterator = new InnerIterator();
		int index = -1;
		int i = -1;
		while(iterator.hasNext() && index == -1) {
			i++;
			E current = iterator.next();
			if (current.equals(element)) index = i;
		}
		return index;
	}

	@Override
	public boolean isEmpty() {
		InnerIterator iterator = new InnerIterator();
		return !iterator.hasNext();
	}

	@Override
	public E remove(int index) throws NoSuchElementException {
		InnerIterator iterator = new InnerIterator();
		int i = 0;
		while (i < index) {
			if (iterator.hasNext())
				iterator.next();
			else
				throw new NoSuchElementException("Error");
			i++;
		}
		E removed;
		if (iterator.element.next != null) {
			removed = iterator.element.next.object;
			iterator.element.next = iterator.element.next.next;
		}
		else
			throw new NoSuchElementException("Error");
		return removed;
	}

	@Override
	public boolean remove(E e) {
		boolean removed = false;
		int index = indexOf(e);
		if (index != -1) {
			remove(index);
			removed = true;
		}
		return removed;
	}

	@Override
	public int size() {
		InnerIterator iterator = new InnerIterator();
		int i = 0;
		while(iterator.hasNext()) {
			iterator.next();
			i++;
		}
		return i;
	}
}

