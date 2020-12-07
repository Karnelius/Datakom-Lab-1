package com.company;

import java.io.IOException;
import java.net.*;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.out;

public class Main {


    public static InetAddress inetAddressServerList(String[] serverList) {
        InetAddress address = null;
        for (String i : serverList) {
            try {
                address = InetAddress.getByName(i);
                break;
            } catch (UnknownHostException e) {
                System.out.println(e.getMessage());
            }
        }
        return address;
    }

    public static void main(String[] args) {

        String[] serverList = {
                "gbg1.ntp.se",
                "gbg2.ntp.se",
                "mmo1.ntp.se",
                "mmo2.ntp.se",
                "sth1.ntp.se",
                "sth2.ntp.se",
                "svl1.ntp.se",
                "svl2.ntp.se"};

        try {

            DatagramSocket socket = new DatagramSocket();
            InetAddress address = inetAddressServerList(serverList);
            SNTPMessage message = new SNTPMessage();
            byte[] buf = message.toByteArray();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 123);

            socket.send(packet);
            System.out.println("Sent request");
            socket.receive(packet);
            double T4 = (currentTimeMillis() / 1000.0) + 2208988800.0;
            SNTPMessage response = new SNTPMessage(packet.getData());
            System.out.println("Got reply");

            out.println("----Times----");
            double T1 = response.getOriginateTimestamp();
            double T2 = response.getReceiveTimestamp();
            double T3 = response.getTransmitTimestamp();

            double d = ((T4 - T1) - (T3 - T2));
            double t = ((T2 - T1) + (T3 - T4)) / 2;

            System.out.println("'(d)' delay : " + d);
            System.out.println("'(t)' clock off set : " + t);
            System.out.println("---------");

            socket.close();
            System.out.println(response);




        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
