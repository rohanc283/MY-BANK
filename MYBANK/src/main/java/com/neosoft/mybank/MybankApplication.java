package com.neosoft.mybank;

import com.neosoft.mybank.Exceptions.InvalidRequestException;
import com.neosoft.mybank.Model.*;
import com.neosoft.mybank.Repository.BankRepo;
import com.neosoft.mybank.Repository.BranchRepo;
import com.neosoft.mybank.Repository.UsersRepo;
import com.neosoft.mybank.Service.AdminService;
import com.neosoft.mybank.Service.BankService;
import com.neosoft.mybank.Service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
@EnableCaching
public class MybankApplication {

    @Autowired
    private BankRepo bankRepo;

    @Autowired
    private com.neosoft.mybank.Repository.Admin repo;

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private BranchRepo branchRepo;

    @Autowired
    private BranchService branchService;

    @Autowired
    private BankService bankService;


    @PostConstruct
    public void initBank() {
        List<Bank> bankList = Stream.of(
                new Bank(1, "My-Bank", 0, 0, "INDIA", "mybank@gmail.com", "BEST BANK IN INDIA")
        ).collect(Collectors.toList());
        bankRepo.saveAll(bankList);

        List<Admin> adminList = Stream.of(
                new Admin(1, "rohan@admin.com", "rohan")
        ).collect(Collectors.toList());

        List<Users> usersList =
                Stream.of(
                        new Users(1, "rohan@admin.com", "rohan", Boolean.TRUE, String.valueOf(Roles.ROLE_ADMIN))
                ).collect(Collectors.toList());
        repo.saveAll(adminList);
        usersRepo.saveAll(usersList);
        if (!bankService.getBankById(1).isPresent()) {
            throw new InvalidRequestException("Bank Not exist. Let Bank to be established");
        }
        Bank bank = bankService.getBankById(1).get();
        List<Branch> branchList =
                Stream.of(
                        new Branch(1, "Mumbai Main", "C591", "PRATIK SIR",
                               Long.parseLong("9874561235"), "Mumbai Thane", bank, "active")
                ).collect(Collectors.toList());
        branchRepo.saveAll(branchList);
        bank.setId(1);
        bank.setNoOfBranches(bank.getNoOfBranches() + 1);
        bankService.update(bank);
    }

    public static void main(String[] args) {
        SpringApplication.run(MybankApplication.class, args);
    }

}
