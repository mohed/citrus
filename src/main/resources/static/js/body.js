/**
 * Created by Roberto Angius on 2016-10-10.
 */
$(document).ready(function () {
    $(".btn-primary").click(function () {
        $(".collapse").collapse('toggle');
    });
});

$(document).ready(function () {
    $(".collapse-content").click(function () {
        $(".collapse").collapse('toggle');
    });
});