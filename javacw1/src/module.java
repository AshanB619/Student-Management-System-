class Module { // setting the class module
    private int[] marks;

    public Module(int[] marks) {
        this.marks = marks;
    }

    // to get total marks
    public int getTotal_marks() {
        int total = 0;
        for (int mark : marks) {
            total += mark;
        }
        return total;
    }
    // to get average
    public double getAverage() {
        return getTotal_marks() / (double)3;
    }
    // to get grade
    public String getGrade() {
        double average = getAverage();
        if (average >= 80) {
            return "Distinction";
        } else if (average >= 70) {
            return "Merit";
        } else if (average >= 40) {
            return "Pass";
        } else {
            return "Fail";
        }
    }
}
