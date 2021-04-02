package bg.softuni.pethotel.web;

import bg.softuni.pethotel.model.entity.CommentEntity;
import bg.softuni.pethotel.repository.CommentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@Transactional
public class CommentControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CommentRepository commentRepository;

    private Long TEST_COMMENT1_ID, TEST_COMMENT2_ID;
    private final String TEST_COMMENT1_DESCRIPTION = "Test1";
    private final String TEST_COMMENT2_DESCRIPTION = "Test2";

    @Before
    public void setUp() {
        commentRepository.deleteAll();
        CommentEntity comment1 = new CommentEntity();
        comment1.setDescription(TEST_COMMENT1_DESCRIPTION);
        comment1 = commentRepository.save(comment1);
        TEST_COMMENT1_ID = comment1.getId();

        commentRepository.deleteAll();
        CommentEntity comment2 = new CommentEntity();
        comment2.setDescription(TEST_COMMENT2_DESCRIPTION);
        comment2 = commentRepository.save(comment2);
        TEST_COMMENT2_ID = comment2.getId();
    }

    @AfterEach
    public void tearDown() {
        commentRepository.deleteAll();
    }

    @Test
    public void testCommentsGetReturnsCorrectStatus() throws Exception {
        this.mockMvc.perform(get("/comments/pages?=0"))
                .andExpect(status().isOk());
    }
}
