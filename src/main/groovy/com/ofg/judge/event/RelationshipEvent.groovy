package com.ofg.judge.event

import com.ofg.twitter.controller.relations.Relationship;

import groovy.transform.Immutable;

class RelationshipEvent {
	
	final public Relationship relationship

	public RelationshipEvent(Relationship relationship) {
		this.relationship = relationship
	}
}
