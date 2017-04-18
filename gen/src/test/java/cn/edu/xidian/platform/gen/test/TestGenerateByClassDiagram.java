package cn.edu.xidian.platform.gen.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Stack;

import cn.edu.xidian.platform.commons.utils.FileUtils;

/**
 * Created by 费玥 on 2017-4-18.
 */
public class TestGenerateByClassDiagram {

    @Test
    public void testParse() throws IOException {
        String jsonStr = FileUtils.readFileToString(new File("D:\\agile-develop-platform\\gen\\src\\main\\resources\\templates\\gen\\1.mdj"));

        JSONObject jsonObject = JSON.parseObject(jsonStr);
        JSONArray jsonArray = jsonObject.getJSONArray("ownedElements");
        Stack<JSONArray> stack = new Stack<>();
        stack.push(jsonArray);
        while (!stack.isEmpty()) {
            jsonArray = stack.pop();
            for (Object var1 : jsonArray) {
                JSONObject var2 = (JSONObject) var1;
                String type = var2.getString("_type");
                if (type.equals("UMLClass")) {
                    System.out.println(var2.getString("name"));
                }
                if (var2.containsKey("ownedElements")) {
                    JSONArray var3 = var2.getJSONArray("ownedElements");
                    stack.push(var3);
                }
            }

        }
    }
}
