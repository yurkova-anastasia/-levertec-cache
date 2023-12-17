package ru.clevertec.cache.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import ru.clevertec.cache.config.AppConfig;
import ru.clevertec.cache.dto.UserResponseDto;
import ru.clevertec.cache.exception.BadRequestException;
import ru.clevertec.cache.service.UserService;
import ru.clevertec.cache.util.pdf.UserPdfPrinter;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/check/*")
@Setter
public class CheckServlet extends HttpServlet {

    private UserService userService;
    private UserPdfPrinter userPdfPrinter;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        this.userService = AppConfig.getUserService();
        this.userPdfPrinter = AppConfig.getUserPdfPrinter();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();
        String[] uriElements = req.getRequestURI().split("/");
        if (uriElements.length == 3) {
            if (uriElements[2].equals("user")) {
                Long id = Long.valueOf(req.getParameter("id"));
                UserResponseDto userResponseDto = userService.findById(id);
                userPdfPrinter.print(userResponseDto);
                writer.print("The receipt is saved in the current directory");
            } else {
                throw new BadRequestException("It is not possible to generate a receipt for" + uriElements[2]);
            }
        } else {
            throw new BadRequestException("Invalid HTTP request format");
        }
    }

}
