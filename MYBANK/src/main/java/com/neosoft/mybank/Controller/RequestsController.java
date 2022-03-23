package com.neosoft.mybank.Controller;

import com.neosoft.mybank.Exceptions.InvalidRequestException;
import com.neosoft.mybank.Model.*;
import com.neosoft.mybank.Service.BranchService;
import com.neosoft.mybank.Service.CustomerService;
import com.neosoft.mybank.Service.MessageStatusService;
import com.neosoft.mybank.Service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class RequestsController {

    @Autowired
    private RequestService requestService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private MessageStatusService messageStatusService;

    @GetMapping("/message-status")
    public List<MessageStatus> getAllMessageStatus(){
        return messageStatusService.getAllStatus();
    }

    @GetMapping("/message-status/{accNo}")
    public List<MessageStatus> getAllMessageStatusFromAccNo(@PathVariable String accNo){
        List<MessageStatus> messageStatusList = messageStatusService.getAllStatus();
        List<MessageStatus> messageStatuses = new ArrayList<>();
        for (MessageStatus messageStatus : messageStatusList) {
            if (messageStatus.getRequests().getCustomers().getAccNO().equals(accNo)) {
                messageStatuses.add(messageStatus);
            }
        }
        return messageStatuses;
    }

    @GetMapping("/requests")
    public List<MessageStatus> getALlRequests(){
        List<MessageStatus> messageStatusList = messageStatusService.getAllStatusUnderReview();
        List<MessageStatus> messageStatusList2 = messageStatusService.getAllStatusUnderProcessing();
        List<MessageStatus> requests = new ArrayList<>(messageStatusList);
        requests.addAll(messageStatusList2);
        return requests;
    }

    @PostMapping("/add/requestsOfCustomers/{accNO}")
    public void addRequests(@RequestBody Requests requests , @PathVariable String accNO){
        if (!customerService.getCustomerByAccNo(accNO).isPresent()) {
            throw new InvalidRequestException("Customer with Acc no " +
                    accNO + " does not exist.");
        }
        Customers customer = customerService.getCustomerByAccNo(accNO).get();
        Format formatter = new SimpleDateFormat("dd-MMM-yy hh:mm:ss");
        String str = formatter.format(new Date());
        requests.setCustomers(customer);
        requests.setDate(str);
        requestService.add(requests);
        MessageStatus messageStatus = new MessageStatus();
        messageStatus.setStatus(String.valueOf(StatusOptions.UNDER_REVIEW));
        messageStatus.setRequests(requests);
        messageStatusService.add(messageStatus);
    }

    @GetMapping("/requests_from_customers/{accNo}")
    public List<Requests> getRequestsFromCustomerByAccno(@PathVariable String accNo) {
        if (!customerService.getCustomerByAccNo(accNo).isPresent()) {
            throw new InvalidRequestException("Customer with Acc NO " +
                    accNo + " does not exist.");
        }
        Customers customer = customerService.getCustomerByAccNo(accNo).get();
        return requestService.getRequestsFromCustomer(customer.getId());
    }


    @DeleteMapping("/delete/request/{id}")
    public void  delete(@PathVariable int id) {
        if (!requestService.getRequestById(id).isPresent()) {
            throw new InvalidRequestException("Request with ID " +
                    id + " does not exist.");
        }

        MessageStatus messageStatus = messageStatusService.getMessageStatusByCusId(id);
        messageStatus.setStatus(String.valueOf(StatusOptions.REJECTED));
        messageStatusService.update(messageStatus);
    }

    @GetMapping("/under_processing/request/{id}")
    public void  underProcessing(@PathVariable int id) {
        if (!requestService.getRequestById(id).isPresent()) {
            throw new InvalidRequestException("Request with ID " +
                    id + " does not exist.");
        }
        MessageStatus messageStatus = messageStatusService.getMessageStatusByCusId(id);
        messageStatus.setStatus(String.valueOf(StatusOptions.UNDER_PROCESSING));
        messageStatusService.update(messageStatus);
    }

    @GetMapping("/approve/request/{id}")
    public void  approve(@PathVariable int id) {
        if (!requestService.getRequestById(id).isPresent()) {
            throw new InvalidRequestException("Request with ID " +
                    id + " does not exist.");
        }
        MessageStatus messageStatus = messageStatusService.getMessageStatusByCusId(id);
        messageStatus.setStatus(String.valueOf(StatusOptions.APPROVED));
        messageStatusService.update(messageStatus);
    }

}
