package com.craigjperry.dashbutton.drivers;

import org.junit.Test;
import org.pcap4j.core.PcapAddress;
import org.pcap4j.core.PcapNetworkInterface;

import java.net.Inet4Address;

import static org.assertj.core.api.Assertions.assertThat;

public class NetworkInterfaceChooserIntegrationTest {
    @Test
    public void findsFirstInterfaceWithALocalIpv4Address() throws Exception {
        // Given
        NetworkInterfaceChooser networkInterfaceChooser = new NetworkInterfaceChooser();

        // When
        PcapNetworkInterface networkInterface = networkInterfaceChooser.firstLocalInterface();

        // Then
        assertThat(networkInterface).isNotNull();
        assertThat(networkInterface.isLocal()).isTrue();
        assertThat(networkInterface.getAddresses()).size().isGreaterThan(0);
        assertThat(networkInterface.getAddresses()).extracting(PcapAddress::getAddress).contains(Inet4Address.getLocalHost());
    }
}