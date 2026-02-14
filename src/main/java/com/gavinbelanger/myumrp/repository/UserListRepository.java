package com.gavinbelanger.myumrp.repository;

import com.gavinbelanger.myumrp.model.User;
import com.gavinbelanger.myumrp.model.UserList;
import com.gavinbelanger.myumrp.model.ListType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserListRepository extends JpaRepository<UserList, Long> {
    List<UserList> findByUser(User user);
    Optional<UserList> findByUserAndListType(User user, ListType listType);
    List<UserList> findByUserAndIsPublic(User user, boolean isPublic);
}