package com.neosoft.mybank.Repository;

import com.neosoft.mybank.Model.Customers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RequestedAccounts extends JpaRepository<com.neosoft.mybank.Model.RequestedAccounts,Integer> {
    Optional<com.neosoft.mybank.Model.RequestedAccounts> findRequestedAccountsByAdhaarNo(long adhar);
    Optional<com.neosoft.mybank.Model.RequestedAccounts> findRequestedAccountsByPanNo(String panNo);


}
