package Controller;

import service.CategoryService;
import service.TaskService;
import service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Menu {
    private String numberConsole = "";
    private BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    UserService userService = new UserService();
    TaskService taskService = new TaskService();
    CategoryService categoryService = new CategoryService();

    public void showMenuUser() {
        System.out.println("Меню пользователя");
        while (true) {
            System.out.println("Выберите операцию\n" +
                    "1 - Посмотреть все tasks пользователя \n" +
                    "2 - Изменить статус по id \n" +
                    "3 - Посмотреть task по id \n" +
                    "4 - вернуться в предыдущее меню");
            String numberMenu = inputInConsole();
            if (numberMenu.equals("1")) {
                System.out.println("Все tasks пользователя");
                int userId = userService.getIDUser();
                ArrayList<String> allTasksUser = taskService.printAllTasksUser(userId);
                for (String str : allTasksUser) {
                    System.out.println(str);
                }
            } else if (numberMenu.equals("2")) {
                int idTask = -1;
                String taskId = "";
                while (true) {
                    System.out.println("\nВведите id task");
                    taskId = inputInConsole();
                    if (isNumber(taskId)) {
                        idTask = Integer.parseInt(taskId);
                    } else {
                        System.out.println("Нет task с таким id");
                        continue;
                    }
                    if (!taskService.checkIdTask(idTask)) {
                        System.out.println("Нет task с таким id");
                    } else {
                        break;
                    }
                }
                ArrayList<String> allStatuses = taskService.printAllStatuses();
                for (String str : allStatuses) {
                    System.out.println(str);
                }
                int newIdStatus = -1;
                String idStatus = "";
                while (true) {
                    System.out.println("\nВведите id статуса");
                    idStatus = inputInConsole();
                    if (isNumber(idStatus)) {
                        newIdStatus = Integer.parseInt(idStatus);
                    } else {
                        System.out.println("Нет статуса с таким id");
                        continue;
                    }
                    if (!taskService.checkIdStatus(newIdStatus)) {
                        System.out.println("Нет статуса с таким id");
                    } else {
                        break;
                    }
                }
                taskService.changeStatusTaskById(idTask, newIdStatus);
                System.out.println("Статус task изменен");
            } else if (numberMenu.equals("3")) {
                System.out.println("Введите id task");
                String idTask = inputInConsole();
                while (!isNumber(idTask)) {
                    System.out.println("\nВведите id task");
                    idTask = inputInConsole();
                }
                if (taskService.checkIdTask(Integer.parseInt(idTask))) {
                    ArrayList<String> allTasksUser = taskService.printInfoTasksById(Integer.parseInt(idTask));
                    for (String str : allTasksUser) {
                        System.out.println(str);
                    }
                } else {
                    System.out.println("Task с таким id нету");
                }
            } else if (numberMenu.equals("4")) {
                showMenu();
            } else {
                System.out.println("Повторите попытку. Введите нужную операцию");
            }
        }

    }

    //меню юзера +
    public void showMenu() {
        System.out.println("---ToDoList---");
        while (true) {
            System.out.println("Выберите операцию\n" +
                    "1 - Зарегистрироваться\n" +
                    "2 - Войти в имеющийся аккаунт\n" +
                    "3 - Конец работы");
            String numberMenu = inputInConsole();
            if (numberMenu.equals("1")) {
                System.out.println("Введите логин нового user");
                String newUserLogin = inputInConsole();
                while (!checkLength(newUserLogin)) {
                    System.out.println("\nлогин слишком маленький, введите еще раз логин");
                    newUserLogin = inputInConsole();
                }
                if (!userService.checkLoginUser(newUserLogin)) {
                    System.out.println("Введите пароль нового user");
                    String newUserPassword = inputInConsole();
                    while (!checkLength(newUserPassword)) {
                        System.out.println("\nлогин слишком маленький, введите еще раз пароль");
                        newUserPassword = inputInConsole();
                    }
                    System.out.println("Введите имя (ФИО) нового user");
                    String newUserName = inputInConsole();
                    while (!checkLength(newUserName)) {
                        System.out.println("\nимя слишком маленькое, введите еще раз");
                        newUserName = inputInConsole();
                    }
                    ArrayList<String> allRole = userService.printInfoRole();
                    for (String str : allRole) {
                        System.out.println(str);
                    }
                    int newIdRoleUser = -1;
                    String idRoleUser = "";
                    while (true) {
                        System.out.println("\nВведите роль id");
                        idRoleUser = inputInConsole();
                        if (isNumber(idRoleUser)) {
                            newIdRoleUser = Integer.parseInt(idRoleUser);
                        } else {
                            System.out.println("нет роли с таким id");
                            continue;
                        }
                        if (!userService.checkIdRoleUser(newIdRoleUser)) {
                            System.out.println("нет роли с таким id");
                        } else {
                            break;
                        }
                    }
                    userService.addUser(newUserLogin, newUserPassword, newUserName, newIdRoleUser);
                    System.out.println("Новый user успешно создан");
                } else {
                    System.out.println("User с таким логином уже есть, выберите другой");
                }
            } else if (numberMenu.equals("2")) {
                System.out.println("Введите логин");
                String loginUser = inputInConsole();
                System.out.println("Введите пароль");
                String passwordUser = inputInConsole();
                if (userService.authenticationUserByCred(loginUser, passwordUser)) {
                    userService.createUser(loginUser);
                    if (userService.getRole() == 2) {
                        showMenuManager();
                    } else {
                        showMenuUser();
                    }
                } else {
                    System.out.println("Учетные данные неверны");
                }
            } else if (numberMenu.equals("3")) {
                finishWork();
            } else {
                System.out.println("Повторите попытку. Введите нужную операцию");
            }
        }
    }

    // общее меню +
    private void finishWork() {
        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    // конец работы приложение +
    public void showMenuWorkWithUser() {
        while (true) {
            System.out.println("\n---Меню работы с userom---");
            System.out.println("Выберите операцию\n" +
                    "1 - посмотреть информацию о текущем usere\n" +
                    "2 - посмотреть все tasks по id usera\n" +
                    "3 - посмотреть всех users\n" +
                    "4 - добавить usera\n" +
                    "5 - удалить usera\n" +
                    "6 - редактировать usera по id \n" +
                    "7 - вернуться в предыдущее меню");
            String numberMenu = inputInConsole();
            if (numberMenu.equals("1")) {
                System.out.println("Информация о usere");
                int userId = userService.getIDUser();
                ArrayList<String> allInfoUser = userService.printAllInfoUser(userId);
                for (String str : allInfoUser) {
                    System.out.println(str);
                }
            } else if (numberMenu.equals("2")) {
                int idUser = -1;
                String userId = "";
                while (true) {
                    System.out.println("\nВведите роль id");
                    userId = inputInConsole();
                    if (isNumber(userId)) {
                        idUser = Integer.parseInt(userId);
                    } else {
                        System.out.println("Нет usera с таким id");
                        continue;
                    }
                    if (!userService.checkIdUser(idUser)) {
                        System.out.println("Нет usera с таким id");
                    } else {
                        break;
                    }
                }
                ArrayList<String> allTasksUser = taskService.printAllTasksUser(idUser);
                for (String str : allTasksUser) {
                    System.out.println(str);
                }
            } else if (numberMenu.equals("3")) {
                System.out.println("Все users:");
                ArrayList<String> allUsers = userService.printAllUsersInfo();
                for (String str : allUsers) {
                    System.out.println(str);
                }
            } else if (numberMenu.equals("4")) {
                System.out.println("Введите логин нового usera");
                String newUserLogin = inputInConsole();
                while (!checkLength(newUserLogin)) {
                    System.out.println("\nЛогин слишком маленький, введите еще раз логин");
                    newUserLogin = inputInConsole();
                }
                if (!userService.checkLoginUser(newUserLogin)) {
                    System.out.println("Введите пароль нового usera");
                    String newUserPassword = inputInConsole();
                    while (!checkLength(newUserPassword)) {
                        System.out.println("\nПароль слишком маленький, введите еще раз пароль");
                        newUserPassword = inputInConsole();
                    }
                    System.out.println("Введите имя нового usera");
                    String newUserName = inputInConsole();
                    while (!checkLength(newUserName)) {
                        System.out.println("\nИмя слишком маленькое, введите еще раз");
                        newUserName = inputInConsole();
                    }
                    ArrayList<String> allRole = userService.printInfoRole();
                    for (String str : allRole) {
                        System.out.println(str);
                    }
                    int newIdRoleUser = -1;
                    String idRoleUser = "";
                    while (true) {
                        System.out.println("\nВведите роль id");
                        idRoleUser = inputInConsole();
                        if (isNumber(idRoleUser)) {
                            newIdRoleUser = Integer.parseInt(idRoleUser);
                        } else {
                            System.out.println("Нет роли с таким id");
                            continue;
                        }
                        if (!userService.checkIdRoleUser(newIdRoleUser)) {
                            System.out.println("Нет роли с таким id");
                        } else {
                            break;
                        }
                    }
                    userService.addUser(newUserLogin, newUserPassword, newUserName, newIdRoleUser);
                    System.out.println("Новый user создан");
                } else {
                    System.out.println("User с таким логином уже есть, выберите другой");
                }
            } else if (numberMenu.equals("5")) {
                String idUserConsole = "";
                while (!isNumber(idUserConsole)) {
                    System.out.println("\nВведите ID usera которого нужно удалить");
                    idUserConsole = inputInConsole();
                }
                if (userService.checkIdUser(Integer.parseInt(idUserConsole))) {
                    userService.deleteUser(Integer.parseInt(idUserConsole));
                } else {
                    System.out.println("Usera с таким id нету");
                }
            } else if (numberMenu.equals("6")) {
                showMenuChangeUserById();
            } else if (numberMenu.equals("7")) {
                showMenuManager();
            } else {
                System.out.println("Повторите попытку. Введите нужную операцию");
            }
        }
    }

    //+
    public void showMenuChangeUserById() {
        while (true) {
            System.out.println("Выберите операцию для редатирования/изменения\n" +
                    "1 - логина usera\n" +
                    "2 - пароля usera\n" +
                    "3 - имени usera\n" +
                    "4 - роли usera\n" +
                    "5 - вернуться в предыдущее меню");
            String numberMenu = inputInConsole();
            if (numberMenu.equals("1")) {
                int idUser = -1;
                String userId = "";
                while (true) {
                    System.out.println("\nВведите id usera");
                    userId = inputInConsole();
                    if (isNumber(userId)) {
                        idUser = Integer.parseInt(userId);
                    } else {
                        System.out.println("Нет usera с таким id");
                        continue;
                    }
                    if (!userService.checkIdUser(idUser)) {
                        System.out.println("Нет usera с таким id");
                    } else {
                        break;
                    }
                }
                String newLogin = "";
                while (true) {
                    System.out.println("Введите новый логин");
                    newLogin = inputInConsole();
                    if (!checkLength(newLogin)) {
                        System.out.println("Логин слишком краток, введите еще раз");
                        continue;
                    } else {
                    }
                    if (userService.checkLoginUser(newLogin)) {
                        System.out.println("Логин уже занят другим userom");
                    } else {
                        break;
                    }
                }
                userService.changeLoginUserByIdDBRepository(idUser, newLogin);
                System.out.println("Логин изменен");
            } else if (numberMenu.equals("2")) {
                int idUser = -1;
                String userId = "";
                while (true) {
                    System.out.println("\nВведите id usera");
                    userId = inputInConsole();
                    if (isNumber(userId)) {
                        idUser = Integer.parseInt(userId);
                    } else {
                        System.out.println("Нет usera с таким id");
                        continue;
                    }
                    if (!userService.checkIdUser(idUser)) {
                        System.out.println("Нет usera с таким id");
                    } else {
                        break;
                    }
                }
                System.out.println("Введите новый пароль usera");
                String newPassword = inputInConsole();
                while (!checkLength(newPassword)) {
                    System.out.println("Пароль слишком краток, введите еще раз");
                    newPassword = inputInConsole();
                }
                userService.changePasswordUserByIdDBRepository(idUser, newPassword);
                System.out.println("Пароль изменен");
            } else if (numberMenu.equals("3")) {
                int idUser = -1;
                String userId = "";
                while (true) {
                    System.out.println("\nВведите id usera");
                    userId = inputInConsole();
                    if (isNumber(userId)) {
                        idUser = Integer.parseInt(userId);
                    } else {
                        System.out.println("Нет usera с таким id");
                        continue;
                    }
                    if (!userService.checkIdUser(idUser)) {
                        System.out.println("Нет usera с таким id");
                    } else {
                        break;
                    }
                }
                System.out.println("Введите новое имя usera");
                String newName = inputInConsole();
                while (!checkLength(newName)) {
                    System.out.println("Имя слишком краткое, введите еще раз");
                    newName = inputInConsole();
                }
                userService.changeNameUserByIdDBRepository(idUser, newName);
                System.out.println("Имя изменено");
            } else if (numberMenu.equals("4")) {
                int idUser = -1;
                String userId = "";
                while (true) {
                    System.out.println("\nВведите id usera");
                    userId = inputInConsole();
                    if (isNumber(userId)) {
                        idUser = Integer.parseInt(userId);
                    } else {
                        System.out.println("Нет usera с таким id");
                        continue;
                    }
                    if (!userService.checkIdUser(idUser)) {
                        System.out.println("Нет usera с таким id");
                    } else {
                        break;
                    }
                }
                ArrayList<String> allRole = userService.printInfoRole();
                for (String str : allRole) {
                    System.out.println(str);
                }
                int newIdRoleUser = -1;
                String idRoleUser = "";
                while (true) {
                    System.out.println("\nВведите роль id");
                    idRoleUser = inputInConsole();
                    if (isNumber(idRoleUser)) {
                        newIdRoleUser = Integer.parseInt(idRoleUser);
                    } else {
                        System.out.println("Нет роли с таким id");
                        continue;
                    }
                    if (!userService.checkIdRoleUser(newIdRoleUser)) {
                        System.out.println("Нет роли с таким id");
                    } else {
                        break;
                    }
                }
                userService.changeRoleUserByIdDBRepository(idUser, newIdRoleUser);
                System.out.println("Роль изменена");
            } else if (numberMenu.equals("5")) {
                showMenuWorkWithUser();
            } else {
                System.out.println("Повторите попытку. Введите нужную операцию");
            }
        }
    }

    // +
    public void showMenuManager() {
        while (true) {
            System.out.println("Выберите операцию\n" +
                    "1 - Меню работы с tasks\n" +
                    "2 - Меню работы с users\n" +
                    "3 - Меню работы с category\n" +
                    "4 - вернуться в предыдущее меню");
            String numberMenu = inputInConsole();
            if (numberMenu.equals("1")) {
                showMenuWorkWithTasks();
            } else if (numberMenu.equals("2")) {
                showMenuWorkWithUser();
            } else if (numberMenu.equals("3")) {
                showMenuWorkWithCategory();
            } else if (numberMenu.equals("4")) {
                showMenu();
            } else {
                System.out.println("Повторите попытку. Введите нужную операцию");
            }
        }
    }
// +

    public void showMenuWorkWithTasks() {
        System.out.println("---Меню работы с tasks---\n");
        while (true) {
            System.out.println("Выберите операцию\n" +
                    "1 - посмотреть все tasks\n" +
                    "2 - добавить task\n" +
                    "3 - изменить task\n" +
                    "4 - удалить task по ID\n" +
                    "5 - вернуться в предыдущее меню");
            String numberMenu = inputInConsole();
            if (numberMenu.equals("1")) {
                System.out.println("Все tasks:");
                ArrayList<String> allTasks = taskService.printAllTasks();
                for (String str : allTasks) {
                    System.out.println(str);
                }
            } else if (numberMenu.equals("2")) {
                System.out.println("Введите имя task");
                String nameTask = inputInConsole();
                while (!checkLength(nameTask)) {
                    System.out.println("Имя слишком короткое, введите еще раз");
                    nameTask = inputInConsole();
                }
                System.out.println("Опишите task");
                String descriptionTask = inputInConsole();
                System.out.println("Выберите категорию для task");
                ArrayList<String> allCategories = taskService.printAllCategories();
                for (String str : allCategories) {
                    System.out.println(str);
                }
                int idCategory = -1;
                String categotyId = "";
                while (true) {
                    System.out.println("\nВведите id категории");
                    categotyId = inputInConsole();
                    if (isNumber(categotyId)) {
                        idCategory = Integer.parseInt(categotyId);
                    } else {
                        System.out.println("Нет категори с таким id");
                        continue;
                    }
                    if (!taskService.checkIdCategory(idCategory)) {
                        System.out.println("Нет категории с таким id");
                    } else {
                        break;
                    }
                }
                System.out.println("Выберите статус для task");
                ArrayList<String> allStatuses = taskService.printAllStatuses();
                for (String str : allStatuses) {
                    System.out.println(str);
                }
                int idStatus = -1;
                String StatusId = "";
                while (true) {
                    System.out.println("\nВведите id статуса");
                    StatusId = inputInConsole();
                    if (isNumber(StatusId)) {
                        idStatus = Integer.parseInt(StatusId);
                    } else {
                        System.out.println("Нет статуса с таким id");
                        continue;
                    }
                    if (!taskService.checkIdStatus(idStatus)) {
                        System.out.println("Нет статуса с таким id");
                    } else {
                        break;
                    }
                }
                int idUser = -1;
                String userId = "";
                while (true) {
                    System.out.println("\nВведите id usera");
                    userId = inputInConsole();
                    if (isNumber(userId)) {
                        idUser = Integer.parseInt(userId);
                    } else {
                        System.out.println("Нет usera с таким id");
                        continue;
                    }
                    if (!userService.checkIdUser(idUser)) {
                        System.out.println("Нет статуса с таким id");
                    } else {
                        break;
                    }
                }
                taskService.addTaskUser(nameTask, descriptionTask, idCategory, idStatus, idUser);
                System.out.println("Task добавлена");
            } else if (numberMenu.equals("3")) {
                showMenuChangeTasksById();
            } else if (numberMenu.equals("4")) {
                System.out.println("Введите id task которую нужно удалить");
                String idTaskConsole = inputInConsole();
                while (!isNumber(idTaskConsole)) {
                    System.out.println("\nВведите id task которую нужно удалить");
                    idTaskConsole = inputInConsole();
                }
                if (taskService.checkIdTask(Integer.parseInt(idTaskConsole))) {
                    taskService.deleteTaskByID(Integer.parseInt(idTaskConsole));
                } else {
                    System.out.println("Task с таким id нету");
                }
            } else if (numberMenu.equals("5")) {
                showMenuManager();
            } else {
                System.out.println("Повторите попытку. Введите нужную операцию");
            }
        }

    }
//+

    public void showMenuChangeTasksById() {
        while (true) {
            System.out.println("Выберите операцию для изменения:\n" +
                    "1 - имени task\n" +
                    "2 - описания task\n" +
                    "3 - категории task\n" +
                    "4 - статуса task\n" +
                    "5 - id usera в task \n" +
                    "6 - вернуться в предыдущее меню");
            String numberMenu = inputInConsole();
            if (numberMenu.equals("1")) {
                System.out.println("Введите ID в котором будет изменение");
                String idTaskConsole = inputInConsole();
                while (!isNumber(idTaskConsole)) {
                    System.out.println("\nВведите ID в котором будет изменение");
                    idTaskConsole = inputInConsole();
                }
                if (taskService.checkIdTask(Integer.parseInt(idTaskConsole))) {
                    System.out.println("Введите новое имя");
                    String newName = inputInConsole();
                    while (!checkLength(newName)) {
                        System.out.println("\nимя слишком маленькое попробуйте еще раз");
                        newName = inputInConsole();
                    }
                    taskService.changeNameTaskDbRepository(Integer.parseInt(idTaskConsole), newName);
                    System.out.println("Имя изменено");
                } else {
                    System.out.println("Task с таким id нету");
                }
            } else if (numberMenu.equals("2")) {
                System.out.println("Введите ID в котором будет изменение");
                String idTaskConsole = inputInConsole();
                while (!isNumber(idTaskConsole)) {
                    System.out.println("\nВведите ID в котором будет изменение");
                    idTaskConsole = inputInConsole();
                }
                if (taskService.checkIdTask(Integer.parseInt(idTaskConsole))) {
                    System.out.println("Введите новое описание task");
                    String newDescription = inputInConsole();
                    while (!checkLength(newDescription)) {
                        System.out.println("\nописание слишком короткое попробуйте еще раз");
                        newDescription = inputInConsole();
                    }
                    taskService.changeDescriptionTaskDbRepository(Integer.parseInt(idTaskConsole), newDescription);
                    System.out.println("Описание изменено");
                } else {
                    System.out.println("Task с таким id нету");
                }

            } else if (numberMenu.equals("3")) {
                int idTask = -1;
                String taskId = "";
                while (true) {
                    System.out.println("\nВведите id task");
                    taskId = inputInConsole();
                    if (isNumber(taskId)) {
                        idTask = Integer.parseInt(taskId);
                    } else {
                        System.out.println("Нет task с таким id");
                        continue;
                    }
                    if (!taskService.checkIdTask(idTask)) {
                        System.out.println("Нет task с таким id");
                    } else {
                        break;
                    }
                }
                ArrayList<String> allCategories = categoryService.printAllCategory();
                for (String str : allCategories) {
                    System.out.println(str);
                }
                int newIdCategory = -1;
                String idCategory = "";
                while (true) {
                    System.out.println("\nВведите id категории");
                    idCategory = inputInConsole();
                    if (isNumber(idCategory)) {
                        newIdCategory = Integer.parseInt(idCategory);
                    } else {
                        System.out.println("Нет категории с таким id");
                        continue;
                    }
                    if (!taskService.checkIdCategory(newIdCategory)) {
                        System.out.println("Нет категории с таким id");
                    } else {
                        break;
                    }
                }
                taskService.changeCategoryTaskDbRepository(idTask, newIdCategory);
                System.out.println("Категория task изменена");
            } else if (numberMenu.equals("4")) {
                int idTask = -1;
                String taskId = "";
                while (true) {
                    System.out.println("\nВведите id task");
                    taskId = inputInConsole();
                    if (isNumber(taskId)) {
                        idTask = Integer.parseInt(taskId);
                    } else {
                        System.out.println("Нет task с таким id");
                        continue;
                    }
                    if (!taskService.checkIdTask(idTask)) {
                        System.out.println("Нет task с таким id");
                    } else {
                        break;
                    }
                }
                ArrayList<String> allStatuses = taskService.printAllStatuses();
                for (String str : allStatuses) {
                    System.out.println(str);
                }
                int newIdStatus = -1;
                String idStatus = "";
                while (true) {
                    System.out.println("\nВведите id статуса");
                    idStatus = inputInConsole();
                    if (isNumber(idStatus)) {
                        newIdStatus = Integer.parseInt(idStatus);
                    } else {
                        System.out.println("Нет статуса с таким id");
                        continue;
                    }
                    if (!taskService.checkIdStatus(newIdStatus)) {
                        System.out.println("Нет статуса с таким id");
                    } else {
                        break;
                    }
                }
                taskService.changeStatusTaskById(idTask, newIdStatus);
                System.out.println("Статус task изменен");
            } else if (numberMenu.equals("5")) {


                int idTask = -1;
                String taskId = "";
                while (true) {
                    System.out.println("\nВведите id task");
                    taskId = inputInConsole();
                    if (isNumber(taskId)) {
                        idTask = Integer.parseInt(taskId);
                    } else {
                        System.out.println("Нет task с таким id");
                        continue;
                    }
                    if (!taskService.checkIdTask(idTask)) {
                        System.out.println("Нет task с таким id");
                    } else {
                        break;
                    }
                }
                ArrayList<String> allUsers = userService.printAllUsersInfo();
                for (String str : allUsers) {
                    System.out.println(str);
                }
                int newIdUser = -1;
                String idUser = "";
                while (true) {
                    System.out.println("\nВведите id usera");
                    idUser = inputInConsole();
                    if (isNumber(idUser)) {
                        newIdUser = Integer.parseInt(idUser);
                    } else {
                        System.out.println("Нет usera с таким id");
                        continue;
                    }
                    if (!userService.checkIdUser(newIdUser)) {
                        System.out.println("Нет usera с таким id");
                    } else {
                        break;
                    }
                }
                taskService.changeUserTaskDbRepository(idTask, newIdUser);
                System.out.println("User в task изменен");
            } else if (numberMenu.equals("6")) {
                showMenuWorkWithTasks();
            } else {
                System.out.println("Повторите попытку. Введите нужную операцию");
            }
        }

    }

    public void showMenuWorkWithCategory() {
        while (true) {
            System.out.println("Выберите операцию\n" +
                    "1 - Посмотреть все категории\n" +
                    "2 - Добавить категорию\n" +
                    "3 - Удалить категорию\n" +
                    "4 - Изменить категорию\n" +
                    "5 - вернуться в предыдущее меню");
            String numberMenu = inputInConsole();
            if (numberMenu.equals("1")) {
                System.out.println("Все категории:");
                ArrayList<String> allCategory = categoryService.printAllCategory();
                for (String str : allCategory) {
                    System.out.println(str);
                }
            } else if (numberMenu.equals("2")) {
                String newNameCategory = "";
                while (true) {
                    System.out.println("Введите имя новой категории");
                    newNameCategory = inputInConsole();
                    if (!checkLength(newNameCategory)) {
                        System.out.println("Имя категории слишком маленькое");
                        continue;
                    } else {
                    }
                    if (categoryService.checkNameCategory(newNameCategory)) {
                        System.out.println("Категория с таким именем уже имеется");
                        continue;
                    } else {
                        break;
                    }
                }
                categoryService.addCategory(newNameCategory);
                System.out.println("Категория добавлена");

            } else if (numberMenu.equals("3")) {
                String nameCategory = "";
                int idCategory = -1;
                while (true) {
                    System.out.println("Введите имя категории которую нужно удалить");
                    nameCategory = inputInConsole();
                    if (isDefaultName(nameCategory)) {
                        System.out.println("Данную категорию удалять нельзя");
                        continue;
                    } else {

                    }
                    if (!categoryService.checkNameCategory(nameCategory)) {
                        System.out.println("Категории с таким именем не имеется");
                    } else {
                        idCategory = categoryService.getIdCategory(nameCategory);
                        break;
                    }
                }
                categoryService.changeNameCategoryOnDefaultNameCategoryInTasks(idCategory);
                categoryService.deleteCategory(nameCategory);
                System.out.println("Категория удалена");

            } else if (numberMenu.equals("4")) {
                String nameCategory = "";
                while (true) {
                    System.out.println("Введите имя категории в которой будет изменение");
                    nameCategory = inputInConsole();
                    if (!checkLength(nameCategory)) {
                        System.out.println("Имя категории слишком маленькое");
                        continue;
                    } else {
                    }
                    if (!categoryService.checkNameCategory(nameCategory)) {
                        System.out.println("Категории с таким именем не имеется");
                    } else {
                        break;
                    }
                }
                System.out.println("Введите новое имя категории");
                String newNameCategory = inputInConsole();
                while (!checkLength(newNameCategory)) {
                    System.out.println("Имя категории слишком маленькое, введите еще раз");
                    newNameCategory = inputInConsole();
                }
                categoryService.changeNameCategoryByNameDBRepository(nameCategory, newNameCategory);
                System.out.println("Категория изменена");
            } else if (numberMenu.equals("5")) {
                showMenuManager();
            } else {
                System.out.println("Повторите попытку. Введите нужную операцию");
            }
        }
    }


    private String inputInConsole() {
        try {
            numberConsole = bufferedReader.readLine().trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return numberConsole;
    }

    public boolean checkLength(String str) {
        if (str.length() > 3) {
            return true;
        }
        return false;
    }

    private boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private boolean isDefaultName(String str) {
        if (str.equals("обычно")) {
            return true;
        }
        return false;
    }


}
