package com.javarush.task.task37.task3707;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class AmigoSet<E> extends AbstractSet<E> implements Serializable, Cloneable, Set<E> {
    private static final Object PRESENT = new Object();
    private transient HashMap<E,Object> map;

    public AmigoSet() {
        map = new HashMap<>();
    }

    public AmigoSet(Collection<? extends E> collection) {
        int Capacity =  Math.max(16, (int) (collection.size()/.75f + 1) );
        map = new HashMap<E,Object>(Capacity);
        for (E e:
             collection) {
            map.put(e, PRESENT);
        }
    }

    public boolean add(E e){
        if(map.containsKey(e)){
            return false;
        }
        else {
            map.put(e, PRESENT);
            return  true;
        }
    }



    @Override
    public Iterator<E> iterator() {

        return map.keySet().iterator();
    }

    @Override
    public int size() {

        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public boolean remove(Object o) {
        return super.remove(o);
    }

    @Override
    public Object clone() {
        try{
            AmigoSet amigoSet = new AmigoSet<>();
            amigoSet.addAll(this);
            amigoSet.map.putAll((Map) this.map.clone());
            return amigoSet;



    }
        catch (Exception e){
            throw  new InternalError();
        }
    }

    private void writeObject(ObjectOutputStream s) throws java.io.IOException {
        // Write out any hidden serialization magic
        s.defaultWriteObject();

        // Write out HashMap capacity and load factor
        s.writeInt(HashMapReflectionHelper.<Integer>callHiddenMethod(map, "capacity"));
        s.writeFloat(HashMapReflectionHelper.<Float>callHiddenMethod(map, "loadFactor"));

        // Write out size
        s.writeInt(map.size());

        // Write out all elements in the proper order.
        for (E e : map.keySet())
            s.writeObject(e);
    }
// nvcsldfbsdufj;;
    //fjkghfdiu

    private void readObject(ObjectInputStream s) throws java.io.IOException, ClassNotFoundException {
        // Read in any hidden serialization magic
        s.defaultReadObject();

        // Read in HashMap capacity and load factor and create backing HashMap
        int capacity = s.readInt();
        float loadFactor = s.readFloat();
        map = new HashMap<>(capacity, loadFactor);

        // Read in size
        int size = s.readInt();

        // Read in all elements in the proper order.
        for (int i = 0; i < size; i++) {
            E e = (E) s.readObject();
            map.put(e, PRESENT);
        }
    }
}
