package registration;
import util.List;
import util.Sort;
/**
 StudentList class
 @author Kevin Toan, Ysabella Llanos
 */
public class StudentList extends List<Student>{


    /**
     Add student to end of  StudentList
     * @param student student obj
     */
    public void add(Student student){
        super.add(student);
    }

    /**
     * print by lname -> fname -> dob
     */
    public void print(){
        //sort list then print
        if(this.isEmpty()) {
            System.out.println("Student list is empty.");
            return;
        }
        Sort.selSort(this);

        System.out.println("* Student list ordered by last, first name, DOB *");
        for(int i = 0; i < this.size(); i++){
            System.out.println(this.get(i));
        }
        System.out.println("* end of list **");
    }




}
