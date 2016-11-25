package com.hrupin.calendardayviewalgorithm.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Igor Khrupin www.hrupin.com on 11/25/16.
 */

public class Cluster {
    private List<Clique> cliques = new ArrayList<>();
    private int maxCliqueSize = 1;
    private int nextCurrentDrawPosition = 0;

    public void addClique(Clique c) {
        this.cliques.add(c);
        this.maxCliqueSize = Math.max(maxCliqueSize, c.getEvents().size());
    }

    public int getMaxCliqueSize() {
        return maxCliqueSize;
    }

    public Clique getLastClique() {
        if(cliques.size() > 0){
            return cliques.get(cliques.size() - 1);
        }
        return null;
    }

    public List<Event> getEvents() {
        List<Event> events = new ArrayList<>();
        for(Clique clique : cliques) {
            for (Event event : clique.getEvents()) {
                if (!events.contains(event)) {
                    events.add(event);
                }
            }
        }
        return events;
    }

    public int getNextPosition() {
        int position = nextCurrentDrawPosition;
        if(position >= maxCliqueSize){
            position = 0;
        }
        nextCurrentDrawPosition = position + 1;
        return position;
    }
}
