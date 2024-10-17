package com.csse3200.game.components.npc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonalCustomerEnumsTest {

    @Test
    void testEnumValues() {
        // Verify the enum contains the correct values
        PersonalCustomerEnums[] expectedValues = {
            PersonalCustomerEnums.HANK,
            PersonalCustomerEnums.LEWIS,
            PersonalCustomerEnums.SILVER,
            PersonalCustomerEnums.JOHN,
            PersonalCustomerEnums.MOONKI,
            PersonalCustomerEnums.BASIC_CHICKEN,
            PersonalCustomerEnums.BASIC_SHEEP
        };

        assertArrayEquals(expectedValues, PersonalCustomerEnums.values(), "Enum values should match the expected list");
    }

    @Test
    void testEnumValueOf() {
        // Verify that each enum constant can be retrieved using valueOf
        assertEquals(PersonalCustomerEnums.HANK, PersonalCustomerEnums.valueOf("HANK"), "Enum should return HANK");
        assertEquals(PersonalCustomerEnums.LEWIS, PersonalCustomerEnums.valueOf("LEWIS"), "Enum should return LEWIS");
        assertEquals(PersonalCustomerEnums.SILVER, PersonalCustomerEnums.valueOf("SILVER"), "Enum should return SILVER");
        assertEquals(PersonalCustomerEnums.JOHN, PersonalCustomerEnums.valueOf("JOHN"), "Enum should return JOHN");
        assertEquals(PersonalCustomerEnums.MOONKI, PersonalCustomerEnums.valueOf("MOONKI"), "Enum should return MOONKI");
        assertEquals(PersonalCustomerEnums.BASIC_CHICKEN, PersonalCustomerEnums.valueOf("BASIC_CHICKEN"), "Enum should return BASIC_CHICKEN");
        assertEquals(PersonalCustomerEnums.BASIC_SHEEP, PersonalCustomerEnums.valueOf("BASIC_SHEEP"), "Enum should return BASIC_SHEEP");
    }
}
