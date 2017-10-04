package se.klartext.app.data.elasticsearch.converter;

import se.klartext.app.data.jpa.entity.BaseEntity;
import se.klartext.app.data.elasticsearch.document.BaseDocument;

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
