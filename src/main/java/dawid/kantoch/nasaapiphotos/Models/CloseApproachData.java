package dawid.kantoch.nasaapiphotos.Models;

import lombok.Data;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Data
public class CloseApproachData
{
    private Date closeApproachDate;
    private long epochDateCloseApproach;
    private double relativeVelocityKmPerHour;
    private double missDistanceKm;
    private String orbitingBody;

    public CloseApproachData(LocalDate closeApproachDate, long epochDateCloseApproach, double relativeVelocityKmPerHour, double missDistanceKm, String orbitingBody)
    {
        this.closeApproachDate = convertToDateViaInstant(closeApproachDate);
        this.epochDateCloseApproach = epochDateCloseApproach;
        this.relativeVelocityKmPerHour = relativeVelocityKmPerHour;
        this.missDistanceKm = missDistanceKm;
        this.orbitingBody = orbitingBody;
    }

    public Date convertToDateViaInstant(LocalDate dateToConvert)
    {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }
}
