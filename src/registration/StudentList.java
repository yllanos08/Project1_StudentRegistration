package registration;

/**
 StudentList class
 @author Kevin Toan, Ysabella Llanos
 */
public class StudentList {
    private static final int NOT_FOUND = -1;
    private final static int CAPACITY = 4;
    private Student[] list;

    //size is current max size
    private int size;

    /**
     Constructor
     */
    StudentList(){
        this.size = 0;
        this.list = new Student[CAPACITY];
    }

    public int getSize(){return size;}
    public Student[] getList() {
        return list;
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
        Student[] newList = new Student[this.list.length + CAPACITY];
        for(int i = 0; i < list.length; i++){
            newList[i] = list[i];
        }
        this.list = newList;
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

        // {2 ,3 4} -> size = 3 last is 2
        Student lastStudent = this.list[size-1];

        //replace the student
        int indexOfReplacee = this.find(student);

        //make last student nothing
        this.list[size--] = null;
        this.list[indexOfReplacee] = lastStudent;
        System.out.println("[" + student.getProfile().getFname() + " " + student.getProfile().getLname() + " " + student.getProfile().getDob() + "]"
                + " removed from the list");
    }

    /**
     Return T/F if student found in Student List
     * @param student student obj
     * @return True if student found, False if not found, in list.
     */
    public boolean contains(Student student){
        if(size == 0) return false;
        for(int i = 0; i < size; i++){
            if(list[i].equals(student)) return true;
        }
        return false;
    }


    /**
     * print by lname -> fname -> dob
     */
    public void print(){
        //sort list then print
        if(size == 0) {
            System.out.println("Student list is empty.");
            return;
        }
        this.sort();
        System.out.println("* Student list ordered by last, first name, DOB *");
        for(int i = 0; i < this.size; i++){
            System.out.println(list[i]);
        }
        System.out.println("* end of list **");
    }

    private void sort(){
        for(int i = 0; i < this.size; i++){
            Student smallestStu = this.list[i];
            int index = i;
            for(int j = i+ 1; j < this.size; j++){
                if(smallestStu.compareTo(this.list[j]) > 0)
                {
                    smallestStu = this.list[j];
                    index = j;
                }
            }
            Student temp = this.list[i];
            this.list[i] = smallestStu;
            this.list[index] = temp;
        }
    }

    public static void main(String[] args){
        StudentList list = new StudentList();
    }

}
