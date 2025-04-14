package com.kotikom.chess.model.internal;

public class Clock {
    private int hh;
    private int mm;
    private int ss;

    public Clock(int hh, int mm, int ss) {
        this.hh = hh;
        this.mm = mm;
        this.ss = ss;
    }

    public boolean isZero() {
        return (hh == 0 && mm == 0 && ss == 0);
    }

    public void decrementSeconds() {
        if (this.mm == 0 && this.ss == 0) {
            this.ss = 59;
            this.mm = 59;
            this.hh--;
        } else if (this.ss == 0) {
            this.ss = 59;
            this.mm--;
        } else this.ss--;
    }

    public String getTime() {
        return String.format("%02d:%02d:%02d", hh, mm, ss);
    }
}
