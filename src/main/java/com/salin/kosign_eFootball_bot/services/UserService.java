package com.salin.kosign_eFootball_bot.services;

import com.salin.kosign_eFootball_bot.payload.user.AllUserResponse;
import org.springframework.data.domain.Pageable;

import java.security.Principal;

public interface UserService {

    Object getAllUser(Pageable pageable);

    AllUserResponse getUserById(Integer id);

    void deleteUserById(Integer id);

    AllUserResponse getUserDetailsByCurrentUser();

}
