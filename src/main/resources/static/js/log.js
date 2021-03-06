const ajaxUrl = 'api/';
const getPomosUrl = ajaxUrl + 'pomo/get';
const today = moment();
const yesterday = moment().subtract(1, 'days');

const setAuth = function (request) {
    request.setRequestHeader("authorization", 'Bearer ' + Cookies.get('pomodoro-token'));
};

function fetchPomos(startStr, endStr) {
    $.get({
        beforeSend: setAuth,
        url: getPomosUrl + '?from=' + startStr + '&to=' + endStr,
        success: function (projects) {
            $('.project-container').remove();
            $('.durationElement').text(toHHMMSS(0));
            if (projects) {
                processProjectsSetJson(projects)
            } else {
                if ($('#dateSpan').text() === 'Today') {
                    let prjContainer = $('<div>', {class: 'project-container'});
                    prjContainer.text('Do some work');
                    $('.pomo-log-table').append(prjContainer);
                }
            }
        }
    })
}

function processProjectsSetJson(projects) {
    let overallLength = 0;
    projects.sort((a, b) => a.name.localeCompare(b.name));
    projects.forEach(project => {
        overallLength += addProjectToThePage(project);
    });
    $('.durationElement').text(toHHMMSS(overallLength));
}

function addProjectToThePage(project) {
    let prjContainer = $('<div>', {class: 'project-container', id: 'project-container' + project.id});
    let projectNameDiv = $('<div>', {class: 'project-name row',})
        .append($('<div>', {class: 'project-name-col col', text: project.name}))
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
    $div.append($('<div>', {class: 'task-name col-6', text: task.name}));
    $div.append($('<div>', {class: 'col', text: task.pomos.length + ' pomos'}));
    $div.append($('<div>', {class: 'col task-duration-col', text: 'paceholder for length'}));
    tskContainer.append($div);

    tskContainer.click(function () {
        tskContainer.find('.pomo-togglable-row').toggle();
    });
    let overalTaskLength = 0;
    task.pomos.sort((a, b) => new Date(a.finish) - new Date(b.finish));
    task.pomos.forEach(pomo => {
        overalTaskLength += appendAPomo(pomo, tskContainer);
    });
    tskContainer.find('.task-duration-col').text(toHHMMSS(overalTaskLength));
    projectContainer.append(tskContainer);
    return overalTaskLength;
}

function appendAPomo(pomo, tskContainer) {
    let row = $('<div>', {class: 'row pomo-togglable-row'});
    row.append($('<div>', {class: 'col'}))
        .append($('<div>', {class: 'col', text: toPrettyTime(pomo.finish)}))
        .append($('<div>', {class: 'col', text: dropYearFromStringIfItIsCurrentYeat(toPrettyDate(pomo.finish))}))
        .append($('<div>', {class: 'col', text: toHHMMSS(pomo.duration)}));
    row.toggle();
    tskContainer.append(row);
    return pomo.duration;
}

function dropYearFromStringIfItIsCurrentYeat(date) {
    if (date.split(' ')[2] === today.year().toString()) {
        return date.split(' ').splice(0, 2).join(' ');
    } else {
        return date;
    }
}

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

$(window).on('load', function () {
    $('#navbar-log').addClass('active');

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

const toPrettyDate =
    dateTime => new Date(dateTime)
        .toString()
        .split(' ')
        .splice(1, 3)
        .join(' ');

const toPrettyTime =
    dateTime => new Date(dateTime)
        .toTimeString()
        .split(':')
        .splice(0, 2)
        .join(':');

const toHHMMSS = (secs) => {
    let sec_num = parseInt(secs, 10);
    let hours = Math.floor(sec_num / 3600) + ' h';
    let minutes = Math.floor(sec_num / 60) % 60 + ' m';
    let seconds = sec_num % 60 + ' s';
    return [hours, minutes, seconds]
        .map(v => v < 10 ? "0" + v : v)
        .filter(v => v !== '0 h')
        .join(" ")
};
