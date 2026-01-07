package com.devops.app;

import org.junit.Test;
import static org.junit.Assert.*;

public class MainTest {
    
    @Test
    public void testGetMessage() {
        String expected = "Bonjour mon projet DevOps a bien fonctionner";
        String actual = Main.getMessage();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testMessageNotNull() {
        assertNotNull(Main.getMessage());
    }
}
