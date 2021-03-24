$(document).ready(function () {

    /*
     ANIMAL REGISTRATION FORM
     Handle from-end validation and proper fields display
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


    function validateSelectElement(option) {
        if (option.val() === "") {
            option.addClass("is-invalid");
            return true;
        } else {
            option.removeClass("is-invalid")
            return false;
        }
    }

    // Validate mandatory fields on button click
    animalRegistrationButton.on("click", function (e) {
        let castratedSelected = false;
        let foundErrors = false;

        if (validateSelectElement(animalTypeSelect)) {
            foundErrors = true;
        }
        if (validateSelectElement(animalGenderSelect)) {
            foundErrors = true;
        }

        if (animalTypeSelect.val() === "DOG") {
            if (validateSelectElement(animalSizeSelect)) {
                foundErrors = true;
            }
        }

        for (let i = 0; i < castratedInput.length; i++) {
            if (castratedInput[i].checked) {
                castratedSelected = true;
            }
        }

        if (animalNameInput.val().trim() === "") {
            animalNameInput.addClass("is-invalid");
        } else {
            animalNameInput.removeClass("is-invalid");
        }

        if (!castratedSelected) {
            $("#pet-hotel-castrated-field-form").addClass("is-invalid");
            foundErrors = true;
        } else {
            $("#pet-hotel-castrated-field-form").removeClass("is-invalid");
        }

        if (foundErrors) {
            e.preventDefault();
        }
    });
});

