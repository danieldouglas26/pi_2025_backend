package com.lixo.gerenciamento.model.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.lixo.gerenciamento.model.interfaces.Mapper;

public abstract class AbstractMapper<ENTITY, REQUEST, RESPONSE> implements Mapper<ENTITY, REQUEST, RESPONSE> {
    

    protected void copyIfNotNull(String source, Consumer<String> targetSetter) {
        if (source != null) {
            targetSetter.accept(source.trim());
        }
    }
    

    protected <T> void copyIfNotNull(T source, Consumer<T> targetSetter) {
        if (source != null) {
            targetSetter.accept(source);
        }
    }
    

    protected void copyIfPositive(Double source, Consumer<Double> targetSetter) {
        if (source != null && source > 0) {
            targetSetter.accept(source);
        }
    }
    

    protected <T> void copyIfNotEmpty(List<T> source, Consumer<List<T>> targetSetter) {
        if (source != null && !source.isEmpty()) {
            targetSetter.accept(new ArrayList<>(source));
        }
    }
}