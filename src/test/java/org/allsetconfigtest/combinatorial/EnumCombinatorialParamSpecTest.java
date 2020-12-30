package org.allsetconfigtest.combinatorial;

import static java.util.Arrays.asList;
import static org.allsetconfigtest.TestingLevels.UNIT;
import static org.allsetconfigtest.TestingObject.equalsAndHashCode;
import static org.allsetconfigtest.combinatorial.ConnectionType.ATM;
import static org.allsetconfigtest.combinatorial.ConnectionType.CABLE_INTERNET_ACCESS;
import static org.allsetconfigtest.combinatorial.ConnectionType.FIBER;
import static org.allsetconfigtest.combinatorial.ConnectionType.VSAT;
import static org.allsetconfigtest.combinatorial.EnumCombinatorialParamSpec.ToEnum.ofEnum;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.LinkedHashSet;

import org.allsetconfigtest.combinatorial.EnumCombinatorialParamSpec.ToEnum;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test
public class EnumCombinatorialParamSpecTest {

    @Test(priority = 0, groups = UNIT)
    public void equalsAndHashCodeTest() {
	equalsAndHashCode(EnumCombinatorialParamSpec.class);
    }

    // @formatter:off
    @DataProvider
    public Object[][] ofEnumOneValidAndVariousInvalidExceptionData() { //
        return new Object[][] { //
            {  null    ,    new ConnectionType[] { ATM,  CABLE_INTERNET_ACCESS, null }  }, //
            {  FIBER   ,    new ConnectionType[] { }                                    }, //
            {  FIBER   ,    null                                                        }, // 
        };
    }
    // @formatter:on   

    @Test(priority = 1, groups = UNIT, dataProvider = "ofEnumOneValidAndVariousInvalidExceptionData")
    public void ofEnumOneValidAndVariousInvalidExceptionTest(final ConnectionType validArgument, final ConnectionType[] invalidArguments) {

	assertThatThrownBy(() -> ToEnum.<ConnectionType>ofEnum().addValidValue(validArgument).addInvalidValues(invalidArguments)) //
		.isInstanceOf(IllegalArgumentException.class);

    }

    // @formatter:off
    @DataProvider
    public Object[][] ofEnumOneValidAndVariousInvalidData() {
        return new Object[][] {  
            {  FIBER   ,    new ConnectionType[] { ATM , null                  } }, //
            {  VSAT    ,    new ConnectionType[] { null, CABLE_INTERNET_ACCESS } }, //
        };
    }
    // @formatter:on  

    @Test(priority = 2, groups = UNIT, dataProvider = "ofEnumOneValidAndVariousInvalidData")
    public void ofEnumOneValidAndVariousInvalidTest(final ConnectionType validArgument, final ConnectionType[] invalidArguments) {
	final var parameter = ToEnum.<ConnectionType>ofEnum() //
		.addValidValue(validArgument) //
		.addInvalidValues(invalidArguments);

	assertThat(parameter.getValidValues()).contains(validArgument);

	assertThat(parameter.getInvalidValues()).isEqualTo(new LinkedHashSet<>(asList(invalidArguments)));
    }

    @Test(priority = 3, groups = UNIT)
    public void ofEnumByClassTest() {
	final var parameter = ofEnum(ConnectionType.class);

	assertThat(parameter.getValidValues()).contains(FIBER);
    }
}
