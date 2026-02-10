package registration;

public class StudentList {
    private static final int NOT_FOUND = -1;
    private final static int CAPACITY = 4;
    private Student[] list;

    //size is current # of entries in list
    private int size;


    StudentList(){
        this.size = 0;
        this.list = new Student[CAPACITY];
    }
    /**
     * find specific student
     * @param student student TBF (to be found)
     * @return index of student in the list, -1 if not found
     */
    private int find(Student student){
        for(int i = 0; i < this.size; i++){
            if(student.equals(this.list[i])) return i;
        }
        return NOT_FOUND;
    }
    /**
     * increases array size by 4 [CAPACITY]
     */
    private void grow() {
        this.size += CAPACITY;
        Student[] newList = new Student[this.size];
    }

    /**
     Add student to end of  StudentList
     * @param student student obj
     */
    public void add(Student student){
        //if size is at MAX then we grow
        if(this.size == this.list.length){
            this.grow();
        }
        this.list[size++] = student;
    }

    /**
     Replace removed student w student at end of list
     * @param student student obj
     */
    public void remove(Student student){
        //if student not found cant remove them !
        if(!this.contains(student)) return;

        // {2 ,3 4} -> size = 3 last is 2
        Student lastStudent = this.list[size-1];

        //replace the student
        int indexOfReplacee = this.find(student);

        //make last student nothing
        this.list[size-1] = null;
        this.list[indexOfReplacee] = lastStudent;
    }

    /**
     Return T/F if student found in Student List
     * @param student student obj
     * @return True if student found, False if not found, in list.
     */
    public boolean contains(Student student){
        for (Student s : this.list) {
            if (s.equals(student)) return true;
        }
        return false;
    }


    /**
     * print by lname -> fname -> dob
     */
    public void print(){
        //sort list then print
        this.sort();
        for(Student s: this.list){
            System.out.print(s + " ");
        }

    }

    private void sort(){
        for(int i = 0; i < this.size; i++){
            Student smallestStu = this.list[i];
            for(int j = 0; j < this.size; j++){
                if(smallestStu.compareTo(this.list[j]) > 0) smallestStu = this.list[j];
            }
            this.list[i] = smallestStu;
        }
    }

    public static void main(String[] args){
        StudentList list = new StudentList();
    }

}
