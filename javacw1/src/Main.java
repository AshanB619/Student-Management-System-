// import libraries that required for the coursework
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

public class Main {
    static Object[][] student_data = new Object[100][5]; // crete an array with Object because it can store all types of data
    static int available_seat = 100; // setting available seats to 100

    public static void main(String[] args) {
        Scanner main_input = new Scanner(System.in); // taking the user input when user come to the main menu
        while (true) {
            System.out.println("1)Check Available Seats");
            System.out.println("2)Register Student");
            System.out.println("3)Delete Student");
            System.out.println("4)Find Students");
            System.out.println("5)Store student Data");
            System.out.println("6)Load Student Data");
            System.out.println("7)View Students List");
            System.out.println("8)Manage students Details");
            System.out.println("0)Exit");
            String choice = main_input.nextLine();

            // using switch case to take the inputs
            switch (choice) {
                case "1":
                    check_avb_seats();
                    break;
                case "2":
                    register_students();
                    break;
                case "3":
                    delete_student();
                    break;
                case "4":
                    Find_student();
                    break;
                case "5":
                    store_student_details_in_file();
                    break;
                case "6":
                    lord_students_details();
                    break;
                case "7":
                    sort_list_of_student();
                    break;
                case "8":
                    manage_student_data();
                    break;
                default:
                    System.out.println("Invalid Index|Enter number between 0 to 8"); // error handling for unknown input from the user
                    break;
            }


            if (choice.equals("0")) {
                break;
            }
        }


    }

    // Method to check the number of available seats
    public static void check_avb_seats() {
        lord_students_details();
        for (Object[] count : student_data) {
            for (Object sub_count : count) {
                if (sub_count != null) {
                    available_seat--;
                    break;
                }
            }
        }
        System.out.println(available_seat); // print the final available seats
    }

    // method for checking weather it is available using students id number
    public static boolean is_available(String student_id_for_check) {
        lord_students_details(); // loading student details after taking id number
        boolean is_identical = false;
        for (Object[] to_check : student_data) {
            if (to_check[0] != null && to_check[0].equals(student_id_for_check)) {
                is_identical = true;
                break;
            }
        }
        return is_identical; // return if it is identical
    }

    // method for register students
    public static void register_students() {
        lord_students_details();
        if (available_seat == 0) { // check the number of available seats before register
            System.out.println("ALL slots are booked");
        } else { // if seats are available then register the students
            while (true) { // taking student details as a user input
                Scanner reg_stu = new Scanner(System.in);
                System.out.println("Enter student ID:");
                String stu_id_reg = reg_stu.nextLine();
                boolean reg_identical = is_available(stu_id_reg);
                if (!reg_identical) {
                    if (stu_id_reg.length() == 8 && stu_id_reg.charAt(0) == 'w') { // validation of user student id
                        String numeric_check = stu_id_reg.substring(1);
                        if (numeric_check.matches("\\d+")) {
                            for (Object[] reg_store : student_data) {
                                if (reg_store[0] == null) {
                                    reg_store[0] = stu_id_reg;
                                    System.out.println("Student successfully registered ");
                                    break;
                                }
                            }
                            break;
                        } else {
                            System.out.println("Invalid Format:(Ex w1234567)"); // error handling
                        }

                    } else {
                        System.out.println("Invalid Format:(Ex w1234567)");

                    }
                } else {
                    System.out.println("Student Id must be Unique");
                }

            }


        }
    }

    // method for delete student details

    public static void delete_student() {
        lord_students_details();
        Scanner delete_stu = new Scanner(System.in);
        while (true) {
            System.out.println("Enter student id:"); // taking student id as a user input
            String del_id = delete_stu.nextLine();
            if (del_id.length() == 8 && del_id.charAt(0) == 'w') {
                String numeric_check_del = del_id.substring(1);
                if (numeric_check_del.matches("\\d+")) { // checking student id before deleting
                    boolean studentFound = false;
                    for (Object[] to_delete : student_data) {
                        if (to_delete[0] != null && to_delete[0].equals(del_id)) { // deleting process
                            for (int to_sub_delete = 0; to_sub_delete < 5; to_sub_delete++) {
                                to_delete[to_sub_delete] = null;
                            }
                            System.out.println("Student details successfully deleted");
                            store_student_details_in_file();
                            studentFound = true;
                            break;
                        }
                    }
                    // error handling part for deleting student
                    if (!studentFound) {
                        System.out.println("Student Id not found");
                    }
                    break;
                } else {
                    System.out.println("Invalid Format:(Ex w1234567)");
                }
            } else {
                System.out.println("Invalid Format:(Ex w1234567)");
            }
        }
    }

    // method for loading the student details and read the file
    public static void lord_students_details() {
        int row_index = 0;
        try {
            File readfile = new File("cwjava.txt"); // reading the file
            Scanner file_reader = new Scanner(readfile);
            while (file_reader.hasNextLine()) {
                String text = file_reader.nextLine();
                String[] values = text.split(",");//To handle comma after data

                if (student_data[row_index] == null) {
                    student_data[row_index] = new Object[5]; // 5 columns
                }


                for (int i = 0; i < values.length && i < 5; i++) {
                    student_data[row_index][i] = values[i];
                }

                row_index++;
            }
            file_reader.close();
        } catch (IOException e) { // error handling for the file reading
            System.out.println("Error when reading file: ");
        }

    }


    // storing student details in the text file
    public static void store_student_details_in_file() {
        boolean isEmpty = true;

        for (Object[] check_is_empty : student_data) {
            if (check_is_empty[0] != null) {
                isEmpty = false;
                break;
            }
        }

        if (!isEmpty) {
            try {
                FileWriter javaFile = new FileWriter("cwjava.txt"); // writing details to the file
                for (Object[] store_student : student_data) {
                    if (store_student[0] != null) {
                        boolean to_comma = true; // To handle comma place
                        for (int i = 0; i < store_student.length; i++) {
                            if (store_student[i] != null) {
                                if (!to_comma) {
                                    javaFile.write(",");
                                }
                                javaFile.write(store_student[i].toString());
                                to_comma = false;
                            }
                        }
                        javaFile.write("\n"); // Add a new line after each row
                    }
                }
                // writing is successfully done
                System.out.println("Student Data Stored successfully ");
                javaFile.close();
            } catch (IOException e) { // exception handling for the file writing
                System.out.println(e);
            }
        } else {
            System.out.println("No student data to store ");
        }
    }

    // method for searching and find the student
    public static void Find_student() {
        lord_students_details();
        Scanner find_student = new Scanner(System.in); // taking the input
        while (true) {
            System.out.println("Enter Student id To find");
            String Find_stu = find_student.nextLine();
            if (Find_stu.length() == 8 && Find_stu.charAt(0) == 'w') { // checking the format of the id number
                String find_numeric_check = Find_stu.substring(1);
                if (find_numeric_check.matches("\\d+")) {
                    boolean find_identical = is_available(Find_stu);
                    if (find_identical) {
                        for (int i = 0; i < student_data.length; i++) { // display student details
                            if (student_data[i][0] != null && student_data[i][0].equals(Find_stu)) {
                                System.out.println("Student Id-" + student_data[i][0]);
                                System.out.println("Student Name-" + student_data[i][1]);
                                System.out.println("Module Mark 1-" + student_data[i][2]);
                                System.out.println("Module Mark 2-" + student_data[i][3]);
                                System.out.println("Module Mark 3-" + student_data[i][4]);
                                System.out.println("Thank you");
                                break;
                            }
                        }
                        break;
                    } else {
                        System.out.println("Student ID not found"); // error handling part for the find student
                    }

                } else {
                    System.out.println("Invalid Format:(Ex w1234567)");
                }

            } else {
                System.out.println("Invalid Format:(Ex w1234567)");
            }
        }

    }

    // sorting algorithm for sorting student details (bubble sort)
    public static void sort_list_of_student() {
        boolean is_available = false;
        for (Object[] find_avb_sort : student_data) {
            if (find_avb_sort[1] != null) {
                is_available = true;
                break;
            }

        }
        if (is_available) {
            lord_students_details();
            int count = 0;
            for (Object[] row : student_data) {
                if (row[1] != null) {
                    count++;
                }
            }
            Object[][] sort_array = new Object[count][5];
            int index = 0;
            for (Object[] row : student_data) {
                if (row[0] != null) {
                    sort_array[index++] = row;
                }
            }
            Arrays.sort(sort_array, Comparator.comparing(to_sort -> (String) to_sort[1]));
            for (Object[] output : sort_array) {
                for (Object sub_output : output) {
                    System.out.printf("%-20s", sub_output);
                }
                System.out.println("\n");
            }
            System.out.println("Thank You");

        } else {
            System.out.println("No data to sort"); // error handling for the sorting algorithm
        }


    }

    // method for managing the student data
    public static void manage_student_data() {
        lord_students_details();
        Scanner manage_student_input = new Scanner(System.in); // taking student id as the user input
        System.out.println("Enter Student Id:");
        String student_id_store = manage_student_input.nextLine();
        boolean check_student_id_store = is_available(student_id_store);

        if (check_student_id_store) { // menu for the input operation
            while (true) {
                System.out.println("a) To Enter Student name");
                System.out.println("b) To Enter Student's Module Marks");
                System.out.println("c) To Get Student's Summary");
                System.out.println("d) To Generate Student's Report");
                System.out.println("e) To Exit");
                System.out.println("Enter letter:");
                String letter = manage_student_input.nextLine();

                switch (letter) {
                    case "a":
                        while (true) {
                            System.out.println("Enter Student Name:");
                            String student_name = manage_student_input.nextLine();
                            if (student_name.matches("[a-zA-Z ]+")) { // checking the matching of student name (validity)
                                student student1 = new student(student_name,student_id_store);
                                for (Object[] store_student_name:student_data){
                                    if (store_student_name[0]!=null && store_student_name[0].equals(student1.getStudent_id())){
                                        store_student_name[1]=student1.getStudent_name();
                                        break;
                                    }
                                }
                                System.out.println("Entered name successfully ");
                                break;
                            }else{
                                System.out.println("You can enter only letter for student name(ex-ashan bandara)"); // error handling part for case a
                            }
                        }
                        break;
                    case "b": // case b (taking student marks)
                        int[] marks = new int[3];
                        student student1=new student(student_id_store);
                        for (int module_input = 0; module_input < 3; module_input++) {
                            while (true) {
                                try {
                                    System.out.println("Enter Module Mark " + (module_input + 1) + ":");
                                    marks[module_input] = Integer.parseInt(manage_student_input.nextLine());
                                    if (marks[module_input]<101){
                                        break;
                                    }else{
                                        System.out.println("You cannot enter more than 100 marks"); // error handling the student marks
                                    }
                                } catch (Exception e) {
                                    System.out.println("you can enter only integers");
                                }
                            }
                        }
                        student1.setModule_marks(marks);
                        for (Object[] add_data:student_data){
                            if (add_data[0] != null && add_data[0].equals(student1.getStudent_id())){
                                add_data[2]=student1.getModule_marks()[0];
                                add_data[3]=student1.getModule_marks()[1];
                                add_data[4]=student1.getModule_marks()[2];

                            }

                        }
                        System.out.println("Module mark entered successfully ");
                        break;
                    case "c": // case c (number of 40 + (mark) students )
                        String[] student_count = new String[100];
                        student student3=new student(student_id_store);
                        lord_students_details();
                        int studentIndex = 0;
                        int total_student_count=0;
                        for (Object[] studentDatum : student_data) {
                            if (studentDatum[0] != null) {
                                student_count[studentIndex] =(String)studentDatum[0];
                                if (studentDatum[2]!=null && studentDatum[3]!=null && studentDatum[4]!=null ){
                                    int marks1 = Integer.parseInt((String) studentDatum[2]);
                                    int marks2 = Integer.parseInt((String) studentDatum[3]);
                                    int marks3 = Integer.parseInt((String) studentDatum[4]);
                                    if (marks1 >= 40 && marks2 >= 40 && marks3 >= 40) {
                                        total_student_count++;
                                    }

                                }
                                studentIndex++;

                            }

                        }
                        student3.setStudent_id(student_count);
                        student3.setStudent_more_40(total_student_count);
                        System.out.println("Number of registered student : " + student3.count_num_student());
                        System.out.println("Number of students who are scored more than 40  : "+student3.getStudent_count_40());
                        break;
                    case "d": // case d display student details
                        lord_students_details();
                        for (int i = 0; i < student_data.length - 1; i++) {
                            for (int j = 0; j < student_data.length - i - 1; j++) {
                                if (student_data[j][0] != null) {
                                    int[] marks_main = new int[3];
                                    int[] marks_sub = new int[3];
                                    if (student_data[j][2] != null) {
                                        marks_main[0] = Integer.parseInt((String) student_data[j][2]);
                                    }
                                    if (student_data[j][3] != null){
                                        marks_main[1] = Integer.parseInt((String) student_data[j][3]);
                                    }
                                    if (student_data[j][4] != null) {
                                        marks_main[2] = Integer.parseInt((String) student_data[j][4]);
                                    }
                                    if (student_data[j + 1][2] != null){
                                        marks_sub[0] = Integer.parseInt((String) student_data[j + 1][2]);
                                    }
                                    if (student_data[j + 1][3] != null){
                                        marks_sub[1] = Integer.parseInt((String) student_data[j + 1][3]);
                                    }
                                    if (student_data[j + 1][4] != null){
                                        marks_sub[2] = Integer.parseInt((String) student_data[j + 1][4]);
                                    }

                                    Module module1 = new Module(marks_main);
                                    Module module2 = new Module(marks_sub);

                                    if (module1.getAverage() < module2.getAverage()) {
                                        Object[] temp_max = student_data[j];
                                        student_data[j] = student_data[j + 1];
                                        student_data[j + 1] = temp_max;
                                    }
                                }
                            }
                        }
                        System.out.println("----------------------------------------------------------------------------------------------" +
                                "------------------------------------");
                        System.out.printf("%-20s %-20s %-15s %-15s %-15s %-15s %-15s %-15s\n",
                                "Student ID|", "Student Name|", "Module 1|", "Module 2|", "Module 3|", "Total Marks|", "Average|", "Grade|");
                        System.out.println("-----------------------------------------------------------------------------------------------" +
                                "-----------------------------------");
                        for (Object[] for_student_report : student_data) {
                            if (for_student_report[0] != null) {
                                String studentId = (String) for_student_report[0];
                                String studentName = (String) for_student_report[1];
                                int[] sub_marks_report = new int[3];
                                if (for_student_report[2] != null){
                                    sub_marks_report[0] = Integer.parseInt((String) for_student_report[2]);
                                }
                                if (for_student_report[3] != null){
                                    sub_marks_report[1] = Integer.parseInt((String) for_student_report[3]);
                                }
                                if (for_student_report[4] != null){
                                    sub_marks_report[2] = Integer.parseInt((String) for_student_report[4]);
                                }

                                Module module3 = new Module(sub_marks_report);
                                System.out.printf("%-20s %-20s %-15s %-15s %-15s %-15s %-15.2f %-15s\n",
                                        studentId, studentName, sub_marks_report[0], sub_marks_report[1], sub_marks_report[2],
                                        module3.getTotal_marks(), module3.getAverage(), module3.getGrade());
                                System.out.println("-----------------------------------------------------------------------------------------" +
                                        "-----------------------------------------");
                            }
                        }
                    case "e":
                        return;
                    default:
                        System.out.println("Invalid input. You can enter a, b, c, d, or e."); // error handling part
                        break;
                }
            }
        } else {
            System.out.println("Student ID not found.");
        }

    }

}