# Secure Notepad

Application implements browsing, editing and creating of text file that stores on the server. It ensures intruder can't read the text files even if communication messages between client and server got intercepted.

## Encryption algorithm summary

1. Secure connection is established by using RSA asymmetric encryption. Client generates pair of private and public keys, than sends public key to the server
2. Texts encrypted by AES algorithm (OFB — Output Feed
Back). Session key initially transferred through a secure connection (using RSA). 


### Run Spring Boot server from source root
```
mvn spring-boot:run
```
### Run React App from _src/main/frontend/_
```
npm run start
```
