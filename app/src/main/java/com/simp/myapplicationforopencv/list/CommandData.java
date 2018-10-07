package com.simp.myapplicationforopencv.list;

public class CommandData {
    private long id;
    private String command;
    private String name;

    public CommandData( String command,long id) {
        this.id = id;
        this.command = command;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
