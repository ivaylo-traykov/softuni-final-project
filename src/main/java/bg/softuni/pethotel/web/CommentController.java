package bg.softuni.pethotel.web;

import bg.softuni.pethotel.model.binding.CommentBindingModel;
import bg.softuni.pethotel.model.enums.CommentStatusEnum;
import bg.softuni.pethotel.model.service.CommentServiceModel;
import bg.softuni.pethotel.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ModelAndView commentsPage() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("comments");

        return modelAndView;
    }

    @GetMapping("/pages")
    public ResponseEntity<Map<String, Object>> getComments(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "5") int size) {
        try {
            Page<CommentServiceModel> comments = commentService.findApprovedComments(page, size);
            Map<String, Object> response = new HashMap<>();
            response.put("comments", comments.getContent());
            response.put("currentPage", comments.getNumber());
            response.put("totalItems", comments.getTotalElements());
            response.put("totalPages", comments.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin
    @PostMapping(value = "/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CommentBindingModel> addComment(
            @RequestBody CommentBindingModel commentBindingModel,
            Principal principal) {

        commentBindingModel.setAuthor(principal.getName());
        commentService.addComment(commentBindingModel);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/pending")
    public ModelAndView pendingCommentsPage() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("pending-comments");

        return modelAndView;
    }

    @GetMapping("/pending-comments")
    public ResponseEntity<Map<String, Object>> pendingComments() {
        try {
            List<CommentServiceModel> pendingComments = commentService.findCommentsByStatus(CommentStatusEnum.PENDING);
            Map<String, Object> response = new HashMap<>();
            response.put("comments", pendingComments);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/approve/{id}")
    public ResponseEntity<CommentServiceModel> approveComment(@PathVariable Long id) {

        commentService.approveComment(id);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/archive/{id}")
    public ResponseEntity<CommentServiceModel> archiveComment(@PathVariable Long id) {

        commentService.archiveComment(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/archived")
    public ModelAndView archivedCommentsPage() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("archived-comments");

        return modelAndView;
    }

    @GetMapping("/archived-comments")
    public ResponseEntity<Map<String, Object>> archivedComments() {
        try {
            List<CommentServiceModel> pendingComments = commentService.findCommentsByStatus(CommentStatusEnum.ARCHIVED);
            Map<String, Object> response = new HashMap<>();
            response.put("comments", pendingComments);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<CommentServiceModel> deleteComment(@PathVariable Long id) {

        commentService.deleteComment(id);

        return ResponseEntity.ok().build();
    }
}

