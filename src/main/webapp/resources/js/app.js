var pomodoro = {
    started: false,
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
        var self = this;
        this.minutesDom = $('#minutes');
        this.secondsDom = $('#seconds');
        this.fillerDom = $('#filler');
        this.interval = setInterval(function () {
            self.intervalCallback.apply(self);
        }, 100);
        $('#work').click(function () {
            self.startWork.apply(self);
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
            if (pomodoro.originalMin > 0) {
                pomodoro.originalMin--;
            }
            $('#duration').html(pomodoro.originalMin);
            $('#minutes').html(pomodoro.originalMin);
        });
        $('#increaseDuration').click(function () {
            pomodoro.originalMin++;
            $('#duration').html(pomodoro.originalMin);
            $('#minutes').html(pomodoro.originalMin);
        });
    },

    resetVariables: function (mins, secs, started) {
        this.minutes = mins;
        this.originalMin = mins;
        this.seconds = secs;
        this.originalSec = secs;
        this.started = started;
        this.fillerIncrement = 200 / (this.minutes * 60);
        this.fillerHeight = 0;
    },

    resetVariablesDefault: function (started) {
        this.resetVariables(this.originalMin, this.originalSec, started)
    },

    startWork: function () {
        this.resetVariablesDefault(true);
    },

    startShortBreak: function () {
        this.resetVariables(5, 0, true);
    },

    startLongBreak: function () {
        this.resetVariables(15, 0, true);
    },

    stopTimer: function () {
        this.resetVariablesDefault(false);
        this.updateDom();
    },

    toDoubleDigit: function (num) {
        if (num < 10) {
            return "0" + parseInt(num, 10);
        }
        return num;
    },

    updateDom: function () {
        this.minutesDom.text(this.toDoubleDigit(this.minutes));
        this.secondsDom.text(this.toDoubleDigit(this.seconds));
        this.fillerHeight = this.fillerHeight + this.fillerIncrement;
        this.fillerDom.css('height', this.fillerHeight + 'px');
    },

    intervalCallback: function () {
        if (!this.started) return false;
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
        this.started = false;
        this.fillerHeight = 0;
        this.addPomo(this.originalMin * 60 + this.originalSec);
    },

    addPomo: function (duration) {
        $.post({
            url: 'ajax/add/' + duration + '/',
            type: 'POST',
            error: function (xhr, desc, err) {
                console.log(xhr);
                console.log("Details0: " + desc + "\nError:" + err);
            }
        });
    }
};

window.onload = function () {
    pomodoro.init();
};