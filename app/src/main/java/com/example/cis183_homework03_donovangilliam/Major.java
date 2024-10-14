package com.example.cis183_homework03_donovangilliam;

public class Major
{
    private int MajorId;
    private String MajorName;
    private String MajorPrefix;

    public Major()
    {

    }

    public Major(int mi, String mn, String mp)
    {
        MajorId = mi;
        MajorName = mn;
        MajorPrefix = mp;
    }

    public int getMajorId() {
        return MajorId;
    }

    public void setMajorId(int majorId) {
        MajorId = majorId;
    }

    public String getMajorName() {
        return MajorName;
    }

    public void setMajorName(String majorName) {
        MajorName = majorName;
    }

    public String getMajorPrefix() {
        return MajorPrefix;
    }

    public void setMajorPrefix(String majorPrefix) {
        MajorPrefix = majorPrefix;
    }
}
