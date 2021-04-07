package dawid.kantoch.nasaapiphotos.Models.EONETpackage;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Data
public class Geometries
{
    private Date date;
    private String type;
    private double coordinateX, coordinateY;
    private String coordinates;

    public Geometries(LocalDateTime date, String type, double coordinateX, double coordinateY)
    {
        this.date = convertToDateViaInstant(date);
        this.type = type;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.coordinates = this.coordinateX+":"+this.coordinateY;
    }

    Date convertToDateViaInstant(LocalDateTime dateToConvert) {
        return java.util.Date
                .from(dateToConvert.atZone(ZoneId.systemDefault())
                        .toInstant());
    }
}
