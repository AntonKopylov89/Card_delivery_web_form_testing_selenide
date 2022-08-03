package ru.netology.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.Keys.BACK_SPACE;

public class CardDeliveryTest {

    public void setDaysPlusToday(int daysPlusToday) {
        this.daysPlusToday = daysPlusToday;
    }

    private int daysPlusToday;

    public String setDateOfDelivery() {
        LocalDate today = LocalDate.now();
        LocalDate date = today.plusDays(daysPlusToday);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.YYYY");
        return formatter.format(date);
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }


    @Test
    void shouldSubmitRequest() {
        setDaysPlusToday(5);
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(BACK_SPACE);
        $("[data-test-id=date] input").setValue(String.valueOf(setDateOfDelivery()));
        $("[data-test-id=name] input").setValue("Салтыков-Щедрин Евгений");
        $("[data-test-id=phone] input").setValue("+79578664311");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id='notification']").shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldCityError() {
        setDaysPlusToday(5);
        $("[data-test-id=city] input").setValue("Сызрань");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(BACK_SPACE);
        $("[data-test-id=date] input").setValue(String.valueOf(setDateOfDelivery()));
        $("[data-test-id=name] input").setValue("Салтыков-Щедрин Евгений");
        $("[data-test-id=phone] input").setValue("+79578664311");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldDeliveryDateError() {
        setDaysPlusToday(1);
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(BACK_SPACE);
        $("[data-test-id=date] input").setValue(String.valueOf(setDateOfDelivery()));
        $("[data-test-id=name] input").setValue("Салтыков-Щедрин Евгений");
        $("[data-test-id=phone] input").setValue("+79578664311");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id=date] .input_invalid .input__sub").shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }


    @Test
    void shouldNameError() {
        setDaysPlusToday(5);
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(BACK_SPACE);
        $("[data-test-id=date] input").setValue(String.valueOf(setDateOfDelivery()));
        $("[data-test-id=name] input").setValue("John Dow");
        $("[data-test-id=phone] input").setValue("+79578664311");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldPhoneError() {
        setDaysPlusToday(5);
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(BACK_SPACE);
        $("[data-test-id=date] input").setValue(String.valueOf(setDateOfDelivery()));
        $("[data-test-id=name] input").setValue("Салтыков-Щедрин Евгений");
        $("[data-test-id=phone] input").setValue("79578664311");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldErrorIfCityEmpty() {
        setDaysPlusToday(5);
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(BACK_SPACE);
        $("[data-test-id=date] input").setValue(String.valueOf(setDateOfDelivery()));
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
        setDaysPlusToday(5);
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(BACK_SPACE);
        $("[data-test-id=date] input").setValue(String.valueOf(setDateOfDelivery()));
        $("[data-test-id=phone] input").setValue("+79578664311");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldErrorIfPhoneEmpty() {
        setDaysPlusToday(5);
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(BACK_SPACE);
        $("[data-test-id=date] input").setValue(String.valueOf(setDateOfDelivery()));
        $("[data-test-id=name] input").setValue("Салтыков-Щедрин Евгений");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldErrorWithoutCheckbox() {
        setDaysPlusToday(5);
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(BACK_SPACE);
        $("[data-test-id=date] input").setValue(String.valueOf(setDateOfDelivery()));
        $("[data-test-id=name] input").setValue("Салтыков-Щедрин Евгений");
        $("[data-test-id=phone] input").setValue("+79578664311");
        $(".button").click();
        $("[data-test-id='agreement'].input_invalid").shouldBe(visible);
    }

    @AfterEach
    void closeWebBrowser() {
        closeWebDriver();
    }
}
