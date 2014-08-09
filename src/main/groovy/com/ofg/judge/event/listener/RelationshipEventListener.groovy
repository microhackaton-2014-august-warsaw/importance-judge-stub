package com.ofg.judge.event.listener

import org.springframework.stereotype.Service;

import groovy.util.logging.Slf4j;

import com.google.common.eventbus.Subscribe;
import com.ofg.judge.event.RelationshipEvent;

@Slf4j
class RelationshipEventListener {
	
	@Subscribe
	public handleRelationshipEvent(RelationshipEvent event) {
		log.debug("TODO: publish to external service here")
		// TODO: publish to external service here
	}
}
