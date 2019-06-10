package gpse.team52.contract;

public interface HasPassword {

    String getPassword();
    String getPasswordConfirm();

    void setPassword(String password);
    void setPasswordConfirm(String passwordConfirm);
}
