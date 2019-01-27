package list;

import java.util.*;


/**
 * Класс реализует список с возможностью печати его содержимого в консоль стандартными средствами языка
 * @author Fedor Zolotukhin
 * @param <E> -- Тип хранимых данных
 */
public class PrintableList<E> implements List<E> {

    /** Размер списка */
    private int size;

    /** Указатель на первый элемент */
    private PrintableListNode<E> first;

    /** Указатель на последний элемент */
    private  PrintableListNode<E> last;

    /**
     * Конструктор без аргументов, инициализирует переменные
     */
    public PrintableList () {
        size = 0;
        first=last=null;
    }

    /**
     * Конструктор с переменным числом аргументов
     * Добавляет аргументы в список в порядке их следования
     * @param items набор элементов
     */
    public PrintableList (E ... items) {
        addAll(Arrays.asList(items));
    }

    /**
     * Медод возвращает текущий размер списка
     * @return возвращает размер списка
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Метод проверяет пустоту списка
     * @return возвращает false если в списке нет элементов, иначе true
     */
    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Метод проверяет, содержится ли элемент в списке
     * @param o -- элемент, для которого проверяется его наличие
     * @return возвращает true если элемнет найден, false иначе
     */
    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    /**
     * Создает итератор по списку
     * @return возвращает объект класса ListIterator по этому списку
     */
    @Override
    public ListIterator<E> iterator() {
        return new Itr(0);
    }

    /**
     * Медод возвращает массив, содержащий элементы списка, не нарушая их порядка
     * @return Массив элементов списка
     */
    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        int i = 0;

        for (E item : this) {
            result[i++] = item;
        }

        return result;
    }

    /**
     * Метод добавляет элемент в конец списка
     * @param o -- элемент для добавления
     * @return всегда возвращает true
     */
    @Override
    public boolean add(E o) {
        linkLast(o);
        return true;
    }

    /**
     * Удаляет элемент из списка
     * @param o -- элемент для удаления
     * @return -- всегда возвращает true
     */
    @Override
    public boolean remove(Object o) {
        remove(indexOf(o));
        return true;
    }

    /**
     * Добавляет все элементы из коллекции в конец списка
     * @param c -- коллекция с элементами для добавления
     * @return всегда возвращает true
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {

        for(E item : c) {
            linkLast(item);
        }

        return true;
    }

    /**
     * Добавляет все элементы из коллекции после указанного индекса
     * @param index -- индекс после которого начинается вставка
     * @param c -- коллекция с элементами для добавления
     * @return всегда возвращает true
     * @throws IndexOutOfBoundsException если индекс за пределами списка
     */
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (!isIndexValid(index)) {
            throw new IndexOutOfBoundsException();
        }

        final PrintableListNode<E> targetNode = node(index);

        if (targetNode == last) {
            addAll(c);
        }
        else {

            for (E item : c) {
                linkBefore(targetNode.next, item);
            }
        }

        return true;

    }

    /**
     * Очищает список
     */
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

    /**
     * Возвращает элемент по индексу
     * @param index -- индекс элемента
     * @return значение элемента по индексу
     * @throws IndexOutOfBoundsException если индекс за пределами списка
     */
    @Override
    public E get(int index) {

        if (!isIndexValid(index)) {
            throw new IndexOutOfBoundsException();
        }

        return node(index).item;
    }

    /**
     * Меняет значение элемента по указанному индексу
     * @param index -- индекс элемента
     * @param element -- новое значение элемента
     * @return возвращает старое значение элемента
     * @throws IndexOutOfBoundsException если индекс за пределами списка
     */
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

    /**
     * Добавляет новый элемент после указанного индекса
     * @param index -- индекс, после которого произойдёт добавление
     * @param element -- значение нового элемента
     * @throws IndexOutOfBoundsException если индекс за пределами списка
     */
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

    /**
     * Удаляет элемент по индексу
     * @param index -- индекс элемента для удаления
     * @return возвращает зачение удалённого элемента
     * @throws IndexOutOfBoundsException если индекс за пределами списка
     */
    @Override
    public E remove(int index) {

        if (!isIndexValid(index)){
            throw new IndexOutOfBoundsException();
        }

        final PrintableListNode<E> targetNode = node(index);
        final E targetValue = targetNode.item;
        unlink(targetNode);
        targetNode.item = null;

        return targetValue;
    }

    /**
     * Возвращает индекс первого с начала элемента с заданным значением в списке или -1
     * @param o -- элемент для которого вычисляется индекс
     * @return индекс элемента или -1, если элемент на был найден
     */
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

    /**
     * Возвращает индекс первого с конца элемента с заданным значением в списке или -1
     * @param o -- элемент для которого вычисляется индекс
     * @return индекс элемента или -1, если элемент на был найден
     */
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

    /**
     * Возвращает объект класса ListIterator, указывающий на начало списка
     * @return возвращает объект класса ListIterator, указывающи йна начало списка
     */
    @Override
    public ListIterator<E> listIterator() {
        return new Itr(0);
    }

    /**
     * Возвращает элемент класса ListIterator, указывающий на элемент с заданным индексом
     * @param index -- индекс элемента, на который будет указывать итератор
     * @return возвращает элемент класса ListIterator, указывающий на элемент с заданным индексом
     * @throws IndexOutOfBoundsException если индекс за пределами списка
     */
    @Override
    public ListIterator<E> listIterator(int index) {

        if(!isIndexValid(index)) {
            throw new IndexOutOfBoundsException();
        }
        return new Itr(index);
    }

    /**
     * Возвращает срез списка от fromIndex до toIndex-1
     * @param fromIndex -- индекс начала среза, входит в срез
     * @param toIndex -- индекс конца среза, не входит в срез
     * @return возвращает срез списка от fromIndex до toIndex-1
     * @throws IndexOutOfBoundsException если хотя бы один из индексов за пределами списка
     */
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

    /**
     * Оставляет в списке только элементы содержащиеся в коллекции
     * @param c -- коллекция-фильтр
     * @return возвращает true, если хотя бы один элемент был удалён
     */
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

    /**
     * Удаляет из списка все элементы содержащиеся в коллекции
     * @param c -- коллекция-фильтр
     * @return возвращает true если хотя быодин элемент был удалён
     */
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

    /**
     * Проверяет наличие всех элементов из коллекции в списке
     * @param c -- коллекция-фильтр
     * @return возвращает true если в списке есть все элементы из коллекции
     */
    @Override
    public boolean containsAll(Collection c) {

        for (Object e : c) {

            if (!contains(e)) {
                return false;
            }

        }

        return true;
    }

    /**
     * Записывает список в указанный массив с начала массива
     * Если массив меньше списка, он расширяется
     * Если массив больше списка, список заканчивается элементом со значением null, остальные элементы не перезаписываются
     * @param a -- целевой массив
     * @param <T> -- тип элементов целевого массива
     * @return возвращает изменённый массив
     */
    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            a = (T[]) java.lang.reflect.Array.newInstance(
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

    /**
     * Печатает содержимое списка, каждыйэлемент с новой строки
     * @return возвращает строку с содержанием списка, оканчивающуюся на \n
     */
    @Override
    public String toString() {
        String result = new String();

        for (E item: this) {
            result += item.toString() + "\n";
        }

        return result;
    }

    /**
     * Метод проверяет правильность индекса
     * @param index -- проверяемый индекс
     * @return возвращает true, если индекс лежит внутри списка
     */
    private boolean isIndexValid(int index) {
        return index >= 0 && index < size;
    }

    /**
     * Возвращает элемент по индексу
     * ВНИМАНИЕ!!! Метод не проверяет валидность индекса
     * @param index -- индекс элемента
     * @return возвращает элемент списка по индексу
     */
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

    /**
     * Присоединяет элемент в конец списка
     * @param item -- значение присоединяемого элемента
     */
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

    /**
     * Присоединяет элемент ПЕРЕД указанным
     * @param node -- элемент, перед которым будет производиться вставка
     * @param item -- значение присоединяемого элемента
     */
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

    /**
     * Отсоединяет указанный элемент
     * @param node -- элемент для отсоединения
     */
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


    /**
     * Класс элемента списка
     * @param <E> -- тип значения элемента
     */
    private static class PrintableListNode<E> {

        /** Значение элемента */
        E item;

        /** Указатель на предыдущий элемент */
        PrintableListNode<E> prev;

        /** Указатель на следующий элемент */
        PrintableListNode<E> next;

        /**
         * Конструктор, формирует элемент по значению и ссылкам на соседние элементы
         * @param prev -- указатель на предыдущий элемент
         * @param element -- значение элемента
         * @param next -- указатель на следующий элемент
         */
        PrintableListNode(PrintableListNode<E> prev, E element, PrintableListNode<E> next) {
            this.prev = prev;
            this.item = element;
            this.next = next;
        }
    }

    /**
     * Класс итератора по списку
     */
    private class Itr implements ListIterator<E> {

        /** Индекс следующего элемента */
        private int nextIndex;

        /** Указатель на следующий элемент */
        private PrintableListNode<E> nextNode;

        /** Указатель на последний возвращённый элемент */
        private PrintableListNode<E> lastReturnedNode;

        /**
         * Конструктор, формирует итератор
         * @param start -- индекс элемента, на который бует указывать итератор
         */
        Itr(int start) {

            if (!isIndexValid(start)) {
                throw new IndexOutOfBoundsException();
            }

            nextNode = node(start);
            nextIndex = start;
        }

        /**
         * Проверяет, остались ли ещё элементы в списке
         * @return возвращает true, если есть ещё элементы
         */
        @Override
        public boolean hasNext() {
            return nextIndex < size;
        }

        /**
         * Возвращает значение следующего элемента, итератор сдвигается вперёд
         * @return возвращает значение следующего элемента
         * @throws NoSuchElementException если нет следующего
         */
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

        /**
         * Проверяет наличие предыдущего элемента
         * @return возвращает true, если есть элементы перед текущим
         */
        @Override
        public boolean hasPrevious() {
            return nextIndex > 0;
        }

        /**
         * Возвращает значение предыдущего элемента, сдвигает итератор назад
         * @return возвращает значение предыдущего элемента
         * @throws NoSuchElementException если нет предыдущего
         */
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

        /**
         * Возвращает индекс следующего элемента
         * @return возвращает индекс следующего элемента
         */
        @Override
        public int nextIndex() {
            return nextIndex;
        }

        /**
         * Возвращает индекс предыдущего элемента
         * @return возвращает индекс предыдущего элемента
         */
        @Override
        public int previousIndex() {
            return nextIndex - 1;
        }

        /**
         * Удаляет последний возвращённый элемент
         * @throws IllegalStateException если последний возвращённый == null
         */
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

        /**
         * Меняет значение последнего возвращённого элемента
         * @param e -- новое значение элемента
         * @throws IllegalStateException если последний возвращённый == null
         */
        @Override
        public void set(E e) {

            if (lastReturnedNode == null) {
                throw new IllegalStateException();
            }

            lastReturnedNode.item = e;
        }

        /**
         * Добавляет элемент после последнего возвращённого
         * Устанавливает lastReturnedNode = null
         * @param e -- значение нового элемента
         */
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