package se.klartext.app.data.jpa.repository.api;

import se.klartext.app.data.jpa.entity.Post;

import java.util.List;

public interface ClosureTable<T> {

    void addTreeNode(T ancestor, T descendant);

    long descendantCount(T ancestor);

    List<Object[]> findDescendants(Post ancestor);
}
