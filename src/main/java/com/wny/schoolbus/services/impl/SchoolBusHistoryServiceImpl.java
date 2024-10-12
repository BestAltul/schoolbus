package com.wny.schoolbus.services.impl;

import com.wny.schoolbus.entities.impl.SchoolBusImpl;
import com.wny.schoolbus.services.SchoolBusHistoryService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.Audited;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class SchoolBusHistoryServiceImpl implements SchoolBusHistoryService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly=true)
    public List<Number> getRevisionsForEntity(Integer entityId){
        AuditReader auditReader = AuditReaderFactory.get(entityManager);
        return auditReader.getRevisions(SchoolBusImpl.class,entityId);
    }

    @Transactional(readOnly=true)
    public List<SchoolBusImpl> getEntitiesAtRevisions(Integer entityId, List<Number> revisions) {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);
        List<SchoolBusImpl> revisionEntities = new ArrayList<>();

        for (Number revision : revisions) {
            SchoolBusImpl entityAtRevision = auditReader.find(SchoolBusImpl.class, entityId, revision);
            if (entityAtRevision != null) {
                Hibernate.initialize(entityAtRevision.getDashCam());
                Hibernate.initialize(entityAtRevision.getRadio());
                revisionEntities.add(entityAtRevision);
            }
        }

        return revisionEntities;
    }


    public Map<String,Boolean> getChangedFields(Integer entityId, Number revision){
        AuditReader auditReader = AuditReaderFactory.get(entityManager);

        String[] fieldsToCheck = {"name", "busType", "terminal", "dashCam", "radio"};
        Map<String,Boolean> modifiedFields = new HashMap<>();

        for(String field: fieldsToCheck){
            boolean isChange = !auditReader.createQuery()
                    .forRevisionsOfEntity(SchoolBusImpl.class, false, true)
                    .add(AuditEntity.id().eq(entityId))
                    .add(AuditEntity.revisionNumber().eq(revision))
                    .add(AuditEntity.property(field).hasChanged())
                    .getResultList()
                    .isEmpty();

            modifiedFields.put(field,isChange);
        }

        return modifiedFields;

    }
}
