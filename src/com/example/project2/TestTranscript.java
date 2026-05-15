package com.example.project2;
import java.util.*;

public class TestTranscript {

    public static void main(String[] args) {
        Transcript transcript = new Transcript();

        System.out.println("请输入课程数：");

        Scanner input = new Scanner(System.in);

        transcript.setCourseNumber(input.nextInt());
    }

}