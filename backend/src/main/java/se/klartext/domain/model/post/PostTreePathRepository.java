package se.klartext.domain.model.post;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;
import java.util.Optional;
import java.util.stream.Stream;

public interface PostTreePathRepository extends
        Repository<PostTreePath,PostTreePathId>, JpaSpecificationExecutor<PostTreePath>{

    Stream<PostTreePath> findByIdDescendantId(Long descendantId);

    Optional<PostTreePath> findOne(PostTreePathId id);

    PostTreePath save(PostTreePath postTreePath);

    Iterable<PostTreePath> save(Iterable<PostTreePath> treePaths);
}
