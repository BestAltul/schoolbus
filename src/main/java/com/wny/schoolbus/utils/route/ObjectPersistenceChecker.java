package com.wny.schoolbus.utils.route;

import com.wny.schoolbus.exceptions.route.ObjectNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public class ObjectPersistenceChecker {

    public static void checkObjectPersistence(String id, JpaRepository<?, String> repository, String name) {
        if (repository.findById(id).isEmpty()) {
            throw new ObjectNotFoundException(String.format("%s with id %s not found", name, id));
        }
    }
}
