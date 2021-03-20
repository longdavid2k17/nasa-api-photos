package dawid.kantoch.nasaapiphotos.Models;

import lombok.Data;

@Data
public class Camera
{
    private Long id;
    private String name, full_name;
    private int rover_id;

    public Camera(Long id, String name, String full_name, int rover_id) {
        this.id = id;
        this.name = name;
        this.full_name = full_name;
        this.rover_id = rover_id;
    }
}
