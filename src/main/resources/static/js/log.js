const ajaxUrl = 'ajax/';
const today = moment();
const yesterday = moment().subtract(1, 'days');

$(window).on('load', function () {

    let start = moment();
    let end = moment();

    function cb(start, end) {
        let str;
        if (start._d.toLocaleDateString() === end._d.toLocaleDateString()) {
            if (start._d.toLocaleDateString() === today._d.toLocaleDateString()) {
                str = 'Today';
            } else if (start._d.toLocaleDateString() === yesterday._d.toLocaleDateString()) {
                str = 'Yesterday';
            } else {
                str = start.format('MMMM D, YYYY');
            }
        } else {
            str = start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY')
        }
        $('#reportrange span').html(str);

        pick_up_the_date_range_and_redraw_the_table();


    }

    $('#reportrange').daterangepicker({
        startDate: start,
        endDate: end,
        ranges: {
            'Today': [moment(), moment()],
            'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
            'Last 7 Days': [moment().subtract(6, 'days'), moment()],
            'Last 30 Days': [moment().subtract(29, 'days'), moment()],
            'This Month': [moment().startOf('month'), moment().endOf('month')],
            'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
        }
    }, cb);

    cb(start, end);

});

function pick_up_the_date_range_and_redraw_the_table() {

}
