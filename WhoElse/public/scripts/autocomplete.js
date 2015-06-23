/**
 * Created by Thanasis on 23/6/2015.
 */


<!-- Google Autocomplete API -->
// This example displays an address form, using the autocomplete feature
// of the Google Places API to help users fill in the information.
var placeSearch, autocomplete1, autocomplete2;

function initializeAutocomplete(document) {
    // Create the autocomplete object, restricting the search
    // to geographical location types.
    autocomplete1 = new google.maps.places.Autocomplete(
        (document.getElementById('startAddress')),
        { types: ['geocode'] });
    autocomplete2 = new google.maps.places.Autocomplete(
        (document.getElementById('endAddress')),
        { types: ['geocode'] });
    // When the user selects an address from the dropdown,
    // populate the address fields in the form.
    google.maps.event.addListener(autocomplete, 'place_changed', function() {
        fillInAddress();
    });
}

// [START region_fillform]
function fillInAddress() {
    // Get the place details from the autocomplete object.
    var place = autocomplete.getPlace();

    for (var component in componentForm) {
        document.getElementById(component).value = '';
        document.getElementById(component).disabled = false;
    }

    // Get each component of the address from the place details
    // and fill the corresponding field on the form.
    for (var i = 0; i < place.address_components.length; i++) {
        var addressType = place.address_components[i].types[0];
        if (componentForm[addressType]) {
            var val = place.address_components[i][componentForm[addressType]];
            document.getElementById(addressType).value = val;
        }
    }
}
// [END region_fillform]
