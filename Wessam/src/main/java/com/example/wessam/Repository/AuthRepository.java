package com.example.wessam.Repository;

import com.example.wessam.Model.Gym;
import com.example.wessam.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<User,Integer> {
    User findUserByUsername(String username);

    User findUserById(Integer id);
}
