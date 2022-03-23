package com.neosoft.mybank.Service;

import com.neosoft.mybank.Model.Admin;
import com.neosoft.mybank.Repository.BankRepo;
import com.neosoft.mybank.Repository.Customers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neosoft.mybank.Model.Bank;

import java.util.Optional;

@Service
public class BankService {
    @Autowired
    private BankRepo bankRepo ;

    @Autowired
    private BranchService branchService ;
    @Autowired
    private  CustomerService customerService;

    @Autowired
    Customers customers;

    public Bank add(Bank bank){
            return bankRepo.save(bank);
    }
    public Optional<Bank> getBankById(int id) {
        return bankRepo.findById(id);
    }
    public Bank update(Bank bank) {
        return bankRepo.save(bank);
    }
    public int noOfBranches(){
        return branchService.getAllBranch().size();
    }
    public int noOfCustomersforDisplay(){
        return customers.findAll().size();
    }

}
