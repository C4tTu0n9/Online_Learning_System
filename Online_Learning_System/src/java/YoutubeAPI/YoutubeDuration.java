/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package YoutubeAPI;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

/**
 *
 * @author Tuan Anh(Gia Truong)
 */
public class YoutubeDuration {

    private static final String API_KEY = "AIzaSyA3MF0f8VzHvVsmJoHkyJN2D3AaAHBDgDs";
    private static final String APPLICATION_NAME = "YouTubeDuration";
    private static final long MAX_RESULTS = 50L;

    public static String extractVideoId(String url) {
        // Tách phần sau cùng của URL sử dụng dấu "/"
        String[] parts = url.split("/");
        // Phần cuối cùng sẽ chứa ID video và có thể có query string
        String idWithQuery = parts[parts.length - 1];

        // Nếu có query string, tách phần ID trước dấu ?
        if (idWithQuery.contains("?")) {
            idWithQuery = idWithQuery.split("\\?")[0];
        }

        return idWithQuery;
    }

    public static void main(String[] args) {
        String url = "https://www.youtube.com/embed/niz7Gg8uB-k?si=bnm5v5MPvW2tE1ls";
        String videoId = extractVideoId(url);
        System.out.println("Video ID: " + videoId);

        // Bây giờ bạn có thể gọi hàm getVideoDuration với videoId
        long duration = getVideoDuration(videoId);
        System.out.println("Video Duration: " + duration + " seconds");
        System.out.println(convertToMinutesAndSeconds(duration));
    }

    public static long getVideoDuration(String video) {
        long videoDuration = 0;
        try {
            YouTube youtubeService = getService();

            // Lấy thời lượng của video cụ thể
            String videoId = video;
            videoDuration = getVideoDuration(youtubeService, videoId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return videoDuration;
    }

    public static YouTube getService() throws GeneralSecurityException, IOException {
        return new YouTube.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), null)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static long getVideoDuration(YouTube youtubeService, String videoId) throws IOException {
        YouTube.Videos.List request = youtubeService.videos()
                .list("contentDetails")
                .setId(videoId)
                .setKey(API_KEY);

        VideoListResponse response = request.execute();
        List<Video> videos = response.getItems();
        if (videos.isEmpty()) {
            throw new IllegalArgumentException("Video not found: " + videoId);
        }

        Video video = videos.get(0);
        String durationString = video.getContentDetails().getDuration();
        return parseDuration(durationString);
    }

    public static long getPlaylistDuration(YouTube youtubeService, String playlistId) throws IOException {
        long totalDuration = 0;
        String nextPageToken = null;

        do {
            YouTube.PlaylistItems.List request = youtubeService.playlistItems()
                    .list("contentDetails")
                    .setPlaylistId(playlistId)
                    .setMaxResults(MAX_RESULTS)
                    .setPageToken(nextPageToken)
                    .setKey(API_KEY);

            PlaylistItemListResponse response = request.execute();
            List<String> videoIds = response.getItems().stream()
                    .map(item -> item.getContentDetails().getVideoId())
                    .toList();

            YouTube.Videos.List videoRequest = youtubeService.videos()
                    .list("contentDetails")
                    .setId(String.join(",", videoIds))
                    .setKey(API_KEY);

            VideoListResponse videoResponse = videoRequest.execute();
            for (Video video : videoResponse.getItems()) {
                String duration = video.getContentDetails().getDuration();
                totalDuration += parseDuration(duration);
            }

            nextPageToken = response.getNextPageToken();
        } while (nextPageToken != null);

        return totalDuration;
    }

    public static long parseDuration(String duration) {
        java.time.Duration d = java.time.Duration.parse(duration);
        return d.getSeconds();
    }

    public static String SumConvertToHoursAndMinutesLesson(long totalSeconds) {
        // Tính số giờ
        long hours = totalSeconds / 3600;
        // Tính số phút
        long minutes = (totalSeconds % 3600) / 60;
        // Tính số giây còn lại
        long seconds = totalSeconds % 60;
        String strHours = (hours < 10) ? "0" + hours : String.valueOf(hours);
        String strMinutes = (minutes < 10) ? "0" + minutes : String.valueOf(minutes);
        String strSecconds = (seconds < 10) ? "0" + seconds : String.valueOf(seconds);
        return strHours + " hrs " + strMinutes + " min "+ strSecconds +"sec";
    }
    
        public static String SumConvertToMinutesAndSecondsLesson(long totalSeconds) {
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        String strMinutes = (minutes < 10) ? "0" + minutes : String.valueOf(minutes);
        String strSeconds = (seconds < 10) ? "0" + seconds : String.valueOf(seconds);
        return strMinutes + " min " +" "+ strSeconds + "sec";
    }
    

    public static String convertToMinutesAndSeconds(long totalSeconds) {
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        String strMinutes = (minutes < 10) ? "0" + minutes : String.valueOf(minutes);
        String strSeconds = (seconds < 10) ? "0" + seconds : String.valueOf(seconds);
        return strMinutes + ":" + strSeconds;
    }

}
