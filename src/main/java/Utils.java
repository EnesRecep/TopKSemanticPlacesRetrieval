import Model.PlaceLoosenessPair;
import Model.WordLoosenessPair;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.FSTObjectOutput;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Utils {

    public static double distanceInKM(double lat1, double lon1, double lat2, double lon2) {

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;

        return (dist);
    }
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    public static boolean containsIgnoreCase(String str, String searchStr)     {
        if(str == null || searchStr == null) return false;

        final int length = searchStr.length();
        if (length == 0)
            return true;

        for (int i = str.length() - length; i >= 0; i--) {
            if (str.regionMatches(true, i, searchStr, 0, length))
                return true;
        }
        return false;
    }

    public static HashMap<Integer, Vertex> ReadMap( InputStream stream ) throws IOException, ClassNotFoundException
    {
        FSTObjectInput in = null;
        try {
            in = new FSTObjectInput(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HashMap<Integer, Vertex> result = (HashMap<Integer, Vertex>)in.readObject();
        in.close(); // required !
        return result;
    }

    public static void WriteMap(OutputStream stream, HashMap<Integer, Vertex> toWrite ) throws IOException
    {
        FSTObjectOutput out = new FSTObjectOutput(stream);
        out.writeObject( toWrite );
        out.close(); // required !
    }

    public static void writeHashMap(Map map, String name){
        Kryo kryo = new Kryo();
        kryo.register(HashMap.class);
        kryo.register(Vertex.class);
        kryo.register(Location.class);
        kryo.register(String.class);
        kryo.register(Integer.class);
        kryo.register(ArrayList.class);
        kryo.register(PlaceLoosenessPair.class);
        kryo.register(WordLoosenessPair.class);
        kryo.register(HashSet.class);


        Output output = null;
        try {
            output = new Output(new FileOutputStream(name + ".bin"));
            kryo.writeObject(output, map);
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
    public static Map readHashMap(String name){
        Kryo kryo = new Kryo();
        kryo.register(HashMap.class);
        kryo.register(Vertex.class);
        kryo.register(Location.class);
        kryo.register(String.class);
        kryo.register(Integer.class);
        kryo.register(ArrayList.class);
        kryo.register(PlaceLoosenessPair.class);
        kryo.register(WordLoosenessPair.class);
        kryo.register(HashSet.class);


        Input input = null;
        Map map = null;
        try {
            input = new Input(new FileInputStream(name + ".bin"));
            map = kryo.readObject(input, HashMap.class);
            input.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return map;
    }
}
