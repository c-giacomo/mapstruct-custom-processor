package it.generic.mapper.common;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

<#list items as singleItem>
import ${singleItem.sourceType};
import ${singleItem.targetType};
import ${singleItem.mapperType}Impl;
</#list>

import org.springframework.stereotype.Component;
import it.generic.mapper.model.SourceTarget;
import it.generic.mapper.model.GenericAbstractMapper;

@Component
@SuppressWarnings("rawtypes")
public class GenericBeansMapper implements GenericAbstractMapper {

    private Map<SourceTarget, BaseMapper> bindings;

    public GenericBeansMapper() {

        Map<SourceTarget, BaseMapper> tempBindings = new HashMap<>();

        <#list items as item>
        tempBindings.put(new SourceTarget( ${item.sourceTypeShort}.class, ${item.targetTypeShort}.class ), new ${item.mapperTypeShort}Impl() );
        </#list>

        this.bindings = Collections.unmodifiableMap(tempBindings);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T map(Object from, Class<T> to) {
        BaseMapper mapper = this.bindings.get(new SourceTarget(from.getClass(), to));
        if (mapper != null) {
            return (T) mapper.map(from);
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T map(Object from, Object to) {
        BaseMapper mapper = this.bindings.get(new SourceTarget(from.getClass(), to.getClass()));
        if (mapper != null) {
            return (T) mapper.update(from, to);
        }
        return null;
    }

    @Override
    public boolean hasRegisteredMapper(Class from, Class to) {
        return this.bindings.containsKey(new SourceTarget(from, to));
    }
}