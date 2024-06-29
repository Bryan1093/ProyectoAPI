package Model;

import java.time.LocalDate;
import java.util.List;

public class MarsPhoto {
    private int id;
    private int sol;
    private String cameraName;
    private String cameraFullName;
    private String imgSrc;
    private LocalDate earthDate;
    private String roverName;
    private String roverStatus;
    private LocalDate roverLandingDate;
    private LocalDate roverLaunchDate;
    private int roverMaxSol;
    private LocalDate roverMaxDate;
    private int roverTotalPhotos;
    private List<String> roverCameras;

    public MarsPhoto(int id, int sol, String cameraName, String cameraFullName, String imgSrc, LocalDate earthDate, String roverName, String roverStatus, LocalDate roverLandingDate, LocalDate roverLaunchDate, int roverMaxSol, LocalDate roverMaxDate, int roverTotalPhotos, List<String> roverCameras) {
        this.id = id;
        this.sol = sol;
        this.cameraName = cameraName;
        this.cameraFullName = cameraFullName;
        this.imgSrc = imgSrc;
        this.earthDate = earthDate;
        this.roverName = roverName;
        this.roverStatus = roverStatus;
        this.roverLandingDate = roverLandingDate;
        this.roverLaunchDate = roverLaunchDate;
        this.roverMaxSol = roverMaxSol;
        this.roverMaxDate = roverMaxDate;
        this.roverTotalPhotos = roverTotalPhotos;
        this.roverCameras = roverCameras;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getSol() {
        return sol;
    }

    public String getCameraName() {
        return cameraName;
    }

    public String getCameraFullName() {
        return cameraFullName;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public LocalDate getEarthDate() {
        return earthDate;
    }

    public String getRoverName() {
        return roverName;
    }

    public String getRoverStatus() {
        return roverStatus;
    }

    public LocalDate getRoverLandingDate() {
        return roverLandingDate;
    }

    public LocalDate getRoverLaunchDate() {
        return roverLaunchDate;
    }

    public int getRoverMaxSol() {
        return roverMaxSol;
    }

    public LocalDate getRoverMaxDate() {
        return roverMaxDate;
    }

    public int getRoverTotalPhotos() {
        return roverTotalPhotos;
    }

    public List<String> getRoverCameras() {
        return roverCameras;
    }
}
