import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.6.1"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.0"
	kotlin("plugin.spring") version "1.6.0"
}

group = "com.dyurekdeler"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	// https://mvnrepository.com/artifact/org.springframework.data/spring-data-jpa
	implementation("org.springframework.data:spring-data-jpa:2.6.0")
	// https://mvnrepository.com/artifact/org.springframework.data/spring-data-mongodb
	implementation("org.springframework.data:spring-data-mongodb:3.3.0")
	// https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-netflix-eureka-client
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:3.1.0")
	// https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-config
	implementation("org.springframework.cloud:spring-cloud-starter-config:3.1.0")
	// https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-actuator
	implementation("org.springframework.boot:spring-boot-starter-actuator:2.6.1")


}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
