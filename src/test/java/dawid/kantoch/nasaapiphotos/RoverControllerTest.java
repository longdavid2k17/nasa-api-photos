package dawid.kantoch.nasaapiphotos;

import dawid.kantoch.nasaapiphotos.Controllers.RoverController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class RoverControllerTest
{
    @Autowired
    private RoverController roverController;

    @Test
    public void contextLoad() throws Exception
    {
        assertThat(roverController).isNotNull();
    }
}
