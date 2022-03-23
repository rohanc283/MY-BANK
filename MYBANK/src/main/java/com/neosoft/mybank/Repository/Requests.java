package com.neosoft.mybank.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Requests extends JpaRepository<com.neosoft.mybank.Model.Requests,Integer> {

    List<com.neosoft.mybank.Model.Requests> findByCustomers_Id(int id);
}
