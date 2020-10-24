# Secure Notepad

Application implements browsing, editing, and creating text files that store on the server. It ensures intruder can't read the text files even if communication messages between client and server got intercepted.

## Encryption algorithm summary

1. A secure connection is established by using RSA asymmetric encryption. The client generates a pair of private and public keys, then sends the public key to the server.
2. Texts encrypted by AES algorithm (OFB — Output Feed Back). The session key initially transferred through a secure connection (using RSA).


### Run Spring Boot server from source root
```
mvn spring-boot:run
```
### Run React App from _src/main/frontend/_
```
npm run start
```
