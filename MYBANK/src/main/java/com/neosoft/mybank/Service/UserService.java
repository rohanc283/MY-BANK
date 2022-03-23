package com.neosoft.mybank.Service;

import com.neosoft.mybank.Model.Admin;
import com.neosoft.mybank.Model.Users;
import com.neosoft.mybank.Repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UsersRepo usersRepo;
    public Users add(Users users) {
        return usersRepo.save(users);
    }


}
