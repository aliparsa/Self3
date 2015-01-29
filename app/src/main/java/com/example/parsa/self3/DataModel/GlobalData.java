package com.example.parsa.self3.DataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ashkan on 1/28/2015.
 */
public class GlobalData {

    static Personnel personnel;

    private GlobalData(){
        //no instance
    }

    public static Personnel getPersonnel() {
        return personnel;
    }

    public static void setPersonnel(Personnel personnel2) {
        personnel = personnel2;
    }
}
