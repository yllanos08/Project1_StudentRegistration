package registration;

import java.util.Iterator;

/**
 Generic List class
 * @param <E>
 * @author Ysabella Llanos, Kevin Toan
 */
public class List <E> implements Iterable <E>{
    private static final int CAPACITY = 4;
    private E[] objects;
    private int size;


    /**
     Default constructor
     */
    public List()
    {
        objects = (E[]) new Object[CAPACITY];
        size = 0;
    }

    /**
     Find index of generic type e
     * @param e generic type to be found
     * @return index of generic type e; -1 otherwise.
     */
    private int find(E e)
    {


        return -1;
    }

    /**
     Grow the size of the array by 4
     */
    private void grow()
    {

    }

    /**
     Check if generic type e is in list
     * @param e generic type to be found
     * @return true if e is found in list; false otherwise
     */
    public boolean contains(E e)
    {
        return false;
    }

    /**
     Insert generic type e into list
     * @param e generic type to be added
     */
    public void add(E e)
    {

    }


    /**
     Remove generic type e from list
     * @param e generic type to be removed
     */
    public void remove (E e)
    {

    }

    /**
     Checks if the list is empty or not.
     * @return true if list is empty; false otherwise
     */
    public boolean isEmpty()
    {
        return false;
    }

    /**
     Returns the size of the list
     * @return size of the list.
     */
    public int size()
    {
        return this.size();
    }

    /**
     Traverse the list using for-each loop.
     * @return an iterator over elements in the list.
     */
    @Override
    public Iterator<E> iterator() {return new ListIterator();}


    /**
     Return the object at the index
     * @param index index used to get object e
     * @return object at given index
     */
    public E get(int index)
    {
        return objects[index];
    }
    /**
     Putting object e at provided index
     * @param index index to insert object in
     * @param e object to be inserted
     */
    public void set (int index, E e)
    {

    }

    /**
     Return index of object e or -1
     * @param e object to be found
     * @return index of object e; -1 otherwise.
     */
    public int indexOf(E e)
    {


        return -1;
    }

    /**
     Private inner class for the iterator to work properly.
     */
    private class ListIterator implements Iterator<E> {
        private int index;

        /**
         Used to check if array is empty/ at end of the list
         * @return true if there are still items in the list; false otherwise
         */
        @Override
        public boolean hasNext() {return index < size;}

        /**
         Used to return the next object in the list
         * @return next object in the list
         */
        @Override
        public E next() {return objects[index++];}

    }
}
