package com.epam.gavura;

import com.epam.gavura.client.TypiCodeClient;
import com.epam.gavura.model.Post;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import static java.util.Arrays.stream;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class PutMethodTest {

    @Test
    public void putPostTest() {
        int randomPostId = stream(new TypiCodeClient().getAllPosts()
            .checkStatusCode(SC_OK)
            .getBodyAs(Post[].class))
            .findAny().orElseThrow(() -> new IllegalArgumentException("No posts found"))
            .getId();

        Post updatedPost = Post.builder()
            .id(randomPostId)
            .title("Updated Title")
            .body("Updated Body")
            .userId(1)
            .build();

        new TypiCodeClient().putPostById(randomPostId, updatedPost)
            .checkStatusCode(SC_OK);

        Post replacedPost = new TypiCodeClient().getPostById(randomPostId)
            .checkStatusCode(SC_OK)
            .getBodyAs(Post.class);

        assertThat(replacedPost)
            .as("Post should be replaced with updated content")
            .isEqualTo(updatedPost);
    }

    @Test
    public void updateNonExistentPostTest() {
        log.info("Attempting to update a non-existent post");
        int nonExistentId = 999;
        Post updatedPost = Post.builder()
            .id(nonExistentId)
            .title("Non-existent Title")
            .body("Non-existent Body")
            .userId(1)
            .build();

        new TypiCodeClient().putPostById(nonExistentId, updatedPost)
            .checkStatusCode(SC_NOT_FOUND);
    }
}
