import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    static AtomicInteger list3 = new AtomicInteger(0);
    static AtomicInteger list4 = new AtomicInteger(0);
    static AtomicInteger list5 = new AtomicInteger(0);
    static AtomicInteger flag = new AtomicInteger(0);




    public static void main(String[] args) throws InterruptedException {

        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        new Thread(() -> {

            for (int i = 0; i < texts.length; i++) {

                String clean = texts[i].replaceAll("\\s+", "").toLowerCase();
                StringBuilder plain = new StringBuilder(clean);
                StringBuilder reverse = plain.reverse();


                if (reverse.toString().equals(clean)) {
                    if (reverse.length() == 3) {
                        list3.incrementAndGet();
                    }
                    if (reverse.length() == 4) {
                        list4.incrementAndGet();
                    }
                    if (reverse.length() == 5) {
                        list5.incrementAndGet();
                    }

                }

                if (i == texts.length - 1) {
                    flag.incrementAndGet();
                }

            }

        }).start();


        new Thread(() -> {

            for (int i = 0; i < texts.length; i++) {

                char c = texts[i].charAt(0);
                for (int j = 1; j < texts[i].length(); j++) {
                    if (texts[i].charAt(j) == c) {
                        if (texts[i].length() == 3) {
                            list3.incrementAndGet();
                        }
                        if (texts[i].length() == 4) {
                            list4.incrementAndGet();
                        }
                        if (texts[i].length() == 5) {
                            list5.incrementAndGet();
                        }
                    }

                    if (i == texts.length - 1) {
                        flag.incrementAndGet();
                    }
                }
            }

        }).start();

        new Thread(() -> {

            for (int i = 0; i < texts.length; i++) {

                for (int j = 0; j < texts[i].length(); j++) {
                    if (texts[i].charAt(j) > texts[i].charAt(j + 1)){
                        if (texts[i].length() == 3) {
                            list3.incrementAndGet();
                        }
                        if (texts[i].length() == 4) {
                            list4.incrementAndGet();
                        }
                        if (texts[i].length() == 5) {
                            list5.incrementAndGet();
                        }
                    }
                }

            }
        }).start();



        while (true) {
            if(3 == flag.get()) {
                System.out.println(list3);
                System.out.println(list4);
                System.out.println(list5);
                break;
            }else {
                System.out.println(flag.get());
            }
        }

    }

    public static String generateText (String letters,int length){
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}

