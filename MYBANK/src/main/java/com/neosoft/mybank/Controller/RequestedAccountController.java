package com.neosoft.mybank.Controller;

import com.neosoft.mybank.Exceptions.InvalidRequestException;
import com.neosoft.mybank.Model.ApplicationStatus;
import com.neosoft.mybank.Model.RequestedAccounts;
import com.neosoft.mybank.Model.StatusOptions;
import com.neosoft.mybank.Service.ApplicationStatusService;
import com.neosoft.mybank.Service.BranchService;
import com.neosoft.mybank.Service.RequestedAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class RequestedAccountController {

    @Autowired
    private RequestedAccountService requestedAccountService;

    @Autowired
    private BranchService branchService;

    @Autowired
    private ApplicationStatusService applicationStatusService;

    @GetMapping("/requested_accounts")
    public List<ApplicationStatus> getALlRequestedAcc(){
        List<ApplicationStatus> applicationStatusList = applicationStatusService.getAllStatusUnderReview();
        List<ApplicationStatus> applicationStatusList2 = applicationStatusService.getAllStatusUnderProcessing();
        List<ApplicationStatus> requestedAccountsList = new ArrayList<>(applicationStatusList);
        requestedAccountsList.addAll(applicationStatusList2);
        return requestedAccountsList;
    }

    @PostMapping("/add/requested_accounts/{branchCode}")
    public void addRequestedAccounts(@RequestBody RequestedAccounts requestedAccounts,@PathVariable String branchCode){
        if (!branchService.getBranchByIfscCode(branchCode).isPresent()) {
            throw new InvalidRequestException("Branch with Ifsc Code " +
                    branchCode + " does not exist.");
        }
        RequestedAccounts requestedAccounts1 = new RequestedAccounts();
        if (requestedAccountService.getRequestedAccountsByAdharNo(requestedAccounts.getAdhaarNo()).isPresent()) {
            requestedAccounts1 = requestedAccountService.getRequestedAccountsByAdharNo(requestedAccounts.getAdhaarNo()).get();
            requestedAccounts1.setBranch(branchService.getBranchByIfscCode(branchCode).get());
            requestedAccounts1.setName(requestedAccounts.getName());
            requestedAccounts1.setPhoneNo(requestedAccounts.getPhoneNo());
            requestedAccounts1.setEmailId(requestedAccounts.getEmailId());
            requestedAccounts1.setDob(requestedAccounts.getDob());
            requestedAccounts1.setAccType(requestedAccounts.getAccType());
            requestedAccounts1.setAddress(requestedAccounts.getAddress());
            requestedAccounts1.setPanNo(requestedAccounts.getPanNo());
            requestedAccountService.update(requestedAccounts1);
            ApplicationStatus applicationStatus = applicationStatusService.getApplicationStatusByRequestId(requestedAccounts1.getId());
            applicationStatus.setStatus(String.valueOf(StatusOptions.UNDER_REVIEW));
            applicationStatusService.update(applicationStatus);

        }else if(requestedAccountService.getRequestedAccountsByPanNo(requestedAccounts.getPanNo()).isPresent()){
            requestedAccounts1 = requestedAccountService.getRequestedAccountsByPanNo(requestedAccounts.getPanNo()).get();
            requestedAccounts1.setBranch(branchService.getBranchByIfscCode(branchCode).get());
            requestedAccounts1.setName(requestedAccounts.getName());
            requestedAccounts1.setPhoneNo(requestedAccounts.getPhoneNo());
            requestedAccounts1.setEmailId(requestedAccounts.getEmailId());
            requestedAccounts1.setDob(requestedAccounts.getDob());
            requestedAccounts1.setAdhaarNo(requestedAccounts.getAdhaarNo());
            requestedAccounts1.setAccType(requestedAccounts.getAccType());
            requestedAccounts1.setAddress(requestedAccounts.getAddress());
            requestedAccountService.update(requestedAccounts1);
            ApplicationStatus applicationStatus = applicationStatusService.getApplicationStatusByRequestId(requestedAccounts1.getId());
            applicationStatus.setStatus(String.valueOf(StatusOptions.UNDER_REVIEW));
            applicationStatusService.update(applicationStatus);
        }
        else {
            requestedAccounts.setBranch(branchService.getBranchByIfscCode(branchCode).get());
            requestedAccountService.add(requestedAccounts);
            ApplicationStatus status = new ApplicationStatus();
            status.setRequestedAccounts(requestedAccounts);
            status.setStatus(String.valueOf(StatusOptions.UNDER_REVIEW));
            applicationStatusService.add(status);
        }

    }

    @DeleteMapping("/delete/requested_accounts/{id}")
    public void  delete(@PathVariable int id) {
        if (!requestedAccountService.getRequestedAccountsById(id).isPresent()) {
            throw new InvalidRequestException("RequestedAccount with ID " +
                    id + " does not exist.");
        }
        ApplicationStatus applicationStatus = applicationStatusService.getApplicationStatusByCusId(id);
        applicationStatus.setStatus(String.valueOf(StatusOptions.REJECTED));
        applicationStatusService.update(applicationStatus);
    }

    @GetMapping("/under_processing/requested_accounts/{id}")
    public void  underProcessing(@PathVariable int id) {
        if (!requestedAccountService.getRequestedAccountsById(id).isPresent()) {
            throw new InvalidRequestException("RequestedAccount with ID " +
                    id + " does not exist.");
        }
        ApplicationStatus applicationStatus = applicationStatusService.getApplicationStatusByCusId(id);
        applicationStatus.setStatus(String.valueOf(StatusOptions.UNDER_PROCESSING));
        applicationStatusService.update(applicationStatus);
    }

    @GetMapping("/requested_accounts/{id}")
    public RequestedAccounts getAdminById(@PathVariable int id) {
        if (!requestedAccountService.getRequestedAccountsById(id).isPresent()) {
            throw new InvalidRequestException("RequestedAccount with ID " +
                    id + " does not exist.");
        }
        return requestedAccountService.getRequestedAccountsById(id).get();

    }

}
