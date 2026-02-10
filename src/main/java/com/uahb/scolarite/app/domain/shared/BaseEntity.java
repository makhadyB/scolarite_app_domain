package com.uahb.scolarite.app.domain.shared;

import java.util.Objects;

public class BaseEntity<ID>{

    private final ID value;

    protected BaseEntity(ID value) {

        if (value == null){
            throw new IllegalArgumentException("Id obligatoire!!");
        }
        this.value = value;
    }

    public ID value(){
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity<?> that = (BaseEntity<?>) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
