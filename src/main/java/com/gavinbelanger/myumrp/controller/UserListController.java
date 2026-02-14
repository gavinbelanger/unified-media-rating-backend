package com.gavinbelanger.myumrp.controller;

import com.gavinbelanger.myumrp.model.*;
import com.gavinbelanger.myumrp.service.UserListService;
import com.gavinbelanger.myumrp.repository.UserRepository;
import com.gavinbelanger.myumrp.repository.MediaRepository;
import com.gavinbelanger.myumrp.repository.UserListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lists")
public class UserListController {

    @Autowired
    private UserListService userListService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private UserListRepository userListRepository;

    @GetMapping("/user/{username}")
    public ResponseEntity<List<UserList>> getUserLists(@PathVariable String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<UserList> lists = userListService.getUserLists(user);
        return ResponseEntity.ok(lists);
    }

    @PostMapping("/create")
    public ResponseEntity<UserList> createList(
            @RequestParam String username,
            @RequestParam String name,
            @RequestParam(required = false) String description,
            @RequestParam ListType listType) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserList list = userListService.createList(user, name, description, listType);
        return ResponseEntity.ok(list);
    }

    @PostMapping("/{listId}/add-media")
    public ResponseEntity<UserListItem> addMediaToList(
            @PathVariable Long listId,
            @RequestParam Long mediaId,
            @RequestParam(required = false) String notes) {

        UserList list = userListRepository.findById(listId)
                .orElseThrow(() -> new RuntimeException("List not found"));
        Media media = mediaRepository.findById(mediaId)
                .orElseThrow(() -> new RuntimeException("Media not found"));

        UserListItem item = userListService.addMediaToList(list, media, notes);
        return ResponseEntity.ok(item);
    }

    @DeleteMapping("/{listId}/remove-media/{mediaId}")
    public ResponseEntity<String> removeMediaFromList(
            @PathVariable Long listId,
            @PathVariable Long mediaId) {

        UserList list = userListRepository.findById(listId)
                .orElseThrow(() -> new RuntimeException("List not found"));
        Media media = mediaRepository.findById(mediaId)
                .orElseThrow(() -> new RuntimeException("Media not found"));

        userListService.removeMediaFromList(list, media);
        return ResponseEntity.ok("Media removed from list");
    }

    @GetMapping("/{listId}/contains/{mediaId}")
    public ResponseEntity<Boolean> checkMediaInList(
            @PathVariable Long listId,
            @PathVariable Long mediaId) {

        UserList list = userListRepository.findById(listId)
                .orElseThrow(() -> new RuntimeException("List not found"));
        Media media = mediaRepository.findById(mediaId)
                .orElseThrow(() -> new RuntimeException("Media not found"));

        boolean contains = userListService.isMediaInList(list, media);
        return ResponseEntity.ok(contains);
    }
}
