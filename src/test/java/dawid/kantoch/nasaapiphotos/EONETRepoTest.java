package dawid.kantoch.nasaapiphotos;

import dawid.kantoch.nasaapiphotos.Models.EONETpackage.Event;
import dawid.kantoch.nasaapiphotos.Repos.EONETRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class EONETRepoTest
{
    @Autowired
    private EONETRepo eonetRepo;

    @Test
    public void checkIfListIsNotNull() throws Exception
    {
        assertThat(eonetRepo.getEvents(5)).isNotNull();
    }

    @Test
    public void checkIfCanAddEvent() throws Exception
    {
        int countOfResults = eonetRepo.getEvents(5).size();
        eonetRepo.addEvent(new Event());
        assertThat(eonetRepo.getEventList().size()).isEqualTo(countOfResults+1);
    }
}
