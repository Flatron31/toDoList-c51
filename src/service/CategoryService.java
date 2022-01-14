package service;

import repository.DBRepository;

import java.util.ArrayList;

public class CategoryService {
    DBRepository dbRepository = new DBRepository();

    public ArrayList<String> printAllCategory() {
        return dbRepository.printAllCategory();
    }

    public boolean checkNameCategory(String nameCategory) {
        return dbRepository.checkNameCategory(nameCategory);
    }

    public void addCategory(String newNameCategory) {
        dbRepository.addCategory(newNameCategory);
    }

    public void changeNameCategoryByNameDBRepository(String nameCategory, String newNameCategory) {
        dbRepository.changeNameCategoryByNameDBRepository(nameCategory, newNameCategory);
    }

    public void changeNameCategoryOnDefaultNameCategoryInTasks(int nameCategory) {
        dbRepository.changeNameCategoryOnDefaultNameCategoryInTasks(nameCategory);
    }

    public void deleteCategory(String nameCategory) {
        dbRepository.deleteCategory(nameCategory);
    }

    public int getIdCategory(String nameCategory) {
        return dbRepository.getIdCategory(nameCategory);
    }


}
