package kakaopay.service;

import kakaopay.entity.User;
import kakaopay.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User create() throws Exception{
        User newUser = new User();
        return this.userRepository.save(newUser);
    }

}
