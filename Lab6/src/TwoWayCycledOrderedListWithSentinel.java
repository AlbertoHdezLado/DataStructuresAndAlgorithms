import java.util.Iterator;
import java.util.ListIterator;

public class TwoWayCycledOrderedListWithSentinel<E> implements IList<E> {

    private class Element {
        public Element(E e) {
            this.object = e;
            this.next = this;
            this.prev = this;
        }

        public Element(E e, Element next, Element prev) {
            this.object = e;
            this.next = next;
            this.prev = prev;
        }

        // add element e after this
        public void addAfter(Element elem) {
            elem.next = this.next;
            elem.prev = this;
            this.next.prev = elem;
            this.next = elem;
        }

        // assert it is NOT a sentinel
        public void remove() {
            this.prev.next = this.next;
            this.next.prev = this.prev;
        }

        E object;
        Element next = null;
        Element prev = null;
    }


    Element sentinel;
    int size;

    private class InnerIterator implements Iterator<E> {
        Element e;

        public InnerIterator() {
            e = sentinel;
        }

        @Override
        public boolean hasNext() {
            return e.next.object != null;
        }

        @Override
        public E next() {
            e = e.next;
            return e.object;
        }
    }

    private class InnerListIterator implements ListIterator<E> {
        Element e;

        public InnerListIterator() {
            e = sentinel;
        }

        @Override
        public boolean hasNext() {
            return e.next.object != null;
        }

        @Override
        public E next() {
            e = e.next;
            return e.object;
        }

        @Override
        public void add(E arg0) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasPrevious() {
            return e.prev.object != null;
        }

        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public E previous() {
            e = e.prev;
            return e.object;
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
        public void set(E arg0) {
            throw new UnsupportedOperationException();
        }
    }

    public TwoWayCycledOrderedListWithSentinel() {
        sentinel = new Element(null);
    }

    //@SuppressWarnings("unchecked")
    @Override
    public boolean add(E e) {
        InnerListIterator iter = new InnerListIterator();
        if (iter.hasNext()) iter.next();
        while (iter.e != sentinel && ((Comparable<E>) iter.e.object).compareTo(e) <= 0) iter.next();
        iter.previous();
        iter.e.addAfter(new Element(e));
        size++;
        return true;
    }

    private Element getElement(int index) {
        InnerListIterator iter = new InnerListIterator();
        Element elem = null;
        int i = -1;
        while (iter.hasNext() && i < index) {
            iter.next();
            i++;
        }
        if (i == index) elem = iter.e;
        return elem;
    }

    private Element getElement(E obj) {
        InnerListIterator iter = new InnerListIterator();
        Element elem = null;
        boolean found = false;
        while (iter.hasNext() && !found) if (iter.next().equals(obj)) {
            found = true;
            elem = iter.e;
        }
        return elem;
    }

    @Override
    public void add(int index, E element) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void clear() {
        InnerListIterator iter = new InnerListIterator();
        iter.e.next = iter.e;
        iter.e.prev = iter.e;
        size = 0;
    }

    @Override
    public boolean contains(E element) {
        InnerListIterator iter = new InnerListIterator();
        boolean found = false;
        while (iter.hasNext() && !found) if (iter.next().equals(element)) found = true;
        return found;
    }

    @Override
    public E get(int index) {
        return getElement(index).object;
    }

    @Override
    public E set(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(E element) {
        InnerListIterator iter = new InnerListIterator();
        int pos = -1;
        boolean found = false;
        while (iter.hasNext() && !found) {
            if (iter.next().equals(element)) found = true;
            pos++;
        }
        return pos;
    }

    @Override
    public boolean isEmpty() {
        InnerListIterator iter = new InnerListIterator();
        return iter.e.next == iter.e;
    }

    @Override
    public Iterator<E> iterator() {
        return new InnerIterator();
    }

    @Override
    public ListIterator<E> listIterator() {
        return new InnerListIterator();
    }

    @Override
    public E remove(int index) {
        Element elem = getElement(index);
        if (elem != null) {
            elem.remove();
            size--;
        }
        return elem.object;
    }

    @Override
    public boolean remove(E e) {
        Element elem = getElement(e);
        if (elem != null) {
            elem.remove();
            size--;
        }
        return elem != null;
    }

    @Override
    public int size() {
        return size;
    }

    //@SuppressWarnings("unchecked")
    public void add(TwoWayCycledOrderedListWithSentinel<E> other) {
        if (!other.isEmpty()) {
            InnerListIterator iter = new InnerListIterator();
            Element elem = other.getElement(0);
            if (iter.hasNext()) {
                iter.next();
                while (elem.object != null) {
                    if (((Comparable<E>) elem.object).compareTo(iter.e.object) < 0) {
                        this.add(elem.object);
                        elem = elem.next;
                    } else iter.next();
                }
            } else {
                while (elem.object != null) {
                    this.add(elem.object);
                    elem = elem.next;
                }
            }
            size += other.size();
            other.clear();
        }
    }

    //@SuppressWarnings({ "unchecked", "rawtypes" })
    public void removeAll(E e) {
        InnerListIterator iter = new InnerListIterator();
        E obj = null;
        Element elem = null;
        while (iter.hasNext()) {
            obj = iter.next();
            if (obj.equals(e)) {
                elem = iter.e;
                iter.previous();
                elem.remove();
            }
        }
    }

    public String toStringReverse() {
        InnerListIterator iter = new InnerListIterator();
        String retStr = "";
        if (iter.hasPrevious()) {
            retStr = "\n" + iter.previous().toString();
            int count = 1;
            while (iter.hasPrevious()) {
                if (count % 10 == 0) retStr += "\n";
                else retStr += " ";
                retStr += iter.previous().toString();
                count++;
            }
        }
        return retStr;
    }

}