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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getLandingDate() {
        return landingDate;
    }

    public void setLandingDate(LocalDate landingDate) {
        this.landingDate = landingDate;
    }

    public LocalDate getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(LocalDate launchDate) {
        this.launchDate = launchDate;
    }

    public RoverStatus getStatus() {
        return status;
    }

    public void setStatus(RoverStatus status) {
        this.status = status;
    }

    public int getMaxSol() {
        return maxSol;
    }

    public void setMaxSol(int maxSol) {
        this.maxSol = maxSol;
    }

    public LocalDate getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(LocalDate maxDate) {
        this.maxDate = maxDate;
    }

    public int getTotalPhotos() {
        return totalPhotos;
    }

    public void setTotalPhotos(int totalPhotos) {
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