const ajaxUrl = 'ajax/';
const getPomosUrl = ajaxUrl + 'pomo/get';
const today = moment();
const yesterday = moment().subtract(1, 'days');

function fetchPomos(startStr, endStr) {
    $.get({
        url: getPomosUrl + '?from=' + startStr + '&to=' + endStr,
        success: function (data) {
            $('.project-container').remove();
            if (data) {
                processUserJson(data)
            }
        }
    })
}

function processUserJson(data) {
    let overallLength = 0;
    data.projects.forEach(project => {
        overallLength += addProjectToThePage(project);
    });

    $('.durationElement').text(toHHMMSS(overallLength));
}

function addProjectToThePage(project) {
    let prjContainer = $('<div>', {class: 'project-container', id: 'project-container' + project.id});
    let projectNameDiv = $('<div>', {class: 'project-name row',})
        .append($('<div>', {class: 'col', text: project.name}))
        .append($('<div>', {class: 'col', text: ''}))
        .append($('<div>', {class: 'col', text: ''}))
        .append($('<div>', {class: 'col project-duration-col', text: ''}));

    prjContainer.append(projectNameDiv);
    $('.pomo-log-table').append(prjContainer);
    let overalDuration = 0;
    project.tasks.forEach(task => {
        overalDuration += addTaskToThePage(task, prjContainer)
    });

    prjContainer.find('.project-duration-col').text(toHHMMSS(overalDuration));
    return overalDuration;
}

function addTaskToThePage(task, projectContainer) {
    let tskContainer = $('<div>', {class: 'task-container', id: 'task-container' + task.id});
    let $div = $('<div>', {class: 'row'});
    $div.append($('<div>', {class: 'col', text: task.name}));
    $div.append($('<div>', {class: 'col', text: ''}));
    $div.append($('<div>', {class: 'col', text: task.pomos.length + 'pomos'}));
    $div.append($('<div>', {class: 'col task-duration-col', text: 'paceholder for length'}));
    tskContainer.append($div);
    projectContainer.append(tskContainer);

    let overalTaskLength = 0;

    task.pomos.forEach(pomo => {
        addPomoToThePage(pomo, tskContainer);
        overalTaskLength += pomo.duration;
    });

    tskContainer.find('.task-duration-col').text(toHHMMSS(overalTaskLength));
    return overalTaskLength;
}

function addPomoToThePage(pomo, tskContainer) {
    console.log(pomo)
}

function dropYearFromStringIfItIsCurrentYeat(date) {
    if (date.split(' ')[2] === today.year().toString()) {
        return date.split(' ')[0] + ' ' + date.split(' ')[1];
    } else {
        return date;
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
    let hours = Math.floor(sec_num / 3600) % 24 + ' h';
    let minutes = Math.floor(sec_num / 60) % 60 + ' m';
    let seconds = sec_num % 60 + ' s';
    return [hours, minutes, seconds]
        .map(v => v < 10 ? "0" + v : v)
        .filter(v => v !== '0 h')
        .join(" ")
};
