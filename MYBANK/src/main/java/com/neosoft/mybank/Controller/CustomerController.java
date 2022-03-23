package com.neosoft.mybank.Controller;

import com.neosoft.mybank.Exceptions.InvalidRequestException;
import com.neosoft.mybank.Model.*;
import com.neosoft.mybank.Repository.UsersRepo;
import com.neosoft.mybank.Service.BankService;
import com.neosoft.mybank.Service.BranchService;
import com.neosoft.mybank.Service.CustomerService;
import com.neosoft.mybank.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BranchService branchService;

    @Autowired
    private BankService bankService;

    @Autowired
    private UserService userService;

    @Autowired
    private UsersRepo usersRepo;

    @GetMapping("/noOfCustomers")
    public int noOfCustomers(){
        return customerService.noOfCustomers();
    }

    @GetMapping("/customers")
    public List<Customers> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    @PostMapping("/add/customerToBranch/{ifsc}")
    public void addCustomers(@RequestBody Customers customer , @PathVariable String ifsc){
        if (!branchService.getBranchByIfscCode(ifsc).isPresent()) {
            throw new InvalidRequestException("Branch with IFSC Code " +
                    ifsc + " does not exist.");
        }
        Branch branch = branchService.getBranchByIfscCode(ifsc).get();
        customer.setBranch(branch);
        customerService.add(customer);
        if(!bankService.getBankById(1).isPresent()){
            throw new InvalidRequestException("Bank Not exist. Let Bank to be established");
        }
        Bank bank = bankService.getBankById(1).get();
        bank.setId(1);
        bank.setNoOfCustomers(bank.getNoOfCustomers()+1);
        bankService.update(bank);

        Users users = new Users();
        users.setUserName(String.valueOf(customer.getAdhaarNo()));
        users.setPassword(String.valueOf(customer.getPinNo()));
        users.setRoles(String.valueOf(Roles.ROLE_CUSTOMER));
        users.setActive(true);
        userService.add(users);

    }

    @GetMapping("/customers/{id}")
    public Customers getCustomersById(@PathVariable int id) {
        if (!customerService.getCustomerById(id).isPresent()) {
            throw new InvalidRequestException("Customers with ID " +
                    id + " does not exist.");
        }
        return customerService.getCustomerById(id).get();
    }

    @DeleteMapping("/delete/customer/{id}")
    public void  delete(@PathVariable int id) {
        if (!customerService.getCustomerById(id).isPresent()) {
            throw new InvalidRequestException("Customer with ID " +
                    id + " does not exist.");
        }
        if(!bankService.getBankById(1).isPresent()){
            throw new InvalidRequestException("Bank Not exist. Let Bank to be established");
        }
        Bank bank = bankService.getBankById(1).get();
        bank.setId(1);
        bank.setNoOfCustomers(bank.getNoOfCustomers()-1);
        bankService.update(bank);
        customerService.delete(id);
    }

    @PutMapping("/update/customer/{id}")
    public Customers updateInfo(@RequestBody Customers customer , @PathVariable int id) {
        if(customerService.getCustomerById(id).isPresent()) {
            customer.setId(id);
            Users users = usersRepo.findByUserName(String.valueOf(customer.getAdhaarNo())).get();
            users.setPassword(String.valueOf(customer.getPinNo()));
            usersRepo.save(users);
            return customerService.update(customer);
        }
        else {
            throw new InvalidRequestException("Customer with ID " +
                    id + " does not exist.");
        }
    }

    @PutMapping("/admin/update/customer/{id}")
    public Customers adminUpdateInfo(@RequestBody Customers customer , @PathVariable int id) {
        if(customerService.getCustomerById(id).isPresent()) {
            customer.setId(id);
            Users users = usersRepo.findByUserName(String.valueOf(customer.getAdhaarNo())).get();
            users.setPassword(String.valueOf(customer.getPinNo()));
            usersRepo.save(users);
            return customerService.update(customer);
        }
        else {
            throw new InvalidRequestException("Customer with ID " +
                    id + " does not exist.");
        }
    }

    @PostMapping("/login-customer")
    public void login(@RequestBody Customers cus, HttpSession session , HttpServletRequest request){
//        System.out.println(cus);
        request.getSession().setAttribute("accNo",cus.getAccNO());
//        System.out.println(request.getSession().getAttribute("accNo"));
    }


    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/customer_from_session/{accNo}")
    public Customers getCustomerFromSession(@PathVariable String accNo){
        if(!customerService.getCustomerByAccNo(accNo).isPresent()) {
            throw new InvalidRequestException("Customer with Acc No " +
                    accNo + " does not exist.");
        }
        return customerService.getCustomerByAccNo(accNo).get();
    }

}
