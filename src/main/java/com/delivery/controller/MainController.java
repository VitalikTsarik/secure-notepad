package com.delivery.controller;

import com.delivery.dto.SessionKeyRequest;
import com.delivery.dto.SessionKeyResponse;
import com.delivery.dto.TextDTO;
import com.delivery.repository.SessionsRepo;
import com.delivery.service.UserService;
import com.delivery.util.RSAUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/")
public class MainController {

    private final static Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private SessionsRepo sessionsRepo;

    @GetMapping("/sessionKey")
    public ResponseEntity<?> getSessionKey(@RequestParam String openRSAkey) throws NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException, UnsupportedEncodingException {
        RSAUtil.encrypt("DATA", openRSAkey);

        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        SecretKey sessionKey = keyGenerator.generateKey();

        String sessionKeyString = Base64.getEncoder().encodeToString(sessionKey.getEncoded());
        logger.info("Generated session key: " + sessionKeyString);

        SessionKeyResponse response = new SessionKeyResponse();
        response.setEncryptedSessionKey(Base64.getEncoder().encodeToString(RSAUtil.encrypt(sessionKeyString, openRSAkey)));
        logger.info("Encrypted session key: " + response.getEncryptedSessionKey());

        sessionsRepo.getSecretKeyMap().put(response.getEncryptedSessionKey(), sessionKey);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/text")
    public ResponseEntity<?> postText(@RequestBody TextDTO textDTO) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        SecretKey secretKey = sessionsRepo.getSecretKeyMap().get(textDTO.getEncryptedSessionKey());

//        // decode the base64 encoded string
//        byte[] decodedKey = Base64.getDecoder().decode(RSAUtil.decrypt(textDTO.getEncryptedSessionKey(), publicRSAkey));
//        // rebuild key using SecretKeySpec
//        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        String decryptedText = new String(cipher.doFinal(textDTO.getEncryptedText().getBytes()));
        logger.info("Decrypted text: " + decryptedText);

        return ResponseEntity.ok().build();
    }

//	@PostMapping("/signin")
//	public ResponseEntity<?> authenticateUser(@Valid @RequestBody SignInRequest signInRequest) {
//		User user;
//		try {
//			user = userService.signIn(signInRequest.getLogin(), signInRequest.getPassword());
//		} catch (BadCredentialsException ex) {
//			return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
//		}
//
//		return ResponseEntity.ok(
//				new JwtResponse(
//						jwtUtils.generateJwtToken(user),
//						user.getLogin(),
//						user.getRole()
//				)
//		);
//	}
//
//	@PostMapping("/signup")
//	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
//		try {
//			userService.signUp(
//					signUpRequest.getLogin(),
//					signUpRequest.getPassword(),
//					signUpRequest.getFirstName(),
//					signUpRequest.getMiddleName(),
//					signUpRequest.getLastName(),
//					signUpRequest.getRole()
//			);
//		} catch (LoginIsBusyException e) {
//			return ResponseEntity
//					.badRequest()
//					.body(new MessageResponse("Error: Login is busy!"));
//		}
//
//		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
//	}
}
