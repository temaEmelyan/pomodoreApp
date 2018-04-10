const ajaxUrl = 'ajax/';
const addPomoUrl = ajaxUrl + 'pomo/add';
const projectUrl = ajaxUrl + 'project/';
const addProjectUrl = projectUrl + 'add';
const getProjectsUrl = projectUrl + 'get';
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
    },

    dropDownDeactivate: function () {
        $('#projectsDropDown').prop('disabled', 'disabled');
    }
};

let failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function failNoty(jqXHR) {
    closeNoty();
    // https://stackoverflow.com/questions/48229776
    let errorInfo = JSON.parse(jqXHR.responseText);
    failedNote = new Noty({
        text: "<span class='glyphicon glyphicon-exclamation-sign'></span> &nbsp;" + "error status" + ": " + jqXHR.status + "<br>" + errorInfo.type + "<br>" + errorInfo.detail,
        type: "error",
        layout: "bottomRight"
    }).show();
}

$(window).on('load', function () {
    pomodoro.init();

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(jqXHR);
    });

    $('#projectsDropDown').change(function () {
        if ($(this).val() === "-1") {
            $('#addNewProjModal').modal('show');
        }
    });

    $(function () {
        let token = $("meta[name='_csrf']").attr("content");
        let header = $("meta[name='_csrf_header']").attr("content");
        $(document).ajaxSend(function (e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });
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
    }
});

$(window).on('beforeunload', function (e) {
    if (pomodoro.pomodoroIsActive) {
        util.addPomo(pomodoro.originalMin * 60 + pomodoro.originalSec - pomodoro.minutes * 60 - pomodoro.seconds);
        this.resetVariablesDefault(false);
    }
});

let util = {
    saveProject: function () {
        let serialize = $('#addProjectForm').serialize();
        $.post({
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
        });
    },

    fetchProjects: function (nameOfTheNewProject) {
        $.get({
            url: getProjectsUrl,
            success: function (projects) {
                util.updateSelectWithNewData(projects, nameOfTheNewProject);
            },
            error: function (xhr, desc, err) {
                console.log(xhr);
                console.log('Details: ' + desc + '\nError:' + err);
            }
        });
    },

    updateSelectWithNewData: function (data, nameOfTheNewProject) {
        let dropDown = $('#projectsDropDown');
        $('.optionProjectName').remove();
        data.reverse().forEach(value => {
            dropDown.prepend($('<option>', {
                class: 'optionProjectName',
                value: value.id,
                text: value.name
            }));
        });

        $('.optionProjectName').filter(function () {
            // noinspection EqualityComparisonWithCoercionJS
            return $(this).html() == nameOfTheNewProject
        }).attr('selected', 'selected')
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
            url: addPomoUrl +
            '?length=' + duration
            + '&projectId=' + $('#projectsDropDown').val()
            + '&clientTimeZone=' + timeZoneOffset,
            error: function (xhr, desc, err) {
                console.log(xhr);
                console.log('Details: ' + desc + '\nError:' + err);
            }
        });
    },
};