package se.klartext.app.data.api.jpa;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;
import se.klartext.app.model.TreePath;
import se.klartext.app.model.TreePathId;

import java.util.Optional;
import java.util.stream.Stream;

public interface TreePathRepository<E extends TreePath> extends
        Repository<E,TreePathId>, JpaSpecificationExecutor<E>{

    Stream<E> findByIdDescendantId(Long descendantId);

    Optional<E> findOne(TreePathId id);

    TreePath save(TreePath treePath);

    Iterable<E> save(Iterable<E> treePaths);
}
