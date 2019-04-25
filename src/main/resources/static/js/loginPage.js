function getFormData($form) {
    const unindexed_array = $form.serializeArray();

    const indexed_array = {};

    $.map(unindexed_array, function (n, i) {
        indexed_array[n['name']] = n['value'];
    });
    return indexed_array;

}

$(window).on('load', function () {
    $('#form-button').on('click', () => {
        let $form = $('.form-signin');
        let requestBody = JSON.stringify(getFormData($form));
        const settings = {
            async: true,
            crossDomain: false,
            url: "api/login",
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "cache-control": "no-cache",
            },
            processData: false,
            data: requestBody
        };
        $.ajax(settings).done(function (response) {
            Cookies.set("pomodoro-token", response.token);
            $form.submit();
        });
        return false;
    })
});
