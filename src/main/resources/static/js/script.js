$(document).ready(function () {

    /*
     ANIMAL REGISTRATION FORM
     Handle front-end validation and proper fields display
     */
    const animalTypeSelect = $("#animalTypeSelect");
    const animalSizeSelect = $("#dogSizeSelect");
    const animalGenderSelect = $("#animalGenderSelect");
    const animalNameInput = $("#animalNameInput");
    const castratedInput = document.getElementsByName("castrated");
    const animalRegistrationButton = $("#animalRegistrationButton");

    // Function to show and hide the size input field only for dogs
    function hideAndShowSizeField() {
        if (animalTypeSelect.val() === "DOG") {
            $("#pet-hotel-dog-size-select").show();
            animalSizeSelect.attr("required", true);
        } else {
            $("#pet-hotel-dog-size-select").hide();
            $("#dogSizeSelect option[value='']").attr('selected', 'selected');
            animalSizeSelect.attr("required", false);
        }

    }

    hideAndShowSizeField();

    animalTypeSelect.change(function () {
        hideAndShowSizeField();
    });

    /*
        USER REGISTRATION FORM
        Check if password length and if passwords match
     */

    const passwordField = $("#userRegistrationForm #floatingPassword");
    const confirmPasswordField = $("#userRegistrationForm #floatingConfirmPassword");
    let password = passwordField.val();
    let confirmPassword = confirmPasswordField.val();

    function passwordsMatch(password, confirmPassword) {
        if (password === confirmPassword) {
            confirmPasswordField.addClass("is-valid");
            confirmPasswordField.removeClass("is-invalid");
            $("#userRegisterPasswordMismatch").attr("hidden", true);
            return true;
        } else {
            confirmPasswordField.addClass("is-invalid");
            confirmPasswordField.removeClass("is-valid");
            $("#userRegisterPasswordMismatch").attr("hidden", false);
            return false;
        }
    }

    function checkPasswordLength(password) {
        if (password.length >= 6) {
            passwordField.addClass("is-valid");
            passwordField.removeClass("is-invalid");
            $("#passwordLengthError").attr("hidden", true);
            return true;
        } else {
            passwordField.addClass("is-invalid");
            passwordField.removeClass("is-valid");
            $("#passwordLengthError").attr("hidden", false);
            return false;
        }
    }

    function enableRegisterButton() {
        if (!(checkPasswordLength(password) && passwordsMatch(password, confirmPassword))) {
            $("#userRegisterSubmitButton").attr("disabled", true);
        } else {
            $("#userRegisterSubmitButton").attr("disabled", false);
        }
    }

    passwordField.change(function () {
        password = passwordField.val();
        enableRegisterButton();
    });

    confirmPasswordField.change(function () {
        confirmPassword = confirmPasswordField.val();
        enableRegisterButton();
    });
});

