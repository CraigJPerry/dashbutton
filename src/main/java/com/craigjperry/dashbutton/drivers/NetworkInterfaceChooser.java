package com.craigjperry.dashbutton.drivers;

import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.Pcaps;
import org.springframework.stereotype.Component;

import java.net.Inet4Address;
import java.net.UnknownHostException;

@Component
class NetworkInterfaceChooser {
    PcapNetworkInterface firstLocalInterface() {
        try {
            return Pcaps.getDevByAddress(Inet4Address.getLocalHost());
        } catch (PcapNativeException|UnknownHostException e) {
            throw new IllegalStateException("pcap library initialisation failed because: " + e);
        }
    }
}
