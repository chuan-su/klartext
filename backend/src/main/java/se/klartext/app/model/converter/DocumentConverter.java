package se.klartext.app.model.converter;

import se.klartext.app.model.BaseEntity;
import se.klartext.app.model.elasticsearch.BaseDocument;

import java.util.function.Function;

public abstract class DocumentConverter<E extends BaseEntity,D extends BaseDocument>{

    private final Function<E,D> fromEntity;

    public DocumentConverter(Function<E, D> fromEntity) {
        this.fromEntity = fromEntity;
    }

    public final D convertFromEntity(E entity){
        return fromEntity.apply(entity);
    }
}
