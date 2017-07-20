package pkuyouth;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by WangJian on 2017/2/18.
 */
public class RegexTest extends BaseTest {
    @Test
    public void test() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("E:\\IdeaProjects\\PKUYouthWechatAPP\\article.html")));
        StringBuilder articleBuilder = new StringBuilder();
        String temp = null;
        while ((temp = bufferedReader.readLine()) != null) {
            articleBuilder.append(temp);
            // articleBuilder.append("\n");
        }
        String article = articleBuilder.toString();
        article = article.replaceAll("section", "p");
        Document doc = Jsoup.parse(article);
        Elements p = doc.select("p");
        StringBuilder result = new StringBuilder();
        boolean isWrite = false;
        for (Element e : p) {
            Elements children = e.children();
            for(Element img:children){
                if(img.is("img") && isWrite)
                    // System.out.println(img);
                    result.append("<p>"+img+"</p>");
            }
            if (haveSubSection(children)) {
                continue;
            }
            String text = e.text();
            if(StringUtils.isEmpty(text)||StringUtils.isAllBlank(text)){
                continue;
            }
            text = "<p>"+text+"</p>";
            // System.out.println(text);
            if(text.contains("本报记者")){
                isWrite = true;
            }
            if(text.contains("微信编辑")){
                isWrite = false;
            }
            if(isWrite) {
                result.append(text);
            }
        }

        System.out.println(result);
    }


    private static boolean haveSubSection(Elements children) {
        for (Element e : children) {
            if (e.is("p")) {
                return true;
            }
        }
        return false;
    }
}
