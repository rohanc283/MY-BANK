package com.neosoft.mybank.Service;

import com.neosoft.mybank.Model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private com.neosoft.mybank.Repository.Admin adminRepo;

    public List<Admin> getAllAdmin(){
        return adminRepo.findAll();
    }

    public Admin add(Admin admin) {
        return adminRepo.save(admin);
    }

    public void delete(int id) {
        adminRepo.deleteById(id);
    }

    public Optional<Admin> getAdminById(int id) {
        // TODO Auto-generated method stub
        return adminRepo.findById(id);
    }
    public Admin update(Admin admin) {
        // TODO Auto-generated method stub
        return adminRepo.save(admin);
    }


}
