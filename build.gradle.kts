plugins {
	java
	id("org.springframework.boot") version "3.2.3"
	id("io.spring.dependency-management") version "1.1.4"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("org.postgresql:postgresql")
	// https://mvnrepository.com/artifact/org.tensorflow/tensorflow
	implementation("org.tensorflow:tensorflow:1.15.0")
	// https://mvnrepository.com/artifact/org.tensorflow/tensorflow-core-platform
	implementation("org.tensorflow:tensorflow-core-platform:0.3.3")
	// https://mvnrepository.com/artifact/org.tensorflow/tensorflow-core-api
	implementation("org.tensorflow:tensorflow-core-api:0.3.3")

	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")

	implementation ("org.springframework.boot:spring-boot-starter-data-rest")
	developmentOnly ("org.springframework.boot:spring-boot-devtools")

	implementation ("org.mapstruct:mapstruct:1.5.5.Final")
	implementation ("io.jsonwebtoken:jjwt-api:0.11.5")
	implementation ("io.jsonwebtoken:jjwt-impl:0.11.5")
	implementation ("io.jsonwebtoken:jjwt-jackson:0.11.5")

	implementation ("io.springfox:springfox-swagger2:3.0.0")
	implementation ("io.springfox:springfox-swagger-ui:3.0.0")
	implementation ("io.springfox:springfox-boot-starter:3.0.0")
	implementation ("javax.servlet:javax.servlet-api:4.0.1")

	implementation ("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.4.0")
	implementation ("org.springframework.boot:spring-boot-starter-validation")
	implementation ("org.springdoc:springdoc-openapi-ui:1.8.0")

	implementation ("org.springframework.boot:spring-boot-starter-mail:3.2.4")

	//For Gradle, add to dependencies section of build.gradle
	implementation ("com.cloudinary:cloudinary-http44:1.36.0")
	implementation ("com.cloudinary:cloudinary-taglib:1.36.0")
	implementation("io.github.cdimascio:dotenv-java:2.2.4")
	implementation ("org.modelmapper:modelmapper:3.2.0")


	//Implement ZXing to generate Barcode and QRCode
	implementation("com.google.zxing:core:3.5.3")
	implementation("com.google.zxing:javase:3.5.3")


	//Implement Google Map APis
	implementation ("com.google.maps:google-maps-services:2.2.0")
	// https://mvnrepository.com/artifact/com.mapbox.mapboxsdk/mapbox-sdk-turf
	implementation("com.mapbox.mapboxsdk:mapbox-sdk-turf:5.9.0-alpha.1")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
