console.log("loaded");

$("#pet-hotel-dog-size-select").hide();
$("#animalTypeSelect").change(function () {
    if ($("#animalTypeSelect").val() === "DOG") {
        $("#pet-hotel-dog-size-select").show();
    } else {
        $("#pet-hotel-dog-size-select").hide().val(0).change();
        $("#pet-hotel-dog-size-select  option[value='']").attr('selected', 'selected');
    }
});