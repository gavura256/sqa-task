package com.epam.gavura.client;

import com.epam.gavura.model.Post;

public class TypiCodeClient extends ServiceClient {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private static final String POSTS = "/posts";
    private static final String COMMENTS = "/comments";

    public TypiCodeClient() {
        super();
    }

    public ResponseHandler getAllPosts() {
        return defaultGet(String.format("%s%s", BASE_URL, POSTS));
    }

    public ResponseHandler getPostById(int id) {
        return defaultGet(String.format("%s%s/%d", BASE_URL, POSTS, id));
    }

    public ResponseHandler getPostByIdComments(int postId) {
        return defaultGet(String.format("%s%s/%d/comments", BASE_URL, POSTS, postId));
    }

    public ResponseHandler getAllCommentsFilteredByPostId(int postId) {
        return defaultGet(String.format("%s%s?postId=%d", BASE_URL, COMMENTS, postId));

    }

    public ResponseHandler createPost(Post body) {
        return defaultPost(String.format("%s%s", BASE_URL, POSTS), body);
    }

    public ResponseHandler deletePostById(int id) {
        return defaultDelete(String.format("%s%s/%d", BASE_URL, POSTS, id));
    }

    public ResponseHandler putPostById(int id, Post body) {
        return defaultPut(String.format("%s%s/%d", BASE_URL, POSTS, id), body);
    }

    public ResponseHandler patchPostById(int id, Object body) {
        return defaultPut(String.format("%s%s/%d", BASE_URL, POSTS, id), body);
    }
}
