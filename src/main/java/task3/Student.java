package task3;

public class Student extends Person {
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
