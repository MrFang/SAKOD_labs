package list;

import java.util.List;
import java.util.Collection;
import java.util.ListIterator;
import java.util.Iterator;
import java.util.Objects;
import java.util.NoSuchElementException;

public class PrintableList<E> implements List<E> {

    private int size;
    private PrintableListNode<E> first;
    private  PrintableListNode<E> last;

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public ListIterator<E> iterator() {
        return new Itr(0);
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        int i = 0;

        for (E item : this) {
            result[i++] = item;
        }

        return result;
    }

    @Override
    public boolean add(E o) {
        linkLast(o);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        remove(indexOf(o));
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {

        for(E item : c) {
            linkLast(item);
        }

        size+= c.size();

        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (!isIndexValid(index)) {
            throw new IndexOutOfBoundsException();
        }

        final PrintableListNode<E> targetNode = node(index);

        if (targetNode == last) {
            addAll(c);
        }

        for(E item : c) {
            linkBefore(targetNode.next, item);
        }

        size+= c.size();

        return true;

    }

    @Override
    public void clear() {
        for (PrintableListNode<E> x = first; x != null; ) {
            PrintableListNode<E> next = x.next;
            x.item = null;
            x.next = null;
            x.prev = null;
            x = next;
        }
        first = last = null;
        size = 0;
    }

    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public E set(int index, E element) {
        if (!isIndexValid(index)) {
            throw new IndexOutOfBoundsException();
        }
        PrintableListNode<E> targetNode = node(index);
        E oldValue = targetNode.item;
        targetNode.item = element;

        return oldValue;
    }

    @Override
    public void add(int index, E element) {

        if (!isIndexValid(index)) {
            throw new IndexOutOfBoundsException();
        }

        if (index == size-1) {
            add(element);
        }

        linkBefore(node(index).next, element);
    }

    @Override
    public E remove(int index) {

        if (!isIndexValid(index)){
            throw new IndexOutOfBoundsException();
        }

        PrintableListNode<E> targetNode = node(index);
        final E targetValue = targetNode.item;
        unlink(targetNode);
        targetNode = null;

        return targetValue;
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;

        if (o == null) {
            for (PrintableListNode<E> x = first; x != null; x = x.next) {
                if (x.item == null)
                    return index;
                index++;
            }
        }
        else {
            for (PrintableListNode<E> x = first; x != null; x = x.next) {
                if (o.equals(x.item))
                    return index;
                index++;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int index = size;

        if (o == null) {
            for (PrintableListNode<E> x = last; x != null; x = x.prev) {
                index--;
                if (x.item == null)
                    return index;
            }
        }
        else {
            for (PrintableListNode<E> x = last; x != null; x = x.prev) {
                index--;
                if (o.equals(x.item))
                    return index;
            }
        }

        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new Itr(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {

        if(!isIndexValid(index)) {
            throw new IndexOutOfBoundsException();
        }
        return new Itr(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {

        if (!isIndexValid(fromIndex) || !isIndexValid(toIndex)) {
            throw new IndexOutOfBoundsException();
        }
        PrintableList<E> result = new PrintableList<E>();

        for(int i = fromIndex; i < toIndex; ++i) {
            result.add(node(i).item);
        }

        return result;
    }

    @Override
    public boolean retainAll(Collection c) {
        Objects.requireNonNull(c);
        boolean modified = false;
        Iterator<E> it = iterator();

        while (it.hasNext()) {

            if (!c.contains(it.next())) {
                it.remove();
                modified = true;
            }

        }

        return modified;
    }

    @Override
    public boolean removeAll(Collection c) {
        Objects.requireNonNull(c);
        boolean modified = false;
        Iterator<E> it = iterator();

        while (it.hasNext()) {

            if (c.contains(it.next())) {
                it.remove();
                modified = true;
            }

        }

        return modified;
    }

    @Override
    public boolean containsAll(Collection c) {

        for (Object e : c) {

            if (!contains(e)) {
                return false;
            }

        }

        return true;
    }

    @Override
    public <E> E[] toArray(E[] a) {
        if (a.length < size) {
            a = (E[]) java.lang.reflect.Array.newInstance(
                    a.getClass().getComponentType(), size);
        }
        int i = 0;
        Object[] result = a;

        for (PrintableListNode x = first; x != null; x = x.next) {
            result[i++] = x.item;
        }

        if (a.length > size) {
            a[size] = null;
        }

        return a;
    }

    @Override
    public String toString() {
        String result = new String();

        for (E item: this) {
            result += item.toString() + "\n";
        }

        return result;
    }

    private boolean isIndexValid(int index) {
        return index >= 0 && index < size;
    }

    private PrintableListNode<E> node(int index) {
        if (index < (size >> 1)) {
            PrintableListNode<E> x = first;
            for (int i = 0; i < index; i++)
                x = x.next;
            return x;
        } else {
            PrintableListNode<E> x = last;
            for (int i = size - 1; i > index; i--)
                x = x.prev;
            return x;
        }
    }

    private void linkLast(E item) {
        final PrintableListNode<E> l = last;
        final PrintableListNode<E> newNode = new PrintableListNode<E>(l, item, null);
        last = newNode;

        if (l == null) {
            first = newNode;
        }
        else {
            l.next = newNode;
        }

        size++;
    }

    private void linkBefore (PrintableListNode<E> node, E item) {
        final PrintableListNode<E> prev = node.prev;
        final PrintableListNode<E> newNode = new PrintableListNode<E>(prev, item, node);
        node.prev = newNode;

        if (prev == null) {
            first = newNode;
        }
        else {
            prev.next = newNode;
        }

        size++;

    }

    private void unlink (PrintableListNode<E> node) {
        final PrintableListNode<E> next = node.next;
        final PrintableListNode<E> prev = node.prev;

        if (node == first) {
            first = next;
        }
        else {
            prev.next = next;
            node.prev = null;
        }

        if (node == last) {
            last = next;
        }
        else {
            next.prev = prev;
            node.next = null;
        }

        node.item = null;
        size--;
    }


    private static class PrintableListNode<E> {
        E item;
        PrintableListNode<E> prev;
        PrintableListNode<E> next;

        PrintableListNode(PrintableListNode<E> prev, E element, PrintableListNode<E> next) {
            this.prev = prev;
            this.item = element;
            this.next = next;
        }
    }

    private class Itr implements ListIterator<E> {

        private int nextIndex;
        private PrintableListNode<E> nextNode;
        private PrintableListNode<E> lastReturnedNode;

        Itr(int start) {

            if (!isIndexValid(start)) {
                throw new IndexOutOfBoundsException();
            }

            nextNode = node(start);
            nextIndex = start;
        }

        @Override
        public boolean hasNext() {
            return nextIndex < size;
        }

        @Override
        public E next() {

            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            nextIndex++;
            lastReturnedNode = nextNode;
            nextNode = nextNode.next;
            return lastReturnedNode.item;
        }

        @Override
        public boolean hasPrevious() {
            return nextIndex > 0;
        }

        @Override
        public E previous() {

            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }

            nextIndex--;
            lastReturnedNode = nextNode = nextNode == null ? last : nextNode.prev;
            nextNode = nextNode.prev;
            return lastReturnedNode.item;
        }

        @Override
        public int nextIndex() {
            return nextIndex;
        }

        @Override
        public int previousIndex() {
            return nextIndex - 1;
        }

        @Override
        public void remove() {

            if (lastReturnedNode == null) {
                throw new IllegalStateException();
            }

            if (nextNode == lastReturnedNode) {
                nextNode = lastReturnedNode.next;
            }
            else {
                nextIndex--;
            }

            unlink(lastReturnedNode);
            lastReturnedNode = null;
        }

        @Override
        public void set(E e) {

            if (lastReturnedNode == null) {
                throw new IllegalStateException();
            }

            lastReturnedNode.item = e;
        }

        @Override
        public void add(E e) {

            lastReturnedNode = null;
            if (nextNode == null) {
                linkLast(e);
            }
            else {
                linkBefore(nextNode, e);
            }

            nextIndex++;
        }
    }
}