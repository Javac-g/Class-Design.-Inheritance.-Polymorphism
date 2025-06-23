package task3;

public class Worker extends Person {
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
