package com.neosoft.mybank.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface Customers extends JpaRepository<com.neosoft.mybank.Model.Customers,Integer> {

    Optional<com.neosoft.mybank.Model.Customers> findByAccNO(String acc);

    List<com.neosoft.mybank.Model.Customers> findByBranch_Id(int id);
}
