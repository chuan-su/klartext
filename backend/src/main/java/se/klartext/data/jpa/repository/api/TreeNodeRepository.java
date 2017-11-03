package se.klartext.data.jpa.repository.api;

import org.springframework.data.jpa.repository.JpaRepository;
import se.klartext.domain.model.post.Post;

public interface TreeNodeRepository<T extends Post> extends
        JpaRepository<T,Long>, ClosureTable<T> {
}
