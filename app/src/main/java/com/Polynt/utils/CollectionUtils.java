package com.Polynt.utils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Alex on 2/12/2015.
 */
public class CollectionUtils {

    public static <T> Collection<T> filter(Collection<T> target, Predicate<T> predicate){
        Collection<T> filteredCollection = new ArrayList<>();
        for (T t : target){
            if (predicate.apply(t)){
                filteredCollection.add(t);
            }
        }
        return filteredCollection;
    }

    public interface Predicate<T>{
        public boolean apply(T type);
    }

}
