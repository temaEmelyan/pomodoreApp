const ajaxUrl = 'ajax/';
const timerSpeed = 1;

let pomodoro = {
    timerIsRunning: false,
    pomodoroIsActive: false,
    minutes: 0,
    seconds: 0,
    fillerHeight: 0,
    fillerIncrement: 0,
    interval: null,
    minutesDom: null,
    secondsDom: null,
    fillerDom: null,

    originalMin: 25,
    originalSec: 0,

    init: function () {
        let self = this;
        this.minutesDom = $('#minutes');
        this.secondsDom = $('#seconds');
        this.fillerDom = $('#filler');
        this.interval = setInterval(function () {
            self.intervalCallback.apply(self);
        }, 1000 / timerSpeed);

        $('#work').click(function () {
            if (!self.pomodoroIsActive) {
                self.startWork.apply(self);
                $('#work').text('Pause');
            } else if (self.timerIsRunning) {
                self.timerIsRunning = false;
                $('#work').text('Resume');
            } else if (!self.timerIsRunning) {
                self.timerIsRunning = true;
                $('#work').text('Pause');
            }
        });

        $('#shortBreak').click(function () {
            self.startShortBreak.apply(self);
        });
        $('#longBreak').click(function () {
            self.startLongBreak.apply(self);
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
    },

    resetVariables: function (mins, secs, timerIsRunning) {
        this.minutes = mins;
        this.originalMin = mins;
        this.seconds = secs;
        this.originalSec = secs;
        this.timerIsRunning = timerIsRunning;
        this.fillerIncrement = 200 / (this.minutes * 60);
        this.fillerHeight = 0;
    },

    resetVariablesDefault: function (started) {
        this.resetVariables(this.originalMin, this.originalSec, started);
        $('#work').text('Work');
    },

    startWork: function () {
        this.resetVariablesDefault(true);
        this.pomodoroIsActive = true;
    },

    startShortBreak: function () {
        this.resetVariables(5, 0, true);
    },

    startLongBreak: function () {
        this.resetVariables(15, 0, true);
    },

    stopTimer: function () {
        if (this.pomodoroIsActive) {
            util.addPomo(pomodoro.originalMin * 60 + pomodoro.originalSec - pomodoro.minutes * 60 - pomodoro.seconds);
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
    },

    timerComplete: function () {
        this.timerIsRunning = false;
        this.pomodoroIsActive = false;
        this.fillerHeight = 0;
        util.addPomo(this.originalMin * 60 + this.originalSec);
    }
};

$(window).on('load', function () {
    pomodoro.init();
});

$(window).on('beforeunload', function (e) {
    if (pomodoro.pomodoroIsActive) {
        util.addPomo(pomodoro.originalMin * 60 + pomodoro.originalSec - pomodoro.minutes * 60 - pomodoro.seconds);
        this.resetVariablesDefault(false);
    }
});

let util = {
    toDoubleDigit: function (num) {
        if (num < 10) {
            return '0' + parseInt(num, 10);
        }
        return num;
    },

    addPomo: function (duration) {
        $.ajax({
            url: ajaxUrl + 'add/' + duration,
            type: 'POST',
            error: function (xhr, desc, err) {
                console.log(xhr);
                console.log('Details: ' + desc + '\nError:' + err);
            }
        });

        $.ajax({
            url: ajaxUrl + 'add?length=' + duration,
            type: 'POST',
            error: function (xhr, desc, err) {
                console.log(xhr);
                console.log('Details: ' + desc + '\nError:' + err);
            }
        });

        $.ajax({
            url: ajaxUrl + 'add/' + duration,
            type: 'GET',
            error: function (xhr, desc, err) {
                console.log(xhr);
                console.log('Details: ' + desc + '\nError:' + err);
            }
        });
    },
};