package com.epam.gavura;

import com.epam.gavura.client.TypiCodeClient;
import com.epam.gavura.model.Post;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class PostMethodTest {

    @Test
    public void createPostTest() {
        log.info("Creating a new post with valid data");
        Post newPost = Post.builder()
            .userId(1)
            .title("Sample Title")
            .body("Sample Body")
            .build();
        int newPostId = new TypiCodeClient().createPost(newPost)
            .checkStatusCode(SC_CREATED)
            .getBodyAs(Post.class)
            .getId();

        log.info("New post created with ID: {}", newPostId);
        Post createdPost = new TypiCodeClient().getPostById(newPostId)
            .checkStatusCode(SC_OK)
            .getBodyAs(Post.class);

        log.info("Verifying created post with ID: {}", newPostId);
        assertThat(createdPost).usingRecursiveComparison()
            .ignoringFields(Post.Fields.id)
            .isEqualTo(newPost);
    }

    @Test
    public void createPostWithMissingFieldsTest() {
        new TypiCodeClient().createPost(Post.builder().build())
            .checkStatusCode(SC_BAD_REQUEST);
    }

    @Test
    public void createPostWithInvalidUserIdTest() {
        Post invalidPost = Post.builder()
            .userId(-1)
            .title("Invalid User")
            .body("Invalid User Body")
            .build();

        new TypiCodeClient().createPost(invalidPost)
            .checkStatusCode(SC_BAD_REQUEST);
    }
}
