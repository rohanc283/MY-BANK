package com.neosoft.mybank.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SelfTransactions extends JpaRepository<com.neosoft.mybank.Model.SelfTransactions,Integer> {
    @Transactional
    @Modifying
    @Query("SELECT s FROM SelfTransactions s WHERE  s.transactionType=:type and s.customer.accNO=:accNo")
    List<com.neosoft.mybank.Model.SelfTransactions> depositedMoney(String accNo , String type);

    @Transactional
    @Modifying
    @Query("SELECT s FROM SelfTransactions s WHERE  s.transactionType=:type and s.customer.accNO=:accNo")
    List<com.neosoft.mybank.Model.SelfTransactions> withdrawalMoney(String accNo , String type);
}
