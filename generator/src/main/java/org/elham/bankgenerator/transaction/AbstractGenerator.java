package org.elham.bankgenerator.transaction;


import org.elham.bankgenerator.common.PropertyContainer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class AbstractGenerator<T> {
    private PropertyContainer propertyContainer;

    @Autowired
    public void setPropertyContainer(PropertyContainer propertyContainer) {
        this.propertyContainer = propertyContainer;
    }

    public abstract List<T> generate();

    public PropertyContainer getPropertyContainer() {
        return propertyContainer;
    }
}