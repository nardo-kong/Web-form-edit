package com.example.demo.Controllers;
import com.example.demo.Entities.*;
import com.example.demo.Repositories.*;
import com.example.demo.Services.*;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;






@Controller
public class HomeController {

    @Autowired
    private AdministratorRepository adminRepo;
    @Autowired
    private ScaleRepository scaleRepository;
    @Autowired
    private AnswerRecordRepository answerRecordRepository;
    @Autowired
    private AnswerRecordService answerRecordService;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private QuestionService questionService;

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
            return "redirect:/Dashboard";
        }
    }

    @GetMapping("/Dashboard")
    public String dashboard(Model model) {
        List<Scale> scales = scaleRepository.findAll();
        Map<String, Integer> completedCounts = new HashMap<>();
    
        for (Scale scale : scales) {
            int count = answerRecordRepository.countByScaleIdAndCompleted(scale.getId(), true);
            completedCounts.put(scale.getTitle(), count);
        }
    
        model.addAttribute("scales", scales);
        model.addAttribute("completedCounts", completedCounts);
    
        return "dashboard";
    }

    @PostMapping("/createQus")
    public String createQus() {
        
        return "AddQus";
    }
    
    @GetMapping("/view/{id}")
    public String viewRecords(@PathVariable Long id, Model model) {
        // 1. 根据scale-id查询answer-record
        List<AnswerRecord> answerRecords = answerRecordService.getAnswerRecordsByScaleId(id);
    
        // 2. 对于每个answer-record，根据answer-record-id查询answer
        Map<AnswerRecord, List<Answer>> recordAnswerMap = new LinkedHashMap<>();
        for (AnswerRecord answerRecord : answerRecords) {
            List<Answer> answers = answerService.getAnswersByRecordId(answerRecord.getId());
            recordAnswerMap.put(answerRecord, answers);
        }

        // 3. 查询问题题目
        List<Question> questions = questionService.getQuestionsByScaleTitleRemoveIllustration(id);
    
        // 4. 将查询结果添加到model中
        model.addAttribute("recordAnswerMap", recordAnswerMap);
        model.addAttribute("questions", questions);
    
        // 5. 返回视图的名称
        return "ViewRecords";
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

