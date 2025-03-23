package lk.ijse.gdse.bo;

public interface EncryptAndDecrypt extends SuperBo{
    boolean verifyUser(String username, String password);
    String encryptPassword(String password);
}
