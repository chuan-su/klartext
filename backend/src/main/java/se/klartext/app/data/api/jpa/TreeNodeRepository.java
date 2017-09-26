package se.klartext.app.data.api.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import se.klartext.app.data.api.ClosureTable;
import se.klartext.app.model.TreeNode;

public interface TreeNodeRepository<T extends TreeNode> extends
        JpaRepository<T,Long>, ClosureTable<T> {
}
