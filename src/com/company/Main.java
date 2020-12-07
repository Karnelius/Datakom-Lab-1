package com.company;

import java.io.IOException;
import java.net.*;

public class Main {

    //TODO utöka koden så att den försöker ansluta till en annan server om anslutningen misslyckas

    public static InetAddress inetAdressServerList(String[] serverList) {
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
            InetAddress address = inetAdressServerList(serverList);
            SNTPMessage message = new SNTPMessage();
            byte[] buf = message.toByteArray();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 123);

            socket.send(packet);
            System.out.println("Sent request");
            socket.receive(packet);
            SNTPMessage response = new SNTPMessage(packet.getData());
            System.out.println("Got reply");
            System.out.println("---------");
            socket.close();
            // System.out.println(response);
            response.toString();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
