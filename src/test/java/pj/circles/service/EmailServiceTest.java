package pj.circles.service;

import org.junit.jupiter.api.Test;
import pj.circles.controller.EmailController;

import static org.junit.jupiter.api.Assertions.*;


class EmailServiceTest {




    @Test
    void createKey() {
        EmailController emailController = null;
        String a = emailController.createKey();
        System.out.println(a);
        String b = emailController.createKey();
        System.out.print(b);
    }
}