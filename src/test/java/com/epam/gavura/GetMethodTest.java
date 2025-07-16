package com.epam.gavura;

import com.epam.gavura.client.TypiCodeClient;
import com.epam.gavura.model.Comment;
import com.epam.gavura.model.Post;
import org.testng.annotations.Test;

import static java.util.Arrays.stream;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

public class GetMethodTest {

    @Test
    public void testGetAllPosts() {
        Post[] posts = new TypiCodeClient().getAllPosts()
            .checkStatusCode(SC_OK)
            .getBodyAs(Post[].class);

        assertThat(posts).as("Response should contain posts")
            .isNotEmpty();
    }

    @Test
    public void testGetPostById() {
        Post randonPost = stream(new TypiCodeClient().getAllPosts()
                .checkStatusCode(SC_OK)
                .getBodyAs(Post[].class))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException("No posts found"));

        Post actuaPost = new TypiCodeClient().getPostById(randonPost.getId())
            .checkStatusCode(SC_OK)
            .getBodyAs(Post.class);

        assertThat(actuaPost)
            .as("Resource should response with the same post object")
            .isEqualTo(randonPost);
    }

    @Test
    public void testGetCommentsByPostId() {
        int randomId = stream(new TypiCodeClient().getAllPosts()
                .checkStatusCode(SC_OK)
                .getBodyAs(Post[].class))
            .findAny().orElseThrow()
            .getId();

        Comment[] comments = new TypiCodeClient().getPostByIdComments(randomId)
            .checkStatusCode(SC_OK)
            .getBodyAs(Comment[].class);

        Comment[] filteredComments = new TypiCodeClient().getAllCommentsFilteredByPostId(randomId)
            .checkStatusCode(SC_OK)
            .getBodyAs(Comment[].class);

        assertThat(comments)
            .as("Response should contain exactly the same comments for the post")
            .containsExactly(filteredComments);
    }

    @Test
    public void testGetPostByInvalidId() {
        int invalidId = -1;
        new TypiCodeClient().getPostById(invalidId)
            .checkStatusCode(SC_NOT_FOUND);
    }
}
