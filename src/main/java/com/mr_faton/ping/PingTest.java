package com.mr_faton.ping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;

/**
 * Description
 *
 * @author root
 * @version 1.0
 * @since 03.11.2015
 */
public class PingTest {
    public static void main(String[] args) throws IOException, InterruptedException {
//        InetAddress inetAddress = InetAddress.getByName("192.168.101.113");
//        System.out.println(inetAddress.isReachable(10000));


        String[] comArr = {"cmd.exe", "/c", "netsh interface ipv4 add dnsserver \"Ethernet\" address=192.168.101.78 index=1"};

        Process p = Runtime.getRuntime().exec(comArr);
        p.waitFor();
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream(), "cp866"));
        String line = reader.readLine();
        while (line != null) {
            System.out.println(line);
            line = reader.readLine();
        }
    }
}

