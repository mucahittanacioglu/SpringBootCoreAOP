package com.ts.core.validation;

import com.ts.core.entities.IEntity;
import com.ts.core.exceptions.ValidationException;

public interface IValidator<T extends IEntity> {
    void validate(T entity) throws ValidationException;
}