package dawid.kantoch.nasaapiphotos.Models.EONETpackage;

import lombok.Data;

@Data
public class Category
{
    private Long id;
    private String title;

    public Category(Long id, String title)
    {
        this.id = id;
        this.title = title;
    }

    public Category()
    {

    }
}
