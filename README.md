# 3DES Encryption
3DES Encryption algorithm implementation in Java
- The application takes arguments from command line, the first argument is an encryption key, the second is text to be encrypted
- If encryption key and encryption text weren't passed from the command line, the application will try reading them from environment variables 
 `ENCRYPTION_KEY` `TEXT_TO_ENCRYPT`
- The application uses a 24 bit encryption key and 8 bit initialization vector
- Encryption key and initialization vector use SHA1PRNG for key generation
- DESede/CBC/PKCS5Padding is used as transformation
- Null values are not encrypted, if null used as an input for the encrypt method, the method will immediately return the null reference. The same 
applies to the decrypt method

## To run the application
- Checkout the project
- `mvn package`
- `java -jar coding-0.0.1-SNAPSHOT-jar-with-dependencies.jar KEY 'text to encrypt'`

## To run the application via docker-compose
- Checkout the project
- `mvn package`
- Open the `docker-compose.yml` and set `ENCRYPTION_KEY` `TEXT_TO_ENCRYPT` environment variables
- `docker-compose up`

## To run the application via docker 
- Checkout the project
- `mvn package`
- `docker build -t 3des-algorithm_encryption . `
- `docker run -it 3des-algorithm_encryption encryptionKey text`
