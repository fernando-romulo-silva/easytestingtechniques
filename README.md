# easytestingtechniques

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Project status](https://img.shields.io/badge/Project%20status-Maintenance-orange.svg)](https://img.shields.io/badge/Project%20status-Maintenance-orange.svg)

# About

It's a set of test techniques implementations like pairwise (allpair), equivalence class, random tests, boundary value testing and more.

# Technologies

- Java
- Ant
- Maven
- TestNG
- AssertJ
- Apache Libs

# Install

requirements (environment variables configured): 
 - Java 14
 - Maven 3
 
 ```bash
# clone it
git clone https://github.com/fernando-romulo-silva/easytestingtechniques

# access the project folder
cd easytestingtechniques

# execute
mvn install
```
 

# How to Use

First add it as lib, in pom.xml, add the following xml between `<dependencies> ... </dependencies>`

```xml
<dependency>
	<groupId>org.easytestingtechniques</groupId>	
	<artifactId>easytestingtechniques</artifactId>
	<version>${easytestingtechniques-version}</version>	
	<scope>test</scope>
</dependency>
```

Then you can use it on your automated tests, here with TestNG:

```java
    @DataProvider
    public Object[][] boundaryValueTestingData() {
		return worstNormalBoundaryValueTesting( //
			ofInteger("age", 30, 18, 55), //
			ofInteger("productAmount", 5, 1, 10) //
		);
    }

    @Test(dataProvider = "boundaryValueTestingData")
    public void boundaryValueTestingTest(final Integer age, final Integer productAmount) {
		final var service = new CalculateService();
		service.calculate(age, productAmount);
    }
```

Another example with JUnit:

```java
    public static Stream<Arguments> randomTestingData() {

		final var data = randomTesting( //
			ofString("description", 5, $ -> { //
			    $.maxLength = 15; //
			    $.numbers = false; //
			    $.specialChar = false;//
			}), //
	
			ofInteger("amount", 7, 0, 15) //
		);
	
		return Stream.of(data).map(l -> arguments(l));
    }

    @ParameterizedTest(name = "#{index} - description {0} - amount {1}")
    @MethodSource("randomTestingData")
    public void randomTestingTest(final String description, final Integer amount) {
		final var service = new DescriptionService();
		servico.execute(description, amount);
    }
```