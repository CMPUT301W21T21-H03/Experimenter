package com.DivineInspiration.experimenter.Activity;

import java.util.ArrayList;
import java.util.List;

public interface Subject {
    List<Observer> observers = new ArrayList<>();

    /**
     * Adds observer class
     * @param observer
     * observer
     */
    default void addObserver(Observer observer){
        observers.add(observer);
    }

    /**
     * Removed the observer
     * @param observer
     * observer
     */
    default void removeObserver(Observer observer){
         observers.remove(observer);
     }

    /**
     *
     */
    void updateAll();
}
