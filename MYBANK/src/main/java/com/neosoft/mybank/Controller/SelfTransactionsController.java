package com.neosoft.mybank.Controller;

import com.neosoft.mybank.Exceptions.InvalidRequestException;
import com.neosoft.mybank.Model.Customers;
import com.neosoft.mybank.Model.SelfTransactions;
import com.neosoft.mybank.Service.CustomerService;
import com.neosoft.mybank.Service.SelfTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class SelfTransactionsController {

    @Autowired
    private SelfTransactionService selfTransactionService;

    @Autowired
    private CustomerService customerService;



    @GetMapping("/all_transactions")
    public List<SelfTransactions> getALlMoneySent(){
        return selfTransactionService.getALlTransactions();
    }

    @GetMapping ("/withdraw_money/{fromAccNo}/{amount}")
    public SelfTransactions withdrawMoney(@PathVariable String fromAccNo, @PathVariable float amount){

        if(!customerService.getCustomerByAccNo(fromAccNo).isPresent()){
            throw new InvalidRequestException("Customers with Acc No " +
                    fromAccNo + " does not exist.");
        }

        Customers customer = customerService.getCustomerByAccNo(fromAccNo).get();
        if(amount>customer.getBalance()){
            throw new InvalidRequestException("Customers with Acc No " +
                    fromAccNo + " does not have." + amount+ " to be withdrawn , Balance: " + customer.getBalance());
        }

        Format formatter = new SimpleDateFormat("dd-MMM-yy hh:mm:ss");
        String str = formatter.format(new Date());

        customer.setBalance(customer.getBalance()-amount);
        SelfTransactions selfTransactions = new SelfTransactions();
        selfTransactions.setAmount(amount);
        selfTransactions.setTransactionType("withdrawal");
        selfTransactions.setCustomer(customer);
        selfTransactions.setDate(str);

        customerService.update(customer);
        return selfTransactionService.add(selfTransactions);
    }

    @GetMapping ("/deposit_money/{toAccNo}/{amount}")
    public void depositMoney(@PathVariable String toAccNo, @PathVariable float amount){

        if(!customerService.getCustomerByAccNo(toAccNo).isPresent()){
            throw new InvalidRequestException("Customers with Acc No " +
                    toAccNo + " does not exist.");
        }

        Customers customer = customerService.getCustomerByAccNo(toAccNo).get();

        Format formatter = new SimpleDateFormat("dd-MMM-yy hh:mm:ss");
        String str = formatter.format(new Date());

        customer.setBalance(customer.getBalance()+amount);
        SelfTransactions selfTransactions = new SelfTransactions();
        selfTransactions.setAmount(amount);
        selfTransactions.setTransactionType("deposit");
        selfTransactions.setCustomer(customer);
        selfTransactions.setDate(str);

        customerService.update(customer);
        selfTransactionService.add(selfTransactions);
    }

    @GetMapping("/deposited_money/{accNo}")
    public List<SelfTransactions> depositedMoney(@PathVariable String accNo){
        if(!customerService.getCustomerByAccNo(accNo).isPresent()){
            throw new InvalidRequestException("Customers with Acc No " +
                    accNo + " does not exist.");
        }
        return selfTransactionService.depositedMoney(accNo);
    }
    @GetMapping("/withdrawal_money/{accNo}")
    public List<SelfTransactions> withdrawalMoney(@PathVariable String accNo){
        if(!customerService.getCustomerByAccNo(accNo).isPresent()){
            throw new InvalidRequestException("Customers with Acc No " +
                    accNo + " does not exist.");
        }
        return selfTransactionService.withdrawalMoney(accNo);
    }
}
