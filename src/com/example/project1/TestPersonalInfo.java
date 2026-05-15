package com.example.project1;

import com.example.project1.PersonalInfo;

import java.util.Scanner;

public class TestPersonalInfo {

    public static void main(String[] args) {

        PersonalInfo personalInfo = new PersonalInfo();

        Scanner sc = new Scanner(System.in);

        System.out.println("---请输入您的身份证号码---");

        String id = sc.next();

        personalInfo.setId(id);

        System.out.println(personalInfo.toString());

    }
}
