package com.neosoft.mybank.Repository;

import com.neosoft.mybank.Model.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ApplicationStatusRepo extends JpaRepository<ApplicationStatus,Integer> {
    @Transactional
    @Modifying
    @Query("UPDATE ApplicationStatus a SET a.status = :state where a.id =:idd")
    int updateApplicationStatus(String state , int idd);

    ApplicationStatus findApplicationStatusByRequestedAccounts_Id(int id);

    @Transactional
    @Modifying
    @Query("SELECT a FROM ApplicationStatus a WHERE  a.status=:state")
    List<ApplicationStatus> search(String state);


}
