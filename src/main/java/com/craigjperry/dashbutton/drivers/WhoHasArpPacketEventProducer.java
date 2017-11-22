package com.craigjperry.dashbutton.drivers;

import com.craigjperry.dashbutton.NetworkListener;
import com.craigjperry.dashbutton.WhoHasArpPacketEvent;
import org.pcap4j.core.*;
import org.pcap4j.packet.EthernetPacket;
import org.pcap4j.packet.Packet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.EOFException;
import java.time.LocalDateTime;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

import static com.craigjperry.dashbutton.WhoHasArpPacketEvent.WHO_HAS_ARP_PACKET_BPF_FILTER;
import static org.pcap4j.core.PcapNetworkInterface.PromiscuousMode.PROMISCUOUS;

@Component
public class WhoHasArpPacketEventProducer implements NetworkListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(WhoHasArpPacketEventProducer.class);

    private final NetworkInterfaceChooser networkInterfaceChooser;
    private final BlockingQueue<WhoHasArpPacketEvent> eventsQueue;
    private PcapNetworkInterface networkInterface;
    private PcapHandle handle;

    public WhoHasArpPacketEventProducer(NetworkInterfaceChooser networkInterfaceChooser, BlockingQueue<WhoHasArpPacketEvent> eventsQueue) {
        this.networkInterfaceChooser = networkInterfaceChooser;
        this.eventsQueue = eventsQueue;
    }

    @Override
    public void run() {
        LOGGER.info("Launching network listener thread");
        try {
            setup();
        } catch (PcapNativeException | NotOpenException e) {
            LOGGER.error("Failed to open interface [{}] for sniffing because: {}", networkInterface.getName(), e.getMessage());
            throw new IllegalStateException(e);
        }
        try {
            loop();
        } catch (Exception e) {
            LOGGER.error("Failed to process packet because: {}", e.getMessage());
            throw new IllegalStateException(e);
        }
    }

    private void setup() throws PcapNativeException, NotOpenException {
        networkInterface = networkInterfaceChooser.firstLocalInterface();
        handle = networkInterface.openLive(65536, PROMISCUOUS, 6000);
        handle.setFilter(WHO_HAS_ARP_PACKET_BPF_FILTER, BpfProgram.BpfCompileMode.OPTIMIZE);
    }

    private void loop() throws PcapNativeException, NotOpenException, EOFException {
        while (true) {
            try {
                handlePacket();
            } catch (TimeoutException e) {
                LOGGER.debug("No packets observed in this cycle");
            } catch (InterruptedException e) {
                LOGGER.warn("Interrupted when putting event on the queue because: {}", e);
            }

        }
    }

    private void handlePacket() throws PcapNativeException, EOFException, TimeoutException, NotOpenException, InterruptedException {
        Packet packet = handle.getNextPacketEx();
        LOGGER.debug("Processing ARP packet");
        String macAddress = ((EthernetPacket) packet).getHeader().getSrcAddr().toString();
        WhoHasArpPacketEvent event = new WhoHasArpPacketEvent(LocalDateTime.now(), macAddress);
        LOGGER.debug("Enqueuing ARP Packet Event: {}", event);
        eventsQueue.put(event);
    }
}
