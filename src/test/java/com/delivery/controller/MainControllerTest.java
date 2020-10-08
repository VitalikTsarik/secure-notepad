package com.delivery.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class MainControllerTest {

    @Test
    public void test() throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException {
        MainController controller = new MainController();
        //controller.getSessionKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDVAFUAKu4ip9BhmdSwyuqE7dGjV1qzXurVBhIKtMDYksarlT6awnL08LVsEyDleVCuhWxBZtBGUw8GmL3EiDFeAxlKtX4xPAiGm1JLPFevggZbwD75nYVzYAOW57QfXReYFl++Fnjw+PLjHjP7jdLe6BawuuoT+DNEOBjSWh6HkQIDAQAB");
    }

}
