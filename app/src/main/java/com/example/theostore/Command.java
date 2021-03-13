package com.example.theostore;

import java.util.List;

public class Command {
    public CommandType type;

    // for BRING or FIND_ITEM commands we want to be able to store more information
    public int tray = -1; // don't want any issues where null is actually 0 and then it thinks we have a tray ?...
    public List<String> searchWords;

    public Command(CommandType t) {
        this.type = t;
    }

    public Command(CommandType t, int tr) {
        assert t == CommandType.BRING;
        this.type = t;
        this.tray = tr;
    }

    public Command(CommandType t, List<String> sw) {
        assert t == CommandType.FIND_ITEM;
        this.type = t;
        this.searchWords = sw;
    }
}
