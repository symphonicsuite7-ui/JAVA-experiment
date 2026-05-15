package com.example.project1;

import java.time.LocalDate;
import java.util.Date;

public class PersonalInfo {

    private String id;

    private LocalDate birthday;

    public String getId() {
        return id;
    }


    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String isSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    private  int age;

    private String sex;

    public void setId(String id) {
        this.id = id;

        String year = id.substring(6, 10);

        String month = id.substring(10, 12);

        String  day = id.substring(12, 14);

        this.birthday = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));

        LocalDate now = LocalDate.now();

        this.age = now.getYear() - Integer.parseInt(year);

        String sexCode = id.substring(16, 17);
        if (Integer.parseInt(sexCode) % 2 == 0) {
            this.sex = "女";
        } else {
            this.sex = "男";
        }
    }

    @Override
    public String toString() {
        return "PersonalInfo{" +
                "id='" + id + '\'' +
                ", birthday=" + birthday +
                ", age=" + age +
                ", sex=" + sex +
                '}';
    }

}
