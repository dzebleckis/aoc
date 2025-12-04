import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;

public class Utils {

    public static List<String> getLines(String file) {
        try(var lines = Files.lines(Paths.get(file))) {
            return lines.filter(Predicate.not(String::isBlank)).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
