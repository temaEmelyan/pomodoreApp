const ajaxUrl = 'api/';
const addPomoUrl = ajaxUrl + 'pomo/add';
const projectUrl = ajaxUrl + 'project/';
const addProjectUrl = projectUrl + 'add';
const getProjectsUrl = projectUrl + 'get';
const addTaskUrl = ajaxUrl + 'task/add';
const getTasksUrl = ajaxUrl + 'task/get';
const timerSpeed = 1;
const timeZoneOffset = -new Date().getTimezoneOffset() / 60;
const gongMusic = document.getElementById("myAudio");

let pomodoro = {
    shouldAddPomo: true,
    timerIsRunning: false,
    pomodoroIsActive: false,
    minutes: 0,
    seconds: 0,
    fillerHeight: 0,
    fillerIncrement: 0,
    startedAt: null,
    interval: null,
    minutesDom: null,
    secondsDom: null,
    fillerDom: null,

    originalMin: 25,
    originalSec: 0,
    originalBreakMin: 5,
    originalLongBreakMin: 25,

    init: function () {
        let self = this;
        this.minutesDom = $('#minutes');
        this.secondsDom = $('#seconds');
        this.fillerDom = $('#filler');
        this.interval = setInterval(function () {
            self.intervalCallback.apply(self);
        }, 250 / timerSpeed);

        $('#work').click(function () {
            if ($('#tasksDropDown').val() < 0) {
                errorNoty("Please select a task");
                return;
            }
            if (!self.pomodoroIsActive) {
                self.startWork.apply(self);
                $('#work').text('Pause');
            } else if (self.timerIsRunning) {
                self.timerIsRunning = false;
                $('#work').text('Resume');
            } else if (!self.timerIsRunning) {
                self.timerIsRunning = true;
                self.startedAt = Date.now();
                $('#work').text('Pause');
            }
        });

        $('#shortBreak').click(function () {
            if (!self.timerIsRunning) {
                self.startShortBreak.apply(self);
            }
        });
        $('#longBreak').click(function () {
            if (!self.timerIsRunning) {
                self.startLongBreak.apply(self);
            }
        });
        $('#stop').click(function () {
            self.stopTimer.apply(self);
        });


        $('#decreaseDuration').click(function () {
            if (!pomodoro.timerIsRunning) {
                if (pomodoro.originalMin > 0) {
                    pomodoro.originalMin--;
                }
                $('#duration').html(pomodoro.originalMin);
                $('#minutes').html(pomodoro.originalMin);
            }
        });

        $('#increaseDuration').click(function () {
            if (!pomodoro.timerIsRunning) {
                pomodoro.originalMin++;
                $('#duration').html(pomodoro.originalMin);
                $('#minutes').html(pomodoro.originalMin);
            }
        });

        $('#decreaseDurationBrake').click(function () {
            if (!pomodoro.timerIsRunning) {
                if (pomodoro.originalBreakMin > 0) {
                    pomodoro.originalBreakMin--;
                }
                $('#durationBreak').html(pomodoro.originalBreakMin);
            }
        });

        $('#increaseDurationBrake').click(function () {
            if (!pomodoro.timerIsRunning) {
                pomodoro.originalBreakMin++;
                $('#durationBreak').html(pomodoro.originalBreakMin);
            }
        });
    },

    resetVariables: function (mins, secs, isTimerRunning) {
        this.minutes = mins;

        this.seconds = secs;

        this.timerIsRunning = isTimerRunning;
        this.fillerIncrement = 200 / (this.minutes * 60);
        this.fillerHeight = 0;
        this.startedAt = null;
    },

    resetVariablesDefault: function (isTimerRunning) {
        this.resetVariables(this.originalMin, this.originalSec, isTimerRunning);
        $('#work').text('Work');
        this.dropDownActivate();
    },

    startWork: function () {
        this.resetVariablesDefault(true);
        this.dropDownDeactivate();
        this.pomodoroIsActive = true;
        this.shouldAddPomo = true;
    },

    startShortBreak: function () {
        this.resetVariables(this.originalBreakMin, 0, true);
        this.shouldAddPomo = false;
    },

    startLongBreak: function () {
        this.resetVariables(this.originalLongBreakMin, 0, true);
        this.shouldAddPomo = false;
    },

    stopTimer: function () {
        if (this.pomodoroIsActive) {
            util.addPomo(pomodoro.originalMin * 60 + pomodoro.originalSec
                - pomodoro.minutes * 60 - pomodoro.seconds);
            this.resetVariablesDefault(false);
            this.pomodoroIsActive = false;
        }
        this.resetVariablesDefault(false);
        this.updateDom();
    },

    updateDom: function () {
        this.minutesDom.text(util.toDoubleDigit(this.minutes));
        this.secondsDom.text(util.toDoubleDigit(this.seconds));
        this.fillerHeight = this.fillerHeight + this.fillerIncrement;
        this.fillerDom.css('height', this.fillerHeight + 'px');
    },

    intervalCallback: function () {
        if (!this.timerIsRunning) {
            return false;
        }
        if (this.startedAt == null) {
            this.startedAt = Date.now();
        }
        this.elapsedFromStart = Date.now() - this.startedAt;
        if (this.elapsedFromStart > 1000) {
            this.startedAt += 1000;
            if (this.seconds === 0) {
                if (this.minutes === 0) {
                    this.timerComplete();
                    return;
                }
                this.seconds = 59;
                this.minutes--;
            } else {
                this.seconds--;
            }
            this.updateDom();
        }
    },

    timerComplete: function () {
        this.timerIsRunning = false;
        this.pomodoroIsActive = false;
        this.fillerHeight = 0;
        if (this.shouldAddPomo) {
            util.addPomo(this.originalMin * 60 + this.originalSec);
        }
        util.playGongMusic();
        this.resetVariablesDefault(false);
        this.updateDom();
    },

    dropDownActivate: function () {
        $('#projectsDropDown').prop('disabled', false);
        $('#tasksDropDown').prop('disabled', false);
    },

    dropDownDeactivate: function () {
        $('#projectsDropDown').prop('disabled', 'disabled');
        $('#tasksDropDown').prop('disabled', 'disabled');
    }
};

let lastNoty;

function closeNoty() {
    if (lastNoty) {
        lastNoty.close();
        lastNoty = undefined;
    }
}

function failNoty(jqXHR) {
    closeNoty();
    // https://stackoverflow.com/questions/48229776
    let errorInfo = JSON.parse(jqXHR.responseText);
    lastNoty = new Noty({
        text: "<span class='fas fa-exclamation-circle'></span>"
            + "error status" + ": " + jqXHR.status + "<br>" + errorInfo.error + "<br>"
            + errorInfo.message,
        type: "error",
        layout: "bottomRight"
    }).show();
}

function errorNoty(text) {
    closeNoty();
    // https://stackoverflow.com/questions/48229776
    lastNoty = new Noty({
        text: "<i class='fas fa-exclamation-circle'></i>"
            + ' ' + text,
        type: "error",
        layout: "bottomRight"
    }).show();
}

function doneNoty(text) {
    closeNoty();
    // https://stackoverflow.com/questions/48229776
    lastNoty = new Noty({
        text: "<i class='fas fa-check-square'></i>"
            + ' ' + text,
        type: "success",
        timeout: 3000,
        layout: "bottomRight",
        textSize: 50
    }).show();
}

$(window).on('load', function () {
    $('#navbar-timer').addClass('active');
    pomodoro.init();

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(jqXHR);
    });

    $('#projectsDropDown').change(function () {
        if ($(this).val() === "-1") {
            $('#addNewProjModal').modal('show');
        } else {
            taskUtil.fetchTasks('');//todo implement this
        }
    });

    let $tasksDropDown = $('#tasksDropDown');
    $tasksDropDown.change(function () {
        if ($(this).val() === "-1") {
            $('#addNewTaskModal').modal('show');
        }
    });
    $tasksDropDown.click(function () {
        if ($(this).val() === "-1") {
            $('#addNewTaskModal').modal('show');
        }
    });

    let projectNameInput = $('#project-name')[0];
    projectNameInput.addEventListener('keypress', function (event) {
        if (event.keyCode === 13) {
            event.preventDefault();
        }
    });
    projectNameInput.onkeydown = function (e) {
        if (e.keyCode === 13) {
            util.saveProject();
        }
    };

    let taskNameInput = $('#task-name')[0];
    taskNameInput.addEventListener('keypress', function (event) {
        if (event.keyCode === 13) {
            event.preventDefault();
        }
    });
    taskNameInput.onkeydown = function (e) {
        if (e.keyCode === 13) {
            taskUtil.saveTask();
        }
    }
});

$(window).on('beforeunload', function (e) {
    if (pomodoro.pomodoroIsActive) {
        util.addPomo(pomodoro.originalMin * 60 + pomodoro.originalSec - pomodoro.minutes * 60 - pomodoro.seconds);
        this.resetVariablesDefault(false);
    }
});

//to autofocus on modals
//https://stackoverflow.com/questions/14940423
$('.modal').on('shown.bs.modal', function () {
    $(this).find('[autofocus]').focus();
});

let taskUtil = {
    saveTask: function () {
        let serialize = $('#addTaskForm').serialize();
        let projectId = $('#projectsDropDown').val();
        serialize += '&projectId=' + projectId;
        $.post({
            beforeSend: util.setAuth,
            url: addTaskUrl,
            data: serialize,
            error: function (xhr, desc, err) {
                console.log(xhr);
                console.log('Details: ' + desc + '\nError:' + err);
            }
        }).done(function () {
            let nameOfTheNewTaskInput = $('#task-name');
            taskUtil.fetchTasks(nameOfTheNewTaskInput.val());
            nameOfTheNewTaskInput.val('');
            $('#addNewTaskModal').modal('hide');
        });
    },

    fetchTasks: function (nameOfTheNewTask) {
        $.get({
            beforeSend: util.setAuth,
            url: getTasksUrl + '?projectId=' + $('#projectsDropDown').val(),
            success: function (tasks) {
                taskUtil.updateTaskSelectWithNewData(tasks, nameOfTheNewTask);
            },
            error: function (xhr, desc, err) {
                console.log(xhr);
                console.log('Details: ' + desc + '\nError:' + err);
            }
        });
    },

    updateTaskSelectWithNewData: function (data, nameOfTheNewTask) {
        let dropDown = $('#tasksDropDown');
        $('.optionTaskName').remove();
        data.sort((a, b) => a.name.localeCompare(b.name))
            .reverse()
            .forEach(
                value => {
                    dropDown.prepend($('<option>', {
                        class: 'optionTaskName',
                        value: value.id,
                        text: value.name
                    }));
                });

        if (nameOfTheNewTask === '') {
            $('.optionTaskName').filter(function () {
                return $(this).index() === 0
            }).attr('selected', 'selected')
        } else {
            $('.optionTaskName').filter(function () {
                // noinspection EqualityComparisonWithCoercionJS
                return $(this).html() == nameOfTheNewTask
            }).attr('selected', 'selected')
        }
    },
};


let util = {
    setAuth: function (request) {
        request.setRequestHeader("authorization", 'Bearer ' + Cookies.get('pomodoro-token'));
    },
    saveProject: function () {
        let serialize = $('#addProjectForm').serialize();
        $.post({
            beforeSend: this.setAuth,
            url: addProjectUrl,
            data: serialize,
            error: function (xhr, desc, err) {
                console.log(xhr);
                console.log('Details: ' + desc + '\nError:' + err);
            }
        }).done(function () {
            let nameOfTheNewProjectInput = $('#project-name');
            util.fetchProjects(nameOfTheNewProjectInput.val());
            nameOfTheNewProjectInput.val('');
            $('#addNewProjModal').modal('hide');
            $('.optionTaskName').remove();
        });
    },

    fetchProjects: function (nameOfTheNewProjectInput) {
        $.get({
            beforeSend: this.setAuth,
            url: getProjectsUrl,
            success: function (projects) {
                util.updateProjectSelectWithNewData(projects, nameOfTheNewProjectInput);
            },
            error: function (xhr, desc, err) {
                console.log(xhr);
                console.log('Details: ' + desc + '\nError:' + err);
            }
        });
    },

    updateProjectSelectWithNewData: function (data, nameOfTheNewProject) {
        let dropDown = $('#projectsDropDown');
        $('.optionProjectName').remove();
        data.sort((a, b) => a.name.localeCompare(b.name))
            .reverse()
            .forEach(
                value => {
                    dropDown.prepend($('<option>', {
                        class: 'optionProjectName',
                        value: value.id,
                        text: value.name
                    }));
                });

        if (nameOfTheNewProject === '') {
            $('.optionProjectName').filter(function () {
                return $(this).index() === 0
            }).attr('selected', 'selected')
        } else {
            $('.optionProjectName').filter(function () {
                // noinspection EqualityComparisonWithCoercionJS
                return $(this).html() == nameOfTheNewProject
            }).attr('selected', 'selected')
        }
    },


    toDoubleDigit: function (num) {
        if (num < 10) {
            return '0' + parseInt(num, 10);
        }
        return num;
    },

    playGongMusic: function () {
        gongMusic.play();
    },

    addPomo: function (duration) {
        $.post({
            beforeSend: this.setAuth,
            url: addPomoUrl +
                '?length=' + duration
                + '&taskId=' + $('#tasksDropDown').val()
                + '&clientTimeZone=' + timeZoneOffset,
            error: function (xhr, desc, err) {
                console.log(xhr);
                console.log('Details: ' + desc + '\nError:' + err);
            },

        }).done(function () {
            doneNoty("New Pomo added!")
        });
    },
};
