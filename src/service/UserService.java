package service;

import entity.User;
import repository.DBRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class UserService {
    private BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    DBRepository dbRepository = new DBRepository();
    private User user;

    public int getIDUser() {
        return user.getId();
    }

    public int getRole() {
        return user.getRole();
    }


    public boolean authenticationUserByCred(String loginUser, String passwordUser) {
        return dbRepository.authenticationUserByCredDB(loginUser, passwordUser);
    }

    public void createUser(String a) {
        this.user = dbRepository.createUserDBRepository(a);
    }

    public ArrayList<String> printAllInfoUser(int userId) {
        return dbRepository.printAllInfoUser(userId);
    }

    public ArrayList<String> printInfoRole() {
        return dbRepository.printInfoRole();

    }

    public ArrayList<String> printAllUsersInfo() {
        return dbRepository.printAllUsersInfo();
    }

    public boolean checkIdUser(int idUser) {
        return dbRepository.checkIdUser(idUser);
    }

    public boolean checkLoginUser(String newLoginUser) {
        return dbRepository.checkLoginUser(newLoginUser);
    }

    public boolean checkIdRoleUser(int idRoleUser) {
        return dbRepository.checkIdRoleUser(idRoleUser);
    }

    public void deleteUser(int idUser) {
        dbRepository.deleteUser(idUser);
    }

    public void addUser(String userLogin, String userPassword, String userName, int userRole) {
        dbRepository.addUser(userLogin, userPassword, userName, userRole);
    }


    public void changeLoginUserByIdDBRepository(int idUser, String newLogin) {
        dbRepository.changeLoginUserByIdDBRepository(idUser, newLogin);
    }

    public void changeNameUserByIdDBRepository(int idUser, String newName) {
        dbRepository.changeNameUserByIdDBRepository(idUser, newName);
    }

    public void changePasswordUserByIdDBRepository(int idUser, String newPassword) {
        dbRepository.changePasswordUserByIdDBRepository(idUser, newPassword);
    }

    public void changeRoleUserByIdDBRepository(int idUser, int newIdRoleUser) {
        dbRepository.changeRoleUserByIdDBRepository(idUser, newIdRoleUser);
    }


}
