package dawid.kantoch.nasaapiphotos.Models;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class Photos
{
    private Long id;
    private int sol;
    private Camera camera;
    private String image_src;
    private String earth_date;
    private Rover rover;

    public Photos(Long id, int sol, Camera camera, String image_src, String earth_date, Rover rover)
    {
        this.id = id;
        this.sol = sol;
        this.camera = camera;
        this.image_src = image_src;
        this.earth_date = earth_date;
        this.rover = rover;
    }
}
