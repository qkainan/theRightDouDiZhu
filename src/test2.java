import java.util.ArrayList;
import java.util.List;

public class test2 {
    public static void removeElement(Student student){
        student.grade.remove(1);
        System.out.println(student.grade);
    }
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        Student s = new Student(list);
        removeElement(s);
        System.out.println("----------------");
        System.out.println(s.grade);
    }
}

class Student{
    List<Integer> grade;

    public Student(List<Integer> grade) {
        this.grade = grade;
    }

    public  Student(){

    }

}