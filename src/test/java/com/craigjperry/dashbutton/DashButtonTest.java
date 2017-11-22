package com.craigjperry.dashbutton;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class DashButtonTest {
    @Test
    public void exposesImmutableMacAddressProperty() throws Exception {
        String macAddress = "00:DE:AD:BE:EF:00";
        DashButton dashButton = new DashButton(macAddress);
        assertThat(dashButton.getMacAddress()).isEqualTo(macAddress);
    }

    @Test
    public void honoursEqualsContract() throws Exception {
        DashButton dashButton1 = new DashButton("12:34:56:78:90:00");
        DashButton dashButton2 = new DashButton("00:12:34:56:78:90");
        assertThat(dashButton1).isEqualTo(dashButton1);
        assertThat(dashButton2).isEqualTo(dashButton2);
        assertThat(dashButton1).isNotEqualTo(dashButton2);
    }

    @Test
    public void honoursHashCodeContract() throws Exception {
        DashButton dashButton1 = new DashButton("12:34:56:78:90:00");
        DashButton dashButton2 = new DashButton("00:12:34:56:78:90");
        assertThat(dashButton1.hashCode()).isNotEqualTo(dashButton2.hashCode());
    }

    @Test
    public void hasSensibleToString() throws Exception {
        DashButton dashButton = new DashButton("12:34:56:78:90:00");
        assertThat(dashButton.toString()).isEqualTo("DashButton(macAddress=12:34:56:78:90:00)");
    }
}