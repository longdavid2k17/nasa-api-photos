package dawid.kantoch.nasaapiphotos.Models;

import lombok.Data;

import java.util.Date;

@Data
public class Rover
{
    private Long id;
    private String name;
    private String landing_date, launch_date;
    private String status;

    public Rover(Long id, String name, String landing_date, String launch_date, String status)
    {
        this.id = id;
        this.name = name;
        this.landing_date = landing_date;
        this.launch_date = launch_date;
        this.status = status;
    }
}
