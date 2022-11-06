import java.io.ByteArrayOutputStream

plugins {
    id("org.springframework.boot") version "2.5.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("java")
}

val major = 0
val minor = 1
val patch = getRevisionCount()

group = "pj"
version = "$major.$minor.$patch"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
	implementation ("org.springframework.boot:spring-boot-starter-data-jdbc")
	implementation ("mysql:mysql-connector-java")
	implementation ("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation ("org.springframework.boot:spring-boot-starter-jdbc")
	implementation ("org.springframework.boot:spring-boot-starter-validation")
	implementation ("org.springframework.boot:spring-boot-starter-web")
	implementation ("org.springframework.boot:spring-boot-starter-mail")
	implementation ("org.springframework.boot:spring-boot-starter-security")
	implementation ("io.jsonwebtoken:jjwt:0.2")

	implementation("com.querydsl:querydsl-core")
	implementation("com.querydsl:querydsl-jpa")

	annotationProcessor("com.querydsl:querydsl-apt:${dependencyManagement.importedProperties["querydsl.version"]}:jpa") // querydsl JPAAnnotationProcessor 사용 지정
	annotationProcessor("jakarta.persistence:jakarta.persistence-api") // java.lang.NoClassDefFoundError(javax.annotation.Entity) 발생 대응
	annotationProcessor("jakarta.annotation:jakarta.annotation-api") // java.lang.NoClassDefFoundError (javax.annotation.Generated) 발생 대응

	implementation ("io.springfox:springfox-boot-starter:3.0.0")
	compileOnly ("org.projectlombok:lombok")
	runtimeOnly ("com.h2database:h2")
	runtimeOnly ("mysql:mysql-connector-java")
	annotationProcessor( "org.projectlombok:lombok")
	testImplementation ("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

/****************************************************************
 * Docker 이미지 빌드를 위한 설정입니다.
 ****************************************************************/

val dockerUrl: String? by project
val dockerImageName: String? by project
val dockerUsername: String? by project
val dockerPassword: String? by project
val dockerTag: String? by project

tasks.bootBuildImage {
	imageName = "${dockerImageName ?: project.name}:${dockerTag ?: project.version}"

	print("[bootBuildImage] 이미지 이름: $imageName")

	if (dockerUrl != null) {
		isPublish = true
		docker {
			publishRegistry {
				url = dockerUrl
				username = dockerUsername
				password = dockerPassword
			}
		}
	}
}

/**
 * Git 리비전 카운트를 가져옵니다.
 * 이 카운트를 버전으로 사용합니다.
 */
fun getRevisionCount(): Int {
	val byteOut = ByteArrayOutputStream()
	project.exec {
		commandLine = "git rev-list --count HEAD".split(" ")
		standardOutput = byteOut
	}
	val output = String(byteOut.toByteArray())

	return Integer.parseInt(output.trim())
}