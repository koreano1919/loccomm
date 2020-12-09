package it.nero.loccomm.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.nero.loccomm.web.rest.TestUtil;

public class ProductAttributeTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductAttributeType.class);
        ProductAttributeType productAttributeType1 = new ProductAttributeType();
        productAttributeType1.setId(1L);
        ProductAttributeType productAttributeType2 = new ProductAttributeType();
        productAttributeType2.setId(productAttributeType1.getId());
        assertThat(productAttributeType1).isEqualTo(productAttributeType2);
        productAttributeType2.setId(2L);
        assertThat(productAttributeType1).isNotEqualTo(productAttributeType2);
        productAttributeType1.setId(null);
        assertThat(productAttributeType1).isNotEqualTo(productAttributeType2);
    }
}
