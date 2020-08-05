package com.example.springboottodomvc.web;

import com.example.springboottodomvc.model.VericationForm;
import com.example.springboottodomvc.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.net.BindException;

@Controller
public class AccountController {
    @Autowired
    VerificationTokenService verificationTokenService;

    @GetMapping("/email-varification")
    public String formGet(Model model){
        model.addAttribute("verificationForm", new VericationForm());
        return "verification-form"; }

        @PostMapping("/email-verification")
    public String formPost(@Valid VericationForm vericationForm, BindingResult bindResult, Model model){
        if (!bindResult.hasErrors()){
            model.addAttribute("noErrors",true);
            model.addAttribute("verification",vericationForm);
            verificationTokenService.createVerification(vericationForm.getEmail()); }
            return "verification-form"; }
    @GetMapping("/verify-email")
    @ResponseBody
    public String verifyEmail(String code){
        return verificationTokenService.verifyEmail(code).getBody(); }

}
