package app.controllers;

import app.dtos.CommentDTO;
import app.dtos.SavePostDTO;
import app.models.Comment;
import app.models.Post;
import app.repositories.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController()
@CrossOrigin(origins = "*")
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    PostsRepository postsRepository;

    @PostMapping("")
    ResponseEntity<?> addNewComment(@RequestBody CommentDTO requestBody) {
        Optional<Post> optionalPost = postsRepository.findById(requestBody.getPostId());
        if (optionalPost.isPresent()) {
            Comment comment = new Comment();
            comment.setId(LocalDateTime.now().toString());
            comment.setTimestamp(LocalDateTime.now());
            comment.setContent(requestBody.getContent());
            comment.setUserId(requestBody.getUserId());
            comment.setUserName(requestBody.getUserName());
            comment.setUserSurname(requestBody.getUserSurname());
            List<Comment> comments = optionalPost.get().getComments();
            if (comments == null) {
                comments = new ArrayList<Comment>();
            }
            comments.add(comment);
            optionalPost.get().setComments(comments);
            postsRepository.save(optionalPost.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Not found", HttpStatus.BAD_REQUEST);
        }
    }
}
