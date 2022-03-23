package com.neosoft.mybank.Repository;

import com.neosoft.mybank.Model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface BankRepo extends JpaRepository<com.neosoft.mybank.Model.Bank,Integer> {

}
