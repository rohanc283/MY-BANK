package com.neosoft.mybank.Controller;

import com.neosoft.mybank.Exceptions.InvalidRequestException;
import com.neosoft.mybank.Model.Customers;
import com.neosoft.mybank.Model.MoneySentReceived;
import com.neosoft.mybank.Service.CustomerService;
import com.neosoft.mybank.Service.MoneySentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class MoneySentController {

    @Autowired
    private MoneySentService moneySentService;

    @Autowired
    private CustomerService customerService;



    @GetMapping("/money_sent_received")
    public List<MoneySentReceived> getALlMoneySent(){
        return moneySentService.getAllMoneySentReceived();
    }

    @GetMapping("/money_sended/{accNo}")
    public List<MoneySentReceived> getALlMoneySend(@PathVariable String accNo){
        return moneySentService.getMoneySentByAccNo(accNo);
    }
    @GetMapping("/money_received/{accNo}")
    public List<MoneySentReceived> getALlMoneyReceived(@PathVariable String accNo){
        return moneySentService.getMoneyReceivedByAccNo(accNo);
    }

    @GetMapping ("/send_money_to_accNo/{senderAccNo}/{receiverAccNO}/{amount}")
    public MoneySentReceived sendMoney(@PathVariable String senderAccNo,
                               @PathVariable String receiverAccNO , @PathVariable float amount){

        if(!customerService.getCustomerByAccNo(senderAccNo).isPresent()){
            throw new InvalidRequestException("Customers with Acc No " +
                    senderAccNo + " does not exist.");
        }
        if(!customerService.getCustomerByAccNo(receiverAccNO).isPresent()){
            throw new InvalidRequestException("Customers with Acc No " +
                    receiverAccNO + " does not exist.");
        }
        Customers sender = customerService.getCustomerByAccNo(senderAccNo).get();
        if(amount>sender.getBalance()){
            throw new InvalidRequestException("Customers with Acc No " +
                    senderAccNo + " does not have." + amount+ " to send to " + receiverAccNO);
        }
        Customers receiver = customerService.getCustomerByAccNo(receiverAccNO).get();
        Format formatter = new SimpleDateFormat("dd-MMM-yy hh:mm:ss");
        String str = formatter.format(new Date());
        sender.setBalance(sender.getBalance()-amount);
        receiver.setBalance(receiver.getBalance()+amount);
        MoneySentReceived moneySent = new MoneySentReceived();
        moneySent.setSenderAccNo(senderAccNo);
        moneySent.setReceiverAccNo(receiverAccNO);
        moneySent.setDate(str);
        moneySent.setAmount(amount);
        customerService.update(sender);
        customerService.update(receiver);
        return moneySentService.add(moneySent);
    }
}
