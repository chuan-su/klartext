package se.klartext.converter;

import se.klartext.domain.shared.BaseEntity;
import se.klartext.data.elasticsearch.document.BaseDocument;

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
