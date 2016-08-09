package com.clouway.test.task3;

import com.clouway.task3.Server;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.Rule;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertTrue;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class ServerWithMultipleClientsTest {
    Server server = new Server();
    String fromServer;
    String result = new String();
    @Rule public JUnitRuleMockery context = new JUnitRuleMockery() {{setThreadingPolicy(new Synchroniser());}};


}
