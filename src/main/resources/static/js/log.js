const ajaxUrl = 'ajax/';
const getPomosUrl = ajaxUrl + 'pomo/get';
const today = moment();
const yesterday = moment().subtract(1, 'days');

function fetchPomos(startStr, endStr) {
    $.get({
        url: getPomosUrl + '?from=' + startStr + '&to=' + endStr,
        success: function (data) {
            $('.pomo-table-row').remove();

            let newDuration = 0;
            data.reverse().forEach(project => {
                let projectDuration = 0;

                project.pomoTos.forEach(pomoTo => {
                    projectDuration += pomoTo.duration;
                });

                let newRow = $('<tr>', {
                    class: 'pomo-table-row',
                    id: project.id
                });
                newRow.append($('<td>', {text: project.name}));
                newRow.append($('<td>', {text: ''}));
                newRow.append($('<td>', {text: toHHMMSS(projectDuration)}));
                $('.pomo-log-table').prepend(newRow);

                let projectTableRow = $('#' + project.id + '.pomo-table-row')[0];
                projectTableRow.hiddenData = project.pomoTos;
                projectTableRow.onclick = function () {
                    expandTableRow(projectTableRow);
                };

                newDuration += projectDuration;
            });

            $('.durationElement').html(toHHMMSS(newDuration));
        }
    })
}

function expandTableRow(selector) {

}

$(window).on('load', function () {
    function callBack(start, end) {
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
        $('#reportrange').find('span').html(str);

        let startStr = start.format('YYYY-MM-DD');
        let endStr = end.format('YYYY-MM-DD');
        fetchPomos(startStr, endStr);
    }

    $('#reportrange').daterangepicker({
        startDate: today,
        endDate: today,
        ranges: {
            'Today': [moment(), moment()],
            'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
            'Last 7 Days': [moment().subtract(6, 'days'), moment()],
            'Last 30 Days': [moment().subtract(29, 'days'), moment()],
            'This Month': [moment().startOf('month'), moment().endOf('month')],
            'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
        },
    }, callBack);

    callBack(today, today);
});

const toHHMMSS = (secs) => {
    let sec_num = parseInt(secs, 10);
    let hours = Math.floor(sec_num / 3600) % 24;
    let minutes = Math.floor(sec_num / 60) % 60;
    let seconds = sec_num % 60;
    return [hours, minutes, seconds]
        .map(v => v < 10 ? "0" + v : v)
        .join(":")
};