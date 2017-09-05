/**
 * Author: Andrew Laing
 * Email:  parisianconnections@gmail.com
 * Date:   26/12/2016.
 */
import java.util.HashMap;
import java.util.List;
import java.io.Serializable;

// TODO: 22/12/2016 Add a copy constructor
public class HashMap1D extends HashMap implements Serializable
{
    private HashMap<String, Integer> map;

    /**
     * Constructor
     */
    public HashMap1D()
    {
        map = new HashMap<String, Integer>();
    }


    /**
     * The method addElement adds an element to the HashMap
     * @param key1 The key of the element
     * @param value The value attached to the key
     */
    public void addElement(String key1, Integer value)
    {
        // If the key does not exist create it
        if (!map.containsKey(key1)) {
            map.put(key1, value);
        }
    }


    /**
     * The method getElement returns the value attached to the key
     * passed to the method. If the key does not exist it adds a new
     * element for the key with a value of 0 to the HashMap.
     * @param key1 The key to get the value for.
     * @return The value attached to the key.
     */
    public Integer getElement(String key1)
    {
        if (!map.containsKey(key1)) {
            map.put(key1, 0);
        }
        return map.get(key1);
    }


    /**
     * The method augment increases the value attached to the key by 1.
     * @param key1 The key to augment the value for.
     */
    public void augment(String key1)
    {
        map.put(key1, (getElement(key1) + 1));
    }


    /**
     * The method getKeys adds all of the keys within the HashMap
     * to the List passed to it.
     * @param keys A List to add all of the keys in the HashMap to.
     */
    public void getKeys(List<String> keys)
    {
        for (String key : map.keySet()) {
            keys.add(key);
        }
    }


    /**
     * The method getValue returns the value attached to the key
     * passed to the method. If the key does not exist it returns 0.
     * @param key1 The key to get the value for.
     * @return The value attached to the key.
     */
    public Integer getValue(String key1)
    {
        if (!map.containsKey(key1)) {
            return 0;
        }
        return map.get(key1);
    }


    /**
     * The method getSumOfValues gets the sum of all of the values
     * stored within the HashMap.
     * @return The sum of all of the values stored within the HashMap.
     */
    public int getSumOfValues()
    {
        int sum = 0;
        for (String key : map.keySet()) {
            sum += map.get(key);
        }

        return sum;
    }

}

