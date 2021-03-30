package bg.softuni.pethotel.repository;

import bg.softuni.pethotel.model.entity.CommentEntity;
import bg.softuni.pethotel.model.enums.CommentStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    Page<CommentEntity> findAllByStatusOrderByCreatedDesc(CommentStatusEnum status, Pageable pageable);

    List<CommentEntity> findByStatusOrderByCreatedDesc(CommentStatusEnum status);
}
