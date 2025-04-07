package lk.ijse.gdse.bo.custom.impl;

import lk.ijse.gdse.bo.custom.EncryptAndDecryptBo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class EncryptAndDecryptBoImpl implements EncryptAndDecryptBo {

    private final BCryptPasswordEncoder passwordEncoder;

    public EncryptAndDecryptBoImpl(BCryptPasswordEncoder passwordEncoder) {
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
