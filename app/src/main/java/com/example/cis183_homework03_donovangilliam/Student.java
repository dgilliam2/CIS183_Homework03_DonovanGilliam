package com.example.cis183_homework03_donovangilliam;

public class Student
{
    private String Username;
    private String Fname;
    private String Lname;
    private String Email;
    private int Age;
    private float GPA;
    private int MajorId;

    public Student()
    {

    }

    public Student(String u, String f, String l, String e, int a, float g, int m)
    {
        Username = u;
        Fname = f;
        Lname = l;
        Email = e;
        Age = a;
        GPA = g;
        MajorId = m;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getFname() {
        return Fname;
    }

    public void setFname(String fname) {
        Fname = fname;
    }

    public String getLname() {
        return Lname;
    }

    public void setLname(String lname) {
        Lname = lname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public float getGPA() {
        return GPA;
    }

    public void setGPA(float GPA) {
        this.GPA = GPA;
    }

    public int getMajorId() {
        return MajorId;
    }

    public void setMajorId(int majorId) {
        MajorId = majorId;
    }
}
