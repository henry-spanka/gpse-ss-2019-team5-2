package gpse.team52.contract;

/**
 * HasPassword Validation interface.
 */
public interface HasPassword {

    String getPassword();
    String getPasswordConfirm();

    void setPassword(String password);
    void setPasswordConfirm(String passwordConfirm);
}
