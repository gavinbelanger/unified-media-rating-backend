package com.gavinbelanger.myumrp.service;

import com.gavinbelanger.myumrp.model.*;
import com.gavinbelanger.myumrp.repository.UserListRepository;
import com.gavinbelanger.myumrp.repository.UserListItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserListService {

    @Autowired
    private UserListRepository userListRepository;

    @Autowired
    private UserListItemRepository userListItemRepository;

    public UserList createList(User user, String name, String description, ListType listType) {
        UserList list = new UserList();
        list.setUser(user);
        list.setName(name);
        list.setDescription(description);
        list.setListType(listType);
        return userListRepository.save(list);
    }

    public List<UserList> getUserLists(User user) {
        return userListRepository.findByUser(user);
    }

    public UserList getOrCreateDefaultList(User user, ListType listType) {
        Optional<UserList> existing = userListRepository.findByUserAndListType(user, listType);

        if (existing.isPresent()) {
            return existing.get();
        }

        String defaultName = switch (listType) {
            case WATCHLIST -> "Watchlist";
            case WATCHING -> "Currently Watching";
            case COMPLETED -> "Completed";
            case PLAYING -> "Currently Playing";
            case BACKLOG -> "Game Backlog";
            default -> "My List";
        };

        return createList(user, defaultName, "Default " + defaultName, listType);
    }

    @Transactional
    public UserListItem addMediaToList(UserList list, Media media, String notes) {
        // Check if already in list
        if (userListItemRepository.existsByUserListAndMedia(list, media)) {
            throw new RuntimeException("Media already in this list");
        }

        UserListItem item = new UserListItem();
        item.setUserList(list);
        item.setMedia(media);
        item.setNotes(notes);
        return userListItemRepository.save(item);
    }

    @Transactional
    public void removeMediaFromList(UserList list, Media media) {
        Optional<UserListItem> item = userListItemRepository.findByUserListAndMedia(list, media);
        item.ifPresent(userListItemRepository::delete);
    }

    public boolean isMediaInList(UserList list, Media media) {
        return userListItemRepository.existsByUserListAndMedia(list, media);
    }
}