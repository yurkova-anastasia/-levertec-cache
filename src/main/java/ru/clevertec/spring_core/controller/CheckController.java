package ru.clevertec.spring_core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.spring_core.dto.UserResponseDto;
import ru.clevertec.spring_core.service.UserService;
import ru.clevertec.spring_core.util.pdf.UserPdfPrinter;

@RestController
@RequestMapping("/check")
public class CheckController {

    private UserService userService;
    private UserPdfPrinter userPdfPrinter;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void setUserPdfPrinter(UserPdfPrinter userPdfPrinter) {
        this.userPdfPrinter = userPdfPrinter;
    }

    @GetMapping("/user")
    public String generateReceipt(@RequestParam Long id) {
        UserResponseDto userResponseDto = userService.findById(id);
        userPdfPrinter.print(userResponseDto);
        return "The receipt is saved in the current directory";
    }
}
