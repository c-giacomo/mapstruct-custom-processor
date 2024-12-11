package it.generic.mapper.processor;

import javax.lang.model.type.DeclaredType;

/**
 * @author gchiavolotti
 * 
 */
public class Item {
	private DeclaredType sourceType;
    private DeclaredType targetType;
    private DeclaredType mapperType;
    
    public Item(DeclaredType sourceType, DeclaredType targetType, DeclaredType mapperType) {
        this.sourceType = sourceType;
        this.targetType = targetType;
        this.mapperType = mapperType;
    }
    
	public DeclaredType getSourceType() {
		return sourceType;
	}
	
	public DeclaredType getTargetType() {
		return targetType;
	}
	
	public DeclaredType getMapperType() {
		return mapperType;
	}
	
	public String getSourceTypeShort() {
        return sourceType.asElement().getSimpleName().toString();
    }

    public String getTargetTypeShort() {
        return targetType.asElement().getSimpleName().toString();
    }

    public String getMapperTypeShort() {
        return mapperType.asElement().getSimpleName().toString();
    }
}
