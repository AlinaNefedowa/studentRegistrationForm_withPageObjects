package guru.qa;

        import com.codeborne.selenide.Configuration;
        import com.codeborne.selenide.Selenide;
        import org.junit.jupiter.api.BeforeAll;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;
        import org.openqa.selenium.By;
        import javax.swing.*;
        import static com.codeborne.selenide.Condition.text;
        import static com.codeborne.selenide.Condition.visible;
        import static com.codeborne.selenide.Selectors.byText;
        import static com.codeborne.selenide.Selenide.*;
        import static java.lang.String.format;
        import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentRegistrationFormTestsImproved {

    String firstName = "Alisa",
           lastName = "Berg",
           email = "alisa.berg@gmail.com",
           gender = "Female",
           mobile = "9213332221",
           dateOfBirth = "30 April,1992",
           subjects = "Computer science",
           hobby1 = "Sports",
           hobby2 = "Reading",
           picture = "Picture.jpeg",
           currentAddress = "Operngasse 15, 7",
           state = "Haryana",
           city = "Karnal";
    String expectedFullName = format("%s %s", firstName, lastName),
           expectedStateAndCity = format("%s %s", state, city),
           expectedHobbies = format("%s, %s", hobby1, hobby2);

    @BeforeAll
    static void setUp() {
        Configuration.baseUrl = "https://demoqa.com";
        //Configuration.holdBrowserOpen = true;
    }

    @Test
    void fillFormTest() {

        open("/automation-practice-form");
        $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));
        executeJavaScript("$('footer').remove()");
        executeJavaScript("$('#fixedban').remove()");

        $("#firstName").setValue(firstName);
        $("#lastName").setValue(lastName);
        $("#userEmail").setValue(email);
        $("#genterWrapper").$(byText(gender)).click();
        //$("#gender-radio-2").parent().click();
        $("#userNumber").setValue(mobile);
        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").selectOption("April");
        $(".react-datepicker__year-select").selectOption("1992");
        $(".react-datepicker__day--030:not(.react-datepicker__day--outside-month)").click();

        $("#subjectsInput").setValue(subjects).pressEnter();
        $("#hobbiesWrapper").$(byText(hobby1)).click();
        $("#hobbiesWrapper").$(byText(hobby2)).click();
        //$("#hobbies-checkbox-1").parent().click();
        $("#uploadPicture").uploadFromClasspath("img/"+picture);
        $("#currentAddress").setValue(currentAddress);

        //Scroll the page
        $("#stateCity-wrapper").scrollIntoView(true);

        $("#state").click();
        $("#stateCity-wrapper").$(byText(state)).click();
        $("#city").click();
        $("#city").$(byText(city)).click();

        $("#submit").click();

        //Assertions

        $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
        $(".table-responsive").shouldHave(
                text(expectedFullName),
                text(email),
                text(gender),
                text(mobile),
                text(dateOfBirth),
                text(subjects),
                text(expectedHobbies),
                text(picture),
                text(currentAddress),
                text(expectedStateAndCity)
        );
    }
}
