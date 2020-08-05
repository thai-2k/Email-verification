package com.example.springboottodomvc.service;

import com.example.springboottodomvc.model.User;
import com.example.springboottodomvc.model.VericationToken;
import com.example.springboottodomvc.repository.UserRepository;
import com.example.springboottodomvc.repository.VericationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VerificationTokenService {
    private UserRepository userRepository;
    private VericationTokenRepository vericationTokenRepository;
    private SendingMailService sendingMailService;

    @Autowired
    public VerificationTokenService(UserRepository userRepository,VerificationTokenService verificationTokenService,SendingMailService sendingMailService){
        this.userRepository = userRepository;
        this.vericationTokenRepository = vericationTokenRepository;
        this.sendingMailService = sendingMailService;
    }
    public void createVerification(String email){
        List<User> users = userRepository.findByEmail(email);
        User user;
        if (users.isEmpty()){
            user = new User();
            user.setEmail(email);
            userRepository.save(user);
        }else {
            user = users.get(0);
        }
        List<VericationToken> vericationTokens = vericationTokenRepository.findByUserEmail(email);
        VericationToken vericationToken;
        if (vericationTokens.isEmpty()){
            vericationToken = new VericationToken();
            vericationToken.setUser(user);
            vericationTokenRepository.save(vericationToken);
        }else {
            vericationToken = vericationTokens.get(0);
        }
        sendingMailService.sendVerificationMail(email, vericationToken.getToken());
    }
    public ResponseEntity<String> verifyEmail(String token){
        List<VericationToken> vericationTokens = vericationTokenRepository.findByToken(token);
        if (vericationTokens.isEmpty()){
            return ResponseEntity.badRequest().body("Invalid token");
        }
        VericationToken vericationToken = vericationTokens.get(0);
        if (vericationToken.getExpiredDateTime().isBefore(LocalDateTime.now())){
            return ResponseEntity.unprocessableEntity().body("Expired token");
        }
        vericationToken.setConfirmedDateTime(LocalDateTime.now());
        vericationToken.setStatus(VericationToken.STATUS_VERIFIED);
        vericationToken.getUser().setActive(true);
        vericationTokenRepository.save(vericationToken);
        return ResponseEntity.ok("You have successfully verified your email address");
    }
}
