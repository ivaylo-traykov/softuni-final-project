package bg.softuni.pethotel.web;

import bg.softuni.pethotel.model.entity.CommentEntity;
import bg.softuni.pethotel.model.entity.UserEntity;
import bg.softuni.pethotel.model.enums.CommentStatusEnum;
import bg.softuni.pethotel.repository.CommentRepository;
import bg.softuni.pethotel.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    private Long TEST_COMMENT1_ID, TEST_COMMENT2_ID;
    private final String TEST_COMMENT1_DESCRIPTION = "Test1";
    private final String TEST_COMMENT2_DESCRIPTION = "Test2";

    @BeforeEach
    public void setUp() {
        UserEntity user = new UserEntity()
                .setEmail("Test@mail.com")
                .setFirstName("Ivan")
                .setLastName("Ivanov")
                .setPassword("123456");

        userRepository.save(user);

        commentRepository.deleteAll();
        CommentEntity comment1 = new CommentEntity();
        comment1.setDescription(TEST_COMMENT1_DESCRIPTION)
                .setStatus(CommentStatusEnum.PENDING)
                .setAuthor(user)
                .setRating(4);
        comment1 = commentRepository.save(comment1);
        TEST_COMMENT1_ID = comment1.getId();

        CommentEntity comment2 = new CommentEntity();
        comment2.setDescription(TEST_COMMENT2_DESCRIPTION)
                .setStatus(CommentStatusEnum.PENDING)
                .setAuthor(user)
                .setRating(4);
        comment2 = commentRepository.save(comment2);
        TEST_COMMENT2_ID = comment2.getId();
    }

    @AfterEach
    public void tearDown() {
        commentRepository.deleteAll();
        userRepository.deleteAll();
    }

    @WithMockUser(username = "pesho@mail.com", roles = {"USER", "MODERATOR"})
    @Test
    public void testCommentsGetReturnsCorrectStatus() throws Exception {
        this.mockMvc.perform(get("/comments/pending-comments"))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "pesho@mail.com", roles = {"USER", "MODERATOR"})
    @Test
    public void testCommentsCount() throws Exception {
        this.mockMvc
                .perform(get("/comments/pending-comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comments", hasSize(2)));
    }

    @WithMockUser(username = "pesho@mail.com", roles = {"USER", "MODERATOR"})
    @Test
    public void approveComment() throws Exception {
        this.mockMvc
                .perform(post("/comments/approve/" + TEST_COMMENT1_ID));

        this.mockMvc.perform(get("/comments/pages?page=0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comments", hasSize(1)));
    }

    @WithMockUser(username = "pesho@mail.com", roles = {"USER", "MODERATOR"})
    @Test
    public void archiveComment() throws Exception {
        this.mockMvc
                .perform(post("/comments/archive/" + TEST_COMMENT1_ID));

        this.mockMvc.perform(get("/comments/archived-comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comments", hasSize(1)));
    }

    @WithMockUser(username = "pesho@mail.com", roles = {"USER", "MODERATOR"})
    @Test
    public void deleteComment() throws Exception {
        this.mockMvc
                .perform(post("/comments/delete/" + TEST_COMMENT1_ID));
        Assertions.assertEquals(1, commentRepository.count());
    }

    @WithMockUser(username = "pesho@mail.com", roles = {"USER", "MODERATOR"})
    @Test
    public void addComment() throws Exception {
        CommentEntity comment = new CommentEntity()
                .setDescription("new comment")
                .setRating(5)
                .setStatus(CommentStatusEnum.PENDING);
        commentRepository.save(comment);

        this.mockMvc.perform(post("/comments/add", comment));
        Assertions.assertEquals(3, commentRepository.count());
    }
}
