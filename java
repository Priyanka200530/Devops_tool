labsheet10  jenkin with pipeline and groovy
pipeline {
    agent any
    stages {
        stage('Trigger Freestyle Project') {
            steps {
                echo "Triggering Java-Freestyle-Project..."
                script {
                    def buildInfo = build job: 'Junit Testing Project', wait: true

                    echo "Freestyle Build Number: ${buildInfo.number}"
                    echo "Freestyle Build Status: ${buildInfo.result}"

                    if (buildInfo.result != 'SUCCESS') {
                        error "Freestyle project failed!"
                    }                }            }        }
        stage('Result') {
            steps {
                echo "Git Fetch + Build + Test executed successfully!"
                echo "Check Java-Freestyle-Project for JUnit results."
            }        }    }	     }




labsheet12 docker
pipeline {
    agent any
    tools {
        maven "Maven"
    }
    environment {
        IMAGE_NAME = "labsheet9-app"
    }
    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/Priyanka200530/labsheet9.git'
            }
        }
        stage('Build Maven Project') {
            steps {
                dir('junit2') {
                    bat 'mvn clean package -DskipTests'
                }
            }
        }
        stage('Check Docker') {
            steps {
                bat 'docker --version'
            }
        }
        stage('Build Docker Image') {
            steps {
                dir('junit2') {
                    bat 'docker build --no-cache -t labsheet9-app .'
                }
            }
        }
        stage('Run Container') {
            steps {
                bat 'docker rm -f junit2-container || exit 0'
                bat 'docker run -d --name junit2-container -p 9090:8080 labsheet9-app'
            }
        }
    }
    post {
        success {
            echo 'Pipeline executed successfully!'
        }

        failure {
            echo 'Pipeline failed. Check logs!'
        }
    }
}

junit 
package com.pu.junit;

public class AddTwoNumber 
{	public int add(int a, int b) 
{        return a + b ;    }
    public int subtract(int b, int c)
 {        return b - c;    }
public static void main(String[] args)
 {	AddTwoNumber calc = new AddTwoNumber();
   	 int result1 = calc.add(2, 3);
 	   int result2 = calc.subtract(2, 3);
 	   System.out.println("Result: " + result1);
   	 System.out.println("Result: " + result2);
}
}




package com.pu.junit;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
public class AddTwoNumberTest {
    @Test
    void testAdd()
 {
        AddTwoNumber calc = new AddTwoNumber();
        int result = calc.add(2, 3);
        assertEquals(5, result, "Addition should return 5");
    }
    @Test
    void testSubtract() 
{
    	AddTwoNumber calc = new AddTwoNumber();
        int result = calc.subtract(2, 3);
        assertEquals(-1, result, "Subtraction should return -1");
    }
}



pom.xml
<dependency>
          <groupId>org.junit.jupiter</groupId>
          <artifactId>junit-jupiter</artifactId>
          <version>5.10.2</version>
          <scope>test</scope>
      </dependency>


 <build>
      <plugins>
          <plugin>
              <groupId>org.apache.maven.plugins</groupId>
              <artifactId>maven-surefire-plugin</artifactId>
              <version>3.2.5</version>
          </plugin>
      </plugins>
  </build>


github actions

name: Java CI

on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]

jobs:
  build:
    name: Build and Run Java
    runs-on: ubuntu-latest

    steps:
      # Step 1: Checkout repository
      - name: Checkout code
        uses: actions/checkout@v4

      # Step 2: Setup Java
      - name: Setup JDK 17
        uses: actions/setup-java@v3
        with:
          distribution: 'temurin'
          java-version: '17'

      # Step 3: Compile Java code
      - name: Compile Java
        run: javac *.java

      # Step 4: Run main class
      - name: Run Java program
        run: java Add



