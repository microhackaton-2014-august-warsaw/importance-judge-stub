package com.ofg.twitter.controller.relations

import groovy.transform.Immutable
import groovy.transform.ToString

@ToString
@Immutable
class Relationship{
    final int pairId
    final CorrelationType correlatorType
    final ArrayList<Relation> relations
}

@ToString
@Immutable
class Relation implements Comparable<Relation>{
    final int score
    final String description

    @Override
    int compareTo(Relation that) {
        return that.score - this.score
    }
}


enum CorrelationType {
    PLACE, SENTENCE, TOPIC
}

