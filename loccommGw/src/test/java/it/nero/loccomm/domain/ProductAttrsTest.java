package it.nero.loccomm.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.nero.loccomm.web.rest.TestUtil;

public class ProductAttrsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductAttrs.class);
        ProductAttrs productAttrs1 = new ProductAttrs();
        productAttrs1.setId(1L);
        ProductAttrs productAttrs2 = new ProductAttrs();
        productAttrs2.setId(productAttrs1.getId());
        assertThat(productAttrs1).isEqualTo(productAttrs2);
        productAttrs2.setId(2L);
        assertThat(productAttrs1).isNotEqualTo(productAttrs2);
        productAttrs1.setId(null);
        assertThat(productAttrs1).isNotEqualTo(productAttrs2);
    }
}
