package model;

public class Accountant extends AbstractUser {

    private Role role;

    public Accountant(String name, String surname, String patronymic, String phoneNumber,
                      String email, String password, Integer accountCode) {
        super(name, surname, patronymic, phoneNumber, email, password, accountCode);
        role = Role.ACCOUNTANT;
    }
}
