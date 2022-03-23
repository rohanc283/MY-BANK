package com.neosoft.mybank.Repository;

import com.neosoft.mybank.Model.MessageStatus;
import com.neosoft.mybank.Model.MoneySentReceived;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MoneySentReceivedRepo extends JpaRepository<com.neosoft.mybank.Model.MoneySentReceived,Integer> {
    @Transactional
    @Modifying
    @Query("SELECT m FROM MoneySentReceived m WHERE  m.senderAccNo=:accNo")
    List<MoneySentReceived> moneySent(String accNo);

    @Transactional
    @Modifying
    @Query("SELECT m FROM MoneySentReceived m WHERE  m.receiverAccNo=:accNo")
    List<MoneySentReceived> moneyReceived(String accNo);


}
