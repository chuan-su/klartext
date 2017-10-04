package se.klartext.app.data.api;

import se.klartext.app.model.Post;

import java.util.List;

public interface ClosureTable<T> {

    void addTreeNode(T ancestor, T descendant);

    long descendantCount(T ancestor);

    List<Object[]> findDescendants(Post ancestor);
}
