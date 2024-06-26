package Model;

import java.time.LocalDate;

public class MarsPhoto {
    private int id;
    private int roverId;
    private String roverName;
    private String landingDate;
    private String launchDate;
    private String roverStatus;
    private int maxSol;
    private String maxDate;
    private int totalPhotos;
    private int cameraId;
    private String cameraName;
    private String cameraFullName;
    private String imgSrc;
    private LocalDate earthDate;

    public MarsPhoto(int id, int roverId, String roverName, String landingDate, String launchDate, String roverStatus, int maxSol, String maxDate, int totalPhotos, int cameraId, String cameraName, String cameraFullName, String imgSrc, LocalDate earthDate) {
        this.id = id;
        this.roverId = roverId;
        this.roverName = roverName;
        this.landingDate = landingDate;
        this.launchDate = launchDate;
        this.roverStatus = roverStatus;
        this.maxSol = maxSol;
        this.maxDate = maxDate;
        this.totalPhotos = totalPhotos;
        this.cameraId = cameraId;
        this.cameraName = cameraName;
        this.cameraFullName = cameraFullName;
        this.imgSrc = imgSrc;
        this.earthDate = earthDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoverId() {
        return roverId;
    }

    public void setRoverId(int roverId) {
        this.roverId = roverId;
    }

    public String getRoverName() {
        return roverName;
    }

    public void setRoverName(String roverName) {
        this.roverName = roverName;
    }

    public String getLandingDate() {
        return landingDate;
    }

    public void setLandingDate(String landingDate) {
        this.landingDate = landingDate;
    }

    public String getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(String launchDate) {
        this.launchDate = launchDate;
    }

    public String getRoverStatus() {
        return roverStatus;
    }

    public void setRoverStatus(String roverStatus) {
        this.roverStatus = roverStatus;
    }

    public int getMaxSol() {
        return maxSol;
    }

    public void setMaxSol(int maxSol) {
        this.maxSol = maxSol;
    }

    public String getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(String maxDate) {
        this.maxDate = maxDate;
    }

    public int getTotalPhotos() {
        return totalPhotos;
    }

    public void setTotalPhotos(int totalPhotos) {
        this.totalPhotos = totalPhotos;
    }

    public int getCameraId() {
        return cameraId;
    }

    public void setCameraId(int cameraId) {
        this.cameraId = cameraId;
    }

    public String getCameraName() {
        return cameraName;
    }

    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }

    public String getCameraFullName() {
        return cameraFullName;
    }

    public void setCameraFullName(String cameraFullName) {
        this.cameraFullName = cameraFullName;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public LocalDate getEarthDate() {
        return earthDate;
    }

    public void setEarthDate(LocalDate earthDate) {
        this.earthDate = earthDate;
    }
}