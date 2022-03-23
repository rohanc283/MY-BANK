package com.neosoft.mybank.Controller;

import com.neosoft.mybank.Exceptions.InvalidRequestException;
import com.neosoft.mybank.Model.*;
import com.neosoft.mybank.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private BankService bankService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RequestedAccountService requestedAccountService;

    @Autowired
    private BranchService branchService;

    @Autowired
    private ApplicationStatusService applicationStatusService;

    @Autowired
    private UserService userService;

    @GetMapping("/admins")
    public List<Admin> getSoftUsers() {

        return adminService.getAllAdmin();
    }

    @PostMapping("/add/admin")
    public Admin addAdmin(@RequestBody Admin admin) {
        Users users = new Users();
        users.setUserName(admin.getEmailId());
        users.setPassword(admin.getPass());
        users.setRoles(String.valueOf(Roles.ROLE_ADMIN));
        users.setActive(true);
        userService.add(users);
        return adminService.add(admin);
    }

    @DeleteMapping("/delete/admin/{id}")
    public void delete(@PathVariable int id) {
        if (!adminService.getAdminById(id).isPresent()) {
            throw new InvalidRequestException("Admin with ID " +
                    id + " does not exist.");
        }
        adminService.delete(id);
    }

    @GetMapping("/admin/{id}")
    public Admin getAdminById(@PathVariable int id) {
        if (!adminService.getAdminById(id).isPresent()) {
            throw new InvalidRequestException("Admin with ID " +
                    id + " does not exist.");
        }
        return adminService.getAdminById(id).get();

    }

    @PutMapping("/update/admin/{id}")
    public Admin updateInfo(@RequestBody Admin admin, @PathVariable int id) {
        if (adminService.getAdminById(id).isPresent()) {
            admin.setId(id);
            return adminService.update(admin);
        } else {
            throw new InvalidRequestException("Admin with ID " +
                    id + " does not exist.");
        }
    }

    @GetMapping("/approve_requested_accounts/{id}/{pinNo}")
    public void approveAccounts(@PathVariable int id, @PathVariable String pinNo)  {
        if (!requestedAccountService.getRequestedAccountsById(id).isPresent()) {
            throw new InvalidRequestException("Requested Account with ID " +
                    id + " does not exist.");
        }
        RequestedAccounts requestedAccounts = requestedAccountService.getRequestedAccountsById(id).get();
        Customers customers = new Customers();
        if (!branchService.getBranchByIfscCode(requestedAccounts.getBranch().getBranchIfscCode()).isPresent()) {
            throw new InvalidRequestException("Branch with with IFSC Code " +
                    requestedAccounts.getBranch().getBranchIfscCode() + " does not exist.");
        }
        Branch branch = branchService.getBranchByIfscCode(requestedAccounts.getBranch().getBranchIfscCode()).get();
        customers.setName(requestedAccounts.getName());
        customers.setPhoneNo(requestedAccounts.getPhoneNo());

        int min = 1000;
        int max = 9999;
        String accNo = String.valueOf(requestedAccounts.getAdhaarNo()).substring(0, 7) + requestedAccounts.getPanNo().substring(0, 7);
        customers.setAccNO(accNo);
        customers.setEmailId(requestedAccounts.getEmailId());
        customers.setAddress(requestedAccounts.getAddress());
        customers.setAdhaarNo(requestedAccounts.getAdhaarNo());
        customers.setPanNo(requestedAccounts.getPanNo());
        customers.setBalance(requestedAccounts.getBalance());
        customers.setAccType(requestedAccounts.getAccType());
        customers.setBranch(branch);
        customers.setDob(requestedAccounts.getDob());
        customers.setPinNo(pinNo);
        customerService.add(customers);
        ApplicationStatus applicationStatus = applicationStatusService.getApplicationStatusByCusId(id);
        applicationStatus.setStatus(String.valueOf(StatusOptions.APPROVED));
        applicationStatusService.update(applicationStatus);

        if (!bankService.getBankById(1).isPresent()) {
            throw new InvalidRequestException("Bank Not exist. Let Bank to be established");
        }
        Bank bank = bankService.getBankById(1).get();
        bank.setId(1);
        bank.setNoOfCustomers(bank.getNoOfCustomers() + 1);
        bankService.update(bank);
        Users users = new Users();
        users.setUserName(String.valueOf(customers.getAdhaarNo()));
        users.setPassword(String.valueOf(customers.getPinNo()));
        users.setRoles(String.valueOf(Roles.ROLE_CUSTOMER));
        users.setActive(true);
        userService.add(users);
    }

    @PostMapping("/login-admin")
    public void login(@RequestBody Admin admin, HttpSession session, HttpServletRequest request) {
        System.out.println(admin);
        request.getSession().setAttribute("email", admin.getEmailId());
        System.out.println(request.getSession().getAttribute("email"));
    }
}
