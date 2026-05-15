/**
 * 个人代码签名：黄昭展
 * AI Coding 工具：Manus AI (Version: 2026-05-08)
 */

package com.example.project1;

import java.time.LocalDate;
import java.time.Period;

    public class PersonalInfo {
        private String id;        // 身份证号码
        private String birthday;  // 出生日期
        private int age;          // 年龄
        private String sex;       // 性别

        public PersonalInfo(String id) {
            this.id = id;
            this.birthday = getBirthday(id);
            this.age = getAge(id);
            this.sex = getSex(id);
        }

        public String getId() {
            return id;
        }

        /**
         * 从身份证中提取出生日期（第7位到第14位）
         */
        public String getBirthday(String id) {
            if (id == null || id.length() < 14) {
                return "Invalid ID";
            }
            return id.substring(6, 14);
        }

        /**
         * 根据当前日期计算年龄
         */
        public int getAge(String id) {
            if (id == null || id.length() < 14) {
                return -1;
            }
            try {
                String birthYear = id.substring(6, 10);
                String birthMonth = id.substring(10, 12);
                String birthDay = id.substring(12, 14);

                LocalDate birthDate = LocalDate.of(Integer.parseInt(birthYear), Integer.parseInt(birthMonth), Integer.parseInt(birthDay));
                LocalDate currentDate = LocalDate.now();

                return Period.between(birthDate, currentDate).getYears();
            } catch (Exception e) {
                return -1;
            }
        }

        /**
         * 根据身份证第17位判断性别（奇数为男，偶数为女）
         */
        public String getSex(String id) {
            if (id == null || id.length() < 17) {
                return "Invalid ID";
            }
            int sexDigit = Integer.parseInt(id.substring(16, 17));
            return sexDigit % 2 == 1 ? "男" : "女";
        }

        /**
         * 打印个人自我介绍及解析后的信息
         */
        public void printInfo() {
            System.out.println("大家好，我叫黄昭展，今年" + age + "岁，性别：" + sex + "，目前就读于中南民族大学计算机学院软件工程专业，主要技能是：SpringBoot,Mybatis(Plus),JPA,JDBC,MySQL,Maven等,喜欢玩电脑。");
            System.out.println("身份证号码: " + id);
            System.out.println("出生年月日: " + birthday);
            System.out.println("年龄: " + age);
            System.out.println("性别: " + sex);
        }
    }
