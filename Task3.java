import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



class Person {
    private String name;
    public Person() {

    }

    public Person(String name) {
        if (name != null) {
            this.name = name;
        }
    }
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;

        Person person = (Person) o;

        return name.equals(person.name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Person[name=" + name + "]";
    }
}

class Student extends Person {
    private String studyPlace;
    private int studyYears;
    public Student() {
    }

    public Student(String name, String studyPlace, int studyYears) {
        super(name);
        if (studyPlace != null) {
            this.studyPlace = studyPlace;
        }
        this.studyYears = studyYears;
    }
    public String getStudyPlace() {
        return studyPlace;
    }

    public int getStudyYears() {
        return studyYears;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        if (!super.equals(o)) return false;

        Student student = (Student) o;

        if (studyYears != student.studyYears) return false;
        return studyPlace.equals(student.studyPlace);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        int studyPlaceHashCode = studyPlace != null ? studyPlace.hashCode() : 0;
        result = 31 * result + studyPlaceHashCode;
        result = 31 * result + studyYears;
        return result;
    }

    @Override
    public String toString() {
        return "Student[name=" + getName() + ", studyPlace=" + studyPlace + ", studyYears=" + studyYears + "]";
    }
}


class Worker extends Person{
    private String workPosition;
    private int experienceYears;

    public Worker(String name, String workPosition, int experienceYears) {
        super(name);
        if (workPosition != null) {
            this.workPosition = workPosition;
        }
        this.experienceYears = experienceYears;
    }

    public String getWorkPosition() {
        return workPosition;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Worker)) return false;
        if (!super.equals(o)) return false;

        Worker worker = (Worker) o;

        if (experienceYears != worker.experienceYears) return false;
        return workPosition.equals(worker.workPosition);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        int workPositionHashCode = workPosition != null ? workPosition.hashCode() : 0;
        result = 31 * result + workPositionHashCode;
        result = 31 * result + experienceYears;
        return result;
    }

    @Override
    public String toString() {
        return "Worker[name=" + getName() + ", workPosition=" + workPosition + ", experienceYears=" + experienceYears +
                "]";
    }
}


public class MyUtils {
    public List<Person> maxDuration(List<Person> persons) {

        if (persons == null) {
            return new ArrayList<>();
        }

        if (persons.size() == 0) {
            return persons;
        }

        List<Person> listMaxDuration = new ArrayList<>();
        List<Worker> workers = new ArrayList<>();
        List<Student> students = new ArrayList<>();

        persons.forEach(i -> {
            if (i == null) {
                return;
            }
            if (i instanceof Worker) {
                workers.add((Worker) i);
            } else if (i instanceof Student) {
                students.add((Student) i);
            }
        });


        if (!students.isEmpty()) {
            int maxStudyYear = Collections.max(students, Comparator.comparing(Student::getStudyYears))
                    .getStudyYears();

            students.stream()
                    .filter(i -> i.getStudyYears() == maxStudyYear)
                    .forEach(listMaxDuration::add);

        }


        if (!workers.isEmpty()) {
            int maxExperienceYears = Collections.max(workers, Comparator.comparing(Worker::getExperienceYears))
                    .getExperienceYears();

            workers.stream()
                    .filter(i -> i.getExperienceYears() == maxExperienceYears)
                    .forEach(listMaxDuration::add);
        }

        return listMaxDuration;
    }
}

