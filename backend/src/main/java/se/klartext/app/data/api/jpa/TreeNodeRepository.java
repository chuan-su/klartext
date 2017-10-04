package se.klartext.app.data.api.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import se.klartext.app.data.api.ClosureTable;
import se.klartext.app.model.Post;

public interface TreeNodeRepository<T extends Post> extends
        JpaRepository<T,Long>, ClosureTable<T> {
}
