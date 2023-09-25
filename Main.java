import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

class Admin extends User {
    public Admin(String username, String password) {
        super(username, password);
    }

    public void addQuestion(Quiz quiz, QuizQuestion question) {
        quiz.addQuestion(question);
    }
}

class QuizQuestion {
    private String question;
    private List<String> options;
    private int correctAnswerIndex;

    public QuizQuestion(String question, List<String> options, int correctAnswerIndex) {
        this.question = question;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }
}

class Quiz {
    private List<QuizQuestion> questions;
    private String name;

    public Quiz(String name) {
        this.name = name;
        questions = new ArrayList<>();
    }

    public void addQuestion(QuizQuestion question) {
        questions.add(question);
    }

    public QuizQuestion getQuestion(int index) {
        if (index >= 0 && index < questions.size()) {
            return questions.get(index);
        }
        return null;
    }

    public int getQuizSize() {
        return questions.size();
    }

    public String getName() {
        return name;
    }
}

class QuizManager {
    private List<Quiz> quizzes;
    private List<User> users;
    private Admin admin;

    public QuizManager() {
        quizzes = new ArrayList<>();
        users = new ArrayList<>();
        // Add default admin credentials
        admin = new Admin("admin", "adminpass"); // Change the password to a secure one
    }

    public void createQuiz(Quiz quiz) {
        quizzes.add(quiz);
    }

    public Quiz getQuiz(int index) {
        if (index >= 0 && index < quizzes.size()) {
            return quizzes.get(index);
        }
        return null;
    }

    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    public void registerUser(User user) {
        users.add(user);
    }

    public User loginUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null; // User not found or password incorrect
    }

    public Admin getAdmin() {
        return admin;
    }
}

class QuizUser {
    public void takeQuiz(Quiz quiz) {
        Scanner scanner = new Scanner(System.in);
        int score = 0;

        for (int i = 0; i < quiz.getQuizSize(); i++) {
            QuizQuestion question = quiz.getQuestion(i);
            System.out.println("Question " + (i + 1) + ": " + question.getQuestion());

            List<String> options = question.getOptions();
            for (int j = 0; j < options.size(); j++) {
                System.out.println((j + 1) + ". " + options.get(j));
            }

            int userAnswer;
            while (true) {
                System.out.print("Enter your answer (1-" + options.size() + "): ");
                if (scanner.hasNextInt()) {
                    userAnswer = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
                    if (userAnswer >= 1 && userAnswer <= options.size()) {
                        break;
                    }
                } else {
                    scanner.nextLine(); // Consume the invalid input
                }
                System.out.println("Invalid input. Please enter a number between 1 and " + options.size());
            }

            if (userAnswer == question.getCorrectAnswerIndex() + 1) {
                System.out.println("Correct!\n");
                score++;
            } else {
                System.out.println("Incorrect. The correct answer is: " + (question.getCorrectAnswerIndex() + 1) + "\n");
            }
        }

        System.out.println("Quiz completed. Your score: " + score + " out of " + quiz.getQuizSize());
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        QuizManager quizManager = new QuizManager();

        while (true) {
            System.out.println("=========================================");
            System.out.println("            Welcome to the Quiz Manager!");
            System.out.println("=========================================");
            System.out.println("1. Login as Admin");
            System.out.println("2. Register as User and Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice;

            while (true) {
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
                    break;
                } else {
                    scanner.nextLine(); // Consume the invalid input
                    System.out.print("Invalid input. Enter a number: ");
                }
            }

            if (choice == 1) {
                System.out.print("Enter admin username: ");
                String username = scanner.nextLine();
                System.out.print("Enter admin password: ");
                String password = scanner.nextLine();

                Admin admin = quizManager.getAdmin();

                if (username.equals(admin.getUsername()) && password.equals(admin.getPassword())) {
                    // Automatically log in as admin
                    System.out.println("Admin login successful!");
                    // Admin menu
                    while (true) {
                        System.out.println("\n=========================================");
                        System.out.println("                Admin Menu");
                        System.out.println("=========================================");
                        System.out.println("1. Create Quiz");
                        System.out.println("2. Logout");
                        System.out.print("Enter your choice: ");
                        int adminChoice;

                        while (true) {
                            if (scanner.hasNextInt()) {
                                adminChoice = scanner.nextInt();
                                scanner.nextLine(); // Consume the newline character
                                break;
                            } else {
                                scanner.nextLine(); // Consume the invalid input
                                System.out.print("Invalid input. Enter a number: ");
                            }
                        }

                        if (adminChoice == 1) {
                            // Admin creates a quiz
                            System.out.print("Enter the name of the quiz: ");
                            String quizName = scanner.nextLine();
                            Quiz newQuiz = new Quiz(quizName);

                            while (true) {
                                System.out.print("Enter the question: ");
                                String questionText = scanner.nextLine();

                                List<String> options = new ArrayList<>();
                                for (int i = 1; i <= 4; i++) {
                                    System.out.print("Enter option " + i + ": ");
                                    String option = scanner.nextLine();
                                    options.add(option);
                                }

                                System.out.print("Enter the correct answer (1-4): ");
                                int correctAnswer;

                                while (true) {
                                    if (scanner.hasNextInt()) {
                                        correctAnswer = scanner.nextInt() - 1;
                                        scanner.nextLine(); // Consume the newline character
                                        if (correctAnswer >= 0 && correctAnswer < 4) {
                                            break;
                                        }
                                    } else {
                                        scanner.nextLine(); // Consume the invalid input
                                        System.out.print("Invalid input. Enter a number between 1 and 4: ");
                                    }
                                }

                                QuizQuestion newQuestion = new QuizQuestion(questionText, options, correctAnswer);
                                newQuiz.addQuestion(newQuestion);

                                System.out.print("Add another question? (yes/no): ");
                                String addAnother = scanner.nextLine().toLowerCase();
                                if (!addAnother.equals("yes")) {
                                    break;
                                }
                            }

                            quizManager.createQuiz(newQuiz);
                            System.out.println("Quiz created.");
                        } else if (adminChoice == 2) {
                            break; // Logout
                        } else {
                            System.out.println("Invalid choice. Please try again.");
                        }
                    }
                } else {
                    System.out.println("Invalid admin username or password.");
                }
            } else if (choice == 2) {
                System.out.print("Enter user username: ");
                String username = scanner.nextLine();
                // For simplicity, we are not asking for user passwords during registration.
                // In a real application, you would want to include password handling here.
                User newUser = new User(username, "");
                quizManager.registerUser(newUser);

                // Automatically log in the user
                User loggedInUser = quizManager.loginUser(username, "");

                if (loggedInUser != null) {
                    System.out.println("User registration and login successful!");
                    // User menu
                    while (true) {
                        System.out.println("\n=========================================");
                        System.out.println("                User Menu");
                        System.out.println("=========================================");
                        System.out.println("1. Select and Take Quiz");
                        System.out.println("2. Logout");
                        System.out.print("Enter your choice: ");
                        int userChoice;

                        while (true) {
                            if (scanner.hasNextInt()) {
                                userChoice = scanner.nextInt();
                                scanner.nextLine(); // Consume the newline character
                                break;
                            } else {
                                scanner.nextLine(); // Consume the invalid input
                                System.out.print("Invalid input. Enter a number: ");
                            }
                        }

                        if (userChoice == 1) {
                            // User selects and takes a quiz
                            List<Quiz> availableQuizzes = quizManager.getQuizzes();
                            if (availableQuizzes.isEmpty()) {
                                System.out.println("No quizzes available.");
                            } else {
                                System.out.println("Available Quizzes:");
                                for (int i = 0; i < availableQuizzes.size(); i++) {
                                    System.out.println((i + 1) + ". " + availableQuizzes.get(i).getName());
                                }

                                int quizNumber;

                                while (true) {
                                    System.out.print("Enter the quiz number to take: ");
                                    if (scanner.hasNextInt()) {
                                        quizNumber = scanner.nextInt() - 1;
                                        scanner.nextLine(); // Consume the newline character
                                        if (quizNumber >= 0 && quizNumber < availableQuizzes.size()) {
                                            break;
                                        }
                                    } else {
                                        scanner.nextLine(); // Consume the invalid input
                                        System.out.println("Invalid input. Please enter a valid quiz number.");
                                    }
                                }

                                Quiz selectedQuiz = availableQuizzes.get(quizNumber);
                                QuizUser quizUser = new QuizUser();
                                quizUser.takeQuiz(selectedQuiz);
                            }
                        } else if (userChoice == 2) {
                            break; // Logout
                        } else {
                            System.out.println("Invalid choice. Please try again.");
                        }
                    }
                } else {
                    System.out.println("User registration failed.");
                }
            } else if (choice == 3) {
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
