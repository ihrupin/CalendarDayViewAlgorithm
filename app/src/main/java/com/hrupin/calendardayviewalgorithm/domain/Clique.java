package com.hrupin.calendardayviewalgorithm.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Igor Khrupin www.hrupin.com on 11/24/16.
 */

public class Clique {
    private List<Event> events = new ArrayList<>();

    public List<Event> getEvents() {
        return events;
    }

    public void addEvent(Event node) {
        events.add(node);
    }

    public boolean intersects(Clique clique2) {
        for (Event i : events) {
            for (Event k : clique2.events) {
                if(i.equals(k)){
                    return true;
                }
            }
        }
        return false;
    }
}
