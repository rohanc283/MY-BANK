package com.neosoft.mybank.Repository;
import com.neosoft.mybank.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepo extends JpaRepository<Users,Integer> {
    Optional<Users> findByUserName(String userName);
}
