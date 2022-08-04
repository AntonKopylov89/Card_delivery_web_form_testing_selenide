package ru.netology.service;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.Keys.BACK_SPACE;

public class CardDeliveryTest {

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }


    @Test
    void shouldSubmitRequest() {
        String planningDate = generateDate(5);
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(BACK_SPACE);
        $("[data-test-id=date] input").setValue(String.valueOf(planningDate));
        $("[data-test-id=name] input").setValue("Салтыков-Щедрин Евгений");
        $("[data-test-id=phone] input").setValue("+79578664311");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id='notification']").shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldCityError() {
        String planningDate = generateDate(5);
        $("[data-test-id=city] input").setValue("Сызрань");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(BACK_SPACE);
        $("[data-test-id=date] input").setValue(String.valueOf(planningDate));
        $("[data-test-id=name] input").setValue("Салтыков-Щедрин Евгений");
        $("[data-test-id=phone] input").setValue("+79578664311");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldDeliveryDateError() {
        String planningDate = generateDate(1);
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(BACK_SPACE);
        $("[data-test-id=date] input").setValue(String.valueOf(planningDate));
        $("[data-test-id=name] input").setValue("Салтыков-Щедрин Евгений");
        $("[data-test-id=phone] input").setValue("+79578664311");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id=date] .input_invalid .input__sub").shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }


    @Test
    void shouldNameError() {
        String planningDate = generateDate(5);
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(BACK_SPACE);
        $("[data-test-id=date] input").setValue(String.valueOf(planningDate));
        $("[data-test-id=name] input").setValue("John Dow");
        $("[data-test-id=phone] input").setValue("+79578664311");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldPhoneError() {
        String planningDate = generateDate(5);
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(BACK_SPACE);
        $("[data-test-id=date] input").setValue(String.valueOf(planningDate));
        $("[data-test-id=name] input").setValue("Салтыков-Щедрин Евгений");
        $("[data-test-id=phone] input").setValue("79578664311");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldErrorIfCityEmpty() {
        String planningDate = generateDate(5);
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(BACK_SPACE);
        $("[data-test-id=date] input").setValue(String.valueOf(planningDate));
        $("[data-test-id=name] input").setValue("Салтыков-Щедрин Евгений");
        $("[data-test-id=phone] input").setValue("+79578664311");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldErrorIfDeliveryDateEmpty() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(BACK_SPACE);
        $("[data-test-id=name] input").setValue("Салтыков-Щедрин Евгений");
        $("[data-test-id=phone] input").setValue("+79578664311");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id='date'] .input_invalid .input__sub").shouldHave(exactText("Неверно введена дата"));
    }

    @Test
    void shouldErrorIfNameEmpty() {
        String planningDate = generateDate(5);
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(BACK_SPACE);
        $("[data-test-id=date] input").setValue(String.valueOf(planningDate));
        $("[data-test-id=phone] input").setValue("+79578664311");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldErrorIfPhoneEmpty() {
        String planningDate = generateDate(5);
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(BACK_SPACE);
        $("[data-test-id=date] input").setValue(String.valueOf(planningDate));
        $("[data-test-id=name] input").setValue("Салтыков-Щедрин Евгений");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldErrorWithoutCheckbox() {
        String planningDate = generateDate(5);
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(BACK_SPACE);
        $("[data-test-id=date] input").setValue(String.valueOf(planningDate));
        $("[data-test-id=name] input").setValue("Салтыков-Щедрин Евгений");
        $("[data-test-id=phone] input").setValue("+79578664311");
        $(".button").click();
        $("[data-test-id='agreement'].input_invalid").shouldBe(visible);
    }

    @Test
    void shouldChoseCityFromList() {
        String planningDate = generateDate(5);
        $("[data-test-id=city] input").setValue("Мо");
        $$(".popup .menu-item__control").findBy(text("Москва")).click();
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(BACK_SPACE);
        $("[data-test-id=date] input").setValue(String.valueOf(planningDate));
        $("[data-test-id=name] input").setValue("Салтыков-Щедрин Евгений");
        $("[data-test-id=phone] input").setValue("+79578664311");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id='notification']").shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldSubmitRequest2() {
        String planningDate = generateDate(5);
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(BACK_SPACE);
        $("[data-test-id=date] input").setValue(String.valueOf(planningDate));
        $("[data-test-id=name] input").setValue("Салтыков-Щедрин Евгений");
        $("[data-test-id=phone] input").setValue("+79578664311");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }

    @AfterEach
    void closeWebBrowser() {
        closeWebDriver();
    }
}
