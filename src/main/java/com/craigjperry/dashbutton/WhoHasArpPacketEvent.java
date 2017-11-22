package com.craigjperry.dashbutton;

import lombok.Value;

import java.time.LocalDateTime;

/**
 * When a station joins an ethernet network, it probes the local subnet to check if anyone has the IP address it
 * intends to use. It does this by broadcasting an ARP packet to all other stations on the local subnet. The broadcast
 * mac address is ff:ff:ff:ff:ff:ff. The ARP packet has its type field set to 1. Type 1 is the "Who Has?" query.
 */
@Value
public class WhoHasArpPacketEvent {
    public static final String WHO_HAS_ARP_PACKET_BPF_FILTER = "ether proto \\arp and (arp[6:2] == 1) and ether dst ff:ff:ff:ff:ff:ff";

    private final LocalDateTime receivedPacket;
    private final String macAddress;
}
