original pom.xml

<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		 xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<groupId>com.michaelw</groupId>
	<artifactId>spring-boot-spark</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>spring-boot-spark</name>
	<description>Demo project created by Michael Wang</description>

	<properties>
		<!-- The main class to start by executing java -jar -->
		<start-class>com.michaelw.Application</start-class>
		<java.version>1.8</java.version>
		<spark.version>2.0.2</spark.version>
		<scala.binary.version>2.10</scala.binary.version>
		<scala.project.version>2.0.0</scala.project.version>

	</properties>


	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>1.4.2.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>

	<dependencies>
		<dependency>
			<groupId>com.fasterxml.jackson.core</groupId>
			<artifactId>jackson-databind</artifactId>
			<version>2.6.5</version>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<!-- tag::actuator[] -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>
		<!-- end::actuator[] -->
		<!-- tag::tests[] -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
		<!-- end::tests[] -->

		<dependency>
			<groupId>io.springfox</groupId>
			<artifactId>springfox-swagger-ui</artifactId>
			<version>2.2.2</version>
			<scope>compile</scope>
		</dependency>

		<dependency>
			<groupId>io.springfox</groupId>
			<artifactId>springfox-swagger2</artifactId>
			<version>2.2.2</version>
			<scope>compile</scope>
		</dependency>


		<!-- hadoop: no need hadoop-core as most comming from hadoop-client-->
		<dependency>
			<groupId>org.apache.hadoop</groupId>
			<artifactId>hadoop-common</artifactId>
			<version>2.7.3</version>
		</dependency>
		<dependency>
			<groupId>org.apache.hadoop</groupId>
			<artifactId>hadoop-client</artifactId>
			<version>2.7.3</version>
		</dependency>

		<!-- Spark -->
		<dependency>
			<groupId>org.spark-project.spark</groupId>
			<artifactId>unused</artifactId>
			<version>1.0.0</version>
			<scope>provided</scope>
		</dependency>
		<dependency>
			<groupId>org.apache.spark</groupId>
			<artifactId>spark-sql_${scala.binary.version}</artifactId>
			<version>${scala.project.version}</version>
		</dependency>
		<dependency>
			<groupId>org.apache.spark</groupId>
			<artifactId>spark-core_${scala.binary.version}</artifactId>
			<version>${scala.project.version}</version>

		</dependency>
		<dependency>
			<groupId>org.apache.spark</groupId>
			<artifactId>spark-streaming_${scala.binary.version}</artifactId>
			<version>${scala.project.version}</version>

		</dependency>
		<dependency>
			<groupId>org.apache.spark</groupId>
			<artifactId>spark-hive_${scala.binary.version}</artifactId>
			<version>${scala.project.version}</version>

		</dependency>
		<dependency>
			<groupId>org.apache.spark</groupId>
			<artifactId>spark-graphx_${scala.binary.version}</artifactId>
			<version>${scala.project.version}</version>

		</dependency>
		<dependency>
			<groupId>org.apache.spark</groupId>
			<artifactId>spark-streaming-flume_${scala.binary.version}</artifactId>
			<version>${scala.project.version}</version>

		</dependency>
		<dependency>
			<groupId>org.apache.spark</groupId>
			<artifactId>spark-streaming-kafka-0-8_${scala.binary.version}</artifactId>
			<version>${scala.project.version}</version>

		</dependency>

	</dependencies>


	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
			<plugin>
				<artifactId>maven-failsafe-plugin</artifactId>
				<executions>
					<execution>
						<goals>
							<goal>integration-test</goal>
							<goal>verify</goal>
						</goals>
					</execution>
				</executions>
			</plugin>
            <!-- Maven Assembly Plugin -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-assembly-plugin</artifactId>
                <version>2.4.1</version>
                <configuration>
                    <!-- get all project dependencies -->
                    <descriptorRefs>
                        <descriptorRef>jar-with-dependencies</descriptorRef>
                    </descriptorRefs>
                    <!-- MainClass in mainfest make a executable jar -->
                    <archive>
                        <manifest>
                            <mainClass>com.michaelw.spark.JavaWordCount</mainClass>
                        </manifest>
                    </archive>

                </configuration>
                <executions>
                    <execution>
                        <id>make-assembly</id>
                        <!-- bind to the packaging phase -->
                        <phase>package</phase>
                        <goals>
                            <goal>single</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
		</plugins>
	</build>

</project>
