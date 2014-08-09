package com.ofg.judge.dao

import org.springframework.stereotype.Repository;

import com.ofg.twitter.controller.relations.Relation
import com.ofg.twitter.controller.relations.Relationship

@Repository
class MemoryDatabase implements JudgeDAO{
    final List<Relationship> relationships;

    MemoryDatabase() {
        this.relationships = Collections.synchronizedList(new ArrayList<>());
    }

    @Override
    Relationship updateRelationship(Relationship newRelationship) {
        Relationship workingRelationship = findRelationshipByPairAndCategory(newRelationship)

        if(workingRelationship == null){
            relationships.add(newRelationship)
            return newRelationship
        }else{
            ArrayList<Relation> workingRelations = workingRelationship.getRelations()
            workingRelations.addAll(newRelationship.getRelations())

            relationships.remove(workingRelationship)
            Collections.sort(workingRelations)
            Relationship updatedRelationship = new Relationship(workingRelationship.pairId, workingRelationship.correlatorType, workingRelations)
            relationships.add(updatedRelationship)
            return updatedRelationship
        }

    }

    private Relationship findRelationshipByPairAndCategory(newRelationship) {
        relationships.find({
            relationship -> relationship.pairId.equals(newRelationship.pairId) && relationship.correlatorType.equals(newRelationship.correlatorType)
        })
    }
}
