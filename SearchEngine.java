import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    int num = 0;
    ArrayList<String> list = new ArrayList<String>();
    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            for(int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i));
                num += 1;
            }
            return String.format("Numbers of string: %d", num);
        } else if (url.getPath().contains("/search")) {
            String[] parameters = url.getQuery().split("=");
            for(int i = 0; i < list.size(); i++) {
                if (list.get(i).contains(parameters[1])) {
                    System.out.println(list.get(i));
                }
            }
            return String.format("Finished Searching");
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("String")) {
                    list.add(parameters[1]);
                    return String.format("%s is added to the list! There are %d Strings in the list", parameters[1], num);
                }
            }
            return "404 Not Found!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}