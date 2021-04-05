package dawid.kantoch.nasaapiphotos;

import dawid.kantoch.nasaapiphotos.Controllers.EONETController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class EONETControllerTest
{
    @Autowired
    private EONETController eonetController;

    @Test
    public void contextLoad() throws Exception
    {
        assertThat(eonetController).isNotNull();
    }
}
