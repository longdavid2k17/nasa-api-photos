package dawid.kantoch.nasaapiphotos.Models;

import lombok.Data;

@Data
public class NearEarthObject
{
    private Long id;
    private Long neoReferenceId;
    private String name;
    private String nasaJplUrl;
    private double absoluteMagnitudeHeight;
    private double estimatedDiameterInMetersMin;
    private double estimatedDiameterInMetersMax;
    private boolean isPotentiallyHazardousAsteroid;
    private CloseApproachData closeApproachData;
    private boolean isSentryObject;

    public NearEarthObject(Long id, Long neoReferenceId, String name, String nasaJplUrl, double absoluteMagnitudeHeight, double estimatedDiameterInMetersMin,double estimatedDiameterInMetersMax, boolean isPotentiallyHazardousAsteroid, CloseApproachData closeApproachData, boolean isSentryObject) {
        this.id = id;
        this.neoReferenceId = neoReferenceId;
        this.name = name;
        this.nasaJplUrl = nasaJplUrl;
        this.absoluteMagnitudeHeight = absoluteMagnitudeHeight;
        this.estimatedDiameterInMetersMin = estimatedDiameterInMetersMin;
        this.estimatedDiameterInMetersMax = estimatedDiameterInMetersMax;
        this.isPotentiallyHazardousAsteroid = isPotentiallyHazardousAsteroid;
        this.closeApproachData = closeApproachData;
        this.isSentryObject = isSentryObject;
    }
}
