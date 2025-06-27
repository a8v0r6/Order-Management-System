package com.manage.order.service;

import com.manage.order.dto.UserDTO;
import com.manage.order.entity.User;

public interface UserService {
    public Integer createUser(UserDTO userDTO);

    public UserDTO updateUser(Integer id, UserDTO userDTO);

    public User getUserById(Integer id);

    public void deleteById(Integer id);
}
