package com.clouway.task3;

/**
 * Created by borislav on 09.08.16.
 */
public interface Observer {
    boolean clientIsAdult();
    boolean connectionIsOptimal();
    boolean messageIsValid();
    boolean isClientInformed();
    boolean isClientGreeted();
}
