const ajaxUrl = 'ajax/';
const getPomosUrl = ajaxUrl + 'pomo/get';
const today = moment();
const yesterday = moment().subtract(1, 'days');

function fetchPomos(startStr, endStr) {
    $.get({
        url: getPomosUrl + '?from=' + startStr + '&to=' + endStr,
        success: function (data) {
            $('.pomo-table-row-container').remove();

            let newDuration = 0;
            data.reverse().forEach(project => {
                let projectDuration = 0;

                project.pomoTos.forEach(pomoTo => {
                    projectDuration += pomoTo.duration;
                });

                let newRowContainer = $('<div>', {class: 'pomo-table-row-container', id: project.id});
                let newRow = $('<div>', {class: 'pomo-table-row row'});
                newRow.append($('<div>', {class: 'col', text: project.name}));
                newRow.append($('<div>', {class: 'col', text: ''}));
                newRow.append($('<div>', {class: 'col', text: toHHMMSS(projectDuration)}));

                newRowContainer.append(newRow);
                $('.pomo-log-table').prepend(newRowContainer);

                let projectTableRowContainer = $('#' + project.id + '.pomo-table-row-container');
                projectTableRowContainer.hiddenData = project.pomoTos;
                projectTableRowContainer.shown = false;
                projectTableRowContainer[0].onclick = function () {
                    expandTableRow(projectTableRowContainer);
                };

                newDuration += projectDuration;
            });

            $('.durationElement').html(toHHMMSS(newDuration));
        }
    })
}

function expandTableRow(projectTableRowContainer) {
    console.log(projectTableRowContainer.hiddenData);
    projectTableRowContainer.shown = !projectTableRowContainer.shown;
    if (projectTableRowContainer.shown) {
        let expandedContainer = $('<div>', {class: 'pomo-table-row-expanded-container'});

        projectTableRowContainer.hiddenData.forEach(pomoTo => {
            let newExpandedRow = $('<div>', {class: 'row pomo-table-row-expanded'});
            newExpandedRow.append($('<div>', {class: 'col', text: ''}));
            newExpandedRow.append($('<div>', {class: 'col', text: pomoTo.finish}));
            newExpandedRow.append($('<div>', {class: 'col', text: pomoTo.durationFormattedString}));
            expandedContainer.append(newExpandedRow);
        });

        projectTableRowContainer.append(expandedContainer);
    } else {
        projectTableRowContainer.find('.pomo-table-row-expanded-container').remove();
    }
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