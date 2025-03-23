package lk.ijse.gdse.bo.custom.impl;

import lk.ijse.gdse.bo.EncryptAndDecrypt;
import lk.ijse.gdse.config.FactoryConfiguration;
import lk.ijse.gdse.entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncryptAndDecryptImpl implements EncryptAndDecrypt {

    private final BCryptPasswordEncoder passwordEncoder;

    public EncryptAndDecryptImpl(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean verifyUser(String email, String password) {
        Session session = FactoryConfiguration.getInstance().getSession();

        Query<User> query = session.createQuery("FROM User u WHERE u.email = :email", User.class);
        query.setParameter("email", email);
        User user = query.uniqueResult();

        boolean isMatch = false;

        if(user != null){
            isMatch = passwordEncoder.matches(password, user.getPassword());
        }

        return isMatch;
    }

    @Override
    public String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
