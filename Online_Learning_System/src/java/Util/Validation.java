/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;

import java.io.IOException;

import java.io.InputStream;

/**
 *
 * @author tuong
 */
public class Validation {

    public Validation() {
    }

    public static boolean checkStringArray(String[] str) {
        if (str != null) {
            for (int i = 0; i < str.length; i++) {
                str[i] = str[i].trim();
                if (str[i] == null || str[i].isBlank()) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean checkString(String str) {
        str = str.trim();
        if (str == null || str.isBlank()) {
            return false;
        }
        return true;
    }
    public static boolean checkDesciptionCourse(String str) {
        str = str.trim();
        if (str == null || str.isBlank()) {
            return false;
        }
        if(str.length() > 2000){
            return false;
        }
        return true;
    }
    
    public static boolean checkInt(String str) {
        str = str.trim();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) < '0' || str.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }

    public static boolean checkName(String name) {
        name = name.trim();
        if (name == null || name.isBlank()) {
            return false;
        }
        String[] name_no_space = name.split(" ");
        //String regex = "^[A-Za-z][A-Za-z ]{1,100}$";
        String regex = "^[A-Za-z]{1,100}$";
        for (String name_valid : name_no_space) {
            if (!name_valid.equals("")) {
                if (!name_valid.matches(regex)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static String validName(String name) {
        name = name.trim();
        if (name == null || name.isBlank()) {
            return "";
        }
        String[] name_no_space = name.split(" ");
        name = "";
        for (String name_valid : name_no_space) {
            if (!name_valid.equals("")) {
                name+=name_valid+" ";
            }
        }
        return name.trim();
    }

    public static boolean checkEmail(String email) {
        email = email.trim();
        if (email == null || email.isBlank()) {
            return false;
        }
        String regex = "[a-zA-Z0-9]+@([a-zA-Z]+.){1,2}[a-zA-Z]+";
        return email.matches(regex);
    }

    public static String inputFile(HttpServletRequest request, Part file_image, String folder) {
        String file_name_random = insertImageIntoTomcatServer(request, folder, file_image);
        return insertImageIntoProject(folder, file_image, file_name_random);
    }

    private static String createFileNameRandom(Part file_image) {

        String image_file_name = file_image.getSubmittedFileName();
        String[] image_file_name_split = image_file_name.split("\\.");

        image_file_name_split[0] = image_file_name_split[0] + (int) (Math.random() * 10000);
        image_file_name = image_file_name_split[0] + "." + image_file_name_split[1];

        return image_file_name;
    }

    public static String insertImageIntoTomcatServer(HttpServletRequest request, String folder, Part file_image_course) {
        String image_file_name = createFileNameRandom(file_image_course);
        //tra ve folder khi not_build
        String upload_path_to_server = request.getServletContext().getRealPath(folder).replaceFirst("build", "") + File.separator + image_file_name;
        try {
            FileOutputStream fos = new FileOutputStream(upload_path_to_server);

            InputStream is = file_image_course.getInputStream();

            byte[] data = new byte[is.available()];
            is.read(data);
            fos.write(data);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return image_file_name;
    }

    public static String insertImageIntoProject(String folder, Part file_image_course, String file_name_random) {
        //tra ve path folder khi not_build
        String upload_path_to_project = ServerPath.getPathImage() + File.separator + folder + "/" + file_name_random;

        try {
            FileOutputStream fos = new FileOutputStream(upload_path_to_project);

            InputStream is = file_image_course.getInputStream();

            byte[] data = new byte[is.available()];
            is.read(data);
            fos.write(data);
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return folder + "/" + file_name_random;
    }

//    public static boolean verifyEmail(String email) {
//        email = email.trim();
//        if (email == null || email.equals("")) {
//            return false;
//        }
//        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
//            return false;
//        }
//        return true;
//    }
//
//    private boolean verifyName(String name) {
//        name = name.trim();
//        if (name == null || name.equals("")) {
//            return false;
//        }
//        if (!name.matches("[a-zA-Z]*")) {
//            return false;
//        }
//        return true;
//    }
}
