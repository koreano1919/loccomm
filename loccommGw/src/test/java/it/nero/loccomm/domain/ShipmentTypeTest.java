package it.nero.loccomm.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.nero.loccomm.web.rest.TestUtil;

public class ShipmentTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipmentType.class);
        ShipmentType shipmentType1 = new ShipmentType();
        shipmentType1.setId(1L);
        ShipmentType shipmentType2 = new ShipmentType();
        shipmentType2.setId(shipmentType1.getId());
        assertThat(shipmentType1).isEqualTo(shipmentType2);
        shipmentType2.setId(2L);
        assertThat(shipmentType1).isNotEqualTo(shipmentType2);
        shipmentType1.setId(null);
        assertThat(shipmentType1).isNotEqualTo(shipmentType2);
    }
}
