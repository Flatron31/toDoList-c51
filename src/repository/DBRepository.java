package repository;

import entity.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;

public class DBRepository {
    private String url = "jdbc:mysql://localhost:3306/todo?useUnicode=true&serverTimezone=UTC";
    private String username = "root";
    private String password = "admin";
    private BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));


    public boolean authenticationUserByCredDB(String loginUser, String passwordUser) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE user_login = ? AND user_password = ?");
                preparedStatement.setString(1, loginUser);
                preparedStatement.setString(2, passwordUser);
                ResultSet test = preparedStatement.executeQuery();
                if (test.next())
                    return true;
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return false;
    }

    // вход под логином
    public User createUserDBRepository(String loginUser) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE user_login = ?");
                preparedStatement.setString(1, loginUser);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return new User(resultSet.getInt("user_id"),
                            resultSet.getString("user_login"),
                            resultSet.getString("user_password"),
                            resultSet.getString("user_name"),
                            resultSet.getInt("user_role"));
                }
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return new User(-1, "", "", "", -1);
    }

    //передача созданного юзера для работы в дальнейшем
    public ArrayList<String> printAllInfoUser(int userId) {
        ArrayList<String> allInfoUser = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT users.user_login, user_password, user_name, role_name\n" +
                                "FROM users\n" +
                                "INNER JOIN role r on users.user_role = r.role_id\n" +
                                "WHERE user_id = ?");
                preparedStatement.setInt(1, userId);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    allInfoUser.add("Login = " + resultSet.getString("user_login") +
                            ", password - " + resultSet.getString("user_password") +
                            ", name - " + resultSet.getString("user_name") +
                            ", role - " + resultSet.getString("role_name"));
                }
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return allInfoUser;
    }

    // просмотр юзера
    public ArrayList<String> printAllUsersInfo() {
        ArrayList<String> allUsers = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT users.user_id, user_login, user_password, user_name, role_name\n" +
                                "FROM users\n" +
                                "INNER JOIN role r on users.user_role = r.role_id");
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    allUsers.add("ID user - " + resultSet.getString("user_id") +
                            ", login user - " + resultSet.getString("user_login") +
                            ", password - " + resultSet.getString("user_password") +
                            ", name - " + resultSet.getString("user_name") +
                            ", role - " + resultSet.getString("role_name"));
                }
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return allUsers;
    }

    // вывод всех юзеров
    public void addTask(String nameTask, String descriptionTask, int categoryTask, int statusTask, int userId) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO tasks (task_name, task_description, task_category, task_status, user_id)\n" +
                        "VALUES (?, ?, ?, ?, ?)");
                preparedStatement.setString(1, nameTask);
                preparedStatement.setString(2, descriptionTask);
                preparedStatement.setInt(3, categoryTask);
                preparedStatement.setInt(4, statusTask);
                preparedStatement.setInt(5, userId);
                preparedStatement.execute();
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    // добавление таск
    public ArrayList<String> printAllTasksUser(int userId) {
        ArrayList<String> allTasksUser = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT task_id, users.user_name, role_name, task_name, task_description, status_name, category_name\n" +
                                "FROM users\n" +
                                "INNER JOIN tasks t on users.user_id = t.user_id\n" +
                                "INNER JOIN role r on users.user_role = r.role_id\n" +
                                "INNER JOIN status s on t.task_status = s.status_id\n" +
                                "INNER JOIN category c on t.task_category = c.category_id\n" +
                                "WHERE t.user_id = ?");
                preparedStatement.setInt(1, userId);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    allTasksUser.add("ID task - " + resultSet.getString("task_id") +
                            ", Name - " + resultSet.getString("user_name") +
                            ", role - " + resultSet.getString("role_name") +
                            ", name task - " + resultSet.getString("task_name") +
                            ", description - " + resultSet.getString("task_description") +
                            ", status - " + resultSet.getString("status_name") +
                            ", category - " + resultSet.getString("category_name"));
                }
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return allTasksUser;
    }

    // просмотр всех такс юзера
    public void deleteTask(int idTask) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "DELETE FROM tasks WHERE task_id = ?");
                preparedStatement.setInt(1, idTask);
                preparedStatement.execute();
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    // удаление таск по id
    public ArrayList<String> printAllTasks() {
        ArrayList<String> allTasks = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM tasks");
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    allTasks.add("ID task - " + resultSet.getString("task_id") +
                            ", name - " + resultSet.getString("task_name") +
                            ", description - " + resultSet.getString("task_description") +
                            ", category - " + resultSet.getString("task_category") +
                            ", status - " + resultSet.getString("task_status"));
                }
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return allTasks;
    }

    // вывод всех тасков
    public ArrayList<String> printInfoTasksById(int idTask) {
        ArrayList<String> taskInfo = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT * FROM tasks WHERE task_id = ?");
                preparedStatement.setInt(1, idTask);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    taskInfo.add("ID task - " + resultSet.getString("task_id") +
                            ", task_name - " + resultSet.getString("task_name") +
                            ", task description - " + resultSet.getString("task_description") +
                            ", task category - " + resultSet.getString("task_category") +
                            ", task status - " + resultSet.getString("task_status"));

                }
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return taskInfo;
    }

    // вывод инфы о таск по ID
    public ArrayList<String> printInfoRole() {
        ArrayList<String> roleInfo = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT * FROM role");
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    roleInfo.add("role ID - " + resultSet.getString(1) +
                            ", role name - " + resultSet.getString(2));
                }
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return roleInfo;
    }

    // вывод инфы по ролям
    public ArrayList<String> printAllStatuses() {
        ArrayList<String> statusInfo = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT * FROM status");
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    statusInfo.add("status id - " + resultSet.getString(1) +
                            ", status name - " + resultSet.getString(2));
                }
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return statusInfo;
    }

    // вывод стусов
    public ArrayList<String> printAllCategories() {
        ArrayList<String> categoriesInfo = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT * FROM category");
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    categoriesInfo.add("category id - " + resultSet.getString(1) +
                            ", category name - " + resultSet.getString(2));
                }
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return categoriesInfo;
    }
// вывод категорий

    public boolean checkIdCategory(int idCategory) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT * FROM category WHERE category_id = ?");
                preparedStatement.setInt(1, idCategory);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return false;
    }

    // проверка id категории
    public boolean checkIdStatus(int idStatus) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT * FROM status WHERE status_id = ?");
                preparedStatement.setInt(1, idStatus);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return false;
    }
// проверка id стутуса


    public void changeStatusTaskById(int idTask, int newIdStatus) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "UPDATE tasks SET task_status = ? WHERE task_id = ?");
                preparedStatement.setInt(1, newIdStatus);
                preparedStatement.setInt(2, idTask);
                preparedStatement.execute();
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    // изменение статуса task
    public boolean checkIdTask(int idTask) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT * FROM tasks WHERE task_id = ?");
                preparedStatement.setInt(1, idTask);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return false;
    }

    // проверка имеется ли task с таким id
    public boolean checkIdUser(int idUser) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT * FROM users WHERE user_id = ?");
                preparedStatement.setInt(1, idUser);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return false;
    }

    // проверка пользователя по ID
    public boolean checkIdRoleUser(int idRoleUser) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT * FROM role WHERE role_id = ?");
                preparedStatement.setInt(1, idRoleUser);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return false;
    }

    //проверка роли по id
    public void deleteUser(int idUser) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "DELETE FROM users WHERE user_id = ?");
                preparedStatement.setInt(1, idUser);
                preparedStatement.execute();
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    // удаление юзера по id
    public boolean checkLoginUser(String newLoginUser) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT * FROM users WHERE user_login = ?");
                preparedStatement.setString(1, newLoginUser);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return false;
    }

    // проверка пользователя по логину
    public void addUser(String userLogin, String userPassword, String userName, int userRole) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO users (user_login, user_password, user_name, user_role)\n" +
                                "VALUES (?, ?, ?, ?)");
                preparedStatement.setString(1, userLogin);
                preparedStatement.setString(2, userPassword);
                preparedStatement.setString(3, userName);
                preparedStatement.setInt(4, userRole);
                preparedStatement.execute();
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
// добавление юзера


    //------- изменение полей User
    public void changeLoginUserByIdDBRepository(int idUser, String newlogin) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET user_login = ? WHERE user_id = ? ");
                preparedStatement.setString(1, newlogin);
                preparedStatement.setInt(2, idUser);
                preparedStatement.execute();
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    // изменить логин usera
    public void changePasswordUserByIdDBRepository(int idUser, String newPassword) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "UPDATE users SET user_password = ? WHERE user_id = ?");
                preparedStatement.setString(1, newPassword);
                preparedStatement.setInt(2, idUser);
                preparedStatement.execute();
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    // изменить пароль usera
    public void changeNameUserByIdDBRepository(int idUser, String newName) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET user_name = ? WHERE user_id = ? ");
                preparedStatement.setString(1, newName);
                preparedStatement.setInt(2, idUser);
                preparedStatement.execute();
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    // изменить имя usera
    public void changeRoleUserByIdDBRepository(int idUser, int newIdRoleUser) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "UPDATE users SET user_role = ? WHERE user_id = ? ");
                preparedStatement.setInt(1, newIdRoleUser);
                preparedStatement.setInt(2, idUser);
                preparedStatement.execute();
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
// изменить роль usera


    //----изменение полей Task
    public void changeNameTaskDbRepository(int idTask, String newName) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "UPDATE tasks SET task_name = ? WHERE task_id = ?");
                preparedStatement.setString(1, newName);
                preparedStatement.setInt(2, idTask);
                preparedStatement.execute();
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    // изменить имя task
    public void changeDescriptionTaskDbRepository(int idTask, String newDescription) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "UPDATE tasks SET task_description = ? WHERE task_id = ?");
                preparedStatement.setString(1, newDescription);
                preparedStatement.setInt(2, idTask);
                preparedStatement.execute();
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    // изменить описание task
    public void changeCategoryTaskDbRepository(int idTask, int newIdCategory) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "UPDATE tasks SET task_category = ? WHERE task_id = ?");
                preparedStatement.setInt(1, newIdCategory);
                preparedStatement.setInt(2, idTask);
                preparedStatement.execute();
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    // изменить категорию task
    public void changeUserTaskDbRepository(int idTask, int newIdUser) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "UPDATE tasks SET user_id = ? WHERE task_id = ?");
                preparedStatement.setInt(1, newIdUser);
                preparedStatement.setInt(2, idTask);
                preparedStatement.execute();
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
// изменить юзера в task


    //---изменение полей категории----------
    public ArrayList<String> printAllCategory() {
        ArrayList<String> allCategory = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM category");
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    allCategory.add("ID category - " + resultSet.getString("category_id") +
                            ", name category - " + resultSet.getString("category_name"));
                }
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return allCategory;
    }

    // вывод всех категорий
    public boolean checkNameCategory(String nameCategory) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT * FROM category WHERE category_name = ?");
                preparedStatement.setString(1, nameCategory);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return false;
    }

    // проверка имени категории
    public void addCategory(String newNameCategory) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO category (category_name) VALUES (?)");
                preparedStatement.setString(1, newNameCategory);
                preparedStatement.execute();
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
// добавить новую категорию


    public void changeNameCategoryByNameDBRepository(String nameCategory, String newNameCategory) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "UPDATE category SET category_name = ? WHERE category_name = ?");
                preparedStatement.setString(1, newNameCategory);
                preparedStatement.setString(2, nameCategory);
                preparedStatement.execute();
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
// изменить имя категории

    public void changeNameCategoryOnDefaultNameCategoryInTasks(int idCategory) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "UPDATE tasks SET task_category = 3 WHERE task_category = ?");
                preparedStatement.setInt(1, idCategory);
                preparedStatement.execute();
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
// установить дефаултную категорию в таблице таск

    public void deleteCategory(String nameCategory) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "DELETE FROM category WHERE category_name = ?");
                preparedStatement.setString(1, nameCategory);
                preparedStatement.execute();
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
// удалить категорию

    public int getIdCategory(String nameCategory) {
        int idCategory = -1;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT * FROM category");
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    idCategory = resultSet.getInt(1);
                }
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return idCategory;
    }
// получить id категории


}
