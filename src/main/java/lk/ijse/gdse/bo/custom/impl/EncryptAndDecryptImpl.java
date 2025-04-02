package lk.ijse.gdse.bo.custom.impl;

import lk.ijse.gdse.bo.custom.EncryptAndDecrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class EncryptAndDecryptImpl implements EncryptAndDecrypt {

    private final BCryptPasswordEncoder passwordEncoder;

    public EncryptAndDecryptImpl(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean verifyUser(String checkPassword, String password) {
        return passwordEncoder.matches(password, checkPassword);
    }

    @Override
    public String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
