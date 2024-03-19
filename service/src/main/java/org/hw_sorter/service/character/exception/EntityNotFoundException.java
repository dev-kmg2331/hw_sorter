package org.hw_sorter.service.character.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {
//    private final Class<?> notFoundClass;

//    public EntityNotFoundException(Class<?> notFoundClass) {
//        this.notFoundClass = notFoundClass;
//    }

    public EntityNotFoundException() {}
}
