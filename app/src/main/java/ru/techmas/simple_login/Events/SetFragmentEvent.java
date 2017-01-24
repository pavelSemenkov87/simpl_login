package ru.techmas.simple_login.Events;

/**
 * Created by pavel on 15.01.2017.
 */

public class SetFragmentEvent {
    public final int tipe;

    public SetFragmentEvent(int tipe) {
        this.tipe = tipe;
    }

    public int getTipe() {
        return tipe;
    }
}
