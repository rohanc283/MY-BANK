package com.neosoft.mybank.Controller;

import com.neosoft.mybank.Model.ApplicationStatus;
import com.neosoft.mybank.Service.ApplicationStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ApplicationStatusController {

    @Autowired
    private ApplicationStatusService applicationStatusService;

    @GetMapping("/application-status")
    public List<ApplicationStatus> getALlStatus(){
        return applicationStatusService.getAllStatus();
    }
}
