package se.klartext.app.data.jpa.repository.api;

import org.springframework.data.jpa.repository.JpaRepository;
import se.klartext.app.data.jpa.entity.Post;

public interface TreeNodeRepository<T extends Post> extends
        JpaRepository<T,Long>, ClosureTable<T> {
}
