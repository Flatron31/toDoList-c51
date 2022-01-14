package service;

import repository.DBRepository;

import java.util.ArrayList;


public class TaskService {
    DBRepository dbRepository = new DBRepository();

    public void addTaskUser(String nameTask, String descriptionTask, int categoryTask, int statusTask, int userId) {
        dbRepository.addTask(nameTask, descriptionTask, categoryTask, statusTask, userId);
    }

    public ArrayList<String> printAllTasksUser(int userId) {
        return dbRepository.printAllTasksUser(userId);

    }

    public void deleteTaskByID(int idTask) {
        dbRepository.deleteTask(idTask);
    }

    public ArrayList<String> printAllTasks() {
        return dbRepository.printAllTasks();

    }

    public boolean checkIdTask(int idTask) {
        return dbRepository.checkIdTask(idTask);
    }

    public void changeStatusTaskById(int idTask, int newIdStatus) {
        dbRepository.changeStatusTaskById(idTask, newIdStatus);
    }

    public ArrayList<String> printInfoTasksById(int idTask) {
        return dbRepository.printInfoTasksById(idTask);

    }

    //------- изменение task
    public void changeNameTaskDbRepository(int idTask, String newName) {
        dbRepository.changeNameTaskDbRepository(idTask, newName);
    }

    public void changeDescriptionTaskDbRepository(int idTask, String newDescription) {
        dbRepository.changeDescriptionTaskDbRepository(idTask, newDescription);
    }

    public void changeCategoryTaskDbRepository(int idTask, int newIdCategory) {
        dbRepository.changeCategoryTaskDbRepository(idTask, newIdCategory);
    }

    public void changeUserTaskDbRepository(int idTask, int newIdUser){
        dbRepository.changeUserTaskDbRepository(idTask, newIdUser);
    }



    public ArrayList<String> printAllStatuses() {
        return dbRepository.printAllStatuses();
    }

    public ArrayList<String> printAllCategories(){
        return dbRepository.printAllCategory();
    }

    public boolean checkIdCategory(int idCategory) {
        return dbRepository.checkIdCategory(idCategory);
    }

    public boolean checkIdStatus(int idStatus){
        return dbRepository.checkIdStatus(idStatus);
    }


}
