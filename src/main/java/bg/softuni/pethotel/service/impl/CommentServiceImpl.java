package bg.softuni.pethotel.service.impl;

import bg.softuni.pethotel.model.binding.CommentBindingModel;
import bg.softuni.pethotel.model.entity.CommentEntity;
import bg.softuni.pethotel.model.entity.UserEntity;
import bg.softuni.pethotel.model.enums.CommentStatusEnum;
import bg.softuni.pethotel.model.service.CommentServiceModel;
import bg.softuni.pethotel.repository.CommentRepository;
import bg.softuni.pethotel.service.CommentService;
import bg.softuni.pethotel.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    public CommentServiceImpl(CommentRepository commentRepository, ModelMapper modelMapper, UserService userService) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    public void seedTestComments() {
        CommentEntity comment1 = new CommentEntity()
                .setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam tristique porta dictum. " +
                        "Donec eu eleifend ex. Aliquam nulla libero, volutpat vitae ante ac, sodales ullamcorper mauris.")
                .setRating(3)
                .setStatus(CommentStatusEnum.APPROVED);

        CommentEntity comment2 = new CommentEntity()
                .setDescription("Vivamus vitae dolor ligula. Sed tristique, mauris vitae eleifend pulvinar, augue nibh " +
                        "dignissim ligula, laoreet pretium sem mi a magna. Integer laoreet sit amet mi quis tempus. Curabitur " +
                        "ut porta urna. Donec mollis congue est posuere tempor. Quisque a orci elit. Suspendisse consectetur " +
                        "libero sed mattis dictum. Duis sit amet arcu urna.")
                .setRating(2)
                .setStatus(CommentStatusEnum.APPROVED);

        CommentEntity comment3 = new CommentEntity()
                .setDescription("Curabitur non elementum leo. Sed scelerisque diam quis condimentum vulputate. " +
                        "Duis pretium, sapien faucibus scelerisque ornare, arcu lectus luctus odio, et " +
                        "laoreet nisi turpis non sapien.")
                .setRating(5)
                .setStatus(CommentStatusEnum.PENDING);

        CommentEntity comment4 = new CommentEntity()
                .setDescription("Sed a lorem elementum, efficitur arcu vel, tincidunt purus. Nunc at magna dolor. " +
                        "Vestibulum ex quam, pretium ut iaculis in, congue at magna. Ut nunc est, tincidunt ultrices " +
                        "sem non, semper porttitor lacus.")
                .setRating(4)
                .setStatus(CommentStatusEnum.ARCHIVED);

        commentRepository.saveAll(List.of(comment1, comment2, comment3, comment4));
    }

    @Override
    public Page<CommentServiceModel> findApprovedComments(int pageNo, int pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<CommentEntity> pagedResult = commentRepository.findAllByStatusOrderByCreatedDesc(CommentStatusEnum.APPROVED, paging);
//        Page<CommentEntity> pagedResult = commentRepository.findAll(paging);
        return pagedResult
                .map(c -> {
                    CommentServiceModel newComment = modelMapper.map(c, CommentServiceModel.class);
                    String name = c.getAuthor().getFirstName() + " " + c.getAuthor().getLastName();
                    newComment.setAuthor(name);
                    return newComment;
                });
    }

    @Override
    public void addComment(CommentBindingModel commentBindingModel) {
        UserEntity author = userService.findByEmail(commentBindingModel.getAuthor());

        CommentEntity comment = modelMapper.map(commentBindingModel, CommentEntity.class);
        comment.setAuthor(author);
        comment.setStatus(CommentStatusEnum.PENDING);

        commentRepository.save(comment);
    }


    @Override
    public List<CommentServiceModel> findCommentsByStatus(CommentStatusEnum status) {
        List<CommentEntity> comments = commentRepository.findByStatusOrderByCreatedDesc(status);

        return comments
                .stream()
                .map(c -> {
                    CommentServiceModel pendingComment = modelMapper.map(c, CommentServiceModel.class);
                    pendingComment.setAuthor(c.getAuthor().getFirstName() + " " + c.getAuthor().getLastName());
                    return pendingComment;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void approveComment(Long id) {
        changeCommentStatus(id, CommentStatusEnum.APPROVED);
    }

    @Override
    public void archiveComment(Long id) {
        changeCommentStatus(id, CommentStatusEnum.ARCHIVED);
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    private void changeCommentStatus(Long id, CommentStatusEnum status) {
        CommentEntity comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        comment.setStatus(status);
        commentRepository.save(comment);
    }
}
