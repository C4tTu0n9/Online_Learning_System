/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import Model.StarRatingDTO;
import java.util.ArrayList;

/**
 *
 * @author Tuan Anh(Gia Truong)
 */
public class AVGOfRaing {
    public static ArrayList<Double> AvgRatingCourse(ArrayList<StarRatingDTO> listRatingCourse ) {
        ArrayList<Double> list = new ArrayList<>();
        double avg = 0;
        double count = 0;
            if (!listRatingCourse.isEmpty()) {
            for (StarRatingDTO starRatingDTO : listRatingCourse) {
                avg += starRatingDTO.getStar();
                count++;
            }
            list.add(Math.ceil((avg / count) * 2) / 2.0);
            list.add(count);
        } else {
            // Xử lý khi danh sách rỗng
            list.add(0.0); // Hoặc có thể là Double.NaN tùy vào yêu cầu của bạn
            list.add(0.0); // Số lượng đánh giá là 0
        }
        return list;
        
    }
}
