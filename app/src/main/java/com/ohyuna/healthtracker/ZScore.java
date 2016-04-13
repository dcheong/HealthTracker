package com.ohyuna.healthtracker;

import android.content.Context;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Douglas on 3/29/2016.
 */
public class ZScore {
    private List bheightAgeRaw1;
    private List bheightAgeRaw2;
    private List gheightAgeRaw1;
    private List gheightAgeRaw2;

    private List bweightAgeRaw;
    private List gweightAgeRaw;

    private List bweightHeightRaw;
    private List gweightHeightRaw;

    private double[][] bheightAge1;
    private double[][] bheightAge2;
    private double[][] gheightAge1;
    private double[][] gheightAge2;

    private double[][] bweightAge;
    private double[][] gweightAge;

    private double[][] bweightHeight;
    private double[][] gweightHeight;

    public ZScore(Context context) {
        load(context);
    }
    public double getHA(double height, int age, boolean gender) {
        if (!gender) {
            if (age <= 24 && age >= 0) {
                int row = age;
                System.out.println("age" + age);
                System.out.println(bheightAge1[row][1]);
                System.out.println(bheightAge2[row][2]);
                return (bheightAge1[row][1] - height)/bheightAge1[row][2];
            } else if (age <= 60 && age >= 25) {
                int row = age - 24;
                return (bheightAge2[row][1] - height)/bheightAge2[row][2];
            }
            return -1;
        } else {
            if (age <= 24 && age >= 0) {
                int row = age;
                return (gheightAge1[row][1] - height)/gheightAge1[row][2];
            } else if (age <= 60 && age >= 25) {
                int row = age - 24;
                return (gheightAge2[row][1] - height)/gheightAge2[row][2];
            }
            return -1;
        }
    }
    public double getWA(double weight, int age, boolean gender) {
        if (!gender) {
            if (age <= 1856 && age >= 0) {
                int row = age;
                return bweightAge[row][1]-weight;
            } else {
                return -1;
            }
        } else {
            if (age <= 1856 && age >= 0) {
                int row = age;
                return gweightAge[row][1]-weight;
            } else {
                return -1;
            }
        }
    }
    public double getWH(double weight, double height, boolean gender) {
        if (!gender) {
            if (height <= 120.0 && height >= 65.0) {
                int row = (int)height*10;
                row -= 650;
                if (row < 0) {row=0;}
                if (row > 55) {row=55;}
                System.out.println(bweightHeight[row][1]);
                return bweightHeight[row][1]-weight;
            } else {
                return -1;
            }
        } else {
            if (height <= 120.0 && height >= 65.0) {
                int row = (int)height*10;
                row -= 650;
                if (row < 0) {row=0;}
                if (row > 55) {row=55;}
                return gweightHeight[row][1]-weight;
            } else {
                return -1;
            }
        }
    }
    public void load(Context context) {
        InputStream is1 = context.getResources().openRawResource(R.raw.bha_birth2);
        CSVFile bhabirth2 = new CSVFile(is1);
        bheightAgeRaw1 = bhabirth2.read();

        InputStream is2 = context.getResources().openRawResource(R.raw.bha_25);
        CSVFile bha25 = new CSVFile(is2);
        bheightAgeRaw2 = bha25.read();

        InputStream is3 = context.getResources().openRawResource(R.raw.gha_birth2);
        CSVFile ghabirth2 = new CSVFile(is3);
        gheightAgeRaw1 = ghabirth2.read();

        InputStream is4 = context.getResources().openRawResource(R.raw.gha_25);
        CSVFile gha25 = new CSVFile(is4);
        gheightAgeRaw2 = gha25.read();

        InputStream is5 = context.getResources().openRawResource(R.raw.bwa);
        CSVFile bwa = new CSVFile(is5);
        bweightAgeRaw = bwa.read();

        InputStream is6 = context.getResources().openRawResource(R.raw.gwa);
        CSVFile gwa = new CSVFile(is6);
        gweightAgeRaw = gwa.read();

        InputStream is7 = context.getResources().openRawResource(R.raw.bwh);
        CSVFile bwh = new CSVFile(is7);
        bweightHeightRaw = bwh.read();

        InputStream is8 = context.getResources().openRawResource(R.raw.gwh);
        CSVFile gwh = new CSVFile(is8);
        gweightHeightRaw = gwh.read();
        bheightAge1 = new double[bheightAgeRaw1.size()-1][3];
        System.out.println(bheightAgeRaw1.size());
        for (int i = 1; i < bheightAgeRaw1.size()-1; i++) {
            String[] row = (String[]) (bheightAgeRaw1.get(i));
            bheightAge1[i-1][0] = Double.parseDouble(row[0]);
            bheightAge1[i-1][1] = Double.parseDouble(row[2]);
            bheightAge1[i-1][2] = Double.parseDouble(row[4]);
        }
        System.out.println(bheightAgeRaw2.size());
        bheightAge2 = new double[bheightAgeRaw2.size()-1][3];
        for (int i = 1; i < bheightAgeRaw2.size()-1; i++) {
            String[] row = (String[]) (bheightAgeRaw2.get(i));
            bheightAge2[i-1][0] = Double.parseDouble(row[0]);
            bheightAge2[i-1][1] = Double.parseDouble(row[2]);
            bheightAge2[i-1][2] = Double.parseDouble(row[4]);
        }

        gheightAge1 = new double[gheightAgeRaw1.size()-1][3];
        for (int i = 1; i < gheightAgeRaw1.size()-1; i++) {
            String[] row = (String[]) (gheightAgeRaw1.get(i));
            gheightAge1[i-1][0] = Double.parseDouble(row[0]);
            gheightAge1[i-1][1] = Double.parseDouble(row[2]);
            gheightAge1[i-1][2] = Double.parseDouble(row[4]);
        }

        gheightAge2 = new double[gheightAgeRaw2.size()-1][3];
        for (int i = 1; i < gheightAgeRaw2.size()-1; i++) {
            String[] row = (String[]) (gheightAgeRaw2.get(i));
            gheightAge2[i-1][0] = Double.parseDouble(row[0]);
            gheightAge2[i-1][1] = Double.parseDouble(row[2]);
            gheightAge2[i-1][2] = Double.parseDouble(row[4]);
        }

        bweightAge = new double[bweightAgeRaw.size()-1][2];
        for (int i = 1; i < bweightAgeRaw.size()-1; i++) {
            String[] row = (String[]) (bweightAgeRaw.get(i));
            bweightAge[i-1][0] = Double.parseDouble(row[0]);
            bweightAge[i-1][1] = Double.parseDouble(row[5]);
        }

        gweightAge = new double[gweightAgeRaw.size()-1][2];
        for (int i = 1; i < gweightAgeRaw.size()-1; i++) {
            String[] row = (String[]) (gweightAgeRaw.get(i));
            gweightAge[i-1][0] = Double.parseDouble(row[0]);
            gweightAge[i-1][1] = Double.parseDouble(row[5]);
        }

        bweightHeight = new double[bweightHeightRaw.size()-1][2];
        for (int i = 1; i < bweightHeightRaw.size()-1; i++) {
            String[] row = (String[]) (bweightHeightRaw.get(i));
            bweightHeight[i-1][0] = Double.parseDouble(row[0]);
            bweightHeight[i-1][1] = Double.parseDouble(row[5]);
        }

        gweightHeight = new double[gweightHeightRaw.size()-1][2];
        for (int i = 1; i < gweightHeightRaw.size()-1; i++) {
            String[] row = (String[]) (gweightHeightRaw.get(i));
            gweightHeight[i-1][0] = Double.parseDouble(row[0]);
            gweightHeight[i-1][1] = Double.parseDouble(row[5]);
        }

    }
}
