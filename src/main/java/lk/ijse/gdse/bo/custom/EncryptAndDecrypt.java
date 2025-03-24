package lk.ijse.gdse.bo.custom;

import lk.ijse.gdse.bo.SuperBo;

public interface EncryptAndDecrypt extends SuperBo {
    boolean verifyUser(String username, String password);
    String encryptPassword(String password);
}
