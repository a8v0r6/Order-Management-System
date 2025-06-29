package com.manage.order.service;

import com.manage.order.dto.UserDTO;
import com.manage.order.entity.User;
import com.manage.order.exception.UserNotFoundException;
import com.manage.order.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Integer createUser(UserDTO userDTO) {
        User user = mapToObject(userDTO);
        return userRepository.save(user).getCustomerId();
    }

    private User mapToObject(UserDTO userDTO) {
        User user = new User(userDTO.name(), userDTO.email(), userDTO.address());
        user.setAddress(userDTO.address());
        return user;
    }

    @Override
    public UserDTO updateUser(Integer id, UserDTO userDTO) {
        User user = mapToObject(userDTO);
        user.setCustomerId(id);
        User savedUser = userRepository.save(user);
        return mapToDTO(savedUser);
    }

    private UserDTO mapToDTO(User savedUser) {
        return new UserDTO(savedUser.getName(), savedUser.getEmail(), savedUser.getAddress());
    }

    @Override
    public User getUserById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty() || user.get().isDeleted()) {
            throw new UserNotFoundException("No such user");
        }
        return user.get();
    }

    @Override
    public void deleteById(Integer id) {
        userRepository.softDelete(id);
    }


}
