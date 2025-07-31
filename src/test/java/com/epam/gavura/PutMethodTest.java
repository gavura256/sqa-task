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

    private final TypiCodeClient client = new TypiCodeClient();

    @Test
    public void putPostTest() {

        int randomPostId = client.getAllPosts()
                .checkStatusCode(SC_OK)
                .getBodyAsStream(Post[].class)
                .findAny().orElseThrow(() -> new IllegalArgumentException("No posts found"))
                .getId();

        Post updatedPost = Post.builder()
                .id(randomPostId)
                .title("Updated Title")
                .body("Updated Body")
                .userId(1)
                .build();

        client.putPostById(randomPostId, updatedPost)
                .checkStatusCode(SC_OK);

        Post replacedPost = client.getPostById(randomPostId)
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

        client.putPostById(nonExistentId, updatedPost)
                .checkStatusCode(SC_NOT_FOUND);
    }

}
