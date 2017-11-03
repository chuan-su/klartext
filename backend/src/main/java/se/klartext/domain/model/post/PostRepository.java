package se.klartext.domain.model.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import se.klartext.domain.shared.BaseRepository;

@NoRepositoryBean
public interface PostRepository<T extends Post> extends BaseRepository<T> {

    Page<T> findByCreatedById(Long userId, Pageable pageable);
}
