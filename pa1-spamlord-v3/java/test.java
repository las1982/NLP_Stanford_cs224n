import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {
    public static void main(String[] args) {

        String str = "<address>Apache/2.2.4 (Fedora) Server at cs.stanford.edu Port 80</address>";
        String
                dotSeparator = "((\\.)|(\\sdot\\s)|(\\sdt\\s)|(;))",
                name = "(&lt;)?(?<name>(\\w+" + dotSeparator + "?){1,2})",
                host = "(?<host>(\\w+" + dotSeparator + "+){1,2})",
                atSign = "((@)" + "|" + "(\\s@\\s)" + "|" + "(\\(at\\))" + "|" + "(\\sat\\s))",
                domain = "(?<domain>\\w{2,3})";

        Pattern eMailPt = Pattern.compile(name + "(?!Server)(?<!Server)" + atSign + host + domain);
        Matcher m = eMailPt.matcher(str);
        while (m.find()) {
            System.out.println(m.group());
        }
    }
}