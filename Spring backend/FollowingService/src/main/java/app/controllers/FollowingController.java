package app.controllers;

import app.dtos.FollowingRequestDTO;
import app.dtos.NewFollowingDTO;
import app.dtos.NewFollowingResponseDTO;
import app.models.Follower;
import app.models.Following;
import app.repositories.FollowingRepository;
import app.utils.FollowingStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController()
@CrossOrigin(origins = "*")
@RequestMapping("/following")
public class FollowingController {

    @Autowired
    private FollowingRepository repository;

    @GetMapping("/")
    public ResponseEntity isFollowing(@RequestParam String currentUserId, @RequestParam String userToFollowId) {
        Optional<Following> optional = repository.findByUserId(userToFollowId);
        if (!optional.isPresent()) {
            return ResponseEntity.ok(new NewFollowingResponseDTO(FollowingStatus.NOT_FOLLOWING));
        }

        Following following = optional.get();

        boolean isPending = following.getRequests().stream().anyMatch(x -> x.getUserId().equals(currentUserId));
        boolean isFollowing = following.getFollowers().stream().anyMatch(x -> x.getUserId().equals(currentUserId));

        if (isPending) {
            return ResponseEntity.ok(new NewFollowingResponseDTO(FollowingStatus.PENDING));
        } else if (isFollowing) {
            return ResponseEntity.ok(new NewFollowingResponseDTO(FollowingStatus.FOLLOWING));
        } else {
            return ResponseEntity.ok(new NewFollowingResponseDTO(FollowingStatus.NOT_FOLLOWING));
        }
    }

    @GetMapping("/{userId}/requests")
    public ResponseEntity<List<Follower>> getAllFollowingRequests(@PathVariable String userId) {
        Optional<Following> optional = repository.findByUserId(userId);
        if (!optional.isPresent()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        return ResponseEntity.ok(optional.get().getRequests());
    }

    @PostMapping("/request/accept")
    public ResponseEntity acceptRequest(@RequestBody FollowingRequestDTO requestBody) {
        Optional<Following> optionalCurrentUser = repository.findByUserId(requestBody.getCurrentUserId());
        if (!optionalCurrentUser.isPresent()) {
            return ResponseEntity.badRequest().body("Current user not found");
        }

        Optional<Following> optionalRequestUser = repository.findByUserId(requestBody.getRequestUserId());
        if (!optionalRequestUser.isPresent()) {
            return ResponseEntity.badRequest().body("Request user not found");
        }

        Following currentUser = optionalCurrentUser.get();
        Following requestUser = optionalRequestUser.get();

        requestUser.getFollowing().add(new Follower(requestBody.getCurrentUserId(), requestBody.getCurrentUserName(), requestBody.getCurrentUserLastName()));
        repository.save(requestUser);

        currentUser.setRequests(currentUser.getRequests().stream().filter(x -> !x.getUserId().equals(requestBody.getRequestUserId())).collect(Collectors.toList()));
        currentUser.getFollowers().add(new Follower(requestBody.getRequestUserId(), requestBody.getRequestUserName(), requestBody.getRequestUserLastName()));
        repository.save(currentUser);

        return ResponseEntity.ok("OK");
    }

    @PostMapping("/request/decline")
    public ResponseEntity declineRequest(@RequestBody FollowingRequestDTO requestBody) {
        Optional<Following> optionalCurrentUser = repository.findByUserId(requestBody.getCurrentUserId());
        if (!optionalCurrentUser.isPresent()) {
            return ResponseEntity.badRequest().body("Current user not found");
        }

        Optional<Following> optionalRequestUser = repository.findByUserId(requestBody.getRequestUserId());
        if (!optionalRequestUser.isPresent()) {
            return ResponseEntity.badRequest().body("Request user not found");
        }

        Following currentUser = optionalCurrentUser.get();
        Following requestUser = optionalRequestUser.get();

        requestUser.setFollowing(requestUser.getFollowing().stream().filter(x -> !x.getUserId().equals(currentUser.getUserId())).collect(Collectors.toList()));
        repository.save(requestUser);

        currentUser.setRequests(currentUser.getRequests().stream().filter(x -> !x.getUserId().equals(requestUser.getUserId())).collect(Collectors.toList()));
        repository.save(currentUser);

        return ResponseEntity.ok("OK");
    }

    @PostMapping
    public ResponseEntity follow(@RequestBody NewFollowingDTO dto) {
        FollowingStatus status;

        Optional<Following> optional = repository.findByUserId(dto.getUserToFollowId());
        if (!optional.isPresent()) {
            handleUserToFollowNotExists(dto);
            status = dto.isProfilePrivate() ? FollowingStatus.PENDING : FollowingStatus.FOLLOWING;
        } else {
            Following following = optional.get();
            status = handleUserToFollowExists(dto, following);
        }

        NewFollowingResponseDTO responseDTO = new NewFollowingResponseDTO(status);
        return ResponseEntity.ok().body(responseDTO);
    }

    private void handleUserToFollowNotExists(NewFollowingDTO dto) {
        Following following = new Following();
        following.setFollowing(new ArrayList<>());
        following.setRequests(new ArrayList<>());
        following.setFollowers(new ArrayList<>());
        following.setUserId(dto.getUserToFollowId());
        if (dto.isProfilePrivate()) {
            List<Follower> followers = new ArrayList<>();
            followers.add(new Follower(dto.getCurrentUserId(), dto.getCurrentUserName(), dto.getCurrentUserLastName()));
            following.setRequests(followers);
        } else {
            List<Follower> followers = new ArrayList<>();
            followers.add(new Follower(dto.getCurrentUserId(), dto.getCurrentUserName(), dto.getCurrentUserLastName()));
            following.setFollowers(followers);

            Optional<Following> optionalCurrentUser = repository.findByUserId(dto.getCurrentUserId());
            Follower newFollower = new Follower(dto.getCurrentUserId(), dto.getCurrentUserName(), dto.getCurrentUserLastName());
            if (!optionalCurrentUser.isPresent()) {
                Following currentUserFollowing = new Following();
                currentUserFollowing.setFollowers(new ArrayList<>());
                currentUserFollowing.setRequests(new ArrayList<>());
                currentUserFollowing.setUserId(dto.getCurrentUserId());
                if (!dto.isProfilePrivate()) {
                    List<Follower> followings = new ArrayList<>();
                    followings.add(newFollower);
                    currentUserFollowing.setFollowing(followings);
                }
                repository.save(currentUserFollowing);
            } else {
                optionalCurrentUser.get().getFollowing().add(newFollower);
                repository.save(optionalCurrentUser.get());
            }
        }

        repository.save(following);
    }

    private FollowingStatus handleUserToFollowExists(NewFollowingDTO dto, Following following) {
        FollowingStatus status;

        boolean alreadyFollowing;
        if (dto.isProfilePrivate()) {
            alreadyFollowing = following.getRequests().stream().anyMatch(x -> x.getUserId().equals(dto.getCurrentUserId()));
        } else {
            alreadyFollowing = following.getFollowers().stream().anyMatch(x -> x.getUserId().equals(dto.getCurrentUserId()));
        }

        if (alreadyFollowing) {
            if (dto.isProfilePrivate()) {
                following.setRequests(following.getRequests().stream().filter(x -> !x.getUserId().equals(dto.getCurrentUserId())).collect(Collectors.toList()));
            } else {
                following.setFollowers(following.getFollowers().stream().filter(x -> !x.getUserId().equals(dto.getCurrentUserId())).collect(Collectors.toList()));

                Optional<Following> optionalCurrentUser = repository.findByUserId(dto.getCurrentUserId());
                if (optionalCurrentUser.isPresent()) {
                    optionalCurrentUser.get().setFollowing(optionalCurrentUser.get().getFollowing().stream().filter(x -> !x.getUserId().equals(dto.getCurrentUserId())).collect(Collectors.toList()));
                    repository.save(optionalCurrentUser.get());
                }
            }
            status = FollowingStatus.NOT_FOLLOWING;
        } else {
            if (dto.isProfilePrivate()) {
                following.getRequests().add(new Follower(dto.getCurrentUserId(), dto.getCurrentUserName(), dto.getCurrentUserLastName()));
                status = FollowingStatus.PENDING;
            } else {
                following.getFollowers().add(new Follower(dto.getCurrentUserId(), dto.getCurrentUserName(), dto.getCurrentUserLastName()));
                status = FollowingStatus.FOLLOWING;

                Optional<Following> optionalCurrentUser = repository.findByUserId(dto.getCurrentUserId());
                Follower newFollower = new Follower(dto.getCurrentUserId(), dto.getCurrentUserName(), dto.getCurrentUserLastName());
                if (!optionalCurrentUser.isPresent()) {
                    Following currentUserFollowing = new Following();
                    currentUserFollowing.setFollowers(new ArrayList<>());
                    currentUserFollowing.setRequests(new ArrayList<>());
                    currentUserFollowing.setUserId(dto.getCurrentUserId());
                    if (!dto.isProfilePrivate()) {
                        List<Follower> followers = new ArrayList<>();
                        followers.add(newFollower);
                        currentUserFollowing.setFollowing(followers);
                    }
                    repository.save(currentUserFollowing);
                } else {
                    optionalCurrentUser.get().getFollowing().add(newFollower);
                    repository.save(optionalCurrentUser.get());
                }
            }
        }

        repository.save(following);

        return status;
    }
}
