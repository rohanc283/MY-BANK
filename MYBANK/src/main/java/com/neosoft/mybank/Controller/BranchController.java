package com.neosoft.mybank.Controller;

import com.neosoft.mybank.Exceptions.InvalidRequestException;
import com.neosoft.mybank.Model.Bank;
import com.neosoft.mybank.Model.Branch;
import com.neosoft.mybank.Model.Customers;
import com.neosoft.mybank.Service.AdminService;
import com.neosoft.mybank.Service.BankService;
import com.neosoft.mybank.Service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class BranchController {

    @Autowired
    private BranchService branchService;

    @Autowired
    private BankService bankService;

    @GetMapping("/branches")
    public List<Branch> getAllBranches(HttpSession session) {
        System.out.println(session.getAttribute("email"));
        return branchService.getAllBranch();
    }

    @PostMapping("/add/branch")
    public void addBranch(@RequestBody Branch branch) {
        if (!bankService.getBankById(1).isPresent()) {
            throw new InvalidRequestException("Bank Not exist. Let Bank to be established");
        }

        branch.setBank(bankService.getBankById(1).get());
        branch.setActiveState("active");
        branchService.add(branch);
        Bank bank = bankService.getBankById(1).get();
        bank.setId(1);
        bank.setNoOfBranches(bank.getNoOfBranches() + 1);
        bankService.update(bank);
    }

    @DeleteMapping("/delete/branch/{id}")
    public void delete(@PathVariable int id) {
        if (!branchService.getBranchById(id).isPresent()) {
            throw new InvalidRequestException("Branch with ID " +
                    id + " does not exist.");
        }
        branchService.delete(id);
    }

    @GetMapping("/deactivate/branch/{id}")
    public void deactivate(@PathVariable int id) {
        if (!branchService.getBranchById(id).isPresent()) {
            throw new InvalidRequestException("Branch with ID " +
                    id + " does not exist.");
        }
        branchService.deactivate(id);
    }

    @GetMapping("/activate/branch/{id}")
    public void activate(@PathVariable int id) {
        if (!branchService.getBranchById(id).isPresent()) {
            throw new InvalidRequestException("Branch with ID " +
                    id + " does not exist.");
        }
        branchService.activate(id);
    }

    @GetMapping("/branch/{id}")
    public Branch getAdminById(@PathVariable int id) {
        if (!branchService.getBranchById(id).isPresent()) {
            throw new InvalidRequestException("Branch with ID " +
                    id + " does not exist.");
        }
        return branchService.getBranchById(id).get();

    }


    @GetMapping("/customers_from_branch/{ifsc}")
    public List<Customers> getCustomersFromBranchByIfscCode(@PathVariable String ifsc) {
        if (!branchService.getBranchByIfscCode(ifsc).isPresent()) {
            throw new InvalidRequestException("Branch with IFSC Code " +
                    ifsc + " does not exist.");
        }
        Branch branch = branchService.getBranchByIfscCode(ifsc).get();
        return branchService.getCustomerFromBranchId(branch.getId());
    }

    @PutMapping("/update/branch/{id}")
    public Branch updateInfo(@RequestBody Branch branch, @PathVariable int id) {
        if (branchService.getBranchById(id).isPresent()) {
            branch.setId(id);
            return branchService.update(branch);
        } else {
            throw new InvalidRequestException("Branch with ID " +
                    id + " does not exist.");
        }
    }

}
