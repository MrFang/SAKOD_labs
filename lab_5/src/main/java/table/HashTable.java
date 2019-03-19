package table;

import java.util.LinkedList;

/**
 *
 * @param <K> -- Тип ключа
 * @param <V> -- Тип значения
 */
public class HashTable<K, V> {
    ///Массив хранящий таблицу
    private LinkedList<Node>[] data;

    /**
     * Конструктор, создаёт таблицу размером 10
     */
    public HashTable() {
        data = new LinkedList[10];

        /// Инициализация значений
        for(int i = 0; i < this.data.length; ++i) {
            this.data[i] = new LinkedList<Node>();
        }
    }

    /**
     * Конструктор, создаёт таблицу заданного размера
     * @param length -- размер таблицы
     */
    public HashTable(int length) {
        data = new LinkedList[length];

        /// Инициализация значений
        for(int i = 0; i < this.data.length; ++i) {
            this.data[i] = new LinkedList<Node>();
        }
    }

    /**
     * Добавляет пару ключ-значение в таблицу
     * @param key -- ключ
     * @param value -- значение
     */
    public void add(K key, V value) {
        V v = this.find(key);

        /// Если такой ключ уже есть, обновить значнение
        if (v != null) {
            data[hash(key)].remove(new Node(key, v));
            data[hash(key)].add(new Node(key, value));
        }
        /// Если нет, добваить
        else {
            data[hash(key)].add(new Node(key, value));
        }
    }

    /**
     * Удаляет пару ключ-значение по ключу
     * @param key -- ключ для удаления
     */
    public void remove(K key) {
        V value = find(key);

        /// Если ключ существует
        if (value != null) {
            data[hash(key)].remove(new Node(key, value));
        }

    }


    /**
     * Получает значение по ключу
     * @param key -- ключ для поиска
     * @return возвращает значение
     */
    public V find(K key) {
        LinkedList<Node> values = data[hash(key)];

        // Проходим по цепочке до конца или искомого ключа
        int i= 0;
        for (; i < values.size() && !values.get(i).key.equals(key); ++i);

        // Если дошли до конца, значит ключа нет
        if (i != values.size()) {
            return values.get(i).value;
        }

        return null;
    }

    @Override
    public String toString() {
        String result = "";

        for(int i = 0; i < this.data.length; ++i) {
            result += i + ": ";

            for(int j = 0; j < data[i].size(); ++j) {
                result += "{" + data[i].get(j).key + ":" + data[i].get(j).value + "}" + "->";
            }

            result+="\n";
        }

        return result;
    }

    /**
     * Вычисляет хэш по ключу
     * @param key -- ключ
     * @return -- возвращает хэш ключа
     */
    private int hash(K key) {
        return key.hashCode() % data.length;
    }

    /**
     * Класс пары ключ-значение
     */
    private class Node {
        K key;
        V value;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj.getClass() != this.getClass()) {
                return false;
            }

            return ((Node) obj).key.equals(key) && ((Node) obj).value.equals(value);
        }
    }
}
