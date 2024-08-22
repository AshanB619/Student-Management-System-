public class student { // setting the student class
    String student_id;
    String student_name;
    int[] module_marks;
    String[] student_id_total;
    int student_count_40;

    // adding getters setters and constructor for the class
    public student(String student_name,String student_id){
        this.student_id=student_id;
        this.student_name=student_name;
        this.module_marks=new int[3];
    }
    public student(String student_id){
        this.student_id=student_id;
    }
    public String getStudent_id(){
        return student_id;
    }

    public  String getStudent_name(){
        return student_name;
    }

    public void setModule_marks(int[] marks){
        this.module_marks=marks;
    }
    public int[] getModule_marks(){
        return module_marks;
    }
    public void setStudent_id(String[] student_id) {
        this.student_id_total = student_id;
    }
    public void setStudent_more_40(int count_total){
        this.student_count_40=count_total;
    }
    public int getStudent_count_40(){
        return student_count_40;
    }


    public int count_num_student() {
        int count = 0;
        for (String id : student_id_total) {
            if (id != null) {
                count++;
            }
        }
        return count;
    }






}
