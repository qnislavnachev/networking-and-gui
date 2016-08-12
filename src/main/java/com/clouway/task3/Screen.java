package com.clouway.task3;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public interface Screen {
    // method display, displays the received String message
    void display(String message) throws NoSocketException;
}
