
package ru.netology.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CallbackTest {

    @BeforeEach
    void setUp(){
        open("http://localhost:9999");
    }

    @Test
    void shouldSendForm() {
        $("[data-test-id=name] input").setValue("Василий Иванов-Петров");
        $("[data-test-id=phone] input").setValue("+79270000000");
        $("[data-test-id=agreement]").click();
        $("[type=button] span").click();
        $("[data-test-id=order-success]").shouldHave(text("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldSendEmptyForm() {
        $("[type=button] span").click();
        $("[data-test-id=name]").shouldHave(text("Поле обязательно для заполнения"), cssValue("color", "rgba(255, 92, 92, 1)"));
    }

    @Test
    void shouldSendFormIfOnlyName() {
        $("[data-test-id=name] input").setValue("Евгений");
        $("[type=button] span").click();
        $("[data-test-id=phone]").shouldHave(text("Поле обязательно для заполнения"), cssValue("color", "rgba(255, 92, 92, 1)"));
    }

    @Test
    void shouldSendFormWithoutAgreement() {
        $("[data-test-id=name] input").setValue("Евгений");
        $("[data-test-id=phone] input").setValue("+79270000000");
        $("[type=button] span").click();
        String actual = $("span[class=checkbox__text]").getCssValue("color");
        assertEquals("rgba(255, 92, 92, 1)", actual);
    }

    @Test
    void shouldSendFormWithInvalidName() {
        $("[data-test-id=name] input").setValue("Joe");
        $("[data-test-id=phone] input").setValue("+79270000000");
        $("[data-test-id=agreement]").click();
        $("[type=button] span").click();
        $("[data-test-id=name]").shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."), cssValue("color", "rgba(255, 92, 92, 1)"));
    }

    @Test
    void shouldSendFormWithInvalidPhone() {
        $("[data-test-id=name] input").setValue("Фикус");
        $("[data-test-id=phone] input").setValue("+7927000000");
        $("[data-test-id=agreement]").click();
        $("[type=button] span").click();
        $("[data-test-id=phone]").shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."), cssValue("color", "rgba(255, 92, 92, 1)"));
    }
}