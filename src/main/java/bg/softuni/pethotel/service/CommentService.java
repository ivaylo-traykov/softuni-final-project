package bg.softuni.pethotel.service;

import bg.softuni.pethotel.model.binding.CommentBindingModel;
import bg.softuni.pethotel.model.enums.CommentStatusEnum;
import bg.softuni.pethotel.model.service.CommentServiceModel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommentService {

    void seedTestComments();

    Page<CommentServiceModel> findApprovedComments(int pageNo, int pageSize);

    void addComment(CommentBindingModel commentBindingModel);

    List<CommentServiceModel> findCommentsByStatus(CommentStatusEnum status);

    void approveComment(Long id);

    void archiveComment(Long id);

    void deleteComment(Long id);
}
