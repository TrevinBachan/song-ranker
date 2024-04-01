import java.io.*;
import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class SongRanker {
    private List<Song> songs;
    private Map<String, Integer> songIndex;

    public SongRanker(List<String> songTitles) {
        songs = new ArrayList<>();
        songIndex = new HashMap<>();

        // Initialize songs
        for (String title : songTitles) {
            Song song = new Song(title);
            songs.add(song);
            songIndex.put(title, songs.size() - 1);
        }
    }

    public void compareSongs(String song1, String song2) {
        int index1 = songIndex.get(song1);
        int index2 = songIndex.get(song2);

        songs.get(index1).incrementScore();
    }

    public String getWinner() {
        while (songs.size() > 1) {
            List<String> roundWinners = new ArrayList<>();

            for (int i = 0; i < songs.size(); i += 2) {
                Song song1 = songs.get(i);
                Song song2 = songs.get(i + 1);

                // Simulate user choice (for demo purposes)
                Random random = new Random();
                int choice = random.nextInt(2);

                if (choice == 0) {
                    song1.incrementScore();
                    roundWinners.add(song1.getTitle());
                } else {
                    song2.incrementScore();
                    roundWinners.add(song2.getTitle());
                }
            }

            // Remove losers
            songs.clear();
            for (String winner : roundWinners) {
                songs.add(new Song(winner));
                songIndex.put(winner, songs.size() - 1);
            }
        }

        return songs.get(0).getTitle();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the URL of the artist's Genius page:");
        String url = scanner.nextLine();

        List<String> songTitles = scrapeSongsFromGenius(url);

        SongRanker ranker = new SongRanker(songTitles);
        String winner = ranker.getWinner();
        System.out.println("The winner is: " + winner);
    }

    private static List<String> scrapeSongsFromGenius(String url) {
        List<String> songTitles = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url).get();
            Elements songElements = doc.select("h3");
            for (Element element : songElements) {
                String title = element.text();
                songTitles.add(title);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return songTitles;
    }
}
