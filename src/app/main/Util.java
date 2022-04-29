package app.main;

public class Util {

    /**
     * Utility of the program that help calculate percentage of the value
     * @param value that in total
     * @param total of values
     * @return percent integer
     */
    public static int percent(int value, int total){
        return (int) (((double) value / (double) total)*100);
    }
}
