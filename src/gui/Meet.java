/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

/**
 *
 * @author hadjn
 */
import org.apache.commons.exec.launcher.CommandLauncher;
import org.apache.commons.exec.launcher.CommandLauncherFactory;

import java.io.IOException;

public class Meet {
    public static void main(String[] args) throws IOException {
        String url = "https://meet.google.com/new";
        CommandLauncher launcher = CommandLauncherFactory.createVMLauncher();
        Runtime.getRuntime().exec(String.format("rundll32 url.dll,FileProtocolHandler %s", url));
    }
}


