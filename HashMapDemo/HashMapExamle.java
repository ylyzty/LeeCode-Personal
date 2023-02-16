package HashMapDemo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HashMapExamle {
    public static void main(String[] args) {
        // 使用默认构造函数, 默认容量为16, 载入因子为 0.75
        HashMap<String, String> map = new HashMap<>();

        for (int i = 0; i < 20; i++) {
            // 添加第13个元素时触发扩容：16 * 0.75 = 12
            map.put(i + "", "Example " + i);
        }

        // 支持null作为key和value
        map.put(null, "Null key");
        map.put("Null value", null);

        map.remove("0");

        // get获取指定键对应的值, 若不存在则返回null
        // getOrDefault获取指定键对应的值, 若不存在在则返回默认值
        System.out.println(map.get("2"));
        System.out.println(map.get("20") + " " + map.getOrDefault("20", "Default Value"));

        System.out.println(map.containsKey("2") + " " + map.containsKey("20"));
        System.out.println(map.containsValue("Example 2") + " " + map.containsKey("Example 20"));

        // entrySet()遍历哈希表
        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        
        // keySet()遍历哈希表
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            System.out.println(key);
        }

        // valueSet()获取所有value值
        Collection<String> values = map.values();
        for (String value : values) {
            System.out.println(value);
        }


    }
}
