package com.ofg.judge.rest
import com.google.common.eventbus.EventBus
import com.ofg.judge.dao.JudgeDAO
import com.ofg.judge.event.RelationshipEvent
import com.ofg.twitter.controller.relations.CorrelationType
import com.ofg.twitter.controller.relations.Relation
import com.ofg.twitter.controller.relations.Relationship
import groovy.transform.Immutable
import groovy.transform.ToString
import groovy.util.logging.Slf4j

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

import javax.validation.constraints.NotNull

import static org.springframework.web.bind.annotation.RequestMethod.PUT

@Slf4j
@RestController
@RequestMapping('/relationship')
class RelationshipController {
	
	final private JudgeDAO memoryDatabase
	
	final private EventBus eventBus
	
	@Autowired
	public RelationshipController(JudgeDAO memoryDatabase, EventBus eventBus) {
		this.memoryDatabase = memoryDatabase
		this.eventBus = eventBus
	}

    @RequestMapping(
            method = PUT,
            consumes = 'application/vnd.com.ofg.importance-judge.v1+json',
            produces = 'application/vnd.com.ofg.importance-judge.v1+json')
    void storeRelationship(@RequestBody @NotNull RelationshipDto relationship) {
        String correlatorType = relationship.correlatorType.toUpperCase()
        validateScores(relationship)
        final CorrelationType correlationType = parseCorrelatorType(correlatorType)
        
		Relationship entity = new Relationship(relationship.pairId, correlationType, relationship.relationships)
		
		memoryDatabase.updateRelationship(entity)
		
		eventBus.post(new RelationshipEvent(entity))
    }

    private static Iterable<Relation> validateScores(RelationshipDto relationship) {
        relationship.relationships.each {
            if (it.score < 0 || it.score > 10) {
                throw new ValidationError("Bad score: " + it.score)
            }
        }
    }

    private CorrelationType parseCorrelatorType(String correlatorType) {
        try {
            return CorrelationType.valueOf(correlatorType)
        } catch (IllegalArgumentException e) {
            throw new ValidationError("Bad correlation type: " + correlatorType)
        }
    }

}

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class ValidationError extends RuntimeException {
    ValidationError(String s) {
        super(s)
    }
}

@ToString
@Immutable
class RelationshipDto {
    final int pairId
    final String correlatorType
    final ArrayList<Relation> relationships
}
