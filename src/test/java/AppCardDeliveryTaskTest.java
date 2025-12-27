import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;

public class AppCardDeliveryTaskTest {

    @BeforeEach
    void setup(){
        Selenide.open("http://localhost:9999");
    }

    public String generateDate(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldSubmitFormSuccessfully() {
        String planningDate = generateDate(4, "dd.MM.yyyy");

        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input")
                .press(Keys.SHIFT, Keys.HOME).press(Keys.DELETE).setValue(planningDate);

        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79011234567");
        $("[data-test-id='agreement']").click();
        $("button.button_view_extra").click();
        $("[data-test-id='notification']")
                .should(Condition.visible, Duration.ofSeconds(15))
                .should(Condition.text("Встреча успешно забронирована на " + planningDate));


    }

}
