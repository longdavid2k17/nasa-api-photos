package dawid.kantoch.nasaapiphotos;

import dawid.kantoch.nasaapiphotos.Models.NeoWSDate;
import dawid.kantoch.nasaapiphotos.Services.NeoWSService;
import org.junit.jupiter.api.Test;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class NeoWSServiceTest
{
    @Test
    public void shouldThrowException()
    {
        NeoWSService neoWSService = new NeoWSService();
        String date1Str = "01-05-2020";
        String date2Str = "01-30-2020";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            Date date = formatter.parse(date1Str);
            Date date2 = formatter.parse(date2Str);
            NeoWSDate neoWSDate = new NeoWSDate();
            neoWSDate.setDateFrom(date);
            neoWSDate.setDateTo(date2);
            neoWSService.getData(neoWSDate);
        }
        catch (Exception e)
        {
            assertThat(e.getMessage()).isEqualTo("This request would not return any data or does not meet requirements!");
        }

    }
}
