package Model;

import java.time.LocalDate;

public class Rover {
    private int id;
    private String name;
    private LocalDate landingDate;
    private LocalDate launchDate;
    private RoverStatus status;
    private int maxSol;
    private LocalDate maxDate;
    private int totalPhotos;

    public Rover(int id, String name, LocalDate landingDate, LocalDate launchDate, RoverStatus status, int maxSol, LocalDate maxDate, int totalPhotos) {
        this.id = id;
        this.name = name;
        this.landingDate = landingDate;
        this.launchDate = launchDate;
        this.status = status;
        this.maxSol = maxSol;
        this.maxDate = maxDate;
        this.totalPhotos = totalPhotos;
    }

    @Override
    public String toString() {
        return "Rover{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", landingDate=" + landingDate +
                ", launchDate=" + launchDate +
                ", status=" + status +
                ", maxSol=" + maxSol +
                ", maxDate=" + maxDate +
                ", totalPhotos=" + totalPhotos +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rover rover = (Rover) o;
        return id == rover.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}

enum RoverStatus {
    ACTIVE,
    INACTIVE,
    UNKNOWN
}