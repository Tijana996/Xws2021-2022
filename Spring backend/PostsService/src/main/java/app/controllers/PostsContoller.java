package app.controllers;

import app.dtos.PostDTO;
import app.dtos.SavePostDTO;
import app.models.Post;
import app.repositories.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@RestController()
@CrossOrigin(origins = "*")
@RequestMapping("/posts")
public class PostsContoller {

    @Autowired
    PostsRepository postsRepository;

    @GetMapping("user/{userId}")
    ResponseEntity<List<PostDTO>> getPostsByUser(@PathVariable String userId) {
        List<Post> posts = postsRepository.findPostsByUserId(userId).orElse(Collections.emptyList());

        List<PostDTO> dtos = new ArrayList<>();
        posts.forEach(post -> {
            PostDTO dto = new PostDTO();
            dto.setId(post.getId());
            dto.setContent(post.getContent());
            dto.setTimestamp(post.getTimestamp());
            dto.setUserName(post.getUserName());
            dto.setUserLastName(post.getUserLastName());
            dtos.add(dto);
        });

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping("")
    ResponseEntity<?> addNewComment(@RequestBody SavePostDTO requestBody) {
        Post newPost = new Post();
        newPost.setContent(requestBody.getContent());
        newPost.setUserId(requestBody.getUserId());
        newPost.setTimestamp(LocalDateTime.now());
        newPost.setUserName(requestBody.getUserName());
        newPost.setUserLastName(requestBody.getUserLastName());

        postsRepository.save(newPost);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
