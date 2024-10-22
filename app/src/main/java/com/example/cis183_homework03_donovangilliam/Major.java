package com.example.cis183_homework03_donovangilliam;

public class Major
{
    //I never use MajorId when making a Major object, but I will keep it here anyway
    //There may be a use for it if this program was expanded
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
