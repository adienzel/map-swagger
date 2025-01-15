package com.example;

public class ReferenceComperator {
    @Override public int compare(Person p1, Person p2) {
        return Integer.compare(p1.getAge(), p2.getAge());
    }
}
