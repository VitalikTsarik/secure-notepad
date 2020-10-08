package com.delivery.controller;

import com.delivery.dto.SessionKeyRequest;
import com.delivery.dto.SessionKeyResponse;
import com.delivery.dto.SignInRequest;
import com.delivery.dto.SignUpRequest;
import com.delivery.dto.TextDTO;
import com.delivery.dto.TextWithIdDTO;
import com.delivery.dto.TextsDTO;
import com.delivery.entity.Text;
import com.delivery.entity.User;
import com.delivery.repository.SessionsRepo;
import com.delivery.repository.TextRepo;
import com.delivery.repository.UserRepo;
import com.delivery.service.UserService;
import com.delivery.util.RSAUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api")
public class MainController {

    public final static IvParameterSpec IV_SPEC = new IvParameterSpec(new byte[]{21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34,35, 36});

    private final static Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private SessionsRepo sessionsRepo;

    @Autowired
    private TextRepo textRepo;

    @Autowired
    private UserRepo userRepo;

    @PostMapping("/rsa")
    public ResponseEntity<?> getSessionKey(@RequestBody SessionKeyRequest request) throws NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException, UnsupportedEncodingException {
        String openRsaKey = request.getOpenRSAkey();
        openRsaKey = openRsaKey.replace("-----BEGIN PUBLIC KEY-----", "");
        openRsaKey = openRsaKey.replace("-----END PUBLIC KEY-----", "");
        openRsaKey = openRsaKey.replace("\n", "");
        request.setOpenRSAkey(openRsaKey);
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        SecretKey sessionKey = keyGenerator.generateKey();

        String sessionKeyString = Base64.getEncoder().encodeToString(sessionKey.getEncoded());
        logger.info("Generated session key: " + sessionKeyString);

        SessionKeyResponse response = new SessionKeyResponse();
        response.setEncryptedSessionKey(Base64.getEncoder().encodeToString(RSAUtil.encrypt(sessionKeyString, request.getOpenRSAkey())));
        logger.info("Encrypted session key: " + response.getEncryptedSessionKey());

        sessionsRepo.getSecretKeyMap().put(response.getEncryptedSessionKey(), sessionKey);
        sessionsRepo.getRsaKeysBySecretKey().put(response.getEncryptedSessionKey(), request.getOpenRSAkey());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/texts")
    public ResponseEntity<?> getTexts(@RequestParam String encryptedSessionKey) {
        long userId = sessionsRepo.getUserBySecretKey().get(encryptedSessionKey);
        List<Long> texts = textRepo
                .findAll()
                .stream()
                .filter(x -> x.getOwner().getId().equals(userId))
                .map(Text::getId)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new TextsDTO(texts));
    }

    @GetMapping("/text")
    public ResponseEntity<?> getText(@RequestParam String encryptedSessionKey, @RequestParam long textId) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
        SecretKey secretKey = sessionsRepo.getSecretKeyMap().get(encryptedSessionKey);
        long userId = sessionsRepo.getUserBySecretKey().get(encryptedSessionKey);
        Text text = textRepo.findById(textId).get();
        if (!text.getOwner().getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Cipher cipher = Cipher.getInstance("AES/OFB/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, IV_SPEC);

        byte[] encrypted = cipher.doFinal(text.getText().getBytes());

        return ResponseEntity.ok(Base64.getEncoder().encodeToString(encrypted));
    }

    @PostMapping("/text")
    public ResponseEntity<?> postText(@RequestParam String encryptedSessionKey, @RequestBody TextDTO textDTO) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
        SecretKey secretKey = sessionsRepo.getSecretKeyMap().get(encryptedSessionKey);
        long userId = sessionsRepo.getUserBySecretKey().get(encryptedSessionKey);

        Cipher cipher = Cipher.getInstance("AES/OFB/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, IV_SPEC);
        String decryptedText = new String(cipher.doFinal(Base64.getDecoder().decode(textDTO.getEncryptedText())));
        logger.info("Decrypted text: " + decryptedText);
        Text text = new Text();
        text.setText(decryptedText);
        text.setOwner(userRepo.findById(userId).get());
        textRepo.save(text);

        return ResponseEntity.ok(text.getId());
    }

    @PutMapping("/text")
    public ResponseEntity<?> putText(@RequestParam String encryptedSessionKey, @RequestBody TextWithIdDTO textDTO) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
        SecretKey secretKey = sessionsRepo.getSecretKeyMap().get(encryptedSessionKey);
        Text text = textRepo.findById(textDTO.getId()).get();
        long userId = sessionsRepo.getUserBySecretKey().get(encryptedSessionKey);
        if (!text.getOwner().getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Cipher cipher = Cipher.getInstance("AES/OFB/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, IV_SPEC);
        String decryptedText = new String(cipher.doFinal(Base64.getDecoder().decode(textDTO.getEncryptedText())));
        logger.info("Decrypted text: " + decryptedText);
        text.setText(decryptedText);
        textRepo.save(text);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/text")
    public ResponseEntity<?> deleteText(@RequestParam String encryptedSessionKey, @RequestParam long textId) {
        Text text = textRepo.findById(textId).get();
        long userId = sessionsRepo.getUserBySecretKey().get(encryptedSessionKey);
        if (!text.getOwner().getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        textRepo.deleteById(textId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("auth/signup")
    public ResponseEntity<?> registerUser(@RequestParam String encryptedSessionKey, @Valid @RequestBody SignUpRequest signUpRequest) throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, BadPaddingException, InvalidAlgorithmParameterException {
        SecretKey secretKey = sessionsRepo.getSecretKeyMap().get(encryptedSessionKey);
        Cipher cipher = Cipher.getInstance("AES/OFB/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, IV_SPEC);
        String login = new String(cipher.doFinal(Base64.getDecoder().decode(signUpRequest.getLogin())));
        String password = new String(cipher.doFinal(Base64.getDecoder().decode(signUpRequest.getPassword())));

        User user = userService.signUp(login, password, encryptedSessionKey);
        logger.info("User registered: " + user.getLogin());

        return ResponseEntity.ok().build();
    }

	@PostMapping("auth/signin")
	public ResponseEntity<?> authenticateUser(@RequestParam String encryptedSessionKey, @Valid @RequestBody SignInRequest signInRequest) throws NoSuchAlgorithmException, IllegalAccessException, IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, BadPaddingException, InvalidAlgorithmParameterException {
        SecretKey secretKey = sessionsRepo.getSecretKeyMap().get(encryptedSessionKey);
        Cipher cipher = Cipher.getInstance("AES/OFB/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, IV_SPEC);
        String login = new String(cipher.doFinal(Base64.getDecoder().decode(signInRequest.getLogin())));
        String password = new String(cipher.doFinal(Base64.getDecoder().decode(signInRequest.getPassword())));

        User user = userService.signIn(login, password);

        sessionsRepo.getUserBySecretKey().put(encryptedSessionKey, user.getId());

        return ResponseEntity.ok().build();
	}
}
