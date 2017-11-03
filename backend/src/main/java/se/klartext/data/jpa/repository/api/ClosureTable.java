package se.klartext.data.jpa.repository.api;

import java.util.List;

public interface ClosureTable<T> {

    void addTreeNode(T ancestor, T descendant);

    long descendantCount(T ancestor);

    List<Object[]> findDescendants(T ancestor);
}
