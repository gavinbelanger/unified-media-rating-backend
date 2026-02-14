package com.gavinbelanger.myumrp.repository;

import com.gavinbelanger.myumrp.model.UserListItem;
import com.gavinbelanger.myumrp.model.UserList;
import com.gavinbelanger.myumrp.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserListItemRepository extends JpaRepository<UserListItem, Long> {
    Optional<UserListItem> findByUserListAndMedia(UserList userList, Media media);
    boolean existsByUserListAndMedia(UserList userList, Media media);
}