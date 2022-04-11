import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class TwoWayUnorderedListWithHeadAndTail<E> implements IList<E> {

    private class Element {
        public Element(E e) {
            // TODO
            this.object = e;
            this.next = null;
            this.prev = null;
        }

        public Element(E e, Element next, Element prev) {
            //TODO
            this.object = e;
            this.next = next;
            this.prev = prev;
        }

        E object;
        Element next = null;
        Element prev = null;
    }

    Element head;
    Element tail;
    // can be realization with the field size or without

    private class InnerIterator implements Iterator<E> {
        Element e;
        // TODO maybe more fields....

        public InnerIterator() {
            //TODO
            this.e = head;
        }

        @Override
        public boolean hasNext() {
            //TODO
            return this.e.next != null && this.e.next != tail;
        }

        @Override
        public E next() {
            //TODO
            this.e = this.e.next;
            return e.object;
        }
    }

    private class InnerListIterator implements ListIterator<E> {
        Element elem = head;
        // TODO maybe more fields....

        @Override
        public void add(E e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            // TODO Auto-generated method stub
            return this.elem.next != null && this.elem.next != tail;
        }

        @Override
        public boolean hasPrevious() {
            // TODO Auto-generated method stub
            return this.elem.prev != null && this.elem.prev != head;
        }

        @Override
        public E next() {
            // TODO Auto-generated method stub
            this.elem = this.elem.next;
            return this.elem.object;
        }

        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public E previous() {
            // TODO Auto-generated method stub
            this.elem = this.elem.prev;
            return this.elem.object;
        }

        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();

        }

        @Override
        public void set(E e) {
            // TODO Auto-generated method stub
            this.elem.object = e;

        }
    }

    public TwoWayUnorderedListWithHeadAndTail() {
        head = new Element(null);
        tail = new Element(null);
        clear();
    }

    @Override
    public boolean add(E e) {
        //TODO
        InnerListIterator iter = new InnerListIterator();
        Element elem = new Element(e);

        while (iter.hasNext()) {
            iter.next();
        }
        iter.elem.next = elem;
        elem.prev = iter.elem;
        elem.next = tail;
        tail.prev = elem;
        return true;
    }

    @Override
    public void add(int index, E element) {
        //TODO
        InnerListIterator iter = new InnerListIterator();
        Element elem = new Element(element);

        while (iter.hasNext() && index > 0) {
            iter.next();
            index--;
        }

        if (iter.elem == null || index > 0) {
            throw new NoSuchElementException();
        } else {
            elem.prev = iter.elem;
            elem.next = iter.elem.next;
            iter.elem.next.prev = elem;
            iter.elem.next = elem;
        }
    }

    @Override
    public void clear() {
        //TODO
        this.head.next = this.tail;
        this.tail.prev = this.head;
    }

    @Override
    public boolean contains(E element) {
        //TODO
        InnerListIterator iter = new InnerListIterator();
        while (iter.hasNext()) {
            iter.next();
            if (iter.elem.object.equals(element)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public E get(int index) {
        //TODO
        InnerListIterator iter = new InnerListIterator();
        while (iter.hasNext() && index >= 0) {
            iter.next();
            index--;
        }
        if (iter.elem == null || index >= 0) {
            throw new NoSuchElementException();
        } else {
            return iter.elem.object;
        }
    }

    @Override
    public E set(int index, E element) {
        //TODO
        E value = null;
        InnerListIterator iter = new InnerListIterator();
        Element elem = new Element(element);

        while (iter.hasNext() && index >= 0) {
            iter.next();
            index--;
        }

        if (iter.elem.equals(head) || iter.elem.equals(tail) || index >= 0) {
            throw new NoSuchElementException();
        } else {
            value = iter.elem.object;
            elem.next = iter.elem.next;
            elem.prev = iter.elem.prev;
            iter.elem.next.prev = elem;
            iter.elem.prev.next = elem;
        }

        return value;
    }

    @Override
    public int indexOf(E element) {
        //TODO
        int index = 0;
        InnerListIterator iter = new InnerListIterator();
        boolean found = false;
        while (iter.hasNext() && !found) {
            iter.next();
            if (iter.elem.object.equals(element))
                found = true;
            else
                index++;
        }

        if (!found)
            index = -1;

        return index;
    }

    @Override
    public boolean isEmpty() {
        //TODO
        return this.head.next == this.tail && this.tail.prev == this.head;
    }

    @Override
    public Iterator<E> iterator() {
        return new InnerIterator();
    }

    @Override
    public ListIterator<E> listIterator() { //NO
        throw new UnsupportedOperationException();
    }

    @Override
    public E remove(int index) {
        //TODO
        E value = null;
        InnerListIterator iter = new InnerListIterator();

        while (iter.hasNext() && index >= 0) {
            iter.next();
            index--;
        }
        if (iter.elem == null || index >= 0) {
            throw new NoSuchElementException();
        } else {
            value = iter.elem.object;
            iter.elem.prev.next = iter.elem.next;
            iter.elem.next.prev = iter.elem.prev;
        }
        return value;
    }

    @Override
    public boolean remove(E e) {
        //TODO
        //return true;
        InnerListIterator it = new InnerListIterator();
        while (it.hasNext()) {
            it.next();
            if (it.elem.object.equals(e)) {
                it.elem.prev.next = it.elem.next;
                it.elem.next.prev = it.elem.prev;
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        //TODO
        //return -1;
        int size = 0;
        InnerListIterator it = new InnerListIterator();

        while (it.hasNext()) {
            it.next();
            size++;
        }

        return size;
    }

    public String toStringReverse() {
        InnerListIterator it = new InnerListIterator();
        String retStr = "";

        while (it.hasNext()) {
            it.next();
        }

        //TODO use reverse direction of the iterator

        retStr += "\n";

        while (it.hasPrevious()) {
            retStr = retStr + it.elem.object.toString() + "\n";
            it.previous();
        }

        if (!this.isEmpty()) { //para guardar el ultimo
            retStr += it.elem.object.toString();
        } else {
            return "";
        }

        return retStr;
    }

    public void add(TwoWayUnorderedListWithHeadAndTail<E> other) {
        //TODO
        if (!this.equals(other)) {
            Element thisTail = this.tail.prev;
            Element otherHead = other.head.next;

            thisTail.next = otherHead;
            otherHead.prev = thisTail;

            this.tail.prev = other.tail.prev;
            other.tail.prev.next = this.tail;

            other.clear();

        }
    }
}