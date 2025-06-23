package task3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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

