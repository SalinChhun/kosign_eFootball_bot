package com.salin.kosign_eFootball_bot.serviceImpl;

import com.salin.kosign_eFootball_bot.domain.User;
import com.salin.kosign_eFootball_bot.exception.EntityNotFoundException;
import com.salin.kosign_eFootball_bot.payload.user.AllUserResponse;
import com.salin.kosign_eFootball_bot.payload.user.MainUserResponse;
import com.salin.kosign_eFootball_bot.repository.UserRepository;
import com.salin.kosign_eFootball_bot.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    @Override
    public Object getAllUser(Pageable pageable) {

        Page<User> allUser = userRepository.getAllUser(pageable);
        List<AllUserResponse> allUserResponses = allUser.stream()
                .map(user -> AllUserResponse.builder()
                        .id(user.getId())
                        .firstName(user.getFirstname())
                        .lastName(user.getLastname())
                        .email(user.getEmail())
                        .role(user.getRole().name())
                        .build()
                ).toList();
        return MainUserResponse.builder()
                .users(allUserResponses)
                .page(allUser)
                .build();
    }

    @Override
    public AllUserResponse getUserById(Integer id) {

        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(User.class, "id", id.toString()));
        return AllUserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstname())
                .lastName(user.getLastname())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    @Override
    public void deleteUserById(Integer id) {
        getUserById(id);
        userRepository.deleteById(id);
    }


    @Override
    public AllUserResponse getUserDetailsByCurrentUser() {

        // get user id by login user
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = user.getId();

        return getUserById(userId);
    }

}
