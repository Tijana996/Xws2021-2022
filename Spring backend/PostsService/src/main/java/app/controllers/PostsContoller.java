package app.controllers;

import app.dtos.PostDTO;
import app.dtos.ReactionRequestDTO;
import app.dtos.ReactionsAfterUpdateDTO;
import app.dtos.SavePostDTO;
import app.models.Post;
import app.models.UserInReaction;
import app.repositories.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


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
            dto.setComments(post.getComments() != null ? post.getComments() : new ArrayList<>());
            dto.setLikes(post.getLikes() != null ? post.getLikes() : new ArrayList<>());
            dto.setDislikes(post.getDislikes() != null ? post.getDislikes() : new ArrayList<>());
            dto.setImagePath(post.getImage());
            dto.setLink(post.getLink());
            dtos.add(dto);
        });

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping("")
    ResponseEntity<?> addNewPost(@RequestBody SavePostDTO requestBody) {
        Post newPost = new Post();
        newPost.setContent(requestBody.getContent());
        newPost.setUserId(requestBody.getUserId());
        newPost.setTimestamp(LocalDateTime.now());
        newPost.setUserName(requestBody.getUserName());
        newPost.setUserLastName(requestBody.getUserLastName());
        newPost.setImage(requestBody.getImage());
        newPost.setLikes(new ArrayList<>());
        newPost.setDislikes(new ArrayList<>());
        newPost.setLink(requestBody.getLink());

        postsRepository.save(newPost);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/like")
    ResponseEntity<?> likePost(@RequestBody ReactionRequestDTO requestBody) {
        Optional<Post> optional = postsRepository.findById(requestBody.getPostId());

        if (optional.isPresent()) {
            Post post = optional.get();
            UserInReaction userInReaction = new UserInReaction(requestBody.getUserId(), requestBody.getUserName(), requestBody.getUserLastName());

            if (post.getLikes().contains(userInReaction)) {
                post.setLikes(post.getLikes().stream().filter(like -> !like.getUserId().equals(requestBody.getUserId())).collect(Collectors.toList()));
            } else {
                post.getLikes().add(userInReaction);
                post.setDislikes(post.getDislikes().stream().filter(dl -> !dl.getUserId().equals(requestBody.getUserId())).collect(Collectors.toList()));
            }

            postsRepository.save(post);

            ReactionsAfterUpdateDTO dto = new ReactionsAfterUpdateDTO(post.getLikes(), post.getDislikes());

            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/dislike")
    ResponseEntity<?> dislikePost(@RequestBody ReactionRequestDTO requestBody) {
        Optional<Post> optional = postsRepository.findById(requestBody.getPostId());

        if (optional.isPresent()) {
            Post post = optional.get();
            UserInReaction userInReaction = new UserInReaction(requestBody.getUserId(), requestBody.getUserName(), requestBody.getUserLastName());

            if (post.getDislikes().contains(userInReaction)) {
                post.setDislikes(post.getDislikes().stream().filter(dl -> !dl.getUserId().equals(requestBody.getUserId())).collect(Collectors.toList()));
            } else {
                post.getDislikes().add(userInReaction);
                post.setLikes(post.getLikes().stream().filter(like -> !like.getUserId().equals(requestBody.getUserId())).collect(Collectors.toList()));
            }

            postsRepository.save(post);

            ReactionsAfterUpdateDTO dto = new ReactionsAfterUpdateDTO(post.getLikes(), post.getDislikes());

            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
