package com.example.demo.Controllers;
import com.example.demo.Entities.Administrator;
import com.example.demo.Repositories.AdministratorRepository;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;





@Controller
public class HomeController {

    @Autowired
    private AdministratorRepository adminRepo;

    @GetMapping("/")
    public String home() {
        return "Home";
    }

    @PostMapping("/login")
    public String login(@RequestParam String name, @RequestParam String password, Model model, RedirectAttributes redirectAttributes) {
        String hashedPassword = hashPassword(password);
        Administrator admin = adminRepo.findByName(name);

        if (admin == null || !admin.getPassword().equals(hashedPassword)) {
            model.addAttribute("error", "Invalid username or password");
            return "Home";
        } else {
            //return "redirect:/admin";
            return "AddQus";
        }
    }

    
    public String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedhash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString().toLowerCase();
    }
    
}

