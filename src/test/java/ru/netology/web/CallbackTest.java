
package ru.netology.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

class CallbackTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSendForm() {
        $("[data-test-id=name] input").setValue("Василий Иванов-Петров");
        $("[data-test-id=phone] input").setValue("+79270000000");
        $("[data-test-id=agreement]").click();
        $("[type=button] span").click();
        $("[data-test-id=order-success]")
                .shouldHave(text("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldGetErrorIfEmptyForm() {
        $("[type=button] span").click();
        $("[data-test-id=name].input_invalid .input__sub")
                .shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldGetErrorIfFieldNameEmpty() {
        $("[data-test-id=name] input").setValue("");
        $("[data-test-id=phone] input").setValue("+79270000000");
        $("[data-test-id=agreement]").click();
        $("[type=button] span").click();
        $("[data-test-id=name].input_invalid .input__sub")
                .shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldGetErrorIfFieldPhoneEmpty() {
        $("[data-test-id=name] input").setValue("Василий Иванов-Петров");
        $("[data-test-id=phone] input").setValue("");
        $("[data-test-id=agreement]").click();
        $("[type=button] span").click();
        $("[data-test-id=phone].input_invalid .input__sub")
                .shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldGetErrorIfNotAgreement() {
        $("[data-test-id=name] input").setValue("Евгений");
        $("[data-test-id=phone] input").setValue("+79270000000");
        $("[type=button] span").click();
        $("[data-test-id=agreement].input_invalid .checkbox__text")
                .shouldBe(visible)
                .shouldHave(text("Я соглашаюсь с условиями"));
    }

    @Test
    void shouldGetErrorIfOnlyName() {
        $("[data-test-id=name] input").setValue("Евгений");
        $("[type=button] span").click();
        $("[data-test-id=phone].input_invalid .input__sub")
                .shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldGetErrorIfOnlyPhone() {
        $("[data-test-id=phone] input").setValue("+79270000000");
        $("[type=button] span").click();
        $("[data-test-id=name].input_invalid .input__sub")
                .shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldGetErrorIfOnlyAgreement() {
        $("[data-test-id=agreement]").click();
        $("[type=button] span").click();
        $("[data-test-id=name].input_invalid .input__sub")
                .shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldGetErrorIfInvalidName() {
        $("[data-test-id=name] input").setValue("Joe");
        $("[data-test-id=phone] input").setValue("+79270000000");
        $("[data-test-id=agreement]").click();
        $("[type=button] span").click();
        $("[data-test-id=name].input_invalid .input__sub")
                .shouldHave(text("Имя и Фамилия указаны неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldGetErrorIfInvalidPhone() {
        $("[data-test-id=name] input").setValue("Фикус");
        $("[data-test-id=phone] input").setValue("+7927");
        $("[data-test-id=agreement]").click();
        $("[type=button] span").click();
        $("[data-test-id=phone].input_invalid .input__sub")
                .shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }
}